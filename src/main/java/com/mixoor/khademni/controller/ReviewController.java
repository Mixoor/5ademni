package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.request.ReplayRequest;
import com.mixoor.khademni.payload.request.ReviewRequest;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.ReviewResponse;
import com.mixoor.khademni.repository.JobRepository;
import com.mixoor.khademni.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    ReviewService reviewService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> createReview(@CurrentUser UserPrincipal userPrincipal, ReviewRequest reviewRequest){
        return ResponseEntity.ok().body(reviewService.createReview(userPrincipal,reviewRequest));
    }

    @PutMapping("/")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ReviewResponse updateReview(@CurrentUser UserPrincipal userPrincipal,ReviewRequest reviewRequest){
        return  reviewService.updateReview(userPrincipal,reviewRequest);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> deleteReview(@CurrentUser UserPrincipal userPrincipal,@PathVariable(name = "id") Long id){
        reviewService.deleteReview(userPrincipal,id);

        return ResponseEntity.ok().body("Review Deleted successfully");
    }



    @GetMapping("/client/{id}")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<ReviewResponse> getAllByClient(UserPrincipal userPrincipal,@PathVariable(name = "id" ,required = true) Long id ,@RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page ,
                                                @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return reviewService.getPagedResponseForClient(id,size,page);
    }

    @GetMapping("/freelancer/{id}")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<ReviewResponse> getAllByFreelancer(UserPrincipal userPrincipal,@PathVariable(name = "id" ,required = true) Long id ,@RequestParam(value = "page",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page ,
                                                @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size){
        return reviewService.getPagedResponseForFreelancer(id,size,page);
    }




    @PostMapping("/replay")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public ResponseEntity<?> createReplay(@CurrentUser UserPrincipal userPrincipal, ReplayRequest replayRequest){
        return ResponseEntity.ok().body(reviewService.AddReplay(userPrincipal,replayRequest));
    }


    @PutMapping("/replay")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<?> updateReplay(UserPrincipal userPrincipal,ReplayRequest replayRequest){
        return  ResponseEntity.ok().body(reviewService.AddReplay(userPrincipal,replayRequest));
    }

    @DeleteMapping("/replay")
    @PreAuthorize("hasRole('ROLE_FREELANCER')")
    public ResponseEntity<?> deleteReplay(UserPrincipal userPrincipal,Long client,Long freelancer){
        reviewService.deleteReplay(userPrincipal,client,freelancer);
        return  ResponseEntity.ok().body("Replay deleted successfully");
    }








}
