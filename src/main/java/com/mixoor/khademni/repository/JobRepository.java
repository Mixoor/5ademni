package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Client;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByIdAndAndAvailble(Long aLong, boolean b, Pageable pageable);

    Page<Job> findAllByAvailble(boolean available, Pageable pageable);

    Page<Job> findByClient(Client client, Pageable pageable);

    Page<Job> findBySkillsAndAndAvailble(List<Skill> skill, boolean b, Pageable pageable);

    Page<Job> findByClientAndAvailble(Client client, boolean available, Pageable pageable);

    Page<Job> findByFreelancerAndAvailble(Freelancer freelancer, boolean available, Pageable pageable);

    Page<Job> findBySkills(List<Skill> skills, Pageable pageable);

    Page<Job> findByFreelancer(Freelancer freelancer, Pageable pageable);


}
