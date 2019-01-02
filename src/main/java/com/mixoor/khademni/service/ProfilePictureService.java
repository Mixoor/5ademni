package com.mixoor.khademni.service;

import com.mixoor.khademni.exception.BadRequestException;
import com.mixoor.khademni.property.FileProperties;
import com.mixoor.khademni.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ProfilePictureService {

    private final Path fileStorageLocation;
    @Autowired
    UserRepository userRepository;

    @Autowired
    public ProfilePictureService(FileProperties fileProperties) {

        this.fileStorageLocation = Paths.get(fileProperties.getProfile())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String storeProfilePicture(MultipartFile picture) {

        String fileName = StringUtils.cleanPath(picture.getOriginalFilename());

        try {

            if (fileName.contains(".."))
                throw new BadRequestException("Filename contain invalid path sequence " + fileName);


            if (!(picture.getContentType().equalsIgnoreCase("image/png") ||
                    picture.getContentType().equalsIgnoreCase("image/jpg") ||
                    picture.getContentType().equalsIgnoreCase("image/jpeg"))
                    )
                throw new BadRequestException("Picture must be jpg,png or jpeg " + picture.getContentType());

            String newFileName = UUID.randomUUID().toString() + "-" + fileName;

            Path location = this.fileStorageLocation.resolve(newFileName);

            Files.copy(picture.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);

            System.out.println(ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/profile/")
                    .path(newFileName)
                    .build());

            return newFileName;

        } catch (IOException e) {
            throw new BadRequestException("Could not store file " + fileName);


        }
    }


    public boolean deleteFile(String path) {
        try {
            Files.delete(Paths.get(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
