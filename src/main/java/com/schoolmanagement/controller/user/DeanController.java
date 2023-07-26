package com.schoolmanagement.controller.user;

import com.schoolmanagement.payload.request.DeanRequest;
import com.schoolmanagement.payload.response.DeanResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.user.DeanService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/dean")
@RequiredArgsConstructor
public class DeanController {

    private final DeanService deanService;

    // Not: save() **************************************************************8
    @PostMapping("/save") // http://localhost:8080/dean/save  + POST
    public ResponseMessage<DeanResponse> save(@RequestBody @Valid DeanRequest deanRequest){

        return deanService.save(deanRequest);
    }
}