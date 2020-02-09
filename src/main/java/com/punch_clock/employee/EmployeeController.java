package com.punch_clock.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/employees")
public class EmployeeController {

  @PostMapping
  public ResponseEntity<Void> store(Authentication auth) {
    String email = auth.getName();
    System.out.println("EMAIL: " + email);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
