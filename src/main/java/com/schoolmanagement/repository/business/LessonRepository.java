package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
