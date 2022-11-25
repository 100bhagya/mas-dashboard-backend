package com.mas.dashboard.controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.mas.dashboard.payload.request.*;
import liquibase.util.csv.opencsv.CSVReader;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.ERole;
import com.mas.dashboard.entity.Role;
import com.mas.dashboard.payload.request.SignupRequest;
import com.mas.dashboard.payload.response.JwtResponse;
import com.mas.dashboard.payload.response.MessageResponse;
import com.mas.dashboard.repository.RoleRepository;
import com.mas.dashboard.security.jwt.JwtUtils;
import com.mas.dashboard.security.services.AppUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UtilityController {



    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepository appUserRepository;

    @GetMapping("/getUserProfile")

    public Map<String, Object> getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetailsImpl obj = (AppUserDetailsImpl)auth.getPrincipal();

        Optional<AppUser> optionalUser = appUserRepository.findByEmail(obj.getEmail());
        if(!optionalUser.isPresent()){
            throw new UsernameNotFoundException("No user found");
        }
        AppUser user = optionalUser.get();
        Map<String, Object> addUser = new HashMap<>();
        addUser.put("firstName",user.getFirstName());
        addUser.put("lastName",user.getLastName());
        addUser.put("username",user.getUsername());
        addUser.put("email", user.getEmail());
        addUser.put("profilePic",user.getProfilePic());
        addUser.put("phoneNo",user.getPhoneNo());
        addUser.put("address",user.getAddress());
        addUser.put("postalCode",user.getPostalCode());
        addUser.put("state",user.getState());
        addUser.put("city",user.getCity());
        return addUser;
    }

    @PutMapping("/updateProfile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetailsImpl loggedInUser = (AppUserDetailsImpl)auth.getPrincipal();

        final Optional<AppUser> optionalAppUser = this.appUserRepository.findByEmail(loggedInUser.getEmail());
        if(!optionalAppUser.isPresent()){
            throw new UsernameNotFoundException("No user found");
        }

        final AppUser user =  optionalAppUser.get();

        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setUsername(updateUserRequest.getUsername());
        user.setProfilePic(updateUserRequest.getProfilePic());
        user.setPhoneNo(updateUserRequest.getPhoneNo());
        user.setAddress(updateUserRequest.getAddress());
        user.setPostalCode(updateUserRequest.getPostalCode());
        user.setState(updateUserRequest.getState());
        user.setCity(updateUserRequest.getCity());
        user.setUpdatedDate(new Date());

        this.appUserRepository.save(user);

        return "Your profile has been updated successfully";
    }
}