package com.punch_clock.company;

public class CompanyRequest {

  private String cnpj;
  private String companyName;

  private String firstName;
  private String lastName;
  private String email;
  private String password;

  public CompanyRequest() {}

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return "CompanyRequest [cnpj=" + cnpj + ", companyName=" + companyName + ", firstName="
        + firstName + ", lastName=" + lastName + ", email=" + email + ", password=" + password
        + "]";
  }

}
