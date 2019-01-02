package com.mixoor.khademni.repository;


import com.mixoor.khademni.model.Application;
import com.mixoor.khademni.model.ApplicationId;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, ApplicationId> {

    Page<Application> findByFreelancer(Freelancer freelancer, Pageable pageable);

    Page<Application> findByJob(Job job, Pageable pageable);


}
