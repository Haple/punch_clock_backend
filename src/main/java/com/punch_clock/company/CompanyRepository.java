package com.punch_clock.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	
	Optional<Company> findByCnpj(String cnpj);
	
}
