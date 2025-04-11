package com.example.group07Final;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.example.group07Final.model.Admin;
import com.example.group07Final.model.AdminRepository;
import com.example.group07Final.model.Customer;
import com.example.group07Final.model.CustomerRepository;
import com.example.group07Final.model.Invoice;
import com.example.group07Final.model.InvoiceRepository;
import com.example.group07Final.model.Item;
import com.example.group07Final.model.ItemRepository;
import com.example.group07Final.model.Order;
import com.example.group07Final.model.OrderRepository;

public class Group07FinalMockData {
    public static void generate(AdminRepository adminRepository, CustomerRepository customerRepository, ItemRepository itemRepository, OrderRepository orderRepository, InvoiceRepository invoiceRepository) {
    	ArrayList<Customer> customers = new ArrayList<>();
        ArrayList<Item> items = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();
        Random random = new Random();
    	
    	// Generating administrator
    	String login = "admin";
        String password = "admin123";
        Admin admin = new Admin(login, password);
    	adminRepository.save(admin);

        // Generating customers
        for (int i = 1; i <= 100; i++) {
            String name = "Customer " + i;
            String address = i + " Random St";
            String email = "customer" + i + "@example.com";
            String phone = String.format("%03d-%03d-%04d", i, i + 100, i + 1000);

            Customer customer = new Customer(name, address, email, phone);
            customerRepository.save(customer);
            customers.add(customer);
        }
        System.out.println("100 customers generated!");

        

        // Generating items
        Item item1 = new Item("Apple", "Fresh and organic", 2.99, 150, "Fruits", "https://cdn.pixabay.com/photo/2022/09/16/16/09/harvest-7458975_1280.jpg");
        itemRepository.save(item1);
        items.add(item1);

        Item item2 = new Item("Banana", "Locally sourced", 1.49, 200, "Fruits", "https://cdn.pixabay.com/photo/2017/06/27/22/21/banana-2449019_1280.jpg");
        itemRepository.save(item2);
        items.add(item2);

        Item item3 = new Item("Carrot", "High quality", 0.99, 180, "Vegetables", "https://cdn.pixabay.com/photo/2016/09/25/16/34/carrots-1694087_1280.jpg");
        itemRepository.save(item3);
        items.add(item3);

        Item item4 = new Item("Milk", "Fresh dairy product", 1.69, 120, "Dairy", "https://cdn.pixabay.com/photo/2016/12/06/18/27/cereal-1887237_1280.jpg");
        itemRepository.save(item4);
        items.add(item4);

        Item item5 = new Item("Chicken Breast", "Perfect for any meal", 5.99, 100, "Meat", "https://cdn.pixabay.com/photo/2014/03/05/01/20/chicken-breast-279849_1280.jpg");
        itemRepository.save(item5);
        items.add(item5);

        Item item6 = new Item("Bread", "Great taste guaranteed", 1.29, 250, "Bakery", "https://cdn.pixabay.com/photo/2016/07/11/18/42/bread-1510298_1280.jpg");
        itemRepository.save(item6);
        items.add(item6);

        Item item7 = new Item("Carrot Juice", "Freshly squeezed", 3.49, 80, "Beverages", "https://cdn.pixabay.com/photo/2016/08/26/21/16/carrot-juice-1623157_1280.jpg");
        itemRepository.save(item7);
        items.add(item7);

        Item item8 = new Item("Tomato", "Fresh and nutritious", 1.59, 160, "Vegetables", "https://cdn.pixabay.com/photo/2014/06/18/23/15/vegetable-371919_1280.jpg");
        itemRepository.save(item8);
        items.add(item8);

        Item item9 = new Item("Cheese", "Rich in nutrients", 4.49, 90, "Dairy", "https://cdn.pixabay.com/photo/2020/05/17/04/33/cheese-5179968_1280.jpg");
        itemRepository.save(item9);
        items.add(item9);

        Item item10 = new Item("Beef Steak", "Tender and juicy", 8.99, 50, "Meat", "https://cdn.pixabay.com/photo/2016/09/29/16/40/entrecote-1703298_1280.jpg");
        itemRepository.save(item10);
        items.add(item10);

        Item item11 = new Item("Croissant", "Buttery and flaky", 2.19, 130, "Bakery", "https://cdn.pixabay.com/photo/2021/10/25/16/05/croissant-6741399_1280.jpg");
        itemRepository.save(item11);
        items.add(item11);

        Item item12 = new Item("Orange Juice", "Vitamin C packed", 3.99, 110, "Beverages", "https://cdn.pixabay.com/photo/2017/05/21/16/52/juice-2331722_1280.jpg");
        itemRepository.save(item12);
        items.add(item12);

        Item item13 = new Item("Lettuce", "Crisp and fresh", 1.29, 140, "Vegetables", "https://cdn.pixabay.com/photo/2020/03/18/16/54/red-oak-leaf-lettuce-4944786_1280.jpg");
        itemRepository.save(item13);
        items.add(item13);

        Item item14 = new Item("Yogurt", "Smooth and creamy", 2.49, 95, "Dairy", "https://cdn.pixabay.com/photo/2017/12/14/02/41/yogurt-3018152_1280.jpg");
        itemRepository.save(item14);
        items.add(item14);

        Item item15 = new Item("Pork Chops", "Lean and tender", 6.49, 75, "Meat", "https://cdn.pixabay.com/photo/2019/06/10/15/42/chop-4264609_1280.jpg");
        itemRepository.save(item15);
        items.add(item15);

        System.out.println("15 items generated!");

        // Generating orders
        List<Long> allItemIds = items.stream().map(Item::getId).collect(Collectors.toList());

        for (Customer customer : customers) {
            for (int i = 1; i <= 5; i++) {
                int itemCount = random.nextInt(5) + 1;
                List<Long> selectedItemsIds = allItemIds.stream()
                    .skip(random.nextInt(Math.max(allItemIds.size() - itemCount, 1)))
                    .limit(itemCount)
                    .collect(Collectors.toList());

                // Mapping Long IDs to Item objects
                List<Item> selectedItems = selectedItemsIds.stream()
                    .map(id -> items.stream().filter(item -> item.getId() == id).findFirst().orElse(null))
                    .collect(Collectors.toList());

                LocalDateTime randomDate = LocalDateTime.now().minusDays(random.nextInt(30));
                String orderDateTime = randomDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String deliveryDateTime = randomDate.plusDays(random.nextInt(10)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                String status = random.nextBoolean() ? "Pending" : "Completed";
                String deliveryType = random.nextBoolean() ? "Delivery" : "Pickup";
                double deliveryFee = deliveryType.equals("Delivery") ? 5.00 : 0.00;

                Order order = new Order(selectedItems, customer, orderDateTime, status, deliveryType, deliveryDateTime, deliveryFee);
                orderRepository.save(order);
                orders.add(order);
            }
        }

        System.out.println("5 orders per customer generated!");

        // Generating Invoices
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Order order : orders) {
            String invoiceDateTime = LocalDateTime.now().format(dateTimeFormatter);
            String paymentStatus = random.nextBoolean() ? "Paid" : "Unpaid";
            String paymentMethod = random.nextBoolean() ? "Credit Card" : "Cash";
            String dueDate = paymentStatus.equals("Unpaid")
                    ? LocalDateTime.now().plusDays(random.nextInt(15) + 1).format(dateTimeFormatter)
                    : null;

            Invoice invoice = new Invoice(order, invoiceDateTime, paymentStatus, paymentMethod, dueDate);
            invoiceRepository.save(invoice);
        }
        System.out.println("Invoices generated for all orders!");
    }
}

