package com.schoolmanagement.service.user;

import com.schoolmanagement.entity.concretes.user.ViceDean;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.mappers.ViceDeanMapper;
import com.schoolmanagement.payload.messages.ErrorMessages;
import com.schoolmanagement.payload.messages.SuccessMessages;
import com.schoolmanagement.payload.request.ViceDeanRequest;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.ViceDeanResponse;
import com.schoolmanagement.repository.user.ViceDeanRepository;
import com.schoolmanagement.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ViceDeanService {

    private final ViceDeanRepository viceDeanRepository;
    private final UniquePropertyValidator uniquePropertyValidator;
    private final ViceDeanMapper viceDeanMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    // Not :  Save() *************************************************************************
    public ResponseMessage<ViceDeanResponse> saveViceDean(ViceDeanRequest viceDeanRequest) {
        // !!! Dublicate
        uniquePropertyValidator.checkDuplicate(viceDeanRequest.getUsername(), viceDeanRequest.getSsn(), viceDeanRequest.getPhoneNumber());
        // !!! DTO --> POJO
        ViceDean viceDean = viceDeanMapper.mapViceDeanRequestToViceDean(viceDeanRequest);
        // !!! role ve password.encode
        viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        viceDean.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));

        ViceDean savedViceDean = viceDeanRepository.save(viceDean);

        return ResponseMessage.<ViceDeanResponse>builder()
                .message(SuccessMessages.VICE_DEAN_SAVE)
                .httpStatus(HttpStatus.CREATED)
                .object(viceDeanMapper.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();
    }

    // Not :  UpdateById() ********************************************************************
    public ResponseMessage<ViceDeanResponse> updateViceDean(ViceDeanRequest viceDeanRequest, Long viceDeanId) {
        // !!! var mi yok mu kontrolu
        Optional<ViceDean> viceDean = isViceDeanExist(viceDeanId);

        // !!! unique mi ??
        uniquePropertyValidator.checkUniqueProperties(viceDean.get(), viceDeanRequest);
        // !!! DTO --> POJO
        ViceDean updatedViceDean = viceDeanMapper.mapViceDeanRequestToUpdatedViceDean(viceDeanRequest, viceDeanId);
        // !!! Password.encode
        updatedViceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));

        ViceDean savedViceDean = viceDeanRepository.save(updatedViceDean);

        return ResponseMessage.<ViceDeanResponse>builder()
                .message(SuccessMessages.VICE_DEAN_UPDATE)
                .httpStatus(HttpStatus.OK)
                .object(viceDeanMapper.mapViceDeanToViceDeanResponse(savedViceDean))
                .build();


    }

    private Optional<ViceDean> isViceDeanExist(Long viceDeanId){
        Optional<ViceDean> viceDean = viceDeanRepository.findById(viceDeanId);
        if(! viceDeanRepository.findById(viceDeanId).isPresent()) {
            throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, viceDeanId));
        } else {
            return viceDean;
        }
    }
}