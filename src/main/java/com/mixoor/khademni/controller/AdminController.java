package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.AppException;
import com.mixoor.khademni.model.Language;
import com.mixoor.khademni.model.Role;
import com.mixoor.khademni.model.RoleName;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.SignUpRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.StaticPage;
import com.mixoor.khademni.payload.response.UserSummary;
import com.mixoor.khademni.repository.LanguageRepository;
import com.mixoor.khademni.repository.RoleRepository;
import com.mixoor.khademni.repository.UserRepository;
import com.mixoor.khademni.service.AdminService;
import com.mixoor.khademni.service.ProfilePictureService;
import com.mixoor.khademni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {


    @Autowired
    AdminService adminService;

    @Autowired
    ProfilePictureService profilePictureService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    LanguageRepository languageRepository;


    @GetMapping("/users")
    public PagedResponse<UserSummary> getUsers(@CurrentUser UserPrincipal userPrincipal,
                                               @RequestParam(value = "role", defaultValue ="0") int role,
                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){

        return adminService.userSummaryPagedResponse(RoleName.values()[role],page,size);
    }

    @GetMapping("/static")
    public StaticPage getStatic(){
        return  adminService.staticPage();
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUsers(Long[] ids){
        adminService.deleteUsers(ids);
        return ResponseEntity.ok().body("Users Deleted successfully");
    }

    @PostMapping("/users")
    public ResponseEntity<?> createAdmin(SignUpRequest adminRequest){

        if (userRepository.existsByEmail(adminRequest.getEmail()))
            return new ResponseEntity(new ApiResponse(false, "Email Adresse already exists"), HttpStatus.BAD_REQUEST);

        User user = userService.CreateUser(adminRequest);
        Role AdminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new AppException("Role doesn't exist"));

        user.setRole(AdminRole);
        if (adminRequest.getLanguage() != null) {
            Set<Language> languages = languageRepository.findAllByName((String[]) adminRequest.getLanguage().toArray());
            user.setLanguages(languages);
        }
             userRepository.save(user);

        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));

    }





}
