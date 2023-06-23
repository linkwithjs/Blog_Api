package com.rj.blog.controller;

import com.rj.blog.dto.CommentRequestDTO;
import com.rj.blog.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        return commentService.create(commentRequestDTO);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getBlogs() {
        return commentService.readAll();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> deleteBlog(@PathVariable(value = "id") Long id) {
        return commentService.delete(id);
    }
//
//    @GetMapping("/get/{id}")
//    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
//    public ResponseEntity<?> getBlog(@PathVariable(value = "id") Long blogId) {
//        return blogService.getById(blogId);
//    }
//}
}
