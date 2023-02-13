package com.mas.dashboard.controller;

import com.mas.dashboard.Helper.StudentsDataHelper;
import com.mas.dashboard.entity.StudentData;
import com.mas.dashboard.payload.response.MessageResponse;
import com.mas.dashboard.service.StudentDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/student")
public class StudentDataController {

    @Autowired
    private StudentDataService studentService;

    @PostMapping("/data/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?>upload(@RequestParam("file") MultipartFile file) {
        if(StudentsDataHelper.checkExcelFormat(file)){
            this.studentService.save(file);

            return ResponseEntity.ok(new MessageResponse("The file is Uploaded and data is saved in the db successfully !"));

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");

    }

    @GetMapping("/data")
    public List<StudentData> getAllStudentData() {
        return this.studentService.getAllStudentsData();
    }

}
