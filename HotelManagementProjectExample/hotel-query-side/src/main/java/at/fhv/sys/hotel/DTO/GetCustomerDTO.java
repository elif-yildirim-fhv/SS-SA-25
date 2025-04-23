package at.fhv.sys.hotel.DTO;

import java.time.LocalDate;

public class GetCustomerDTO {
    private String customerId;
    private String name;
    private String email;
    private String address;
    private LocalDate birthDate;

    public GetCustomerDTO(String customerId, String name, String email, String address, LocalDate birthDate) {
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
}
