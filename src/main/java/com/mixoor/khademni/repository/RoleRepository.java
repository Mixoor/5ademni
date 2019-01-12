package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Role;
import com.mixoor.khademni.model.RoleName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName role);

    @Query("select  r from Role r order by ?#{#pageable}")
    Page<Role> search( Pageable pageable);

}
