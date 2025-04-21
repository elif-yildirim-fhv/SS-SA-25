package at.fhv.sys.hotel.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class CustomerQueryPanacheModel extends PanacheEntity {

	public String customerId;
	public String name;
	public String email;
	public String address;
	public LocalDate birthDate;

	public static CustomerQueryPanacheModel findByCustomerId(String customerId) {
		return find("customerId", customerId).firstResult();
	}


}
