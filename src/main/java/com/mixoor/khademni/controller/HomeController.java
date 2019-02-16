package com.mixoor.khademni.controller;


import com.mixoor.khademni.model.Contact;
import com.mixoor.khademni.payload.response.HomeStatic;
import com.mixoor.khademni.repository.ClientRepository;
import com.mixoor.khademni.repository.ContactRepository;
import com.mixoor.khademni.repository.FreelancerRepository;
import com.mixoor.khademni.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    JobRepository jobRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    @Autowired
    ClientRepository clientRepository;


    @Autowired
    ContactRepository contactRepository;


    @GetMapping("/")
    public ResponseEntity index(){

        return ResponseEntity.ok().body(new HomeStatic(clientRepository.count(),
                freelancerRepository.count(),
                jobRepository.count()));
    }

    @PostMapping("/")
    public ResponseEntity Contact(Contact contact) {
        contactRepository.save(contact);
        return ResponseEntity.ok(" send successfully");
    }


}
