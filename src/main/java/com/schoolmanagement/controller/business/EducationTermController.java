package com.schoolmanagement.controller.business;

import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.business.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;


    // Not: Save() ******************************************************************
    @PostMapping("/save") // http://localhost:8080/educationTerm/save  + POST
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<EducationTermResponse> saveEducationTerm(@RequestBody @Valid EducationTermRequest educationTermRequest){

        return educationTermService.saveEducationTerm(educationTermRequest);
    }
}
