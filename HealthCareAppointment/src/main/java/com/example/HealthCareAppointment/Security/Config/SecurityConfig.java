package com.example.HealthCareAppointment.Security.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.HealthCareAppointment.Security.Service.JWTService;
import com.example.HealthCareAppointment.Security.Service.MyUserDetailsService;

import jakarta.servlet.Filter;


@Configuration
@EnableWebSecurity
public class SecurityConfig  {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private JWTService jwtService;

    // @Autowired
    // private JwtAuthFilter jwtAuthFilter;

    

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                    // .requestMatchers("/api/auth/**", "/api/appointments/oauth2/callback").permitAll()
                    .requestMatchers("/api/appointments/**").hasRole("PATIENT")
                    .requestMatchers("/api/doctors/**").hasRole("DOCTOR")
                    .requestMatchers("/api/admin/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                    

                )

                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());


        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }     

    // tạo authentication để có thể lấy thông tin người dùng từ db
    // dc quản lí bởi spring context
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(myUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean 
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    public Filter jwtAuthFilter() {
        return new JwtAuthFilter(jwtService, myUserDetailsService);
    }


}
