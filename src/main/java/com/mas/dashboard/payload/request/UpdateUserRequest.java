package com.mas.dashboard.payload.request;

import java.util.Set;

import javax.validation.constraints.*;

public class UpdateUserRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    private String profilePic;

    private Integer phoneNo;

    private String address;

    private Integer postalCode;

    private String state;

    private String city;

    private String passwordResetToken;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePic() {return profilePic;}

    public void setProfilePic(String profilePic) { this.profilePic = profilePic;}

    public Integer getPhoneNo() {return phoneNo;}

    public void setPhoneNo(Integer phoneNo) {this.phoneNo = phoneNo;}

    public String getAddress() {return address;}

    public void setAddress(String address){this.address = address;}

    public Integer getPostalCode() {return postalCode;}

    public void setPostalCode(Integer postalCode) {this.postalCode = postalCode;}

    public String getState() {return state;}

    public void setState(String state){this.state = state;}

    public String getCity() {return city;}

    public void setCity(String city){this.city = city;}

    public String getPasswordResetToken() {return passwordResetToken;}

    public void setPasswordResetToken(String passwordResetToken){this.passwordResetToken = passwordResetToken;}


}
