package com.schoolmanagement.contactmessage;

import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.request.ContactMessageRequest;
import com.schoolmanagement.payload.response.ContactMessageResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;


    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

        // 1 kullanici 1 gunde sadece 1 mesaj atabilsin
        boolean isSameMessageWithSameEmailForToday =
                contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());
        if(isSameMessageWithSameEmailForToday){

            throw new ConflictException(ErrorMessages.ALREADY_SEND_A_MESSAGE_TODAY);
        }

        // !!! DTO --> POJO
        ContactMessage contactMessage = createContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .status(HttpStatus.CREATED) //httpstatus olmuyordu statusa cevirdim hata olabilir
                .object(createResponse(savedData))
                .build();


    }

    // mapContactMessageRequestToContactMessage
    private ContactMessage createContactMessage(ContactMessageRequest contactMessageRequest){

        return ContactMessage.builder()
                .name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .email(contactMessageRequest.getEmail())
                .date(LocalDate.now())
                .build();

    }
    private ContactMessageResponse createResponse(ContactMessage contactMessage){

        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .subject(contactMessage.getSubject())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .date(LocalDate.now())
                .build();
    }
}