package com.example.goalmates.service;

import com.example.goalmates.exception.BadRequestException;
import com.example.goalmates.models.User;
import com.example.goalmates.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {
    private static final String DIRECTORY = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator;
    @Autowired
    private UserRepository userRepository;

    public ImageService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void downloadImage(String name, HttpServletResponse response) throws IOException {
        File file = new File(DIRECTORY + name);
        Optional<User> user = userRepository.findUserByImage(name);
        if (user.isEmpty()) {
            throw new BadRequestException("Image not found");
        }
        Files.copy(file.toPath(), response.getOutputStream());
    }

    public String uploadImage(MultipartFile image) throws IOException {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;

        if (extension == null) {
            throw new BadRequestException("File type extension is missing");
        }

        if (!(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))) {
            throw new BadRequestException("File type not allowed");
        }

        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(u.getId());
        if (user.isEmpty()) {
            throw new BadRequestException("No such user found");
        }

        if (user.get().getImage() != null) {
            File f = new File(DIRECTORY + user.get().getImage());
            if (f.exists() && !f.isDirectory()) {
                FileUtils.delete(FileUtils.getFile(DIRECTORY + user.get().getImage()));
            }
        }

        File file = new File(DIRECTORY + name);

        Files.copy(image.getInputStream(), file.toPath());
        user.get().setImage(name);
        userRepository.save(user.get());
        return name;
    }
}
