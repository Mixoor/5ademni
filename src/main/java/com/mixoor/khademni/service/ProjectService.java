package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Project;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.model.Vote;
import com.mixoor.khademni.payload.request.NotificationRequest;
import com.mixoor.khademni.payload.request.ProjectRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.ProjectResponse;
import com.mixoor.khademni.repository.ProjectRepository;
import com.mixoor.khademni.repository.UserRepository;
import com.mixoor.khademni.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    NotificationService notificationService;


    public ProjectResponse createProject(UserPrincipal userPrincipal, ProjectRequest projectRequest) {

        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new BadRequestException("User doesn't exist"));

        Project project = ModelMapper .mapRequestToProject(projectRequest, user);
        projectRepository.save(project);

        return ModelMapper .mapProjectToResponse(project, voteRepository.countByProject(project.getId()),false);

    }

    public PagedResponse<ProjectResponse> getProjects(UserPrincipal userPrincipal,int page, int size) {
        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Project> projects = projectRepository.findAll(pageable);

        if (projects.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), projects.getNumber()
                    , projects.getSize(), projects.getTotalElements(), projects.getTotalPages()
                    , projects.isLast());

        List<ProjectResponse> projectResponses = projects.stream().map((p) -> {
            boolean b=voteRepository.Isvoted(userPrincipal.getId(),p.getId())!= null;
            return  ModelMapper .mapProjectToResponse(p, voteRepository.countByProject(p.getId()),b);

        } ).collect(Collectors.toList());

        return new PagedResponse<ProjectResponse>(projectResponses, projects.getNumber(), projects.getSize()
                , projects.getTotalElements(), projects.getTotalPages(), projects.isLast());


    }

    public ProjectResponse getProjectById(UserPrincipal userPrincipal,Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Project doesn't exist"));
        boolean b=voteRepository.Isvoted(userPrincipal.getId(),id)!= null;

        return ModelMapper .mapProjectToResponse(project, voteRepository.countByProject(id),b);

    }

    public long castVote(UserPrincipal userPrincipal, Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Project doesn't exist "));
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException("User doesn't exist"));
        Vote vote = new Vote(project, user);
        voteRepository.findByUserAndProject(user, project)
                .orElse(voteRepository.save(vote));

        //Notifier the creator
        NotificationRequest notificationRequest= new NotificationRequest(project.getCreated().getId(),id+"",8);
        notificationService.createNotification(userPrincipal,notificationRequest);

        return voteRepository.countByProject(project.getId());
    }


    public long deleteVote(UserPrincipal userPrincipal, Long id) {

        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Project doesn't exist "));
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new BadRequestException("User doesn't exist"));
        Vote vote = voteRepository.findByUserAndProject(user, project).orElseThrow(() ->
                new BadRequestException("Vote doesn't exist "));

        voteRepository.delete(vote);

        return voteRepository.countByProject(project.getId());
    }


    private void validatePageAndSize(int page, int size) {

        if (page < 0)
            new BadRequestException("Page must be postive numbre ");

        if (size > AppConstants.MAX_PAGE_SIZE)
            new BadRequestException("Size of page must be less than " + AppConstants.MAX_PAGE_SIZE);
    }


}
