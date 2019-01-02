package com.mixoor.khademni.repository;

import com.mixoor.khademni.model.Document;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository

public interface DocumentRepository extends JpaRepository<Document, Long> {

    Set<Document> findByJob(Job job);

    Set<Document> findByUser(User user);
}
