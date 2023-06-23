package com.rj.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {
    @NotBlank(message = "Comment text is required.")
    private String text;
    @NotBlank(message = "Blog id is required.")
    private Long blogId;

}
