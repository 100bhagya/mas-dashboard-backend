package com.mas.dashboard.controller;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.ERole;
import com.mas.dashboard.entity.Role;
import com.mas.dashboard.payload.request.ForgotPasswordRequest;
import com.mas.dashboard.payload.request.LoginRequest;
import com.mas.dashboard.payload.request.ResetPasswordRequest;
import com.mas.dashboard.payload.request.SignupRequest;
import com.mas.dashboard.payload.response.JwtResponse;
import com.mas.dashboard.payload.response.MessageResponse;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.repository.RoleRepository;
import com.mas.dashboard.security.jwt.JwtUtils;
import com.mas.dashboard.security.services.AppUserDetailsImpl;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    //login endpoint for signing up a user
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AppUserDetailsImpl appUserDetails = (AppUserDetailsImpl) authentication.getPrincipal();
        List<String> roles = appUserDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                appUserDetails.getId(),
                appUserDetails.getUsername(),
                appUserDetails.getEmail(),
                roles));
    }


    //signup endpoint for signing up a user
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        //check if the email already exists in the database
        if (appUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        AppUser user = new AppUser(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                Boolean.FALSE);



        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        final List<String> allowedEmails = new ArrayList<>();

        allowedEmails.add("user1@mas.com");
        allowedEmails.add("user2@mas.com");
        allowedEmails.add("user3@mas.com");
        allowedEmails.add("user4@mas.com");
        allowedEmails.add("user5@mas.com");
        allowedEmails.add("user6@mas.com");
        allowedEmails.add("user7@mas.com");
        allowedEmails.add("saubhagya.gaurav.che16@itbhu.ac.in");
        allowedEmails.add("sahupawan9749568594@gmail.com");
        allowedEmails.add("tusharnath10@gmail.com");

        if (strRoles == null) {
            throw new RuntimeException("Error: Role is not found.");
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    case "user":
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        if(allowedEmails.contains(signUpRequest.getEmail())) {
                            roles.add(userRole);
                        } else {
                            throw new RuntimeException("Email not found in list!");
                        }
                        break;
                    default :
                        throw new RuntimeException("Invalid Role!");
                }
            });
        }

        user.setRoles(roles);
        user.setCreatedDate(new Date());
        user.setUpdatedDate(new Date());
        user.setCreatedBy(-1L);
        user.setUpdatedBy(-1L);


        //save the user
        appUserRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<?> processForgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        String token = UUID.randomUUID().toString();

        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.setPasswordResetToken(token);
            appUserRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Could not find any user with that email " + email);
        }

        return ResponseEntity.ok(new MessageResponse("http://localhost:8080/api/auth/reset_password?token=" + token + "  link sent to mail = " + email));
    }

    @GetMapping("/reset_password")
    public AppUser showResetPasswordForm(@RequestParam final String token) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByPasswordResetToken(token);

        if (! optionalAppUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid Token");
        }

        return optionalAppUser.get();
    }

    @PostMapping("/reset_password")
    public String processResetPassword(@Valid @RequestBody ResetPasswordRequest resetPasswordRequest) {
        String token = resetPasswordRequest.getToken();
        String password = resetPasswordRequest.getPassword();
        Optional<AppUser> optionalAppUser = appUserRepository.findByPasswordResetToken(token);
        if (! optionalAppUser.isPresent()) {
            throw new UsernameNotFoundException("Invalid Token");
        } else {
            AppUser user = optionalAppUser.get();

            String encodedPassword = encoder.encode(password);
            user.setPassword(encodedPassword);
            user.setPasswordResetToken(null);
            appUserRepository.save(user);
            return("You have successfully changed your password.");
        }
    }

}
