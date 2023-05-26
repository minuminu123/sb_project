package com.KoreaIT.smw.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.KoreaIT.smw.demo.service.BeachService;
import com.KoreaIT.smw.demo.service.ReactionPointService;
import com.KoreaIT.smw.demo.vo.Blog;
import com.KoreaIT.smw.demo.vo.ReactionPoint;
import com.KoreaIT.smw.demo.vo.Recommend;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@CrossOrigin(origins = "http://localhost:8081")
@Controller
public class UsrBeachController {

	@Autowired
	Rq rq;

	private final BeachService beachService;

	private final ReactionPointService reactionPointService;

	public UsrBeachController(BeachService beachService, ReactionPointService reactionPointService) {
		this.beachService = beachService;
		this.reactionPointService = reactionPointService;
	}

	/* 해변 리스트를 가져오는 url, 검색어,타입,페이지넘버, 페이지 사이즈를 받는 페이지 */
	@RequestMapping("/usr/beach/list")
	public String index(Model model, @RequestParam(required = false, defaultValue = "") String searchKeyword,
			@RequestParam(required = false, defaultValue = "0") int searchType,
			@RequestParam(required = false, defaultValue = "1") int pageNo,
			@RequestParam(required = false, defaultValue = "10") int pageSize) {

		List<String[]> data = beachService.getCsv();

		List<String[]> filteredData = beachService.getFilterdData(searchType, searchKeyword);

		// 페이지네이션 처리
		int totalCount = filteredData.size();
		int startIdx = (pageNo - 1) * pageSize;
		int endIdx = Math.min(startIdx + pageSize, totalCount);
		List<String[]> paginatedData = filteredData.subList(startIdx, endIdx);

		model.addAttribute("data", paginatedData);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("searchType", searchType);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", pageSize);
		return "usr/beach/list";
	}

	@RequestMapping("/usr/beach/getBeach")
	public String getBeach(@RequestParam(defaultValue = "해수욕장") String name, Model model,
			@RequestParam(defaultValue = "sim") String sortType, String id) {
//		
		List<Blog> BeachDetails = beachService.getBlog(name, sortType);

		List<String> imageUrls = beachService.getImg(name);

		
		/* ResultData에 이 멤버가 이 게시글에 좋아요나 싫어요를 할수있는지를 확인하는 함수(한번만 가능하기 때문) */
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction2(rq.getLoginedMemberId(),
				"beach", id);
		
		/* 만약 게시글에 좋아요를 할수있으면 true를 반환 */
		if(actorCanMakeReactionRd.isSuccess()) {
			model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		}
		
		model.addAttribute("actorCanCancelGoodReaction", reactionPointService.actorCanCancelGoodReaction3(id, "beach"));
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());

		model.addAttribute("BeachDetails", BeachDetails);
		model.addAttribute("imageUrls", imageUrls);
		return "/usr/beach/getBeach";
	}

	/* 네이버 인플루언서 추천 페이지를 jsoup으로 크롤링후 가져온 리스트를 보여주는 url */
	@GetMapping("/usr/home/ex4")
	public String news(Model model) throws Exception {
		List<Recommend> recommendList = beachService.getNewsDatas2();
		int recommendCount = recommendList.size();
		model.addAttribute("beachList", recommendList);
		model.addAttribute("beachCount", recommendCount);
		return "/usr/home/ex4";
	}

	@GetMapping("/proxy-image")
	public ResponseEntity<byte[]> proxyImage(@RequestParam("imageUrl") String imageUrl) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<byte[]> response = restTemplate.getForEntity(imageUrl, byte[].class);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_JPEG); // 이미지 형식에 따라 콘텐츠 타입을 조정합니다.

		return new ResponseEntity<>(response.getBody(), headers, HttpStatus.OK);
	}
}