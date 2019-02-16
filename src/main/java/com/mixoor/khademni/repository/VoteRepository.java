package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Project;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.model.Vote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT count(v.id) from Vote v where v.project.id = :projectId ")
    long countByProject(@Param("projectId") Long projectId);

    @Query("SELECT v from Vote v where v.user.id= :userId")
    List<Vote> findByUser(@Param("userId") Long userId);

    Page<Vote> findAll(Pageable pageable);

    @Query("select v from Vote v where v.project.id= :project and v.user.id= :user")
    Vote Isvoted(@Param("user") Long user, @Param("project") Long project);

    Optional<Vote> findByUserAndProject(User user, Project project);
}
