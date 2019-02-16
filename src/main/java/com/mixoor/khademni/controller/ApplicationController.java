package com.mixoor.khademni.controller;


import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Application;
import com.mixoor.khademni.model.ApplicationId;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.ApplicationRequest;
import com.mixoor.khademni.payload.request.NotificationRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.ApplicationResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.ApplicationRepository;
import com.mixoor.khademni.repository.JobRepository;
import com.mixoor.khademni.service.ApplicationService;
import com.mixoor.khademni.service.JobService;
import com.mixoor.khademni.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApplicationController {

    @Autowired
    JobService jobService;

    @Autowired
    ApplicationService applicationService;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    JobRepository jobRepository;

    @GetMapping("/application/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT','ROLE_FREELANCER')")
    public PagedResponse<ApplicationResponse> getApplications(@CurrentUser UserPrincipal current,
                                                              @PathVariable(value = "id") Long id,
                                                              @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                              @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return applicationService.getAllApplications(id, page, size);

    }

    @PostMapping("/application")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity createApplication(@CurrentUser UserPrincipal userPrincipal,
                                            @Valid ApplicationRequest applicationRequest) {
        ApplicationResponse applicationResponse=applicationService.createApplication(userPrincipal, applicationRequest);

            User user = jobRepository.getOne(applicationRequest.getJobId()).getClient();
        NotificationRequest notificationRequest = new
                NotificationRequest(user.getId(),applicationResponse.getJob()+"?Application="+applicationResponse.getApplication(),3);

        notificationService.createNotification(userPrincipal,notificationRequest);
        return ResponseEntity.ok().body(new ApiResponse(true, "Application created successfully "));

    }

    @DeleteMapping("/application/{post}")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity deleteApplication(@CurrentUser UserPrincipal userPrincipal,
                                            @PathVariable(value = "post") Long post) {
        Application application = applicationRepository.findById(new ApplicationId(userPrincipal.getId(), post))
                .orElseThrow(() -> new BadRequestException("Application doesn't exist "));

        applicationRepository.delete(application);
        return ResponseEntity.ok().body(new ApiResponse(true, "Application created successfully "));

    }


}
