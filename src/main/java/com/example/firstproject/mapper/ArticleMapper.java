package com.example.firstproject.mapper;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {
    public Article toEntity(ArticleDto articleDto){
        Article article = new Article();

        article.setArticleId(articleDto.getArticleId());
        article.setUserId(articleDto.getLoginId());
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());

        return article;
    }

    public ArticleDto toDto(Article article){
        ArticleDto articleDto = new ArticleDto();

        articleDto.setArticleId(article.getArticleId());
        articleDto.setLoginId(article.getUserId());
        articleDto.setTitle(article.getTitle());
        articleDto.setContent(article.getContent());

        return articleDto;
    }
}
