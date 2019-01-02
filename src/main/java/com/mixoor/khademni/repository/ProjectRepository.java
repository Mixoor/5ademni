package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
