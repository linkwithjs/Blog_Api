package com.rj.blog.dto;

import com.rj.blog.models.Blog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class BlogResponseDTO {
    private Long id;
    private String title;
    private String body;

    private String slug;
    private Enum<Blog.StatusEnum> status;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private String thumbnail;
    private String author;
    private List<CommentResponseDTO> comments;

    public BlogResponseDTO(Long id, String title, String body, String slug, Enum<Blog.StatusEnum> status, LocalDateTime created_at, LocalDateTime updated_at, String image, String author) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.slug = slug;
        this.thumbnail = image;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.author = author;
    }


}
