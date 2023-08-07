package com.schoolmanagement.service.validator;

import com.schoolmanagement.entity.concretes.business.LessonProgram;
import com.schoolmanagement.exception.BadRequestException;
import com.schoolmanagement.payload.messages.ErrorMessages;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class DateTimeValidator {

    private boolean checkTime(LocalTime start, LocalTime stop) {
        return start.isAfter(stop) || start.equals(stop);
    }

    public void checkTimeWithException(LocalTime start, LocalTime stop) {
        if(checkTime(start, stop)) {
            throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
        }
    }

    public void checkLessonPrograms(Set<LessonProgram> existLessonProgram, Set<LessonProgram> lessonProgramRequest){

        if(existLessonProgram.isEmpty() && lessonProgramRequest.size()>1){
            checkDuplicateLessonPrograms(lessonProgramRequest);
        } else {
            checkDuplicateLessonPrograms(lessonProgramRequest);
            checkDuplicateLessonPrograms(existLessonProgram,lessonProgramRequest);
        }

    }

    private void checkDuplicateLessonPrograms(Set<LessonProgram> lessonPrograms) {
        Set<String> uniqueLessonProgramKeys = new HashSet<>();

        for (LessonProgram lessonProgram : lessonPrograms) {
            String lessonProgramKey =  lessonProgram.getDay().name() + lessonProgram.getStartTime();
            if(uniqueLessonProgramKeys.contains(lessonProgramKey)){
                throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_ALREADY_EXIST);
            }
            uniqueLessonProgramKeys.add(lessonProgramKey);

        }
    }
    private void checkDuplicateLessonPrograms(Set<LessonProgram> existLessonProgram, Set<LessonProgram> lessonProgramRequest) {

        for (LessonProgram requestLessonprogram : lessonProgramRequest) {
            if(existLessonProgram.stream().
                    anyMatch(lessonProgram -> lessonProgram.getStartTime().equals(requestLessonprogram.getStartTime())
                            				&& lessonProgram.getDay().name().equals(requestLessonprogram.getDay().name()))){
                throw new BadRequestException(ErrorMessages.LESSON_PROGRAM_ALREADY_EXIST);
            }
        }
    }
}