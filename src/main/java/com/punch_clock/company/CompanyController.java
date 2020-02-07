package com.punch_clock.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Optional;
import com.punch_clock.employee.Employee;
import com.punch_clock.employee.EmployeeRepository;

@RestController
@RequestMapping("/v1/companies")
public class CompanyController {

	private CompanyRepository companyRepository;
	private EmployeeRepository employeeRepository;

	@Autowired
	public CompanyController(CompanyRepository companyRepository, EmployeeRepository userRepository) {
		super();
		this.companyRepository = companyRepository;
		this.employeeRepository = userRepository;
	}

	@PostMapping
	private ResponseEntity<Void> createCompany(@RequestBody SignUpRequest signUp) throws Exception {
		
		/**
		 * Check company already exists
		 */
		String cnpj = signUp.getCompany().getCnpj();
		Optional<Company> company = companyRepository.findByCnpj(cnpj);
		if (company.isPresent()) {
			throw new Exception("Company already exists");
		}
		
		/**
		 * Check user already exists
		 */
		String cpf = signUp.getEmployee().getCpf();
		String email = signUp.getEmployee().getEmail();
		Optional<Employee> user = employeeRepository.findByCpfOrEmail(cpf, email);
		if (user.isPresent() && user.get().getCpf().equals(cpf)) {
			throw new Exception("CPF already exists");
		} else if (user.isPresent() && user.get().getEmail().equals(email)) {
			throw new Exception("E-mail already exists");
		}
		
		companyRepository.save(signUp.getCompany());
		employeeRepository.save(signUp.getEmployee());
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
