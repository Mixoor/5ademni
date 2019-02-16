package com.mixoor.khademni.controller;


import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.ResourceNotFoundException;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Gender;
import com.mixoor.khademni.model.Language;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.ExperienceRequest;
import com.mixoor.khademni.payload.request.UserRequest;
import com.mixoor.khademni.payload.response.*;
import com.mixoor.khademni.repository.ClientRepository;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.repository.LanguageRepository;
import com.mixoor.khademni.repository.UserRepository;
import com.mixoor.khademni.service.ProfilePictureService;
import com.mixoor.khademni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/user")
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


    @Autowired
    LanguageRepository languageRepository;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
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


    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }




    @PutMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserProfile updateUser(@CurrentUser UserPrincipal currentUser, @Valid UserRequest userRequest) {
        User  user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new ResourceNotFoundException("User", String.valueOf(currentUser.getId()), " "));


        user.setAboutMe(userRequest.getAboutMe());
        user.setAddress(userRequest.getAddress());
        user.setGender(Gender.values()[userRequest.getGender()]);
        user.setCountry(userRequest.getCountry());
        user.setLanguages(new HashSet<Language>(languageRepository.findAllById(userRequest.getLanguages())));
        user.setName(userRequest.getName());
        user.setDob(userRequest.getDob());
        user.setPhone_number(userRequest.getPhone_number());
        user.setCity(userRequest.getCity());
        if(!userRequest.getPicture().equals(null))
        {
            profilePictureService.deleteFile(user.getPath());
            user.setPath(profilePictureService.storeProfilePicture(userRequest.getPicture()));
        }


        User userFinished= userRepository.save(user);
        UserProfile userProfile= ModelMapper.mapUserToProfile(userFinished);

        return  userProfile;

    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser(@CurrentUser UserPrincipal currentUser) {

        return userRepository.findById(currentUser.getId()).map(user -> {
            userRepository.delete(user);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User", "id", String.valueOf(currentUser.getId())));
    }

    @GetMapping("/users")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<UserSummary> getUsers(@CurrentUser UserPrincipal userPrincipal,@RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
                                               @RequestParam(value = "type",defaultValue = "user") String type){
    return  userService.getUsers(userPrincipal,page,size,type);
    }


    // For Freelancer stuff


    @GetMapping("/{id}/skill")
    public ResponseEntity<List<SkillResponse>> getSkills(@CurrentUser UserPrincipal userPrincipal , @PathVariable(name = "id") Long id){
        Freelancer  freelancer =freelancerRepository.
                findById(id).orElseThrow(()->new BadRequestException("Freelancer doesn't exist"));

        //Getting skills

        List<SkillResponse>skillResponses = freelancer.getSkills().stream().map(skill -> ModelMapper.mapSkillToResponse(skill))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(skillResponses);

    }

    @GetMapping("/{id}/experience")
    public PagedResponse<ExperienceResponse> getExperience(@CurrentUser UserPrincipal userPrincipal , @PathVariable(name = "id") Long id,
                                                                  @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                  @RequestParam(value = "size" , defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        Freelancer  freelancer =freelancerRepository.
                findById(id).orElseThrow(()->
                new BadRequestException("Freelancer doesn't exist"));

        //Getting Experience
        PagedResponse<ExperienceResponse> experienceResponses = userService
                .getAllExperience(freelancer,page,size);

        return experienceResponses;
    }

    @PostMapping("/me/experience")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ExperienceResponse createExperience(@CurrentUser UserPrincipal userPrincipal, ExperienceRequest request){
        Freelancer freelancer = freelancerRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new BadRequestException("Freelancer Invalid "));

       return userService.addExperience(freelancer,request);

    }

    @PostMapping("/me/skill")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public List<SkillResponse> setSkills(@CurrentUser UserPrincipal userPrincipal,List<String> skills){
            return userService.setSkills(userPrincipal,skills);
    }

    @GetMapping("me/static")
    @PreAuthorize("isAuthenticated()")
    public UserStatic userStatic(@CurrentUser UserPrincipal userPrincipal){
        return userService.userStatic(userPrincipal);
    }













}
