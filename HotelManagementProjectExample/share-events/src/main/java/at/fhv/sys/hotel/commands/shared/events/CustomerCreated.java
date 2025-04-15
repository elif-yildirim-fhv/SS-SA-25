package at.fhv.sys.hotel.commands.shared.events;

public class CustomerCreated {
    private String userId;
    private String name;
    private String email;
    private String address;
    private String birthDate;

    public CustomerCreated() {}

    public CustomerCreated(String userId, String name, String email, String address, String birthDate) {
        this.userId = userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
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

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "CustomerCreated{" + "userId='" + userId + '\'' + ", name='" + name + '\'' + ", email='" + email + '\'' + ", address='" + address + '\'' + ", birthDate='" + birthDate + '\'' + '}';
    }
}