package payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Data
@Entity
class Employee {

	private @Id @GeneratedValue Long id;
	private String name;
	private String role;
	@JoinColumn(name = "dept_id")
    @ManyToOne
    private Department dept;
    Employee(){}
	Employee(String name, String role, Department dept) {
		this.name = name;
		this.role = role;
		this.dept = dept;
	}

}