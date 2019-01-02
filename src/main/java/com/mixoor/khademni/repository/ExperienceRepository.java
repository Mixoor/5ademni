package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Experience;
import com.mixoor.khademni.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {


    List<Experience> findByFreelancer(Freelancer freelancer);
}
