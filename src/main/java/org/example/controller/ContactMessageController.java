package org.example.controller;

import org.example.model.ContactMessage;
import org.example.repository.ContactMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/contact-messages")
@Validated
public class ContactMessageController {
    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @PostMapping
    public ResponseEntity<List<ContactMessage>> createContactMessages(
            @Valid @RequestBody List<ContactMessage> contactMessages
    ) {
        List<ContactMessage> savedContactMessages = contactMessageRepository.saveAll(contactMessages);

        return ResponseEntity.ok(savedContactMessages);
    }

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAllContactMessages() {
        List<ContactMessage> contactMessages = contactMessageRepository.findAll();
        return ResponseEntity.ok(contactMessages);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ContactMessage>> getMessagesByPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<ContactMessage> messagesPage = contactMessageRepository.findAll(pageRequest);

        return ResponseEntity.ok(messagesPage);
    }

    @GetMapping("/by-email")
    public ResponseEntity<List<ContactMessage>> getMessagesByEmail(
            @RequestParam(name = "email") @Valid @NotNull @Email String email
    ) {
        List<ContactMessage> messagesByEmail = contactMessageRepository.findByEmail(email);

        if (messagesByEmail.isEmpty()) {
            // Handle the case when no messages are found for the given email
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messagesByEmail);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldError().getDefaultMessage();
        return ResponseEntity.badRequest().body(errorMessage);
    }


    @GetMapping("/by-time")
    public ResponseEntity<List<ContactMessage>> getMessagesByTime(
            @RequestParam(name = "messageTime") @Valid LocalDate messageTime
    ) {
        List<ContactMessage> messagesByTime = contactMessageRepository.findByMessageTime(messageTime);

        if (messagesByTime.isEmpty()) {
            // Handle the case when no messages are found for the given time
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messagesByTime);
    }

    @GetMapping("/by-time-zone")
    public ResponseEntity<List<ContactMessage>> getMessagesByTimeZone(
            @RequestParam(name = "timeZone") @Valid LocalTime timeZone
    ) {
        List<ContactMessage> messagesByTimeZone = contactMessageRepository.findByTimeZone(timeZone);

        if (messagesByTimeZone.isEmpty()) {
            // Handle the case when no messages are found for the given time zone
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messagesByTimeZone);
    }

    @GetMapping("/by-subject")
    public ResponseEntity<List<ContactMessage>> getMessagesBySubject(
            @RequestParam(name = "subject") String subject
    ) {
        List<ContactMessage> messagesBySubject = contactMessageRepository.findBySubjectContaining(subject);

        if (messagesBySubject.isEmpty()) {
            // Handle the case when no messages are found for the given subject
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(messagesBySubject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMessageById(
            @PathVariable(name = "id") Long id
    ) {
        if (!contactMessageRepository.existsById(id)) {
            // Handle the case when no message with the given ID is found
            return ResponseEntity.notFound().build();
        }

        contactMessageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactMessage> updateMessage(
            @PathVariable(name = "id") Long id,
            @Valid @RequestBody ContactMessage updatedMessage
    ) {
        // Check if the message with the given ID exists
        if (!contactMessageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        // Set the ID for the updated message to ensure it's updated in the database
        updatedMessage.setId(id);

        // Save the updated message
        ContactMessage savedMessage = contactMessageRepository.save(updatedMessage);

        return ResponseEntity.ok(savedMessage);
    }
}