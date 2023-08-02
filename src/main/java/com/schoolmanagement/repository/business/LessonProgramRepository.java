package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long> {
}
