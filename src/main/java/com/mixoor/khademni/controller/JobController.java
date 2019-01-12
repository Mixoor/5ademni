package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.payload.request.JobRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.ContractResponse;
import com.mixoor.khademni.payload.response.JobResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.JobRepository;
import com.mixoor.khademni.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    @GetMapping("/jobs")
    public PagedResponse<JobResponse> getAllJobs(@CurrentUser UserPrincipal current,
                                                 @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                 @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getAllJobsAvailable(current, true, page, size);

    }


    @GetMapping("/jobs/searchByTitle")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> getJobsByTitle(@CurrentUser UserPrincipal current,
                                                     @RequestParam(value = "title", defaultValue = "", required = true) String title,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getJobsByTitle(current, title, true, page, size);

    }

    @GetMapping("/jobs/searchBySkill")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> getJobsBySkill(@CurrentUser UserPrincipal current,
                                                     @RequestParam(value = "skill", defaultValue = "", required = true) String skill,
                                                     @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getJobsBySkills(skill.split(","), page, size);

    }


    @GetMapping("/jobs/client")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> getJobsByClient(@CurrentUser UserPrincipal current,
                                                      @RequestParam(value = "id", defaultValue = "", required = true) Long id,
                                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getAlljobsbyClient(id, page, size);


    }

    @GetMapping("/jobs/freelancer")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<JobResponse> getJobsByFreelancer(@CurrentUser UserPrincipal current,
                                                          @RequestParam(value = "id", defaultValue = "", required = true) Long id,
                                                          @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getAllJobsByFreelancer(id, page, size);


    }

    @GetMapping("/jobs/contract")
    @PreAuthorize("hasAnyRole('ROLE_FREELANCER','ROLE_CLIENT')")
    public PagedResponse<ContractResponse> getJobswithContract(@CurrentUser UserPrincipal current,
                                                               @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return jobService.getContract(current, page, size);


    }


    @GetMapping("/job/{id}")
    public JobResponse getJob(@PathVariable(value = "id") Long id) {

        return jobService.getJobById(id);
    }

    @PostMapping("/job")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> createJob(@CurrentUser UserPrincipal userPrincipal, @Valid JobRequest jobRequest) {
        Job job = jobService.createJob(userPrincipal, jobRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(job.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Job Created successfully "));
    }


    @PostMapping("/job/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> setFreelancerAndClose(@CurrentUser UserPrincipal userPrincipal, @PathVariable(value = "id") Long id,
                                                   Long freelancer) {
        jobService.setFreelancerAndClose(id, freelancer);
        return ResponseEntity.ok().body(new ApiResponse(true, "Update finish successfully"));

    }


    @PutMapping("/job/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public Job updateJob(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long id, @Valid JobRequest job) {
        return jobService.UpdateJob(userPrincipal, job);

    }


}
