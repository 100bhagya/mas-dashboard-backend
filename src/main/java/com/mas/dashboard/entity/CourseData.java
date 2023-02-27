package com.mas.dashboard.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "course_data")
public class CourseData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;

    private  String studentName;

    @Email
    private  String Email;

    private  Integer timeSpentMins;

    private Double timeSpentHours;

    private Integer progress;

    private String rollNumber;

    public CourseData(Long id, String courseName, String studentName, String email, Integer timeSpentMins, Double timeSpentHours, Integer progress, String rollNumber) {
        this.id = id;
        this.courseName = courseName;
        this.studentName = studentName;
        Email = email;
        this.timeSpentMins = timeSpentMins;
        this.timeSpentHours = timeSpentHours;
        this.progress = progress;
        this.rollNumber = rollNumber;
    }

    public CourseData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Integer getTimeSpentMins() {
        return timeSpentMins;
    }

    public void setTimeSpentMins(Integer timeSpentMins) {
        this.timeSpentMins = timeSpentMins;
    }

    public Double getTimeSpentHours() {
        return timeSpentHours;
    }

    public void setTimeSpentHours(Double timeSpentHours) {
        this.timeSpentHours = timeSpentHours;
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }
}
