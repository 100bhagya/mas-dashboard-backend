package com.mas.dashboard.repository;

import com.mas.dashboard.entity.CourseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseDataRepository extends JpaRepository<CourseData,Long> {
}
