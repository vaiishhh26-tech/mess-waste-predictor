package com.messwaste;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findByVoteDateAndMeal(LocalDate date, String meal);
    Optional<Vote> findByStudentIdAndVoteDateAndMeal(String studentId, LocalDate date, String meal);
}
