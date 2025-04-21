package at.fhv.sys.hotel.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class CustomerQueryModel {

    @Id
    private String customerId;
    private String name;
    private String email;
    private String address;
    private LocalDate birthDate;



    public CustomerQueryModel() {}

    public CustomerQueryModel(String customerId, String name,  String email, String address, LocalDate birthDate) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;

    }

    public String getCustomerId() {
        return "Customer-" + customerId;
    }

    public String getEmail() {
        return "Customer Email is: " + email;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
