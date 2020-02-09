package com.punch_clock.security;

public class SecurityConstants {

  public static final String SECRET =
      "Q9afCVmA5MEO7LqUUrnvkTYUDAmFD5aaoi1EPNRnUl1C4TiqgGh7c7TnyfmZtZJ";
  public static final long EXPIRATION_TIME = 86_000_000;
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  // ?
  public static final String SIGN_UP_URL = "/v1/companies";
}
