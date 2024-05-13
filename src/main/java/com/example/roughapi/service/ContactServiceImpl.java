package com.example.roughapi.service;

import com.example.roughapi.entity.Confirmation;
import com.example.roughapi.entity.Contact;
import com.example.roughapi.repository.ConfirmationRepository;
import com.example.roughapi.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final String PHOTO_DIRECTORY = "C:\\Users\\navra\\Downloads\\rough-api\\src\\main\\resources\\static";
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final ConfirmationRepository confirmationRepository;
    private final ContactRepository contactRepository;
    private final EmailService emailService;

    @Override
    public Contact getContact(Long id) {
        return contactRepository.findById(id).get();
    }
    @Override
    public Page<Contact> getAllContacts(int page, int size) {
        return contactRepository.findAll(PageRequest.of(page, size, Sort.by("name")));
    }

    @Override
    public Contact createContact(Contact contact) {
        String email = contact.getEmail();
        if(findByEmail(email)) {
            throw new RuntimeException("Email already exists");
        }
        String encryptedPassword = passwordEncoder.encode(contact.getPassword());
        contact.setPassword(encryptedPassword);
        contact.setIsEnbaled(false);
        contactRepository.save(contact);

        Confirmation confirmation = new Confirmation(contact);
        confirmationRepository.save(confirmation);

        emailService.sendSimpleMailMessage(contact.getName(), confirmation.getToken());

        return contact;
    }
    @Override
    public Boolean verifyToken(String token) {
        Confirmation confirmation = confirmationRepository.findByToken(token);
        Contact contact = contactRepository.findByEmailIgnoreCase(confirmation.getContact().getEmail());
        contact.setIsEnbaled(true);
        contactRepository.save(contact);
        return Boolean.TRUE;
    }


    @Override
    public Contact updateContact(Contact contact) {
        return contactRepository.save(contact);
    }
    @Override
    public String deleteContact(Long id) {
        contactRepository.deleteById(id);
        return "Deleted";
    }

    @Override
    public String uploadPhoto(Long id, MultipartFile file) {
        Contact contact = getContact(id);
        String photoURL = photoFunction.apply(id, file);
        contact.setPhotoURL(photoURL);
        contactRepository.save(contact);
        return photoURL;
    }
    @Override
    public byte[] getPhoto(String filename) throws IOException {
        Path location = Paths.get(PHOTO_DIRECTORY + "/" + filename);
        return Files.readAllBytes(location);
    }

    private final Boolean findByEmail(String email) {
        List<Contact> list = contactRepository.findAll();
        Optional<Contact> contact = list.stream()
                .filter(con -> email.equals(con.getEmail()))
                .findFirst();
        return contact.isPresent();
    }

    private final Function<String, String> fileExtension = filename -> {
        return Optional.of(filename)
                .filter(name -> name.contains("."))
                .map(name -> name.substring(filename.lastIndexOf(".") + 1))
                .orElse("png");
    };
    private final BiFunction<Long, MultipartFile, String> photoFunction = (id, file) -> {
        String filename = "contact-" + id + "." + fileExtension.apply(file.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY);
            if(!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(file.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/app/contact/photo/" + filename)
                    .build()
                    .toUriString();
        } catch(Exception e) {
            throw new RuntimeException("Unable to save photo");
        }
    };
}


