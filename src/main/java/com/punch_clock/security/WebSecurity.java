package com.punch_clock.security;

import static com.punch_clock.security.SecurityConstants.SIGN_IN_URL;
import static com.punch_clock.security.SecurityConstants.SIGN_UP_URL;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

  private static final RequestMatcher PUBLIC_URLS = new OrRequestMatcher(
      new AntPathRequestMatcher(SIGN_UP_URL), new AntPathRequestMatcher(SIGN_IN_URL),
      new AntPathRequestMatcher("/v1/sessions", HttpMethod.POST.name()),
      new AntPathRequestMatcher("/actuator/**"), new AntPathRequestMatcher("/docs"),
      new AntPathRequestMatcher("/v2/api-docs"), new AntPathRequestMatcher("/swagger-resources/**"),
      new AntPathRequestMatcher("/configuration/security"),
      new AntPathRequestMatcher("/swagger-ui.html"), new AntPathRequestMatcher("/webjars/**"),
      new AntPathRequestMatcher("/configuration/ui"), new AntPathRequestMatcher("/csrf"),
      new AntPathRequestMatcher("/"));

  private static final RequestMatcher PROTECTED_URLS = new NegatedRequestMatcher(PUBLIC_URLS);

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and().csrf().disable()
        .authorizeRequests().requestMatchers(PROTECTED_URLS).authenticated()
        .and().addFilter(new JWTFilter(authenticationManager()))
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }


  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
