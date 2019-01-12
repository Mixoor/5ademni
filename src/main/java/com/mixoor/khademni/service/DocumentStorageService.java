package com.mixoor.khademni.service;


import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.exception.ResourceNotFoundException;
import com.mixoor.khademni.model.Client;
import com.mixoor.khademni.model.Document;
import com.mixoor.khademni.model.Job;
import com.mixoor.khademni.model.User;
import com.mixoor.khademni.payload.response.UploadFileResponse;
import com.mixoor.khademni.property.FileProperties;
import com.mixoor.khademni.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentStorageService {

    private final Path fileStorageLocation;
    @Autowired
    DocumentRepository documentRepository;

    @Autowired
    public DocumentStorageService(FileProperties fileProperties) {
        this.fileStorageLocation = Paths.get(fileProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new BadRequestException("Could not create the directory where the upload files");
        }
    }


    public String DownloadLink(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(filename)
                .toUriString();
    }


    public UploadFileResponse storeFile(MultipartFile multipartFile) {

        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileDownloadUri = DownloadLink(fileName);

            return new UploadFileResponse(fileName, fileDownloadUri,
                    multipartFile.getContentType(), multipartFile.getSize());


        } catch (IOException e) {
            throw new BadRequestException("Could not store file " + fileName);
        }
    }

    public Resource loadDocAsRessource(String fileName) {
        try {

            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists())
                return resource;
            else
                throw new ResourceNotFoundException("File Not found", "", fileName);

        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException("File Not found", fileName, " " + e);

        }
    }


    public Document documentToJob(UploadFileResponse response, Client client, Job job) {
        Document document = new Document(response.getFileName(), response.getFileType()
                , client, job, response.getSize());
        documentRepository.save(document);
        return document;
    }

    public Document documentToMessage(UploadFileResponse response, User userPrincipal) {

        Document document = new Document(response.getFileName(), response.getFileType(), userPrincipal, response.getSize());
        documentRepository.save(document);
        return document;
    }


}
