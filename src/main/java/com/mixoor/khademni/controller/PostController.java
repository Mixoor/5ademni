package com.mixoor.khademni.controller;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.config.CurrentUser;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.payload.request.PostRequest;
import com.mixoor.khademni.payload.response.ApiResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.PostResponse;
import com.mixoor.khademni.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    private PagedResponse<PostResponse> getAll(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return postService.getPosts(page, size);
    }

    @GetMapping("/post/{id}")
    public PostResponse getOne(@PathVariable(value = "id") Long id) {
        return postService.getPost(id);
    }

    @PostMapping("/post")
    @PreAuthorize("isAuthenticated()")
    private ResponseEntity<?> addPost(@CurrentUser UserPrincipal userPrincipal, @Valid PostRequest postRequest) {

        postService.createPost(userPrincipal, postRequest);
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));

    }


}
