package com.messwaste;

import jakarta.persistence.*;
import java.time.LocalDate;

// One row = one student's answer for one meal on one day
@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentId;   // roll number
    private String meal;        // BREAKFAST / LUNCH / DINNER
    private boolean eating;     // true = coming, false = not coming
    private LocalDate voteDate;

    public Vote() {}

    public Long getId() { return id; }
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }
    public String getMeal() { return meal; }
    public void setMeal(String meal) { this.meal = meal; }
    public boolean isEating() { return eating; }
    public void setEating(boolean eating) { this.eating = eating; }
    public LocalDate getVoteDate() { return voteDate; }
    public void setVoteDate(LocalDate voteDate) { this.voteDate = voteDate; }
}
