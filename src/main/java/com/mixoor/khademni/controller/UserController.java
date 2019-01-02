package com.mixoor.khademni.controller;


import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.ResourceNotFoundException;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.response.UserIdentityAvailability;
import com.mixoor.khademni.payload.response.UserProfile;
import com.mixoor.khademni.repository.ClientRepository;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.repository.UserRepository;
import com.mixoor.khademni.service.ProfilePictureService;
import com.mixoor.khademni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    ProfilePictureService profilePictureService;

    @Autowired
    UserService userService;

    @GetMapping("/user/me")
    public Optional getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        if (currentUser.getAuthorities().toArray()[0].toString().contains("ROLE_CLIENT")) {
            return clientRepository.findById(currentUser.getId());

        } else {
            return freelancerRepository.findById(currentUser.getId());

        }

    }

    @GetMapping("/profile/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserProfile getProfile(@CurrentUser UserPrincipal current, @PathVariable(value = "id") Long id) {
        return userService.getProfile(userRepository.findById(id).orElseThrow(() ->
                new BadRequestException("User Doesn't exist")));
    }


    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @PutMapping("/user/me")
    public User updateUser(@CurrentUser UserPrincipal currentUser, @Valid User userRequest, @RequestParam(name = "picture") MultipartFile picture) {
        return userRepository.findById(currentUser.getId()).map(user -> {

            user.setAboutMe(userRequest.getAboutMe());
            user.setAdresse(userRequest.getAdresse());
            user.setGender(userRequest.getGender());
            user.setCountry(userRequest.getCountry());
            user.setLanguages(userRequest.getLanguages());
            user.setName(userRequest.getName());
            user.setDob(userRequest.getDob());
            user.setPhone_number(userRequest.getPhone_number());
            user.setCity(userRequest.getCity());
            user.setPath(profilePictureService.storeProfilePicture(picture));


            return userRepository.save(user);

        }).orElseThrow(() -> new ResourceNotFoundException("User", String.valueOf(currentUser.getId()), " "));
    }

    @DeleteMapping("user/me")
    public ResponseEntity<?> deleteUser(@CurrentUser UserPrincipal currentUser) {

        return userRepository.findById(currentUser.getId()).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(currentUser.getId())));
    }


}
