package com.rj.blog.controller;

import com.rj.blog.dto.BlogRequestDTO;
import com.rj.blog.service.BlogService;
import com.rj.blog.service.FileUploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private FileUploadService fileUploadService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addBlog(@RequestParam("image") MultipartFile image, @Valid @RequestPart("blog") BlogRequestDTO blogRequestDTO) throws IOException {
        String imageName = fileUploadService.uploadImage(path, image);
        return blogService.create(blogRequestDTO,imageName);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getBlogs() {
        return blogService.readAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBlog(@PathVariable(value = "id") Long blogId) {
        return blogService.delete(blogId);
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getBlog(@PathVariable(value = "id") Long blogId) {
        return blogService.getById(blogId);
    }
}
