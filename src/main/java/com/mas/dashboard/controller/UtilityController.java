package com.mas.dashboard.controller;

import com.mas.dashboard.payload.request.SignupRequest;
import com.mas.dashboard.payload.request.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.ERole;
import com.mas.dashboard.entity.Role;
import com.mas.dashboard.payload.request.LoginRequest;
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
import java.util.Date;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UtilityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepository appUserRepository;

    @PutMapping("/updateProfile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public AppUser updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetailsImpl loggedInUser = (AppUserDetailsImpl)auth.getPrincipal();

        final Optional<AppUser> optionalAppUser = this.appUserRepository.findByEmail(loggedInUser.getEmail());
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

        return this.appUserRepository.save(user);
    }

}
