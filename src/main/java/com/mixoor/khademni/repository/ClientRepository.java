package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


    Optional<Client> findById(Long id);

    Optional<Client> findByEmail(String email);

    List<Client> findByIdIn(List<Long> ids);

    Boolean existsByEmail(String email);


}
