package com.KoreaIT.smw.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.smw.demo.service.ArticleService;
import com.KoreaIT.smw.demo.service.MemberService;
import com.KoreaIT.smw.demo.util.Ut;
import com.KoreaIT.smw.demo.vo.Article;
import com.KoreaIT.smw.demo.vo.Member;
import com.KoreaIT.smw.demo.vo.Rq;

@Controller
public class AdmMemberAndArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	/* 관리자가 멤버와 게시글을 가져오기 위한 url. 
	 * 모든 게시글을 봐야하기 때문에 boardId는 0으로, 권한은 0을 설정해 관리자 권한임을 입증, 페이징과 검색을 위한 매개변수들 추가 */
	@RequestMapping("/adm/memberAndArticle/list")
	public String showList(Model model, @RequestParam(defaultValue = "0") int boardId,
			@RequestParam(defaultValue = "0") String authLevel,
			@RequestParam(defaultValue = "loginId,name,nickname") String MsearchKeywordTypeCode,
			@RequestParam(defaultValue = "") String MsearchKeyword, @RequestParam(defaultValue = "1") int Mpage,
			@RequestParam(defaultValue = "title,body") String AsearchKeywordTypeCode,
			@RequestParam(defaultValue = "") String AsearchKeyword, @RequestParam(defaultValue = "1") int Apage,
			@RequestParam(defaultValue = "recent") String filter) {

		/* 멤버 수 세기 */
		int membersCount = memberService.getMembersCount(authLevel, MsearchKeywordTypeCode, MsearchKeyword);
		/* 게시글 갯수 세기 */
		int articlesCount = articleService.getArticlesCount(boardId, AsearchKeywordTypeCode, AsearchKeyword);

		int memberItemsInAPage = 10;
		int memberPagesCount = (int) Math.ceil((double) membersCount / memberItemsInAPage);

		int articleItemsInAPage = 10;
		int articlePagesCount = (int) Math.ceil((double) articlesCount / articleItemsInAPage);

		/* 멤버들의 권한과 페이징을 위한 매개변수들로 멤버들 가져오기 */
		List<Member> members = memberService.getForPrintMembers(authLevel, MsearchKeyword, MsearchKeyword,
				memberItemsInAPage, Mpage);
		/* 게시글들 가져오기 */
		List<Article> articles = articleService.getForPrintArticles(boardId, articleItemsInAPage, Apage,
				AsearchKeywordTypeCode, AsearchKeyword, filter);

		model.addAttribute("authLevel", authLevel);
		model.addAttribute("MsearchKeywordTypeCode", MsearchKeywordTypeCode);
		model.addAttribute("MsearchKeyword", MsearchKeyword);
		model.addAttribute("AsearchKeywordTypeCode", AsearchKeywordTypeCode);
		model.addAttribute("AsearchKeyword", AsearchKeyword);

		model.addAttribute("articlePagesCount", articlePagesCount);
		model.addAttribute("memberPagesCount", memberPagesCount);

		model.addAttribute("membersCount", membersCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("members", members);
		model.addAttribute("articles", articles);

		return "adm/memberAndArticle/list";
	}

	/* 관리자가 로그아웃할때 사용하는 url */
	@RequestMapping("/adm/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}
	
	@RequestMapping("/adm/memberAndArticle/doDeleteMembers")
	@ResponseBody
	public String doDeleteMembers(@RequestParam(defaultValue = "") String ids,
			@RequestParam(defaultValue = "/adm/memberAndArticle/list") String replaceUri) {
		List<Integer> memberIds = new ArrayList<>();

		for (String idStr : ids.split(",")) {
			memberIds.add(Integer.parseInt(idStr));
		}

		memberService.deleteMembers(memberIds);

		return Ut.jsReplace("해당 회원들이 삭제되었습니다.", replaceUri);
	}

	@RequestMapping("/adm/memberAndArticle/doDeleteArticles")
	@ResponseBody
	public String doDeleteArticles(@RequestParam(defaultValue = "") String ids,
			@RequestParam(defaultValue = "/adm/memberAndArticle/list") String replaceUri) {
		List<Integer> articleIds = new ArrayList<>();

		for (String idStr : ids.split(",")) {
			articleIds.add(Integer.parseInt(idStr));
		}

		articleService.deleteArticles(articleIds);

		return Ut.jsReplace("해당 게시들이 삭제되었습니다.", replaceUri);
	}
}