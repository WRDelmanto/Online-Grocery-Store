package com.example.group07Final;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.group07Final.model.AdminRepository;
import com.example.group07Final.model.CustomerRepository;
import com.example.group07Final.model.InvoiceRepository;
import com.example.group07Final.model.ItemRepository;
import com.example.group07Final.model.OrderRepository;

@SpringBootApplication
public class Group07FinalApplication {
	public static void main(String[] args) {
		SpringApplication.run(Group07FinalApplication.class, args);
	}

	@Bean
	ApplicationRunner init(AdminRepository adminRepository, CustomerRepository customerRepository, ItemRepository itemRepository, OrderRepository orderRepository, InvoiceRepository invoiceRepository) {
	    return args -> {
	    	Group07FinalMockData.generate(adminRepository, customerRepository, itemRepository, orderRepository, invoiceRepository);
	    };
	}
}
