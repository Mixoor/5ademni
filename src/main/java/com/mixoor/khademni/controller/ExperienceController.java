package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Experience;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.payload.request.ExperienceRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.ExperienceResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.ExperienceRepository;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/exp")
public class ExperienceController {

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    public PagedResponse<ExperienceResponse> getExperiences(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "id") Long id,
                                                            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Freelancer doesn't exist"));
        return userService.getAllExperience(freelancer, page, size);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ExperienceResponse createExp(@CurrentUser UserPrincipal principal,
                                        @Valid ExperienceRequest request) {
        Freelancer freelancer = freelancerRepository.findById(principal.getId())
                .orElseThrow(() -> new BadRequestException("Freelancer doesn't exist "));
        return userService.addExperience(freelancer, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<?> removeExp(@CurrentUser UserPrincipal principal, @PathVariable(value = "id") Long id) {
        Freelancer freelancer = freelancerRepository.findById(principal.getId())
                .orElseThrow(() -> new BadRequestException("Freelancer doesn't exist "));

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Experience doesn't exist "));

        if (experience.getFreelancer().getId() == freelancer.getId()) {
            userService.removeExperience(experience);
            return ResponseEntity.ok().body(new ApiResponse(true, "Experience removed successfully"));
        }
        return ResponseEntity.ok().body(new ApiResponse(false, "Erreur "));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public Experience updateExp(@CurrentUser UserPrincipal principal, @PathVariable(value = "id") Long id,
                                @Valid ExperienceRequest request) {
        Freelancer freelancer = freelancerRepository.findById(principal.getId())
                .orElseThrow(() -> new BadRequestException("Freelancer doesn't exist "));

        Experience experience = experienceRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Experience doesn't exist "));

        if (experience.getFreelancer().getId() == freelancer.getId()) {
            experience.setCompanyName(request.getCompanyName());
            experience.setDescription(request.getDescription());
            experience.setEndDate(request.getEndDate());
            experience.setPosition(request.getPosition());
            experience.setStartDate(request.getStartDate());

            return experienceRepository.save(experience);
        }
        return null;
    }
}
