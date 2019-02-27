package payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Department {

	private @Id @GeneratedValue Long id;
	private String name;

    Department(){}
	Department(String name) {
		this.name = name;
	}
}