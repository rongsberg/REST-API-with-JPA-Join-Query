package payroll;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
@RestController
class EmployeeController {

	private final EmployeeRepository repository;

	EmployeeController(EmployeeRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	@GetMapping(path="/employees", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
Resources<Resource<Employee>> all() {

	List<Resource<Employee>> employees = repository.findAll().stream()
		.map(employee -> new Resource<>(employee,
			linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
		.collect(Collectors.toList());

	return new Resources<>(employees,
		linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}
	// Search by role

	@GetMapping(path="/employees", params = "role", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
Resources<Resource<Employee>> searchByRole(@RequestParam("role") String role) {

	List<Resource<Employee>> employees = repository.findbyRole(role).stream()
		.map(employee -> new Resource<>(employee,
			linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
		.collect(Collectors.toList());

	return new Resources<>(employees,
		linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}

	// wildcard Search

	@GetMapping(path="/employees", params = "nameLike", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
Resources<Resource<Employee>> searchNameLike(@RequestParam("nameLike") String name) {

	List<Resource<Employee>> employees = repository.findNameLike(name).stream()
		.map(employee -> new Resource<>(employee,
			linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
		.collect(Collectors.toList());

	return new Resources<>(employees,
		linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}


//Search by dept
	@GetMapping(path="/employees", params = "dName", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
Resources<Resource<Employee>> searchByDept(@RequestParam("dName") String name) {

	List<Resource<Employee>> employees = repository.findEmployeeByDeptName(name).stream()
		.map(employee -> new Resource<>(employee,
			linkTo(methodOn(EmployeeController.class).one(employee.getId())).withSelfRel(),
			linkTo(methodOn(EmployeeController.class).all()).withRel("employees")))
		.collect(Collectors.toList());

	return new Resources<>(employees,
		linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
}
	@PostMapping("/employees")
	Employee newEmployee(@RequestBody Employee newEmployee) {
		return repository.save(newEmployee);
	}
	// Single item

	@GetMapping(path="/employees/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, "application/hal+json"})
Resource<Employee> one(@PathVariable Long id) {

	Employee employee = repository.findById(id)
		.orElseThrow(() -> new EmployeeNotFoundException(id));

	return new Resource<>(employee,
		linkTo(methodOn(EmployeeController.class).one(id)).withSelfRel(),
		linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
}

	@PutMapping("/employees/{id}")
	Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {

		return repository.findById(id)
			.map(employee -> {
				employee.setName(newEmployee.getName());
				employee.setRole(newEmployee.getRole());
				return repository.save(employee);
			})
			.orElseGet(() -> {
				newEmployee.setId(id);
				return repository.save(newEmployee);
			});
	}

	@DeleteMapping("/employees/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}