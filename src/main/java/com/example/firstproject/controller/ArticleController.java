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
    public String articleList(Model model, @RequestParam(value = "page", required = false) String page) {
        model.addAttribute("articleList", articleService.getArticleList());

        return "article/list";
    }

    @GetMapping("/article/view")
    public String viewArticle(Model model, @RequestParam(value = "id", required = false) Long articleId) {
        log.info(articleId.toString());

        if(articleId != null){
            model.addAttribute("article", articleService.readArticle(articleId));

            return "article/view";
        }
        else{
            return "redirect:/article/list";
        }
    }

    @GetMapping("/article/write")
    public String writeArticle(Model model, @RequestParam(value = "id", required = false) String id) {
        Long articleId;

        try {
            articleId = Long.parseLong(id);
        } catch (Exception err) {
            articleId = 0L;
        }

        if (articleId != 0) {
            var articleDto = articleService.readArticle(articleId);

            model.addAttribute("articleDto", articleDto);
        } else {
            model.addAttribute("articleDto", new ArticleDto(0L, "", "", ""));
        }

        return "article/write";
    }

    @PostMapping("/article/save")
    public String saveArticle(Model model, HttpSession session, ArticleDto articleDto) {
        String loginId = (String) session.getAttribute("login_id");

        if (loginId == null) {
            model.addAttribute("errorMessage", "로그인이 필요합니다.");
            return "member/login";
        }

        articleDto.setLoginId(loginId);

        Long resultId = articleService.saveArticle(articleDto);
        if (resultId != 0L) {
            return "redirect:/article/view?id=" + resultId;
        }
        else{
            return "/article/list";
        }
    }
}
