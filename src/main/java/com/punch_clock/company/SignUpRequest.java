package com.punch_clock.company;

import com.punch_clock.employee.Employee;

public class SignUpRequest {
	
	private Company company;
	private Employee employee;
	
	public SignUpRequest(Company company, Employee employee) {
		super();
		this.company = company;
		this.employee = employee;
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
	public String toString() {
		return "SignUpRequest [company=" + company + ", employee=" + employee + "]";
	}

}
