package com.punch_clock.company;

import java.util.Arrays;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.punch_clock.user.Role;
import com.punch_clock.user.RoleRepository;
import com.punch_clock.user.User;
import com.punch_clock.user.UserRepository;

@RestController
@RequestMapping("/v1/companies")
public class CompanyController {

  private CompanyRepository companyRepository;
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  public CompanyController(CompanyRepository companyRepository, UserRepository userRepository,
      RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    super();
    this.companyRepository = companyRepository;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping
  private ResponseEntity<Void> store(@RequestBody CompanyRequest companyRequest) throws Exception {

    /**
     * Check company already exists
     */
    Optional<Company> sameCompany = companyRepository.findByCnpj(companyRequest.getCnpj());
    if (sameCompany.isPresent()) {
      throw new Exception("Company already exists");
    }

    /**
     * Check user already exists
     */
    Optional<User> sameUser = userRepository.findByEmail(companyRequest.getEmail());
    if (sameUser.isPresent()) {
      throw new Exception("E-mail already used");
    }

    Company company = new Company(companyRequest.getCnpj(), companyRequest.getCompanyName());
    company = companyRepository.save(company);

    Role ownerRole = roleRepository.findByName("ROLE_OWNER").get();
    User user = new User();
    user.setFirstName(companyRequest.getFirstName());
    user.setLastName(companyRequest.getLastName());
    user.setEmail(companyRequest.getEmail());
    user.setPassword(bCryptPasswordEncoder.encode(companyRequest.getPassword()));
    user.setRoles(Arrays.asList(ownerRole));
    user.setCompany(company);
    userRepository.save(user);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
