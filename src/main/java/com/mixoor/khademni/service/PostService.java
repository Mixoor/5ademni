package com.mixoor.khademni.service;

import com.mixoor.khademni.Util.AppConstants;
import com.mixoor.khademni.Util.ModelMapper;
import com.mixoor.khademni.config.UserPrincipal;
import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Comment;
import com.mixoor.khademni.model.Post;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.request.CommentRequest;
import com.mixoor.khademni.payload.request.PostRequest;
import com.mixoor.khademni.payload.response.CommentResponse;
import com.mixoor.khademni.payload.response.PagedResponse;
import com.mixoor.khademni.payload.response.PostResponse;
import com.mixoor.khademni.repository.CommentRepository;
import com.mixoor.khademni.repository.PostRepository;
import com.mixoor.khademni.repository.UserRepository;
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
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    public PostResponse createPost(UserPrincipal current, PostRequest postRequest) {
        User user = userRepository.findById(current.getId())
                .orElseThrow(() -> new BadRequestException("User id invalid "));

        Post post = new ModelMapper().mapResquestToPost(postRequest, user);
        postRepository.save(post);

        return new ModelMapper().mapPostToResponse(post, 0L, user);

    }

    public PagedResponse<PostResponse> getPosts(int page, int size) {
        validatePageAndSize(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Post> posts = postRepository.findAll(pageable);

        if (posts.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), posts.getNumber(), posts.getSize()
                    , posts.getTotalElements(), posts.getTotalPages(), posts.isLast());

        List<PostResponse> postResponses = posts.stream().map((post) -> {
            long commentaries = commentRepository.countAllByPost(post);
            return new ModelMapper().mapPostToResponse(post, commentaries, post.getUser());
        }).collect(Collectors.toList());

        return new PagedResponse<PostResponse>(postResponses, posts.getNumber(), posts.getSize()
                , posts.getTotalElements(), posts.getTotalPages(), posts.isLast());

    }

    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Post with id " + id + " is not valid"));

        long comments = commentRepository.countByPost(post);
        return new ModelMapper().mapPostToResponse(post, comments, post.getUser());

    }

    public CommentResponse addComment(UserPrincipal current, Long postId, CommentRequest commentRequest) {

        User user = userRepository.findById(current.getId()).orElseThrow(() -> new BadRequestException("User doesn't exist"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->
                        new BadRequestException("Post with id " + postId + " doesnt exist"));
        Comment comment = new ModelMapper().mapRequestToComment(commentRequest, post, user);
        commentRepository.save(comment);
        long commentCount = commentRepository.countByPost(post);
        return new ModelMapper().mapCommentToResponse(comment, user);
    }

    public CommentResponse updateComment(UserPrincipal current, Long postId, CommentRequest commentRequest) {


        Comment comment = commentRepository.findById(postId).orElseThrow(() -> new BadRequestException("Comment doesn't exist "));

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);

        return new ModelMapper().mapCommentToResponse(comment, comment.getUser());
    }

    public PagedResponse<CommentResponse> getComments(Long id, int page, int size) {
        validatePageAndSize(page, size);

        Post post = postRepository.findById(id).orElseThrow(() -> new BadRequestException("Post doesn't exist"));
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createAt");
        Page<Comment> comments = commentRepository.findByPost(post, pageable);

        if (comments.getTotalElements() == 0)
            return new PagedResponse<>(Collections.emptyList(), comments.getNumber(), comments.getSize()
                    , comments.getTotalElements(), comments.getTotalPages()
                    , comments.isLast());

        List<CommentResponse> commentResponses = comments.stream().map((comment) ->
                new ModelMapper().mapCommentToResponse(comment, comment.getUser())
        ).collect(Collectors.toList());

        return new PagedResponse<CommentResponse>(commentResponses, comments.getNumber(), comments.getSize()
                , comments.getTotalElements(), comments.getTotalPages(), comments.isLast());

    }


    private void validatePageAndSize(int page, int size) {

        if (page < 0)
            new BadRequestException("Page must be postive numbre ");

        if (size > AppConstants.MAX_PAGE_SIZE)
            new BadRequestException("Size of page must be less than " + AppConstants.MAX_PAGE_SIZE);
    }


}
