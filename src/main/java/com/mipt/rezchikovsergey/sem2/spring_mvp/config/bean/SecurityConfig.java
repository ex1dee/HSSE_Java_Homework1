package com.mipt.rezchikovsergey.sem2.spring_mvp.config.bean;

import com.mipt.rezchikovsergey.sem2.spring_mvp.config.props.AppProperties;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.AppUserDetails;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.jwt.JwtAuthFilter;
import com.mipt.rezchikovsergey.sem2.spring_mvp.security.password.PepperedPasswordEncoder;
import java.util.List;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter)
      throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers("/api/v1/auth/**")
                    .permitAll()
                    .requestMatchers("/api/v1/profile")
                    .hasRole("USER")
                    .requestMatchers("/api/v1/docs")
                    .hasAuthority("READ_PRIVILEGE")
                    .requestMatchers("/external/**")
                    .permitAll()
                    .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder(AppProperties props) {
    return new PepperedPasswordEncoder(props.security().passwordPepper());
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
      throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    // eto stub
    AppUserDetails user =
        new AppUserDetails(
            UUID.randomUUID(),
            "user",
            passwordEncoder.encode("password"),
            List.of(new SimpleGrantedAuthority("ROLE_USER")));

    AppUserDetails reader =
        new AppUserDetails(
            UUID.randomUUID(),
            "reader",
            passwordEncoder.encode("password"),
            List.of(
                new SimpleGrantedAuthority("ROLE_USER"),
                new SimpleGrantedAuthority("READ_PRIVILEGE")));

    return new InMemoryUserDetailsManager(user, reader);
  }
}
