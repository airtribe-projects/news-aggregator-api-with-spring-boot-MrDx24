package com.airtribe.NewAggregator.controller;


import com.airtribe.NewAggregator.entity.User;
import com.airtribe.NewAggregator.model.LoginDto;
import com.airtribe.NewAggregator.repository.UserRepository;
import com.airtribe.NewAggregator.service.JwtService;
import com.airtribe.NewAggregator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public String register(@RequestBody User userDetails) {
        userDetails.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        User userResp = userRepository.save(userDetails);
        return userResp != null ? "Success: " + userResp.getPassword() : "Error";
    }

    @PostMapping("/authenticate")
    public String login(@RequestBody LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));

        System.out.println(loginDto);
        if(authentication.isAuthenticated()) {
            return jwtService.generateToken(userService.loadUserByUsername(loginDto.getUsername()));
        }

        return "Error : login failed";
    }
}