package com.example.roughapi.repository;

import com.example.roughapi.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByEmailIgnoreCase(String email);
    Boolean existsByEmail(String email);
}
