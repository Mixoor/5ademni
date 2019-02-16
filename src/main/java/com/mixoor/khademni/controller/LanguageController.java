package com.mixoor.khademni.controller;

import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.model.Language;
import com.mixoor.khademni.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    LanguageRepository  languageRepository;



    @GetMapping("/")
    public List<Language> getLanguages(){
        return  languageRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity saveLanguge(String language){
        Language  l =languageRepository.save(new Language(language));
        return  ResponseEntity.ok().body(l);
    }

    @DeleteMapping("/")
    public ResponseEntity deleteLanguge(String language){
        Language l =languageRepository.findByName(language).orElseThrow(()->new BadRequestException("Language doesn't exist"));

        languageRepository.delete(l);
        return  ResponseEntity.ok().body("Language Deleted successfully");
    }



}

