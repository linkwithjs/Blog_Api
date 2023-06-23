package com.rj.blog.service;

import com.rj.blog.dto.BlogRequestDTO;
import com.rj.blog.dto.BlogResponseDTO;
import com.rj.blog.dto.CommentResponseDTO;
import com.rj.blog.dto.ResponseMapper;
import com.rj.blog.dto.ResponseDTO;
import com.rj.blog.exceptions.CustomException;
import com.rj.blog.models.Blog;
import com.rj.blog.models.User;
import com.rj.blog.repository.BlogRepository;
import com.rj.blog.repository.CommentRepository;
import com.rj.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogService.class);

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    public ResponseEntity<?> create(BlogRequestDTO blogRequestDTO, String imageName) {
        Blog newBlog = new Blog();
        newBlog.setTitle(blogRequestDTO.getTitle());
        newBlog.setBody(blogRequestDTO.getBody());
        newBlog.setSlug(blogRequestDTO.getSlug());
        newBlog.setUser(getAuthenticatedUser().get());
        newBlog.setThubmnail(imageName);
        LOGGER.info("Created new blog");
        Blog savedBlog = blogRepository.save(newBlog);
        List<CommentResponseDTO> comments = new ArrayList<>();
        return ResponseDTO.successResponse("Blog created successfully!", ResponseMapper.BlogResponseMapper(savedBlog, comments));
    }

    public ResponseEntity<?> readAll() {
        List<BlogResponseDTO> blogResponseDTO = new ArrayList<>();
        List<Blog> blogs = blogRepository.findAll();
        blogs.forEach(blog -> {
            List<CommentResponseDTO> comments = commentRepository.findByBlogId(blog.getId()).stream()
                    .map(ResponseMapper::CommentResponseMapper)
                    .collect(Collectors.toList());
            blogResponseDTO.add(ResponseMapper.BlogResponseMapper(blog, comments));
        });
        return ResponseDTO.successResponse("Blogs fetched successfully!", blogResponseDTO);
    }

    public ResponseEntity<?> getById(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Error: Blog not found for this id : " + id, 404));

        List<CommentResponseDTO> comments = commentRepository.findByBlogId(blog.getId()).stream()
                .map(ResponseMapper::CommentResponseMapper)
                .collect(Collectors.toList());

        return ResponseDTO.successResponse("Blog fetched successfully!", ResponseMapper.BlogResponseMapper(blog, comments));
    }

    public ResponseEntity<?> delete(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new CustomException("Error: Blog not found for this id : " + id, 404));
        blogRepository.delete(blog);
        LOGGER.info("{} deleted blog", getAuthenticatedUser().get().getUsername());
        return ResponseDTO.successResponse("Blog deleted successfully!");
    }

    public Optional<User> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        String finalUsername = username;
        return Optional.ofNullable(userRepository.findByUsername(username).orElseThrow(() -> new CustomException("Error: " + finalUsername + " is not found.")));
    }

}
