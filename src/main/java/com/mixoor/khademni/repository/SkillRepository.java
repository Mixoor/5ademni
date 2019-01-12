package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    Optional<Skill> findByName(String name);

    @Query("select distinct s from Skill s where s.name in :skills")
    List<Skill> getAll(@Param("skills") List<String> skills);




}
