package com.alura.forohub.repository;

import com.alura.forohub.domain.Topic;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByOrderByCreatedAtDesc(Limit limit);

    List<Topic> findByCourseContaining(String courseName);

    @Query(value="SELECT * FROM topics WHERE YEAR(created_at)=?1",
            nativeQuery = true
    )
    List<Topic> findByCreatedAtYear(Integer year);
}
