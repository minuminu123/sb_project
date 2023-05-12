package com.KoreaIT.smw.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.KoreaIT.smw.demo.service.RecommendService;
import com.KoreaIT.smw.demo.vo.Recommend;

@Controller
public class RecommendController {

    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @GetMapping("/usr/home/recommend")
    public String news(Model model) throws Exception{
        List<Recommend> recommendList = recommendService.getNewsDatas();
        model.addAttribute("recommendList", recommendList);

        return "/usr/home/recommend";
    }
}