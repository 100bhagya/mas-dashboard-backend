package com.mas.dashboard.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;

@Entity
@Table(name = "students_data")
public class StudentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rollNumber;

    private  String studentName;

    private  Integer totalMarks;
    private  Integer rank;
    private  Double percentile;

    private Date testDate;

    private String testName;
    private String helper;
    private String recentTestName;

    private Double percentileFinal;
    private Integer marksFinal;
    @Email
    private String Email;

    public StudentData(Long id, String rollNumber, String studentName, Integer totalMarks, Integer rank, Double percentile, Date testDate, String testName, String helper, String recentTestName, Double percentileFinal, Integer marksFinal, String email) {
        this.id = id;
        this.rollNumber = rollNumber;
        this.studentName = studentName;
        this.totalMarks = totalMarks;
        this.rank = rank;
        this.percentile = percentile;
        this.testDate = testDate;
        this.testName = testName;
        this.helper = helper;
        this.recentTestName = recentTestName;
        this.percentileFinal = percentileFinal;
        this.marksFinal = marksFinal;
        Email = email;
    }

    public StudentData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
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
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Double getPercentile() {
        return percentile;
    }

    public void setPercentile(Double percentile) {
        this.percentile = percentile;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getHelper() {
        return helper;
    }

    public void setHelper(String helper) {
        this.helper = helper;
    }

    public String getRecentTestName() {
        return recentTestName;
    }

    public void setRecentTestName(String recentTestName) {
        this.recentTestName = recentTestName;
    }

    public Double getPercentileFinal() {
        return percentileFinal;
    }

    public void setPercentileFinal(Double percentileFinal) {
        this.percentileFinal = percentileFinal;
    }

    public Integer getMarksFinal() {
        return marksFinal;
    }

    public void setMarksFinal(Integer marksFinal) {
        this.marksFinal = marksFinal;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
