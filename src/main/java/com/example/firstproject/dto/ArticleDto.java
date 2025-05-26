package com.example.firstproject.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {
    private Long articleId;
    private String loginId;
    private String title;
    private String content;
}
