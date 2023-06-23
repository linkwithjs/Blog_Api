package com.rj.blog.service;

import com.rj.blog.dto.CommentResponseDTO;
import com.rj.blog.dto.ResponseMapper;
import com.rj.blog.dto.CommentRequestDTO;
import com.rj.blog.dto.ResponseDTO;
import com.rj.blog.exceptions.CustomException;
import com.rj.blog.models.Blog;
import com.rj.blog.models.Comment;
import com.rj.blog.repository.BlogRepository;
import com.rj.blog.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogService blogService;


    public ResponseEntity<?> create(CommentRequestDTO commentRequestDTO) {

        Optional<Blog> blog = blogRepository.findById(commentRequestDTO.getBlogId());
        if (blog.isPresent()) {
            Comment newComment = new Comment();
            newComment.setText(commentRequestDTO.getText());
            newComment.setUser(blogService.getAuthenticatedUser().get());
            newComment.setBlog(blog.get());
            LOGGER.info("Created new comment");
            Comment savedComment = commentRepository.save(newComment);
            return ResponseDTO.successResponse("Comment created successfully!", ResponseMapper.CommentResponseMapper(savedComment));
        }
        return ResponseDTO.errorResponse("Blog not found!");

    }

    public ResponseEntity<?> delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Error: Comment not found for this id : " + id, 404));
        commentRepository.delete(comment);
        return ResponseDTO.successResponse("Comment deleted successfully!");
    }

    public ResponseEntity<?> readAll() {

        List<CommentResponseDTO> comments = commentRepository.findAll().stream()
                .map(ResponseMapper::CommentResponseMapper)
                .collect(Collectors.toList());

        return ResponseDTO.successResponse("Comment fetched successfully!", comments);
    }

}
