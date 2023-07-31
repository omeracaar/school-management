package com.schoolmanagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Access;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class LessonResponse {

    private Long lessonId;
    private String lessonName;
    private Integer creditScore;
    private Boolean isCompulsory;
}
