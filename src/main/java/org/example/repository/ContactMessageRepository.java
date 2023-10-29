package org.example.repository;

import org.example.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
    List<ContactMessage> findByEmail(String email);

    List<ContactMessage> findByMessageTime(LocalDate messageTime);


    List<ContactMessage> findByTimeZone(LocalTime timeZone);

    List<ContactMessage> findBySubjectContaining(String subject);

    void deleteById(Long id);
}
