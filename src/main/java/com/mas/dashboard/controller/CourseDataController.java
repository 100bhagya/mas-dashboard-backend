package com.mas.dashboard.controller;

import com.mas.dashboard.Helper.CourseDataHelper;

import com.mas.dashboard.entity.AppUser;
import com.mas.dashboard.entity.CourseData;

import com.mas.dashboard.payload.response.MessageResponse;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.security.services.AppUserDetailsImpl;
import com.mas.dashboard.service.CourseDataService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/course")
public class CourseDataController {

    @Autowired
    private CourseDataHelper courseDataHelper;

    @Autowired
    private CourseDataService courseDataService;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/data/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if(courseDataHelper.checkExcelFormat(file)){
            this.courseDataService.save(file);

            return ResponseEntity.ok(new MessageResponse("The file is Uploaded and data is saved in the db successfully !"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");

    }

    @GetMapping("/data")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<List<CourseData>> getAllStudentData() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AppUserDetailsImpl loggedInUser = (AppUserDetailsImpl)auth.getPrincipal();

        final Optional<AppUser> optionalAppUser = this.appUserRepository.findByEmail(loggedInUser.getEmail());
        if(!optionalAppUser.isPresent()){
            throw new UsernameNotFoundException("No user found");
        }

        final AppUser user =  optionalAppUser.get();
        return ResponseEntity.ok(this.courseDataService.getAllCourseData(user.getRollNo()));
    }

}

