package com.example.group07Final.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByPaymentStatus(String paymentStatus);
    List<Invoice> findByPaymentMethod(String paymentMethod);
}
