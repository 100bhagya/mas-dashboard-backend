package com.mas.dashboard.repository;

import com.mas.dashboard.entity.TaskRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRatingRepository extends JpaRepository<TaskRating, Long> {

  Optional<TaskRating> findByStudentIdAndCategoryAndChapterAndDeletedFalse(final Long studentId,
                                                                         final String category,
                                                                         final String chapter);

  List<TaskRating> findAllByStudentIdAndCategory (final Long studentId, final String category);
}
