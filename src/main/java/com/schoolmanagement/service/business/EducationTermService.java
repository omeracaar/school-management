package com.schoolmanagement.service.business;

import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.business.EducationTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;

    // Not: Save() ******************************************************************
    public ResponseMessage<EducationTermResponse> saveEducationTerm(EducationTermRequest educationTermRequest) {

        validateEducationTermDates(educationTermRequest);
        //educationTermRepository.save()

    }

    private void validateEducationTermDates(EducationTermRequest educationTermRequest){
        // !!! gunler ile ilgili kontrol
        validateEducationTermDatesForUpdate(educationTermRequest);

        // !!! term ile ilgili kontrol
        if(educationTermRepository.existsByTermAndYear(educationTermRequest.getTerm(), educationTermRequest.getStartDate().getYear())) {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR);
        }
    }

    private void validateEducationTermDatesForUpdate(EducationTermRequest educationTermRequest){

        // registrationDate > startDate
        if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())) {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }
        // end > start
        if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())) {
            throw new ResourceNotFoundException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }
    }
}