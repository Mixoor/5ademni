package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.*;
import com.mixoor.khademni.payload.request.SignUpRequest;
import com.mixoor.khademni.payload.request.ExperienceRequest;
import com.mixoor.khademni.payload.response.*;
import com.mixoor.khademni.repository.*;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    JobRepository jobRepository ;

    @Autowired
    ClientRepository clientRepository;



    public User CreateUser(SignUpRequest signUpRequest) {
        User user = new Client();

        SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/DD", Locale.ENGLISH);

        if(!signUpRequest.getConfirm().equals(signUpRequest.getPassword()))
            throw new BadRequestException("Password invalid ");
        user.setAboutMe(signUpRequest.getAboutMe());
        user.setName(signUpRequest.getName());
        user.setAddress(signUpRequest.getAdresse());
        user.setCity(signUpRequest.getCity());
        user.setCountry(signUpRequest.getCountry());
        try {
            user.setDob(format.parse(signUpRequest.getDob()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setEmail(signUpRequest.getEmail());


        user.setGender(signUpRequest.getGender() == 0 ? Gender.male : Gender.female);
        user.setPhone_number(signUpRequest.getPhone());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        user.setPath(profilePictureService.storeProfilePicture(signUpRequest.getPicture()));

        return user;
    }

    public List<SkillResponse> setSkills(UserPrincipal userPrincipal,List<String> skills){
        Freelancer freelancer = freelancerRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new BadRequestException("User invalid"));
        List<Skill> skill = skillRepository.getAll(skills);


        Freelancer updatedFreelancer=removeSkills(freelancer,freelancer.getSkills());


        //remove all redundant values
        Set<Skill> skillSet= new HashSet<Skill>(skill);

        skillSet.forEach(skill1 -> updatedFreelancer.addSkill(skill1));

        return skillSet.stream().map(skill1 -> ModelMapper.mapSkillToResponse(skill1))
                .collect(Collectors.toList());

    }

    public Freelancer removeSkills(Freelancer freelancer, Set<Skill> skills) {

        skills.forEach(skill -> freelancer.removeSkill(skill));
        return freelancerRepository.save(freelancer);

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




    public PagedResponse<UserSummary> getUsers(UserPrincipal userPrincipal, int page, int size, String type){
        List<RoleName> roleNames=new ArrayList<RoleName>();
        if(type.equals("client"))
        roleNames.add(RoleName.ROLE_CLIENT);
        else
        roleNames.add(RoleName.ROLE_FREELANCER);

    if(userPrincipal.getAuthorities().toArray()[0].toString().equals("ROLE_ADMIN")){
        roleNames.add(RoleName.ROLE_ADMIN);
        roleNames.add(RoleName.ROLE_FREELANCER);
        roleNames.add(RoleName.ROLE_CLIENT);
    }

    Pageable pageable =PageRequest.of(page,size,Sort.Direction.DESC,"createdAt");

    Page<User> userPage=userRepository.findAllByRole(roleNames,pageable);

    if(userPage.getTotalElements() == 0 )
        return new PagedResponse<>(Collections.emptyList(),userPage.getNumber(),userPage.getSize(),userPage.getTotalElements(),userPage.getTotalPages(),userPage.isLast());

    List<UserSummary> userSummaries= userPage.stream().map(user -> ModelMapper.mapUserToUserSummary(user))
            .collect(Collectors.toList());

    return new PagedResponse<UserSummary>(userSummaries,userPage.getNumber(),userPage.getSize(),
            userPage.getTotalElements(),userPage.getTotalPages(),userPage.isLast());

    }

    public UserStatic userStatic(UserPrincipal userPrincipal ){
        User user = userRepository.getOne(userPrincipal.getId());
        if(user.getRole().getName()==RoleName.ROLE_FREELANCER){

            Freelancer freelancer = freelancerRepository.findById(user.getId()).orElseThrow(() -> new BadRequestException("User invalid "));
            return new UserStatic(
                    jobRepository.countByFreelancerAndAvailble(freelancer,true),
                    freelancer.getCredit(),
                    jobRepository.countByFreelancerAndAvailble(freelancer,false)
            );

        }else{
            Client client = clientRepository.findById(user.getId()).orElseThrow(() -> new BadRequestException("User invalid "));
            return new UserStatic(
                    jobRepository.countByClientAndAvailble(client,true),
                    client.getCredit(),
                    jobRepository.countByClientAndAvailble(client,false)
            );

        }
    }

    private void validatePageAndSize(int page, int size) {
        if (page >= 0)
            new BadRequestException("Page must be positive number");

        if (size <= AppConstants.MAX_PAGE_SIZE)
            new BadRequestException("Size of page must be under 50 ");
    }
}
