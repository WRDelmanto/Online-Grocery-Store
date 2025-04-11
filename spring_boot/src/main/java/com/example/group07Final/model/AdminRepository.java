package com.example.group07Final.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, String> {
	Optional<Admin> findByLoginAndPassword(String login, String password);
}
