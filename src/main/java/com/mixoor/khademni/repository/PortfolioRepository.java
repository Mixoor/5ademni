package com.mixoor.khademni.repository;


import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    Page<Portfolio> findByFreelancer(Freelancer freelancer, Pageable page);

}
