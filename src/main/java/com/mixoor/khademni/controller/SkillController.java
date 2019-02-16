package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Skill;
import com.mixoor.khademni.payload.request.SkillRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.repository.SkillRepository;
import com.mixoor.khademni.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/skill")
public class SkillController {

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<Skill> getSkills() {
        List<Skill> skills = skillRepository.findAll();
        return skills;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createSkill(@CurrentUser UserPrincipal userPrincipal, @Valid SkillRequest skillRequest) {
        Skill skill = ModelMapper.mapRequestToSkill(skillRequest);

        skillRepository.save(skill);

        return ResponseEntity.ok().body(new ApiResponse(true, "Skill created successfully"));
    }

    @DeleteMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSkill(@CurrentUser UserPrincipal userPrincipal, @Valid SkillRequest skillRequest) {
        Skill skill = skillRepository.findByName(skillRequest.getName())
                .orElseThrow(() -> new BadRequestException("Skill doesn't exist "));

        skillRepository.delete(skill);

        return ResponseEntity.ok().body(new ApiResponse(true, "Skill deleted successfully"));
    }


}
