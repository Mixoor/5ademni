package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Client;
import com.mixoor.khademni.model.Freelancer;
import com.mixoor.khademni.model.Review;
import com.mixoor.khademni.model.ReviewId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,ReviewId> {



    Page<Review> findAllByFreelancer(Freelancer freelancer, Pageable pageable);

    Page<Review> findAllByClient(Client client, Pageable pageable);


    Optional<Review> findByClientAndFreelancer(Client client, Freelancer freelancer);


}
