package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface LanguageRepository extends JpaRepository<Language, Long> {
    Optional<Language> findByName(String name);

    Set<Language> findAllByName(String[] names);
}

