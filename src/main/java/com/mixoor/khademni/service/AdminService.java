package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.model.Role;
import com.mixoor.khademni.model.RoleName;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.StaticPage;
import com.mixoor.khademni.payload.response.UserSummary;
import com.mixoor.khademni.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.mixoor.khademni.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    FreelancerRepository freelancerRepository;
    @Autowired
    ApplicationRepository applicationRepository;
    @Autowired
    VoteRepository voteRepository;
    @Autowired
    JobRepository jobRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    TicketRepository ticketRepository;



    public PagedResponse<UserSummary> userSummaryPagedResponse(RoleName role, int page , int size){

        Pageable pageable = PageRequest.of(page,size,Sort.Direction.DESC,"createdAt");

        List<RoleName> roleNames=new ArrayList<RoleName>();
        roleNames.add(role);
        Page<User> userPage =userRepository.findAllByRole(roleNames,pageable);

        if(userPage.getTotalElements()==0)
            return new PagedResponse<>(Collections.emptyList(),page,size,userPage.getTotalElements(),userPage.getTotalPages(),userPage.isLast());

        List<UserSummary> userSummaries  = userPage.stream().map(user -> {
             return ModelMapper.mapUserToUserSummary(user);
        }).collect(Collectors.toList());

        return new PagedResponse<UserSummary>(userSummaries,userPage.getNumber(),userPage.getSize(),userPage.getTotalElements(),userPage.getTotalPages(),userPage.isLast());

    }

    public StaticPage staticPage (){
        Long users,clients,freelancers,jobsFinished,jobsUnfinished,applications
                ,post,comments,projects,votes,tickets;


        users= userRepository.count();
        clients=clientRepository.count();
        freelancers= freelancerRepository.count();

        jobsFinished=jobRepository.countByAvailble(true);
        jobsUnfinished=jobRepository.countByAvailble(false);

        applications=applicationRepository.count();
        post=postRepository.count();
        comments=commentRepository.count();
        projects=projectRepository.count();

        votes=voteRepository.count();
        tickets=voteRepository.count();

        return new StaticPage(users,clients,freelancers,jobsFinished,jobsUnfinished,applications,
                post,comments,projects,votes,tickets);

    }


    public void deleteUsers(Long[] ids) {

        List<User> users= userRepository.findByIdIn(Arrays.asList(ids));

        users.forEach(user -> userRepository.delete(user));
    }
}
