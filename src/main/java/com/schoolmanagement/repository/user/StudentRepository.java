package com.schoolmanagement.repository.user;

import com.schoolmanagement.entity.concretes.user.Student;
import com.schoolmanagement.payload.response.StudentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    boolean existsByUsername(String username);

    boolean existsBySsn(String ssn);

    boolean existsByPhoneNumber(String phone);

    boolean existsByEmail(String email);

    Student findByUsernameEquals(String username);

    @Query(value = "SELECT (count (s)>0) FROM Student s")
    boolean findStudent();

    @Query(value = "SELECT MAX (s.studentNumber) FROM Student s")
    int getMaxStudentNumber();

    List<Student> getStudentByNameContaining(String studentName);

}
