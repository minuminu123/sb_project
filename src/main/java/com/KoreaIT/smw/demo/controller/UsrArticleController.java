package com.KoreaIT.smw.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.KoreaIT.smw.demo.service.ArticleService;
import com.KoreaIT.smw.demo.service.BoardService;
import com.KoreaIT.smw.demo.service.GenFileService;
import com.KoreaIT.smw.demo.service.ReactionPointService;
import com.KoreaIT.smw.demo.service.ReplyService;
import com.KoreaIT.smw.demo.util.Ut;
import com.KoreaIT.smw.demo.vo.Article;
import com.KoreaIT.smw.demo.vo.Board;
import com.KoreaIT.smw.demo.vo.ReactionPoint;
import com.KoreaIT.smw.demo.vo.Reply;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@Controller
public class UsrArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private GenFileService genFileService;
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;

	/* 게시글의 리스트를 가져오는 url. 검색 조건과 페이징을 위해 매개변수를 받는다.
	 *  @RequestParam를통해 선택적인 매개변수 처리를 하거나 디폴트 값을 지정해줘 오류가 나지 않도록 한다. */
	@RequestMapping("/usr/article/list")
	public String showList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "recent") String filter) {

		/* board 클래스는 article과 조인되어있는 테이블로 어느 게시판에 속해있는지 가져오는 형태이다. 만약 게시판 번호가 지정되어 있지 않다면 없는 게시판으로 지정 */
		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.jsHitoryBackOnView("없는 게시판이야");
		}
		
		/* 게시판의 총 갯수를 가져오는 함수. 매개변수로 boardId, searchKeywordTypeCode, searchKeyword 를 넣어서 해당하는 게시글을 가져와 갯수를 센다. */
		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		// 한 페이지에 넣을 리스트 수
		int itemsInAPage = 10;

		// 페이지 수 세기
		int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);

		/* 게시글들의 리스트를 받아오는 함수로 boardId, itemsInAPage, page, searchKeywordTypeCode,
				searchKeyword 를 매개변수로 받는다. */
		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode,
				searchKeyword, filter);
		/* 프론트에서 사용하기 위한 데이터를 보낸다. */
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);
		model.addAttribute("searchKeyword", searchKeyword);
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("articles", articles);

		return "usr/article/list";
	}

	/* 게시글 수정 url */
	@RequestMapping("/usr/article/modify")
	public String showModify(Model model, int id) {
		/* 로그인한 멤버의 아이디와 게시글의 아이디를 통해 게시글을 불러오는 함수 */
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		/* 만약 게시글이 없다면 경고 메시지 출력 후 뒤로가기 */
		if (article == null) {
			return rq.jsHitoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}
		/* actorCanModify 함수를 통해 게시글을 작성한 작성자만 수정할수 있는지 체크 */
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		/* 만약 실패한다면 실패 메시지 출력 후 뒤로가기 */
		if (actorCanModifyRd.isFail()) {
			return rq.jsHitoryBackOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		return "usr/article/modify";
	}

	/* @ResponseBody: 메서드나 클래스에 적용하여 해당 값을 HTTP 응답의 본문(body)에 직접 작성하도록 지정하는 역할을 합니다. */
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다@", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}
		/* 다 통과해서 게시글이 쓴 작성자와 동일하면 수정할 권한을 부여하고 받은 id, title, body를 통해 게시글을 수정한다. */
		articleService.modifyArticle(id, title, body);
		/* 수정후 게시글 상세보기 페이지로 보낸다. */
		return rq.jsReplace(Ut.f("%d번 글을 수정 했습니다", id), Ut.f("../article/detail?id=%d", id));
	}

	/* 게시글 삭제 url */
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {
		/* 게시글이 존재하는지 확인하는 함수 */
		Article article = articleService.getArticle(id);
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		/* 권한 체크 */
		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 글에 대한 권한이 없습니다", id));
		}
		/* 통과하면 삭제 권한 부여 */
		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.f("%d번 글을 삭제 했습니다", id), "../article/list?boardId=1");
	}

	@RequestMapping("/usr/article/write")
	public String showWrite() {

		return "usr/article/write";
	}
	/* 게시글을 작성할때 boardId, title, body, replaceUri, multipartRequest를 받는 url */
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(int boardId, String title, String body, String replaceUri, MultipartRequest multipartRequest) {
		/* 만약 제목을 적지 않고 제출하면 에러메시지와 뒤로가기, 내용도 마찬가지 */
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-2", "내용을 입력해주세요");
		}

		/* 로그인한 멤버와 내용, 제목 등을 남기기위해 writeArticle함수를 통해 저장 */
		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);

		/* ResultData에 있는 데이터의 값을 id 로 저장 */
		int id = (int) writeArticleRd.getData1();
		/* 만약 돌아갈 uri가 정해지지 않았다면 게시물 상세보기 페이지로 이동 */
		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", id);
		}
		/* MultipartResolver가 반환하는 MultipartHttpServletRequest 객체에서 업로드된 파일들을 맵 형태로 가져온다 */
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		/* keySet함수를 통해 모든 key들을 가져오는 함수이고 key에 해당하는 value를 위해 get함수로 파일이름을 지정해준다  */
		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);
			/* 만약 multipartFile에 값이 들어있다면 저장한다 */
			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, id);
			}
		}
		
		return rq.jsReplace(Ut.f("%d번 글이 생성되었습니다", id), replaceUri);
	}

	/* 게시글 상세보기 url */
	@RequestMapping("/usr/article/detail")
	public String showDetail(Model model, int id) {
		// id 는 article의 id
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		
		if (article == null) {
			return rq.jsHitoryBackOnView(Ut.f("%d번 글은 존재하지 않습니다!", id));
		}

		/* ResultData에 이 멤버가 이 게시글에 좋아요나 싫어요를 할수있는지를 확인하는 함수(한번만 가능하기 때문) */
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"article", id);
		/* ResultData에 이 멤버가 이 댓글에 좋아요를 할수있는지를 확인하는 함수(한번만 가능하기 때문) */
		ResultData actorCanMakeReactionRd2 = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				"reply", id);
		/* 해당 게시글에 해당하는 댓글들을 리스트로 가져온다 */
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
		/* 댓글에 좋아요한 리스트를 가져오기 위한 함수 */
		List<ReactionPoint> reactionPoints = reactionPointService.getReactionPointsByLoginMember(rq.getLoginedMemberId(), "reply");
		
		//널이면 actorCanMakeReplyGood 거짓
		if(reactionPoints == null) {
			model.addAttribute("actorCanMakeReplyGood", false);
		}
		int reactionPointsCount = reactionPoints.size();
		// 있으면 reply2 사용
		model.addAttribute("reactionPoints", reactionPoints);
		model.addAttribute("reactionPointsCount",reactionPointsCount);
		/* 댓글의 갯수를 세기 위한 함수 */
		int repliesCount = replies.size();
		/* 만약 게시글에 좋아요를 할수있으면 true를 반환 */
		if(actorCanMakeReactionRd.isSuccess()) {
			model.addAttribute("actorCanMakeReaction", actorCanMakeReactionRd.isSuccess());
		}
		/* 만약 댓글에 좋아요를 할수있으면 true를 반환 */
		if(actorCanMakeReactionRd2.isSuccess()) {
			model.addAttribute("actorCanMakeReaction2", actorCanMakeReactionRd2.isSuccess());
		}

		model.addAttribute("repliesCount", repliesCount);
		model.addAttribute("replies", replies);
		model.addAttribute("article", article);
		model.addAttribute("loginedMemberId", rq.getLoginedMemberId());
		model.addAttribute("actorCanMakeReactionRd", actorCanMakeReactionRd);
		model.addAttribute("actorCanCancelGoodReaction", reactionPointService.actorCanCancelGoodReaction(id, "article"));
		model.addAttribute("actorCanCancelBadReaction", reactionPointService.actorCanCancelBadReaction(id, "article"));

		return "usr/article/detail";
	}

	/* 조회수를 올리기위한 url */
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(int id) {

		/* 상세보기를 하면 해당 게시글의 조회수를 1 증가한다. */
		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		/* 실패하면 실패 메시지 출력. */
		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		/* newData함수를 통해 새로운 조합으로 재 생성 */
		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		rd.setData2("id", id);

		return rd;
	}

}