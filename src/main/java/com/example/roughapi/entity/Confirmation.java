package com.example.roughapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdAt;

    @OneToOne(targetEntity = Contact.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private Contact contact;

    public Confirmation(Contact contact) {
        this.contact = contact;
        this.createdAt = LocalDateTime.now();
        this.token = UUID.randomUUID().toString();
    }
}
