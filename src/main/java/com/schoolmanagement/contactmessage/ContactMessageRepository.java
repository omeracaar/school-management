package com.schoolmanagement.contactmessage;

import com.schoolmanagement.contactmessage.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {


    boolean existsByEmailEqualsAndDateEquals(String email, LocalDate now);

}

