package com.mas.dashboard.repository;

import com.mas.dashboard.entity.NonTechArticleResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface NonTechArticleResponseRepository  extends JpaRepository<NonTechArticleResponse, Long> {
    Optional<NonTechArticleResponse> findByStudentIdAndNonTechArticleId (final Long studentId, final Long nonTechArticleId);

    List<NonTechArticleResponse> findByStudentIdAndCompleted(final Long studentId, Boolean completed);


}
