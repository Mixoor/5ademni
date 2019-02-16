package com.mixoor.khademni.service;


import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.UnauthorizedException;
import com.mixoor.khademni.model.*;
import com.mixoor.khademni.payload.request.NotificationRequest;
import com.mixoor.khademni.payload.request.ReplayRequest;
import com.mixoor.khademni.payload.request.ReviewRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.ReviewResponse;
import com.mixoor.khademni.repository.ClientRepository;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.repository.JobRepository;
import com.mixoor.khademni.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {


    @Autowired
    JobRepository jobRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    NotificationService notificationService;


    public ReviewResponse createReview(UserPrincipal userPrincipal, ReviewRequest reviewRequest){


        Job job = jobRepository.hasJob(reviewRequest.getClient(),reviewRequest.getFreelancer());

        if(job == null || reviewRequest.getClient() != userPrincipal.getId())
        throw new BadRequestException("You can't review a freelancer without making a contract with him ");

        Freelancer freelancer= freelancerRepository.findById(reviewRequest.getFreelancer()).orElseThrow(() ->  new BadRequestException("Freelancer doesn't exist"));
        Client client= clientRepository.findById(userPrincipal.getId()).orElseThrow(() ->  new BadRequestException("Client doesn't exist"));

        Review review  = ModelMapper.mapResponseToReview(reviewRequest,client,freelancer);
        review=reviewRepository.save(review);

        NotificationRequest notificationRequest= new NotificationRequest(review.getFreelancer().getId(),freelancer.getId()+"?review="+client.getId(),6);
        notificationService.createNotification(userPrincipal,notificationRequest);


        return ModelMapper.mapReviewToResponse(review);

    }

    public ReviewResponse updateReview(UserPrincipal userPrincipal, ReviewRequest reviewRequest){

        Review review= reviewRepository.findById(new ReviewId(reviewRequest.getFreelancer(),reviewRequest.getClient())).orElseThrow(()->new BadRequestException("Review Doesn't exist"));

        review.setMessage(reviewRequest.getMessage());
        review.setRate(reviewRequest.getRate());
        review.setTitle(reviewRequest.getTitle());

        review= reviewRepository.save(review);

        return ModelMapper.mapReviewToResponse(review);
    }

     public PagedResponse<ReviewResponse>  getPagedResponseForFreelancer(Long id,int size,int page){
        Freelancer freelancer= freelancerRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Freelancer doesn't exist"));

        Pageable pageable =PageRequest.of(page,size,Sort.Direction.DESC,"createdAt");
        Page<Review> reviewPage =reviewRepository.findAllByFreelancer(freelancer,pageable);

        if(reviewPage.getTotalElements()==0)
            return new PagedResponse<>(Collections.emptyList(),reviewPage.getNumber(),reviewPage.getSize(),
                    reviewPage.getTotalElements(),reviewPage.getTotalPages(),reviewPage.isLast());

        List<ReviewResponse> reviewResponses = reviewPage.stream().map(review -> {
            return ModelMapper.mapReviewToResponse(review);
        }).collect(Collectors.toList());

        return new PagedResponse<>(reviewResponses,
                reviewPage.getNumber(),reviewPage.getSize(),
                reviewPage.getTotalElements()
                ,reviewPage.getTotalPages(),reviewPage.isLast());

    }

    public PagedResponse<ReviewResponse>  getPagedResponseForClient(Long id,int size,int page){
        Client client= clientRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Client doesn't exist"));

        Pageable pageable =PageRequest.of(page,size,Sort.Direction.DESC,"createdAt");
        Page<Review> reviewPage =reviewRepository.findAllByClient(client,pageable);

        if(reviewPage.getTotalElements()==0)
            return new PagedResponse<>(Collections.emptyList(),reviewPage.getNumber(),reviewPage.getSize(),
                    reviewPage.getTotalElements(),reviewPage.getTotalPages(),reviewPage.isLast());

        List<ReviewResponse> reviewResponses = reviewPage.stream().map(review -> {
            return ModelMapper.mapReviewToResponse(review);
        }).collect(Collectors.toList());

        return new PagedResponse<>(reviewResponses,
                reviewPage.getNumber(),reviewPage.getSize(),
                reviewPage.getTotalElements()
                ,reviewPage.getTotalPages(),reviewPage.isLast());
    }

    public ReviewResponse AddReplay(UserPrincipal userPrincipal, ReplayRequest replayRequest){

        // Getting the Review
          Review review = reviewRepository.findById(new ReviewId(userPrincipal.getId(),replayRequest.getFreelancer())).orElseThrow(
                  ()->new BadRequestException("Review Invalid ")
          );

          review.setReply(replayRequest.getReplay());
          review= reviewRepository.save(review);
        NotificationRequest notificationRequest= new NotificationRequest(review.getClient().getId(),review.getFreelancer().getId()+"?review="+review.getClient().getId(),7);
        notificationService.createNotification(userPrincipal,notificationRequest);

        return ModelMapper.mapReviewToResponse(review);

    }


    public void deleteReplay(UserPrincipal userPrincipal,Long client,Long freelancer) {
        if(client != userPrincipal.getId())
            throw new UnauthorizedException("Unauthorized Exception");

        Client cl = clientRepository.findById(client).orElseThrow(()-> new BadRequestException("User invalid "));
        Freelancer fr = freelancerRepository.findById(freelancer).orElseThrow(()->
        new BadRequestException("User Invalid "));

        Review review= reviewRepository.findById(new ReviewId(freelancer,client)).orElseThrow(()->
        new BadRequestException("Review Invalid"));


        reviewRepository.delete(review);


    }

    public void deleteReview(UserPrincipal userPrincipal ,Long freelancer){
        Review  review = reviewRepository.findById(new ReviewId(freelancer,userPrincipal.getId())).orElseThrow(()-> new BadRequestException("Review doesn't exist"));

        reviewRepository.delete(review);
    }









}
