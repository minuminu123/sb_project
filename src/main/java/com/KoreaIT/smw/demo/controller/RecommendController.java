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

    /* 네이버 인플루언서 추천 페이지를 jsoup으로 크롤링후 가져온 리스트를 보여주는 url */
    @GetMapping("/usr/home/recommend")
    public String news(Model model) throws Exception{
        List<Recommend> recommendList = recommendService.getNewsDatas();
        int recommendCount = recommendList.size();
        model.addAttribute("recommendList", recommendList);
        model.addAttribute("recommendCount", recommendCount);
        return "/usr/home/recommend";
    }
}