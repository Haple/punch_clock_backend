package com.punch_clock;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.punch_clock.company.CompanyRepository;
import com.punch_clock.user.Role;
import com.punch_clock.user.RoleRepository;
import com.punch_clock.user.UserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;
  
  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    if (alreadySetup)
      return;
    
    createRoleIfNotFound("ROLE_OWNER");
    createRoleIfNotFound("ROLE_ADMIN");
    createRoleIfNotFound("ROLE_EMPLOYEE");

    // Company company = new Company("123", "Test S/A");
    // company = companyRepository.save(company);
    // Role ownerRole = roleRepository.findByName("ROLE_OWNER").get();
    //
    // User user = new User();
    // user.setFirstName("Test");
    // user.setLastName("Test");
    // user.setPassword(passwordEncoder.encode("test"));
    // user.setEmail("test@test.com");
    // user.setRoles(Arrays.asList(ownerRole));
    // user.setCompany(company);
    // userRepository.save(user);
  }

  private void createRoleIfNotFound(String name) {
    Optional<Role> role = roleRepository.findByName(name);
    if (!role.isPresent()) {
      roleRepository.save(new Role(name));
    }
  }
}
