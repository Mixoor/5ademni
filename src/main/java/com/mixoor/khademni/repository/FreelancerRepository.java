package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface FreelancerRepository extends JpaRepository<Freelancer, Long> {

    Optional<Freelancer> findByEmail(String email);

    List<Freelancer> findByIdIn(List<Long> ids);

    Boolean existsByEmail(String email);


}
