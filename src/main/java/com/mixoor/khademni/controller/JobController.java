package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.payload.request.JobRequest;
import com.mixoor.khademni.payload.request.NotificationRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.ContractResponse;
import com.mixoor.khademni.payload.response.JobResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.JobRepository;
import com.mixoor.khademni.service.JobService;
import com.mixoor.khademni.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public PagedResponse<JobResponse> getAllJobs(@CurrentUser UserPrincipal current,
                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getAllJobsAvailable(current, true, page, size);

    }




    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> search(@CurrentUser UserPrincipal current,
                                                     @RequestParam(value = "skill", defaultValue = AppConstants.DEFAULT_SKILL) List<String> skill,
                                                     @RequestParam(value = "title", defaultValue = AppConstants.DEFAULT_TITLE) String title,
                                                     @RequestParam(value = "min", defaultValue = "0") Long min,
                                                     @RequestParam(value = "max", defaultValue = "10000000") Long max,
                                                     @RequestParam(value = "delai", defaultValue =AppConstants.DEFAULT_DELAI ) int delai,
                                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size ,
                                                     @RequestParam(value = "order", defaultValue = AppConstants.DEFAULT_DIRECTION) String order) {
        return jobService.searchJob(current,page,size,skill,delai,min,max,title,order);

    }


    @GetMapping("/client")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> getJobsByClient(@CurrentUser UserPrincipal current,
                                                      @RequestParam(value = "id", defaultValue = "", required = true) Long id,
                                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getAlljobsbyClient(id, page, size);


    }

    @GetMapping("/freelancer")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> getJobsByFreelancer(@CurrentUser UserPrincipal current,
                                                          @RequestParam(value = "id", defaultValue = "", required = true) Long id,
                                                          @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getAllJobsByFreelancer(id, page, size);


    }

    @GetMapping("/contract")
    @PreAuthorize("hasAnyRole('ROLE_FREELANCER','ROLE_CLIENT')")
    public PagedResponse<ContractResponse> getJobsWithContract(@CurrentUser UserPrincipal current,
                                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getContract(current, page, size);


    }


    @GetMapping("/{id}")
    public JobResponse getJob(@PathVariable(value = "id") Long id) {

        return jobService.getJobById(id);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> createJob(@CurrentUser UserPrincipal userPrincipal, @Valid JobRequest jobRequest) {
        Job job = jobService.createJob(userPrincipal, jobRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(job.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Job Created successfully "));
    }


    @PostMapping("/set/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> setFreelancerAndClose(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "id") Long id,
                                                   Long freelancer) {
        jobService.setFreelancerAndClose(id, freelancer);
        notificationService.createNotification(userPrincipal,new NotificationRequest(freelancer,""+id,4));
        return ResponseEntity.ok().body(new ApiResponse(true, "Update finish successfully"));

    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CLIENT,ROLE_ADMIN')")
    public Job updateJob(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id, @Valid JobRequest job) {
        return jobService.UpdateJob(userPrincipal, job,id);

    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteFreelancer(@CurrentUser UserPrincipal userPrincipal, NotificationRequest notificationRequest){
        notificationService.createNotification(userPrincipal,notificationRequest);
        return  ResponseEntity.ok().body("Invitation was send");

    }


}
