package at.fhv.sys.hotel.commands.shared.events;

public class CustomerDeleted {
    private String customerId;

    public CustomerDeleted() {}

    public CustomerDeleted(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerDeleted{" +
                "customerId='" + customerId + '\'' +
                '}';
    }
} 