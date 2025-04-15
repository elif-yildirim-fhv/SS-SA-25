package at.fhv.sys.hotel.domain;

public class Customer {
    private String userId;
    private String email;
    private String name;
    private String address;
    private String birthDate;

    public Customer(String name, String email, String address, String birthDate) {
        this.userId = java.util.UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
    }

    public String getUserId() {
        return userId;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
} 