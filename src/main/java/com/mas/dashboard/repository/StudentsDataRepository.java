package com.mas.dashboard.repository;

import com.mas.dashboard.entity.StudentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentsDataRepository extends JpaRepository<StudentData,Long> {
}
