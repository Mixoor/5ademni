package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Application;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.payload.request.ApplicationRequest;
import com.mixoor.khademni.payload.response.ApplicationResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.ApplicationRepository;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.repository.JobRepository;
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
public class ApplicationService {


    @Autowired
    JobRepository jobRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    ModelMapper modelMapper;


    public ApplicationResponse createApplication(UserPrincipal currentUser, ApplicationRequest applicationRequest) {

        Freelancer freelancer = freelancerRepository.findById(currentUser.getId()).orElseThrow(
                () -> new BadRequestException("Freelancer doesn't exist"));

        Job job = jobRepository.findById(applicationRequest.getJobId()).orElseThrow(
                () -> new BadRequestException("Job doesn't exist ")
        );

        Application application = modelMapper.mapRequestToApplication(applicationRequest, freelancer, job);
        applicationRepository.save(application);
        return modelMapper.mapApplicationToResponse(freelancer, application);

    }

    public PagedResponse<ApplicationResponse> getAllApplications(Long jobId, int page, int size) {

        validatePageAndSize(page, size);
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BadRequestException("Job with id : " + jobId + " doesn't exist"));


        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Application> applications = applicationRepository.findByJob(job, pageable);
        if (applications.getTotalElements() == 0)
            return new PagedResponse<>(Collections.EMPTY_LIST
                    , page, size, applications.getTotalElements()
                    , applications.getTotalPages(), applications.isLast());

        List<ApplicationResponse> responseList = applications.stream()
                .map((app) -> modelMapper.mapApplicationToResponse(app.getFreelancer(), app))
                .collect(Collectors.toList());

        return new PagedResponse<ApplicationResponse>(responseList, applications.getNumber()
                , applications.getSize(), applications.getTotalElements(), applications.getTotalPages()
                , applications.isLast());

    }


    private void validatePageAndSize(int page, int size) {
        if (page < 0)
            throw new BadRequestException("Page must be positive number");

        if (size >= AppConstants.MAX_PAGE_SIZE)
            throw new BadRequestException("");
    }
}
