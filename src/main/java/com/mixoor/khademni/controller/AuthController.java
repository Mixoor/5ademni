package com.mixoor.khademni.controller;


import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.JwtTokenProvider;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.AppException;
import com.mixoor.khademni.model.*;
import com.mixoor.khademni.payload.request.FreelancerSignUpRequest;
import com.mixoor.khademni.payload.request.LoginRequest;
import com.mixoor.khademni.payload.request.PasswordRequest;
import com.mixoor.khademni.payload.request.SignUpRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.JwtAuthenticationResponse;
import com.mixoor.khademni.repository.*;
import com.mixoor.khademni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    LanguageRepository languageRepository;

    @Autowired
    SkillRepository skillRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }


    @PostMapping("/signup/client")
    public ResponseEntity<?> registerClient(@Valid SignUpRequest SignUpRequest) {
        if (userRepository.existsByEmail(SignUpRequest.getEmail()))
            return new ResponseEntity(new ApiResponse(false, "Email Adresse already exists"), HttpStatus.BAD_REQUEST);


        //creating Client

        Client client = (Client) userService.CreateUser(SignUpRequest);

        Role clientRole = roleRepository.findByName(RoleName.ROLE_CLIENT)
                .orElseThrow(() -> new AppException("Role doesn't exist"));

        client.setRole(clientRole);
        if (SignUpRequest.getLanguage() != null) {
            Set<Language> languages = languageRepository.findAllByName((String[]) SignUpRequest.getLanguage().toArray());
            client.setLanguages(languages);
        }
        Client c = clientRepository.save(client);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{id}")
                .buildAndExpand(c.getId()).toUri();


        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }

    @PostMapping("/signup/freelancer")
    public ResponseEntity<?> registerFreelancer(@Valid @RequestBody FreelancerSignUpRequest freelancerSignUpRequest) {
        if (userRepository.existsByEmail(freelancerSignUpRequest.getEmail()))
            return new ResponseEntity(new ApiResponse(false, "Email Adresse already exists"), HttpStatus.BAD_REQUEST);


        if(!freelancerSignUpRequest.getConfirm().equals(freelancerSignUpRequest.getPassword()))
            return new ResponseEntity(new ApiResponse(false, "Password  invalid"), HttpStatus.BAD_REQUEST);
        //creating freelancer
        Freelancer freelancer = (Freelancer) userService.CreateUser(freelancerSignUpRequest);

        Role clientRole = roleRepository.findByName(RoleName.ROLE_FREELANCER).orElseThrow(() -> new AppException("Role doesn't exist"));

        freelancer.setRole(clientRole);

        if (!freelancerSignUpRequest.getLanguage().isEmpty()) {
            Set set =new HashSet<Long>(freelancerSignUpRequest.getLanguage());
            List<Language> languages = languageRepository.findAllById(set);
            freelancer.setLanguages(new HashSet<Language>(languages));
        }

        Set<Skill> skills = new HashSet<>(skillRepository.findAllById(freelancerSignUpRequest.getSkill()));
        Freelancer c = freelancerRepository.save(freelancer);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{id}")
                .buildAndExpand(c.getId()).toUri();


        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));

    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@CurrentUser UserPrincipal userPrincipal,PasswordRequest passwordResquest){
        String old= passwordEncoder.encode(userPrincipal.getPassword());

        if(old.equals(passwordResquest.getOldPassword())){

            if(passwordResquest.getNewPassword().equals(passwordResquest.getConPassword())){
                User user = userRepository.getOne(userPrincipal.getId());
                user.setPassword(passwordEncoder.encode(passwordResquest.getNewPassword()));
                userRepository.save(user);
            }else
                return ResponseEntity.badRequest().body("New Password and Confirmation are Incorrect ! ");
        }else
            return ResponseEntity.badRequest().body("Old Password Incorrect ! ");


        return ResponseEntity.ok("Password Change successfully");

    }


}
