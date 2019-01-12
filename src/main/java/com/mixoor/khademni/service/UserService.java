package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.*;
import com.mixoor.khademni.payload.request.ClientSignUpRequest;
import com.mixoor.khademni.payload.request.ExperienceRequest;
import com.mixoor.khademni.payload.response.ExperienceResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.SkillResponse;
import com.mixoor.khademni.payload.response.UserProfile;
import com.mixoor.khademni.repository.ExperienceRepository;
import com.mixoor.khademni.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProfilePictureService profilePictureService;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    ExperienceRepository experienceRepository;

    public User CreateUser(ClientSignUpRequest clientSignUpRequest) {
        User user = new Client();

        SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/DD", Locale.ENGLISH);


        user.setAboutMe(clientSignUpRequest.getAboutMe());
        user.setName(clientSignUpRequest.getName());
        user.setAdresse(clientSignUpRequest.getAdresse());
        user.setCity(clientSignUpRequest.getCity());
        user.setCountry(clientSignUpRequest.getCountry());
        try {
            user.setDob(format.parse(clientSignUpRequest.getDob()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setEmail(clientSignUpRequest.getEmail());


        user.setGender(clientSignUpRequest.getGender() == 0 ? Gender.male : Gender.female);
        user.setPhone_number(clientSignUpRequest.getPhone());
        user.setPassword(passwordEncoder.encode(clientSignUpRequest.getPassword()));

        user.setPath(profilePictureService.storeProfilePicture(clientSignUpRequest.getPicture()));

        return user;
    }

    public List<SkillResponse> addSkill(Freelancer freelancer, List<Skill> skills) {

        if (skills.isEmpty())
            return Collections.emptyList();

        skills.forEach(skill -> freelancer.removeSkill(skill));

        List<SkillResponse> skillResponses = skills.stream().map(skill -> ModelMapper
                .mapSkillToResponse(skill)).collect(Collectors.toList());
        return skillResponses;

    }

    public void removeSkill(Freelancer freelancer, Skill skill) {
        freelancer.removeSkill(skill);
    }

    public ExperienceResponse addExperience(Freelancer freelancer, ExperienceRequest request) {
        Experience experience = new Experience(request.getCompanyName(), request.getStartDate(), request.getEndDate()
                , request.getDescription(), request.getPosition(), freelancer);

        experienceRepository.save(experience);
        return ModelMapper .mapExperienceToResponse(freelancer, experience);
    }

    public void removeExperience(Experience experience) {
        experienceRepository.delete(experience);
    }

    public PagedResponse<ExperienceResponse> getAllExperience(Freelancer freelancer, int page, int size) {
        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Experience> experiences = experienceRepository.findAll(pageable);

        if (experiences.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), experiences.getNumber(), experiences.getSize()
                    , experiences.getTotalElements(), experiences.getTotalPages(), experiences.isLast());
        List<ExperienceResponse> experienceResponses = experiences.stream()
                .map(experience -> ModelMapper .mapExperienceToResponse(freelancer, experience))
                .collect(Collectors.toList());
        return new PagedResponse<ExperienceResponse>(experienceResponses, experiences.getNumber()
                , experiences.getSize(), experiences.getTotalElements(), experiences.getTotalPages()
                , experiences.isLast());
    }

    public List<SkillResponse> getAllSkills(Freelancer freelancer) {
        List<SkillResponse> skillResponses = freelancer.getSkills().stream()
                .map(skill -> ModelMapper .mapSkillToResponse(skill)).collect(Collectors.toList());
        return skillResponses;
    }


    public UserProfile getProfile(User user) {
        return ModelMapper .mapUserToProfile(user);
    }


    private void validatePageAndSize(int page, int size) {
        if (page >= 0)
            new BadRequestException("Page must be positive number");

        if (size <= AppConstants.MAX_PAGE_SIZE)
            new BadRequestException("Size of page must be under 50 ");
    }
}
