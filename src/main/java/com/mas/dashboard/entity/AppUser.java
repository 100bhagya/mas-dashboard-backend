package com.mas.dashboard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "app_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class AppUser {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String firstName;

  @NotBlank
  private String lastName;

  @NotBlank
  @Size(max = 20)
  private String username;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 120)
  private String password;

  private String profilePic;

  private String phoneNo;

  private String address;

  private String postalCode;

  private String state;

  private String city;

  private String passwordResetToken;

  private Boolean deleted;

  private String courseId;

  private int year;

  private String rollNo;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  @CreatedDate
  private Date createdDate;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  @LastModifiedDate
  private Date updatedDate;

  @CreatedBy
  private Long createdBy;

  @LastModifiedBy
  private Long updatedBy;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(	name = "user_roles",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public AppUser() {
  }

  public AppUser(String firstName, String lastName, String username, String email, String password, Boolean deleted) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.deleted = deleted;
    this.courseId = "MAS101";
    this.year = 2023;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() { return lastName; }

  public void setLastName(String lastName) { this.lastName = lastName; }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public String getProfilePic() {return profilePic;}

  public void setProfilePic(String profilePic) { this.profilePic = profilePic;}

  public String getPhoneNo() {return phoneNo;}

  public void setPhoneNo(String phoneNo) {this.phoneNo = phoneNo;}

  public String getAddress() {return address;}

  public void setAddress(String address){this.address = address;}

  public String getPostalCode() {return postalCode;}

  public void setPostalCode(String postalCode) {this.postalCode = postalCode;}

  public String getState() {return state;}

  public void setState(String state){this.state = state;}

  public String getCity() {return city;}

  public void setCity(String city){this.city = city;}

  public String getPasswordResetToken() {return passwordResetToken;}

  public void setPasswordResetToken(String passwordResetToken){this.passwordResetToken = passwordResetToken;}

  public Boolean getDeleted() { return deleted; }

  public void setDeleted(Boolean deleted) {this.deleted = deleted; }

  public void setCreatedDate(Date createdDate) {this.createdDate = createdDate; }

  public void setUpdatedDate(Date updatedDate) {this.updatedDate = updatedDate; }

  public void setCreatedBy(Long createdBy) {this.createdBy = createdBy; }

  public void setUpdatedBy(Long updatedBy) {this.updatedBy = updatedBy; }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setRollNo(String rollNo) {
    this.rollNo = rollNo;
  }

  public String getRollNo() {
    return rollNo;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getYear() {
    return year;
  }

}