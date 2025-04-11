package com.example.group07Final.controller;

import com.example.group07Final.model.Invoice;
import com.example.group07Final.model.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/invoices") // Base URL for invoices
public class Group07FinalInvoiceController {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @GetMapping
    public ResponseEntity<List<Invoice>> getAllInvoices() {
        try {
            List<Invoice> invoices = invoiceRepository.findAll();
            if (invoices.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(invoices, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable long id) {
        Optional<Invoice> invoiceData = invoiceRepository.findById(id);
        return invoiceData.map(invoice -> new ResponseEntity<>(invoice, HttpStatus.OK))
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/payment-status/{paymentStatus}")
    public ResponseEntity<List<Invoice>> getInvoicesByPaymentStatus(@PathVariable String paymentStatus) {
        List<Invoice> invoices = invoiceRepository.findByPaymentStatus(paymentStatus);
        if (invoices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @GetMapping("/payment-method/{paymentMethod}")
    public ResponseEntity<List<Invoice>> getInvoicesByPaymentMethod(@PathVariable String paymentMethod) {
        List<Invoice> invoices = invoiceRepository.findByPaymentMethod(paymentMethod);
        if (invoices.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invoices, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        try {
            Invoice savedInvoice = invoiceRepository.save(invoice);
            return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable long id, @RequestBody Invoice invoice) {
        Optional<Invoice> invoiceData = invoiceRepository.findById(id);
        if (invoiceData.isPresent()) {
            Invoice existingInvoice = invoiceData.get();
            existingInvoice.setOrder(invoice.getOrder());
            existingInvoice.setInvoiceDateTime(invoice.getInvoiceDateTime());
            existingInvoice.setPaymentStatus(invoice.getPaymentStatus());
            existingInvoice.setPaymentMethod(invoice.getPaymentMethod());
            existingInvoice.setDueDate(invoice.getDueDate());

            Invoice updatedInvoice = invoiceRepository.save(existingInvoice);
            return new ResponseEntity<>(updatedInvoice, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
