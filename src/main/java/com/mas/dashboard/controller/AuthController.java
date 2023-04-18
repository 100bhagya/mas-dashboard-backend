package com.mas.dashboard.controller;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.ERole;
import com.mas.dashboard.entity.MasterPassword;
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
import com.mas.dashboard.security.services.AppUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import static org.aspectj.bridge.MessageUtil.print;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppUserDetailsServiceImpl userDetailsService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    MasterPassword masterPassword;

    @Autowired private JavaMailSender javaMailSender;

    //login endpoint for signing up a user
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        UserDetails user = userDetailsService.loadUserByUsername(loginRequest.getEmail().toLowerCase());



        if(!encoder.matches(loginRequest.getPassword(), user.getPassword()) && !loginRequest.getPassword().equals(masterPassword.getMasterPassword()))
            throw new RuntimeException("Incorrect Credentials");

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(user, loginRequest.getPassword(), user.getAuthorities());

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
                appUserRepository.getById(appUserDetails.getId()).getRollNo(),
                roles));
    }


    //signup endpoint for signing up a user
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        //check if the email already exists in the database
        if (appUserRepository.existsByEmail(signUpRequest.getEmail().toLowerCase())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }



        // Create new user's account
        AppUser user = new AppUser(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail().toLowerCase(),
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
        allowedEmails.add("user8@mas.com");
        allowedEmails.add("user9@mas.com");
        allowedEmails.add("user10@mas.com");
        allowedEmails.add("saubhagya.gaurav.che16@itbhu.ac.in");
        allowedEmails.add("shubhamaryan1234@gmail.com");
        allowedEmails.add("gauravsaubhagya3@gmail.com");
        allowedEmails.add("admin@myanalyticsschool.com");
        allowedEmails.add("harikeshkrsingh20@gmail.com");
        allowedEmails.add("sajanp.tonge.che16@itbhu.ac.in");
        allowedEmails.add("suyash.ratna.met17@itbhu.ac.in");
        allowedEmails.add("shubham.aryan.che16@itbhu.ac.in");
        allowedEmails.add("saransh.goswami.che16@itbhu.ac.in");
        allowedEmails.add("cjbizz01@gmail.com");
        allowedEmails.add("kumarankit1446@gmail.com");
        allowedEmails.add("kaishabdul7@gmail.com");
        allowedEmails.add("abhaskumarmishra.mst22@itbhu.ac.in");
        allowedEmails.add("abhay.krsingh.mst19@itbhu.ac.in");
        allowedEmails.add("abhinav.kumar.phe19@itbhu.ac.in");
        allowedEmails.add("abhinav.mathaniya.phe19@itbhu.ac.in");
        allowedEmails.add("abhishekarya911@gmail.com");
        allowedEmails.add("chauhan.abhisheks.cd.mec19@itbhu.ac.in");
        allowedEmails.add("abhishek_k@hs.iitr.ac.in");
        allowedEmails.add("abhishekkumar.mst22@itbhu.ac.in");
        allowedEmails.add("abhiojha251198@gmail.com");
        allowedEmails.add("20je0041@me.iitism.ac.in");
        allowedEmails.add("f20190723@pilani.bits-pilani.ac.in");
        allowedEmails.add("agrawaladitya270@gmail.com");
        allowedEmails.add("ashwar1233@gmail.com");
        allowedEmails.add("20je0072@ese.iitism.ac.in");
        allowedEmails.add("oe22m006@smail.iitm.ac.in");
        allowedEmails.add("akashiitian4161@gmail.com");
        allowedEmails.add("singhakashf7@gmail.com");
        allowedEmails.add("akshit7402@gmail.com");
        allowedEmails.add("alagu.oviyakp.bme19@itbhu.ac.in");
        allowedEmails.add("alakhniranjanchaturvedi@gmail.com");
        allowedEmails.add("amitpatel.dhar@gmail.com");
        allowedEmails.add("amritraj2323@gmail.com");
        allowedEmails.add("anand.keshav.bce19@itbhu.ac.in");
        allowedEmails.add("aniket1205singh@gmail.com");
        allowedEmails.add("akahirwar20@iitk.ac.in");
        allowedEmails.add("arkadev12@gmail.com");
        allowedEmails.add("arunkumar9265@gmail.com");
        allowedEmails.add("aryan.mandal.met20@itbhu.ac.in");
        allowedEmails.add("suryawanshiatharva2029@gmail.com");
        allowedEmails.add("banoth.vkumar.chy19@itbhu.ac.in");
        allowedEmails.add("f20190739@hyderabad.bits-pilani.ac.in");
        allowedEmails.add("deepakolic4@gmail.com");
        allowedEmails.add("deepak.d.saini@gmail.com");
        allowedEmails.add("devshadav@gmail.com");
        allowedEmails.add("deveshpandey3103@gmail.com");
        allowedEmails.add("dharavath.ravikumar.phe20@itbhu.ac.in");
        allowedEmails.add("erfahad28@gmail.com");
        allowedEmails.add("gajanan.govindraoi.mst19@itbhu.ac.in");
        allowedEmails.add("ganjisushmareddy0000@gmail.com");
        allowedEmails.add("gargi_2001ee89@iitp.ac.in");
        allowedEmails.add("girdharisansi2002@gmail.com");
        allowedEmails.add("f20202492@hyderabad.bits-pilani.ac.in");
        allowedEmails.add("hari22.iitk@gmail.com");
        allowedEmails.add("himanshukumar84091@gmail.com");
        allowedEmails.add("hmaurya387@gmail.com");
        allowedEmails.add("jaypatidar556@gmail.com");
        allowedEmails.add("20je0452@mme.iitism.ac.in");
        allowedEmails.add("ae18b029@smail.iitm.ac.in");
        allowedEmails.add("jyoti.19je0407@mme.iitism.ac.in");
        allowedEmails.add("k.pruseth@gmail.com");
        allowedEmails.add("kapil.suman.mst19@itbhu.ac.in");
        allowedEmails.add("khatravathrahulrathod@gmail.com");
        allowedEmails.add("andarghiskekomalrajesh.phe22@itbhu.ac.in");
        allowedEmails.add("krishnakumarsharma.mst22@itbhu.ac.in");
        allowedEmails.add("maloth.devilal.met19@itbhu.ac.in");
        allowedEmails.add("manognapallam@gmail.com");
        allowedEmails.add("mansikoshti3003@gmail.com");
        allowedEmails.add("mohdarifkhan822@gmail.com");
        allowedEmails.add("m4mohitgond170@gmail.com");
        allowedEmails.add("moumitadoloi.bme22@itbhu.ac.in");
        allowedEmails.add("nakshatra.patil.mec20@itbhu.ac.in");
        allowedEmails.add("naveenkumar12062002@gmail.com");
        allowedEmails.add("navneetmech22@gmail.com");
        allowedEmails.add("mishraparul1604@gmail.com");
        allowedEmails.add("krprabhasw@gmail.com");
        allowedEmails.add("nandaprateek19@gmail.com");
        allowedEmails.add("2020meb1301@iitrpr.ac.in");
        allowedEmails.add("prativanaik343@gmail.com");
        allowedEmails.add("rahul.nagrajrao.civ20@itbhu.ac.in");
        allowedEmails.add("rajaff.ali.met19@itbhu.ac.in");
        allowedEmails.add("12345ramkadam@gmail.com");
        allowedEmails.add("ram9785224038@gmail.com");
        allowedEmails.add("oe22m016@smail.iitm.ac.in");
        allowedEmails.add("ravi.mehra.bce19@itbhu.ac.in");
        allowedEmails.add("rishabh2do@gmail.com");
        allowedEmails.add("vermarishi377@gmail.com");
        allowedEmails.add("ritesh.dadlani.cd.civ20@itbhu.ac.in");
        allowedEmails.add("roshaniitcor2020@gmail.com");
        allowedEmails.add("sahab_aajaz@eq.iitr.ac.in");
        allowedEmails.add("sahilagrawal1210@gmail.com");
        allowedEmails.add("saravanasarovaram@gmail.com");
        allowedEmails.add("satish.kumar.bce19@itbhu.ac.in");
        allowedEmails.add("satwikn5@gmail.com");
        allowedEmails.add("satyambirpur11@gmail.com");
        allowedEmails.add("22mt0363@iitism.ac.in");
        allowedEmails.add("sharonroy.bme22@itbhu.ac.in");
        allowedEmails.add("shazeblalu@gmail.com");
        allowedEmails.add("shivamsingh66102@gmail.com");
        allowedEmails.add("adsripathi@gmail.com");
        allowedEmails.add("sudhir.hatwal.mec20@itbhu.ac.in");
        allowedEmails.add("sumankhansda@gmail.com");
        allowedEmails.add("swarnendub10@gmail.com");
        allowedEmails.add("sweata.bhadra.bce19@itbhu.ac.in");
        allowedEmails.add("veerarameshgm.mst22@itbhu.ac.in");
        allowedEmails.add("f20201153@hyderabad.bits-pilani.ac.in");
        allowedEmails.add("pranjali.gupta.mst19@itbhu.ac.in");
        allowedEmails.add("ce22m017@smail.iitm.ac.in");
        allowedEmails.add("kaushikkuppili123@gmail.com");
        allowedEmails.add("khushbuverma8511@gmail.com");
        allowedEmails.add("akshaykmr22@iitk.ac.in");
        allowedEmails.add("gurubal.birajdar.bme19@itbhu.ac.in");
        allowedEmails.add("satyamshivam100@gmail.com");
        allowedEmails.add("ramdayalcktd2003@gmail.com");
        allowedEmails.add("gaurav573.gk@gmail.com");
        allowedEmails.add("neimuonkim.lhouvum.met20@itbhu.ac.in");
        allowedEmails.add("jaruplavath.shobha.mec20@itbhu.ac.in");
        allowedEmails.add("bhardwajanushka1202@gmail.com");
        allowedEmails.add("pratikprabhat593@gmail.com");
        allowedEmails.add("dhiraj.mohanpathori.che20@itbhu.ac.in");
        allowedEmails.add("abinash02.94@gmail.com");
        allowedEmails.add("ankathi.pranitha.civ20@itbhu.ac.in");
        allowedEmails.add("vangala.poojasri.civ20@itbhu.ac.in");


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
                        if(allowedEmails.contains(signUpRequest.getEmail().toLowerCase())) {
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
    public String processForgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail().toLowerCase();
        String token = UUID.randomUUID().toString();

        Optional<AppUser> optionalUser = appUserRepository.findByEmail(email);
        String response = "";
        if (optionalUser.isPresent()) {
            AppUser user = optionalUser.get();
            user.setPasswordResetToken(token);
            appUserRepository.save(user);
            response = response + "Hi " + user.getFirstName() + " an email to reset your password has been sent to your email address";
            String resetPasswordLink = "https://dashboard.myanalyticsschool.com/resetPassword/" + token;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("gauravsaubhagya3@gmail.com");
            message.setTo(email);
            message.setSubject("Password reset link: MAS Dashboard");
            message.setText(resetPasswordLink);
            javaMailSender.send(message);
        } else {
//            response = response + "Could not find any user with email " + email;
            throw new UsernameNotFoundException("Could not find any user with that email " + email);
        }

        return response;
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam final String token) {
        String response = "";
        Optional<AppUser> optionalAppUser = appUserRepository.findByPasswordResetToken(token);

        if (! optionalAppUser.isPresent()) {
//            response = response + "Invalid Link to reset password";
            throw new UsernameNotFoundException("Invalid Link to reset password");
        }else {
            AppUser user = optionalAppUser.get();
            response = response + "Hi " + user.getFirstName() + " update your password here!";
        }

        return response;
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
