package com.rj.blog.dto;

import com.rj.blog.models.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String text;
    private Enum<Blog.StatusEnum> status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String author;
    private Long blog;
}
