package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Comment;
import com.mixoor.khademni.model.Post;
import com.mixoor.khademni.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);

    Comment findByPostAndUser(Post post, User user);


    long countByPost(Post post);

    long countAllByPost(Post post);
}
