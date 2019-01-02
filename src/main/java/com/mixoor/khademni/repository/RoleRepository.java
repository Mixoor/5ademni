package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Role;
import com.mixoor.khademni.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName role);


}
