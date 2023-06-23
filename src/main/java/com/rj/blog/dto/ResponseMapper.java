package com.rj.blog.dto;

import com.rj.blog.models.Blog;
import com.rj.blog.models.Comment;

import java.util.List;

public class ResponseMapper {

    public static BlogResponseDTO BlogResponseMapper(Blog blog, List<CommentResponseDTO> commentList) {
        return new BlogResponseDTO(blog.getId(), blog.getTitle(), blog.getBody(), blog.getSlug(), blog.getStatus(), blog.getCreatedAt(), blog.getUpdatedAt(),blog.getThubmnail(),  blog.getUser().getUsername(), commentList);
    }

    public static CommentResponseDTO CommentResponseMapper(Comment comment) {
        return new CommentResponseDTO(comment.getId(), comment.getText(), comment.getStatus(), comment.getCreatedAt(), comment.getUpdatedAt(), comment.getUser().getUsername(), comment.getBlog().getId());
    }
}
