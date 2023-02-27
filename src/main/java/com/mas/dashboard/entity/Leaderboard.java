package com.mas.dashboard.entity;

import javax.persistence.*;

@Entity
@Table(name = "leaderboard_data")
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String studentName;

    private  Integer totalMarks;

    private  Integer Rank;

    private String rollNumber;


    public Leaderboard(Long id, String studentName, Integer totalMarks, Integer rank, String rollNumber) {
        this.id = id;
        this.studentName = studentName;
        this.totalMarks = totalMarks;
        Rank = rank;
        this.rollNumber = rollNumber;
    }

    public Leaderboard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Integer getRank() {
        return Rank;
    }

    public void setRank(Integer rank) {
        Rank = rank;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}
