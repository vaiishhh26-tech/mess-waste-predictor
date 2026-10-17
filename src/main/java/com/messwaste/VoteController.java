package com.messwaste;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
public class VoteController {

    private final VoteRepository repo;

    @Value("${mess.total-students}")
    private int totalStudents;

    @Value("${mess.buffer-percent}")
    private int bufferPercent;

    public VoteController(VoteRepository repo) {
        this.repo = repo;
    }

    // 1) Student submits "eating" or "not eating"
    @PostMapping("/vote")
    public ResponseEntity<Map<String, String>> vote(@RequestBody Vote incoming) {
        if (incoming.getStudentId() == null || incoming.getStudentId().isBlank()
                || incoming.getMeal() == null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Enter your roll number and choose a meal."));
        }
        LocalDate today = LocalDate.now();
        String meal = incoming.getMeal().toUpperCase();
        String id = incoming.getStudentId().trim().toUpperCase();

        // If the student already voted for this meal today, update it (no double counting)
        Vote vote = repo.findByStudentIdAndVoteDateAndMeal(id, today, meal)
                .orElseGet(Vote::new);
        vote.setStudentId(id);
        vote.setMeal(meal);
        vote.setEating(incoming.isEating());
        vote.setVoteDate(today);
        repo.save(vote);

        return ResponseEntity.ok(Map.of("message", "Saved! Your " + meal.toLowerCase()
                + " choice is recorded."));
    }

    // 2) Mess staff dashboard: counts + prediction for every meal
    @GetMapping("/summary")
    public List<Map<String, Object>> summary() {
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> result = new ArrayList<>();

        for (String meal : List.of("BREAKFAST", "LUNCH", "DINNER")) {
            List<Vote> votes = repo.findByVoteDateAndMeal(today, meal);
            long eating = votes.stream().filter(Vote::isEating).count();
            long notEating = votes.size() - eating;

            // Prediction logic:
            // students who said "eating" + 5% buffer for walk-ins
            int predicted = (int) Math.ceil(eating * (1 + bufferPercent / 100.0));
            // Food saved compared to cooking for everyone
            int saved = Math.max(0, totalStudents - predicted);

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("meal", meal);
            row.put("responses", votes.size());
            row.put("eating", eating);
            row.put("notEating", notEating);
            row.put("predictedPlates", predicted);
            row.put("platesSaved", saved);
            row.put("totalStudents", totalStudents);
            result.add(row);
        }
        return result;
    }
}
