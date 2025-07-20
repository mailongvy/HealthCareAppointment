package com.example.HealthCareAppointment.Security.Service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.HealthCareAppointment.Model.User;
import com.example.HealthCareAppointment.Repositories.UserRepository;
import com.example.HealthCareAppointment.Security.User.UserPrincipal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // trả vê thông tin người dùng dựa vào đăng nhập
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email " + email);
        }
        return new UserPrincipal(user);
    }
    
}
