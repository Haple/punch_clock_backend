package com.punch_clock.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	Optional<Employee> findByCpfOrEmail(String cpf, String email);
	
}
