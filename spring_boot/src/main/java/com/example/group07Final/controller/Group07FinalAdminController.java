package com.example.group07Final.controller;

import com.example.group07Final.model.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/admin") // Base URL for administrator
public class Group07FinalAdminController {
    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/{login}/{password}")
    public ResponseEntity<Boolean> checkCredentials(@PathVariable String login, @PathVariable String password) {
        try {
            boolean isValid = adminRepository.findByLoginAndPassword(login, password).isPresent();
            return new ResponseEntity<>(isValid, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}