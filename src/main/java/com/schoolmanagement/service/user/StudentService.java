package com.schoolmanagement.service.user;

import com.schoolmanagement.entity.concretes.user.AdvisoryTeacher;
import com.schoolmanagement.entity.concretes.user.Student;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.StudentMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.StudentRequest;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.StudentResponse;
import com.schoolmanagement.repository.user.StudentRepository;
import com.schoolmanagement.service.business.AdvisoryTeacherService;
import com.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final AdvisoryTeacherService advisoryTeacherService;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    // Not :  Save() **********************************************************
    public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {

        // !!! AdvisorTeacher id kontrol
        AdvisoryTeacher advisoryTeacher =
                advisoryTeacherService.getAdvisoryTeacherById(studentRequest.getAdvisorTeacherId());

        // !!! duplicate kontrolu
        uniquePropertyValidator.checkDuplicate(studentRequest.getUsername(), studentRequest.getSsn(),
                studentRequest.getPhoneNumber(), studentRequest.getEmail());

        // !!! DTO --> POJO
        Student student = studentMapper.mapStudentRequestToStudent(studentRequest);

        student.setAdvisoryTeacher(advisoryTeacher);
        student.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        student.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        student.setActive(true);
        // !!! Ogrnci numarasi setleniyor :
        student.setStudentNumber(getLastNumber());

        return ResponseMessage.<StudentResponse>builder()
                .object(studentMapper.mapStudentToStudentResponse(studentRepository.save(student)))
                .message(SuccessMessages.STUDENT_SAVE)
                .build();
    }

    private int getLastNumber() {
        // !!! DB hic ogrenci yoksa
        if (!studentRepository.findStudent()) {
            return 1000;
        }

        return studentRepository.getMaxStudentNumber() + 1;
    }

    // Not: ChangeActiveStatus() ***********************************************
    public ResponseMessage changeStatus(Long id, boolean status) {
        Student student = isStudentExist(id);
        student.setActive(status);
        studentRepository.save(student);

        return ResponseMessage.builder()
                .message("Student is " + (status ? "active" : "passive"))
                .httpStatus(HttpStatus.OK)
                .build();

    }

    public Student isStudentExist(Long studentId) {

        return studentRepository.findById(studentId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, studentId)));
    }

    //Not: getAll() ****************************************************************
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(studentMapper::mapStudentToStudentResponse)
                .collect(Collectors.toList());
    }

    //Not: Update() ****************************************************************
    public ResponseMessage<StudentResponse> updateStudent(Long studentId, StudentRequest studentRequest) {
        Student student = isStudentExist(studentId);
        AdvisoryTeacher advisoryTeacher = advisoryTeacherService.getAdvisoryTeacherById(studentRequest.getAdvisorTeacherId());
        uniquePropertyValidator.checkUniqueProperties(student, studentRequest);

        Student studentForUpdate = studentMapper.mapStudentRequestToUpdatedStudent(studentRequest, studentId);

        studentForUpdate.setPassword(passwordEncoder.encode(studentRequest.getPassword()));
        studentForUpdate.setAdvisoryTeacher(advisoryTeacher);
        studentForUpdate.setStudentNumber(student.getStudentNumber());
        studentForUpdate.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        studentForUpdate.setActive(true);

        studentRepository.save(studentForUpdate);

        return ResponseMessage.<StudentResponse>builder()
                .object(studentMapper.mapStudentToStudentResponse(studentRepository.save(studentForUpdate)))
                .message(SuccessMessages.STUDENT_UPDATE)
                .httpStatus(HttpStatus.OK)
                .build();

    }


    public ResponseMessage deleteStudentById(Long studentId) {
        isStudentExist(studentId);
        studentRepository.deleteById(studentId);

        return ResponseMessage.builder()
                .message(SuccessMessages.STUDENT_DELETE)
                .httpStatus(HttpStatus.OK)
                .build();
    }
    // Not : getByName() *********************************************************
    public List<StudentResponse> findStudentByName(String studentName) {

        return studentRepository.getStudentByNameContaining(studentName)
                .stream()
                .map(studentMapper::mapStudentToStudentResponse)
                .collect(Collectors.toList());
    }
}
