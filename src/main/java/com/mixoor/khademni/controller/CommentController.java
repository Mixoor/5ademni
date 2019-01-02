package com.mixoor.khademni.controller;


import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.payload.request.CommentRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.CommentResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.repository.CommentRepository;
import com.mixoor.khademni.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CommentController {


    @Autowired
    PostService postService;

    @Autowired
    CommentRepository commentRepository;


    @GetMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public PagedResponse<CommentResponse> getComments(@PathVariable(value = "id") Long id,
                                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return postService.getComments(id, page, size);
    }

    @PostMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public CommentResponse addComment(@CurrentUser UserPrincipal user, @PathVariable(value = "id") Long id, @Valid CommentRequest commentRequest) {
        return postService.addComment(user, id, commentRequest);
    }

    @PutMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public CommentResponse updateComment(@CurrentUser UserPrincipal user, @PathVariable(value = "id") Long id, @Valid CommentRequest commentRequest) {
        return postService.updateComment(user, id, commentRequest);
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteComment(@CurrentUser UserPrincipal user, @PathVariable(value = "id") Long id, @Valid CommentRequest commentRequest) {
        commentRepository.delete(commentRepository.findById(id).orElseThrow(() ->
                new BadRequestException("Comment doesn't exist ")));

        return ResponseEntity.ok().body(new ApiResponse(true, "Comment deleted with successfully"));
    }


}
