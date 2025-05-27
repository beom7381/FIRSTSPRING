package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.mapper.ArticleMapper;
import com.example.firstproject.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleMapper articleMapper;

    public List<ArticleDto> getArticleList() {
        return articleRepository.findAll()
                .stream()
                .map(articleMapper::toDto)
                .toList();
    }

    public Long saveArticle(ArticleDto articleDto) {
        try {
            Article article;

            article = articleMapper.toEntity(articleDto);

            return articleRepository.save(article).getArticleId();
        } catch (Exception err) {
            err.printStackTrace();
            return 0L;
        }
    }

    public ArticleDto readArticle(Long articleId) {
        Article article = articleRepository.findByArticleId(articleId);

        if (article == null) {
            return null;
        } else {
            return articleMapper.toDto(article);
        }
    }

    public boolean deleteArticle(Long articleId) {
        Article article = articleRepository.findByArticleId(articleId);

        if (article != null) {
            articleRepository.delete(article);

            return true;
        } else {
            return false;
        }
    }
}
