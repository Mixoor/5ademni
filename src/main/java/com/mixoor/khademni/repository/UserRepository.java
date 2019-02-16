package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.RoleName;
import com.mixoor.khademni.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByIdIn(List<Long> userIds);

    Boolean existsByEmail(String email);

    @Query("select u from User u where u.role.name in :rolename order by  ?#{#pageable}")
    Page<User> findAllByRole(@Param("rolename") List<RoleName> roleName, Pageable pageable);





}
