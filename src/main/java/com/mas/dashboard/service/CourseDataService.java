package com.mas.dashboard.service;

import com.mas.dashboard.Helper.CourseDataHelper;
import com.mas.dashboard.Helper.StudentsDataHelper;
import com.mas.dashboard.entity.CourseData;
import com.mas.dashboard.entity.StudentData;
import com.mas.dashboard.repository.AppUserRepository;
import com.mas.dashboard.repository.CourseDataRepository;
import com.mas.dashboard.repository.StudentsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class CourseDataService {
    @Autowired
    private CourseDataRepository courseRepo;
    @Autowired
    private AppUserRepository appUserRepository;

    public void  save (MultipartFile file){
        try {
            List<CourseData> courseData = CourseDataHelper.convertExcelToListOfCourseData(file.getInputStream());
            this.courseRepo.saveAll(courseData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<CourseData> getAllCourseData(String userRollNo){
        return this.courseRepo.findAll().stream()
                .filter(test -> test.getRollNumber().equals(userRollNo))
                .collect(Collectors.toList());
    }
}
