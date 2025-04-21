package at.fhv.sys.hotel.commands.shared.events;

import java.time.LocalDate;

public class CustomerUpdated {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private LocalDate birthDate;

    public CustomerUpdated() {}

    public CustomerUpdated(String customerId, String name, String email, String address, LocalDate birthDate) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
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

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "CustomerUpdated{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
} 