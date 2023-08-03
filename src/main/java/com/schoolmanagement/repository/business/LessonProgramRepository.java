package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonProgramRepository  extends JpaRepository<LessonProgram, Long> {
    List<LessonProgram> findByTeachers_IdNull();

    List<LessonProgram> findByTeachers_IdNotNull();
}