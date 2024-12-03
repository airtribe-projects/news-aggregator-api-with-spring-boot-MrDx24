package com.airtribe.NewAggregator.service;

import com.airtribe.NewAggregator.entity.User;
import com.airtribe.NewAggregator.model.LoginDto;
import com.airtribe.NewAggregator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if(user.isPresent()) {
            var userobj = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userobj.getUsername())
                    .password(userobj.getPassword())
                    .build();
        }
        else {
            throw new UsernameNotFoundException(username);
        }

    }
}
