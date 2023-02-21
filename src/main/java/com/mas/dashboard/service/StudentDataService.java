package com.mas.dashboard.service;

import com.mas.dashboard.Helper.StudentsDataHelper;
import com.mas.dashboard.entity.StudentData;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.repository.StudentsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentDataService {
    @Autowired
    private StudentsDataRepository studentRepo;
    @Autowired
    private AppUserRepository appUserRepository;

    public void  save (MultipartFile file){
        try {
            List<StudentData> studentData = StudentsDataHelper.convertExcelToListOfStudentsData(file.getInputStream());
            this.studentRepo.saveAll(studentData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<StudentData> getAllStudentsData(String userRollNo){
        return this.studentRepo.findAll().stream()
                .filter(test -> test.getRollNumber().equals(userRollNo))
                .collect(Collectors.toList());
    }
}
