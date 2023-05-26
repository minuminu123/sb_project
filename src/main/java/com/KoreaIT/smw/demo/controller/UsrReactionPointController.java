package com.KoreaIT.smw.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.smw.demo.service.ArticleService;
import com.KoreaIT.smw.demo.service.ReactionPointService;
import com.KoreaIT.smw.demo.vo.Article;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@Controller
public class UsrReactionPointController {
	@Autowired
	private Rq rq;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private ArticleService articleService;

	/* 좋아요하는 url(ajax) */
	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody

	public ResultData doGoodReaction(Model model, String relTypeCode, int relId, String replaceUri) {

		/* 좋아요 할수 있는지 멤버 아이디와 relTypeCode, relId를 받아와서 체크하는 함수 */
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		/* 1이면 좋아요 이미 했다는 뜻, -1이면 싫어요를 눌렀다는 뜻 */
		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

		/* 이미 좋아요를 했다는 뜻이므로 다시한번 클릭하면 좋아요를 취소하는 로직 */
		if (actorCanMakeReaction == 1) {
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "좋아요 취소");
			/* 이미  싫어요를 했다는 뜻이므로 좋아요 클릭하면 싫어요 없애고 좋아요 늘리는 로직 */
		} else if (actorCanMakeReaction == -1) {
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-2", "싫어요 누른 상태입니다.");
		}
		/* 아무 리액션도 하지 않았으면 좋아요 실행 */
		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			ResultData.from("F-2", rd.getMsg());
		}

		return ResultData.from("S-3", "좋아요");
	}

	@RequestMapping("/usr/reactionPoint/doGoodReaction2")
	@ResponseBody

	public ResultData doGoodReaction2(Model model, String relTypeCode, int relId, String replaceUri) {

		/* 좋아요 할수 있는지 멤버 아이디와 relTypeCode, relId를 받아와서 체크하는 함수 */
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		/* 1이면 좋아요 이미 했다는 뜻, -1이면 싫어요를 눌렀다는 뜻 */
		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

		/* 이미 좋아요를 했다는 뜻이므로 다시한번 클릭하면 좋아요를 취소하는 로직 */
		if (actorCanMakeReaction == 1) {
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "좋아요 취소");
			/* 이미  싫어요를 했다는 뜻이므로 좋아요 클릭하면 싫어요 없애고 좋아요 늘리는 로직 */
		} else if (actorCanMakeReaction == -1) {
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-2", "싫어요 누른 상태입니다.");
		}
		/* 아무 리액션도 하지 않았으면 좋아요 실행 */
		ResultData rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			ResultData.from("F-2", rd.getMsg());
		}

		return ResultData.from("S-3", "좋아요");
	}
	
	@RequestMapping("/usr/reactionPoint/doBadReaction")
	@ResponseBody
	public ResultData doBadReaction(String relTypeCode, int relId, String replaceUri) {
		
		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

		if (actorCanMakeReaction == -1) {
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "싫어요 취소");
		} else if (actorCanMakeReaction == 1) {
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-2", "좋아요 누른 상태입니다.");
		}

		ResultData rd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		if (rd.isFail()) {
			rq.jsHistoryBack("F-2", rd.getMsg());
		}

		return ResultData.from("S-3", "싫어요");

	}

}
