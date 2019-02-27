package payroll;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository eRepo, DepartmentRepository dRepo) {
		return args -> {
			Department nf = new Department("NF");
			Department ni = new Department("NI");
			log.info("Preloading " + dRepo.save(ni));			
			log.info("Preloading " + dRepo.save(nf));			
			
			log.info("Preloading " + eRepo.save(new Employee("Ron", "Architect", nf)));
			log.info("Preloading " + eRepo.save(new Employee("Andrew", "Developer", ni)));
			log.info("Preloading " + eRepo.save(new Employee("Jenna", "Developer", nf)));
			log.info("Preloading " + eRepo.save(new Employee("Shirley", "Developer", ni)));
			log.info("Preloading " + eRepo.save(new Employee("Jennifer", "Manager", nf)));
		};
	}
}