package at.fhv.sys.hotel.domain;

import java.time.LocalDate;

public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private LocalDate birthDate;

    public Customer(String name, String email, String address, LocalDate birthDate) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (name.length() < 2 || name.length() > 100) {
            throw new IllegalArgumentException("Name must be between 2 and 100 characters");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (address.length() < 5 || address.length() > 200) {
            throw new IllegalArgumentException("Address must be between 5 and 200 characters");
        }
        if (birthDate == null) {
            throw new IllegalArgumentException("Birth date is required");
        }
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birth date cannot be in the future");
        }
      

        this.customerId = java.util.UUID.randomUUID().toString();
        this.name = name.trim();
        this.email = email.trim();
        this.address = address.trim();
        this.birthDate = birthDate;
    }

    public void update(String name, String email, String address, LocalDate birthDate) {
        if (name != null && !name.trim().isEmpty()) {
            if (name.length() < 2 || name.length() > 100) {
                throw new IllegalArgumentException("Name must be between 2 and 100 characters");
            }
            this.name = name.trim();
        }
        if (email != null && !email.trim().isEmpty()) {
            this.email = email.trim();
        }
        if (address != null && !address.trim().isEmpty()) {
            if (address.length() < 5 || address.length() > 200) {
                throw new IllegalArgumentException("Address must be between 5 and 200 characters");
            }
            this.address = address.trim();
        }
        if (birthDate != null) {
            if (birthDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Birth date cannot be in the future");
            }
            if (LocalDate.now().getYear() - birthDate.getYear() < 18) {
                throw new IllegalArgumentException("Customer must be at least 18 years old");
            }
            this.birthDate = birthDate;
        }
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
} 