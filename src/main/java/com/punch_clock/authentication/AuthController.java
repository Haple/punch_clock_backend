package com.punch_clock.authentication;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.punch_clock.security.SecurityConstants.EXPIRATION_TIME;
import static com.punch_clock.security.SecurityConstants.SECRET;
import static com.punch_clock.security.SecurityConstants.TOKEN_PREFIX;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwt.JWT;
import com.punch_clock.user.User;
import com.punch_clock.user.UserRepository;

@RestController
@RequestMapping("/v1/authentications")
public class AuthController {

  private UserRepository userRepository;
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public AuthController(UserRepository userRepository,
      BCryptPasswordEncoder bCryptPasswordEncoder) {
    super();
    this.userRepository = userRepository;
    this.passwordEncoder = bCryptPasswordEncoder;
  }

  @PostMapping
  public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest auth) throws Exception {
    User user = userRepository
        .findByEmail(auth.getEmail())
        .orElseThrow(
            () -> new Exception("User not found: " + auth.getEmail()));
    boolean matches = passwordEncoder.matches(auth.getPassword(), user.getPassword());
    if (!matches)
      throw new Exception("Wrong password");
    String[] roles = user.getAuthorities()
        .stream()
        .map((role) -> role.getAuthority())
        .toArray(String[]::new);
    String token = TOKEN_PREFIX + JWT.create()
        .withSubject(user.getEmail())
        .withArrayClaim("roles", roles)
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(HMAC512(SECRET.getBytes()));
    return new ResponseEntity<AuthResponse>(new AuthResponse(token), HttpStatus.OK);
  }
}
