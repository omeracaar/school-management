package com.schoolmanagement.repository.business;

import com.schoolmanagement.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface LessonProgramRepository  extends JpaRepository<LessonProgram, Long> {
    List<LessonProgram> findByTeachers_IdNull();

    List<LessonProgram> findByTeachers_IdNotNull();

    @Query("SELECT l FROM LessonProgram l INNER JOIN l.teachers teachers WHERE teachers.username = ?1 ")
    Set<LessonProgram> getLessonProgramByTeacherUsername(String userName);

    @Query("SELECT l FROM LessonProgram l INNER JOIN l.students students WHERE students.username = ?1 ")
    Set<LessonProgram> getLessonProgramByStudentsUsername(String userName);

}