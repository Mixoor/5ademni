package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.ResourceNotFoundException;
import com.mixoor.khademni.exception.UnauthorizedException;
import com.mixoor.khademni.model.*;
import com.mixoor.khademni.payload.request.JobRequest;
import com.mixoor.khademni.payload.response.ContractResponse;
import com.mixoor.khademni.payload.response.JobResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.UploadFileResponse;
import com.mixoor.khademni.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    DocumentStorageService documentStorageService;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private FreelancerRepository freelancerRepository;

    private ModelMapper modelMapper = new ModelMapper();


    public Job createJob(UserPrincipal current, JobRequest jobRequest) {

        Client client = clientRepository.findById(current.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client ", "id", String.valueOf(current.getId())));

        ModelMapper modelMapper = new ModelMapper();

        Job job = modelMapper.mapJobRequestToJob(jobRequest, client);
        List<Document> document=jobRequest.getFiles().stream()
                .map( (f) ->
             documentStorageService
                     .documentToJob(documentStorageService.storeFile(f)
                             ,client,job)
        ).collect(Collectors.toList());


        return job;
    }

    public Job UpdateJob(UserPrincipal current, JobRequest jobRequest) {

        Client client = clientRepository.findById(current.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client ", "id", String.valueOf(current.getId())));

        if(client.getId().equals(current.getId()))
            throw new UnauthorizedException("Unauthorized Error");

        ModelMapper modelMapper = new ModelMapper();

        Job job = modelMapper.mapJobRequestToJob(jobRequest, client);
        List<Document> document=jobRequest.getFiles().stream()
                .map( (f) ->
                        documentStorageService
                                .documentToJob(documentStorageService.storeFile(f)
                                        ,client,job)
                ).collect(Collectors.toList());


        return job;
    }





    public PagedResponse<JobResponse> getAllJobsAvailable(UserPrincipal current, boolean b, int page, int size) {
        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Job> jobs = jobRepository.findAllByAvailble(b, pageable);



        ModelMapper modelMapper = new ModelMapper();

        if (jobs.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), jobs.getNumber()
                    , jobs.getSize()
                    , jobs.getTotalElements()
                    , jobs.getTotalPages(), jobs.isLast());

        List<JobResponse> jobResponses = jobs.stream().map(job -> modelMapper.mapJobtoJobResponse(job, job.getClient()))
                .collect(Collectors.toList());

        PagedResponse<JobResponse> jobResponsePagedResponse =
                new PagedResponse<>(jobResponses, jobs.getNumber()
                        , jobs.getSize()
                        , jobs.getTotalElements()
                        , jobs.getTotalPages(), jobs.isLast());

        return jobResponsePagedResponse;

    }

    public PagedResponse<JobResponse> getJobsByTitle(UserPrincipal current, String title, boolean b, int page, int size) {
        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Job> jobs = jobRepository.findAllByAvailble(b, pageable);

        ModelMapper modelMapper = new ModelMapper();

        if (jobs.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), jobs.getNumber()
                    , jobs.getSize()
                    , jobs.getTotalElements()
                    , jobs.getTotalPages(), jobs.isLast());

        List<JobResponse> jobResponses = jobs.stream().filter((job) -> job.getTitle().contains(title))
                .map(job ->
                        modelMapper.mapJobtoJobResponse(job, job.getClient())
                )
                .collect(Collectors.toList());

        Page<JobResponse> jobPage = new PageImpl<JobResponse>(jobResponses);

        PagedResponse<JobResponse> jobResponsePagedResponse =
                new PagedResponse<>(jobResponses, jobPage.getNumber()
                        , jobPage.getSize()
                        , jobPage.getTotalElements()
                        , jobPage.getTotalPages(), jobPage.isLast());

        return jobResponsePagedResponse;

    }

    public JobResponse getJobById(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job", "id", String.valueOf(id)));

        JobResponse jobResponse = new ModelMapper().mapJobtoJobResponse(job, job.getClient());

        return jobResponse;

    }

    public PagedResponse<JobResponse> getAllJobsByFreelancer(Long id, int size, int page) {

        validatePageAndSize(page, size);

        Freelancer freelancer = freelancerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Freelancer", "id", String.valueOf(id)));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Job> jobs = jobRepository.findByFreelancer(freelancer, pageable);

        if (jobs.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), page, size, jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());

        List<JobResponse> jobResponses = jobs.stream()
                .map((j) -> {

                    JobResponse jobResponse = modelMapper.mapJobtoJobResponse(j, j.getClient());
                    return jobResponse;
                }).collect(Collectors.toList());

        return new PagedResponse<JobResponse>(jobResponses, jobs.getTotalPages(), jobResponses.size()
                , jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());
    }

    public PagedResponse<JobResponse> getAlljobsbyClient(Long id, int size, int page) {
        validatePageAndSize(page, size);

        Client client = clientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", String.valueOf(id)));

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Job> jobs = jobRepository.findByClient(client, pageable);

        if (jobs.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), page, size, jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());


        List<JobResponse> jobResponses = jobs.stream().map((job) ->
                modelMapper.mapJobtoJobResponse(job, job.getClient())).collect(Collectors.toList());

        return new PagedResponse<JobResponse>(jobResponses, jobs.getNumber(), jobs.getSize()
                , jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());

    }

    public JobResponse setFreelancerAndClose(Long job, Long freelancer) {

        Freelancer freelancer1 = freelancerRepository.findById(freelancer).orElseThrow(() ->
                new BadRequestException("Freelancer with " + freelancer + "  doesn't exists "));

        Job job1 = jobRepository.findById(job).orElseThrow(() -> new BadRequestException("Job doesn't exists"));

        job1.setFreelancer(freelancer1);
        job1.setAvailble(false);

        return modelMapper.mapJobtoJobResponse(job1, job1.getClient());

    }


    public PagedResponse<ContractResponse> getContract(UserPrincipal user, int page, int size) {

        validatePageAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "updateAt");
        Page<Job> jobs;

        if (user.getAuthorities().toArray()[0].toString() == "ROLE_CLIENT") {
            Client u = clientRepository.findById(user.getId()).orElseThrow(() -> new BadRequestException("User is not available "));
            jobs = jobRepository
                    .findByClientAndAvailble(u, false, pageable);
        } else {
            Freelancer u = freelancerRepository.findById(user.getId()).orElseThrow(() -> new BadRequestException("User is not available "));
            jobs = jobRepository
                    .findByFreelancerAndAvailble(u, false, pageable);
        }


        if (jobs.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), jobs.getNumber(), jobs.getSize(), jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());


        List<ContractResponse> list = jobs.stream().map(job -> modelMapper.mapJobToContract(job)).collect(Collectors.toList());

        return new PagedResponse<ContractResponse>(list, jobs.getNumber(), jobs.getSize(), jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());


    }

    public PagedResponse<JobResponse> getJobsBySkills(String[] skills, int page, int size) {
        validatePageAndSize(page, size);

        List<Skill> skillList = Arrays.stream(skills).map((s) -> skillRepository.findByName(s).orElseThrow(() -> new BadRequestException("Skill not found")))
                .collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Job> jobs = jobRepository.findAllByAvailble(true, pageable);

        if (jobs.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), jobs.getNumber(), jobs.getSize(), jobs.getTotalElements(), jobs.getTotalPages(), jobs.isLast());

        List<Job> filteredJob = jobs.stream().filter(job -> {
            return job.getSkills().containsAll(skillList);
        }).collect(Collectors.toList());

        Page<Job> jobPage = new PageImpl<Job>(filteredJob);


        List<JobResponse> jobResponses = filteredJob.stream().map(job -> {
            Client client = clientRepository.findById(job.getClient().getId()).orElseThrow(() -> new BadRequestException("Client doesnt exist"));
            return modelMapper.mapJobtoJobResponse(job, client);
        })
                .collect(Collectors.toList());


        return new PagedResponse<JobResponse>(jobResponses, jobPage.getNumber(), jobPage.getSize(),
                jobPage.getTotalElements(), jobPage.getTotalPages(), jobPage.isLast());

    }


    private void validatePageAndSize(int page, int size) {

        if (page < 0)
            new BadRequestException("Page must be postive numbre ");

        if (size > AppConstants.MAX_PAGE_SIZE)
            new BadRequestException("Size of page must be less than " + AppConstants.MAX_PAGE_SIZE);
    }


}
