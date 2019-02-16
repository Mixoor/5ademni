package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Client;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findAllByAvailble(boolean available, Pageable pageable);

    Page<Job> findByClient(Client client, Pageable pageable);

    Page<Job> findByClientAndAvailble(Client client, boolean available, Pageable pageable);

    Page<Job> findByFreelancerAndAvailble(Freelancer freelancer, boolean available, Pageable pageable);

    Page<Job> findByFreelancer(Freelancer freelancer, Pageable pageable);

    @Query("select j from Job j where  j.title like %:title% and j.availble = true and  (j.budget >= :min and j.budget <= :max) and j.delai >= :delai  order by ?#{#pageable}")
    Page<Job> searchByTitle(@Param("title") String title, @Param("max") Long max, @Param("min") Long min, @Param("delai") int delai, Pageable pageable);

    @Query("select j from Job j where j.skills in :skills and j.title like %:title% and j.availble = true and (j.budget >= :min and j.budget <= :max) and j.delai >= :delai order by ?#{#pageable}")
    Page<Job> searchBySkillsAndTitle(@Param("skills") List<Skill> skills, @Param("title") String title, @Param("max") Long max, @Param("min") Long min, @Param("delai") int delai, Pageable pageable);


    @Query("select count(j) from Job j where j.availble= :ava")
    Long countByAvailble(@Param("ava") Boolean aBoolean);


    @Query("select j from Job j where j.freelancer.id= :freelancer and j.client.id= :client order by j.id DESC")
    Job hasJob(@Param("client") Long client, @Param("freelancer") Long freelancer);







}
