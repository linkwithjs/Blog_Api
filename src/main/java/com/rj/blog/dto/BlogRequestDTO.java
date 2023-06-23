package com.rj.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogRequestDTO {
    @NotBlank(message = "Blog title is required.")
    private String title;
    @NotBlank(message = "Blog content is required.")
    private String body;
    private String slug;
}
