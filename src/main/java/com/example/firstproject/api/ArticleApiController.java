package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    // 게시글 전체 반환
    @GetMapping("/api/articles")
    public List<ArticleDto> getArticleList() {
        return articleService.getArticleList();
    }

    // 게시글 한개만 반환
    @GetMapping("/api/articles/{id}")
    public ArticleDto getArticle(@PathVariable("id") Long articleId) {
        return articleService.readArticle(articleId);
    }

    // 게시글 생성
    @PostMapping("/api/articles")
    public ArticleDto saveArticle(@RequestBody ArticleDto articleDto) {
        Long articleId = articleService.saveArticle(articleDto);

        return articleService.readArticle(articleId);
    }

    // 게시글 수정
    @PatchMapping("/api/articles/{id}")
    public ArticleDto updateArticle(@PathVariable("id") Long articleId, @RequestBody ArticleDto articleDto) {
        articleDto.setArticleId(articleId);
        articleService.saveArticle(articleDto);

        return articleService.readArticle(articleId);
    }

    // 게시글 삭제
    @DeleteMapping("/api/articles/{id}")
    public boolean deleteArticle(@PathVariable("id") Long articleId) {
        return articleService.deleteArticle(articleId);
    }
}