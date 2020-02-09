package com.punch_clock.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.punch_clock.security.SecurityConstants.EXPIRATION_TIME;
import static com.punch_clock.security.SecurityConstants.HEADER_STRING;
import static com.punch_clock.security.SecurityConstants.SECRET;
import static com.punch_clock.security.SecurityConstants.SIGN_IN_URL;
import static com.punch_clock.security.SecurityConstants.TOKEN_PREFIX;
import java.io.IOException;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.punch_clock.user.User;;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

  private AuthenticationManager authenticationManager;

  public JWTLoginFilter(AuthenticationManager authenticationManager) {
    this.setFilterProcessesUrl(SIGN_IN_URL);
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    try {
      User credentials = new ObjectMapper()
          .readValue(request.getInputStream(), User.class);
      return authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              credentials.getEmail(),
              credentials.getPassword()));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication auth) throws IOException, ServletException {
    User user = (User) auth.getPrincipal();
    String[] roles = user.getAuthorities()
        .stream()
        .map((role) -> role.getAuthority())
        .toArray(String[]::new);
    String token = JWT.create()
        .withSubject(user.getUsername())
        .withArrayClaim("roles", roles)
        .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .sign(HMAC512(SECRET.getBytes()));
    response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
  }

}
