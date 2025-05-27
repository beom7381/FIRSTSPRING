package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleDto;
import com.example.firstproject.mapper.ArticleMapper;
import com.example.firstproject.service.ArticleService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleMapper articleMapper;

    @GetMapping("/article/list")
    public String articleList(Model model) {
        model.addAttribute("articleList", articleService.getArticleList());

        return "article/list";
    }

    @GetMapping("/article/view/{id}")
    public String viewArticle(Model model, HttpSession session, @PathVariable(value = "id") Long articleId) {
        var article = articleService.readArticle(articleId);
        String loginId = (String) session.getAttribute("login_id");

        if (article != null) {
            if(loginId != null && loginId.equals(article.getLoginId())) {
                model.addAttribute("isMyArticle", true);
            }

            model.addAttribute("article", article);

            return "article/view";
        } else {
            return "redirect:/article/list";
        }
    }

    @GetMapping("/article/write")
    public String writeArticle(Model model, @RequestParam(value = "articleId", required = false) Long articleId) {
        if (articleId != null && articleId != 0L) {
            var articleDto = articleService.readArticle(articleId);

            model.addAttribute("article", articleDto);
        } else {
            model.addAttribute("article", new ArticleDto(0L, "", "", ""));
        }

        return "article/write";
    }

    @PostMapping("/article/edit")
    public String editArticle(Model model, HttpSession session, @RequestParam(value = "id") Long articleId) {
        String loginId = (String) session.getAttribute("login_id");

        if (loginId == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "member/login";
        }

        ArticleDto articleDto = articleService.readArticle(articleId);

        if (articleDto != null) {
            model.addAttribute("article", articleDto);
            return "article/write";
        } else {
            return "redirect:/article/list";
        }
    }

    @PostMapping("/article/save")
    public String saveArticle(Model model, HttpSession session, ArticleDto articleDto) {
        String loginId = (String) session.getAttribute("login_id");

        if (loginId == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "member/login";
        }

        articleDto.setLoginId(loginId);

        if(articleDto.getArticleId() == 0){
            articleDto.setArticleId(null);
        }

        Long resultId = articleService.saveArticle(articleDto);

        if (resultId != 0L) {
            model.addAttribute("article", articleService.readArticle(resultId));
            return "redirect:/article/view/" + resultId;
        } else {
            return "/article/list";
        }
    }

    @PostMapping("/article/delete")
    public String deleteArticle(Model model, HttpSession session, @RequestParam(value = "id", required = false) Long articleId) {
        String loginId = (String) session.getAttribute("login_id");

        if (loginId == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "member/login";
        }

        if (articleId != null) {
            ArticleDto articleDto = articleService.readArticle(articleId);

            if (articleDto.getLoginId().equals(loginId)) {
                articleService.deleteArticle(articleId);
            } else {
                model.addAttribute("errorMessage", "권한이 없습니다.");
                return "article/view";
            }
        }

        return "redirect:/article/list";
    }
}
