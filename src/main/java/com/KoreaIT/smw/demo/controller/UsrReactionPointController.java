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

	@RequestMapping("/usr/reactionPoint/doGoodReaction")
	@ResponseBody

	public ResultData doGoodReaction(Model model, String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionRd = reactionPointService.actorCanMakeReaction(rq.getLoginedMemberId(),
				relTypeCode, relId);

		int actorCanMakeReaction = (int) actorCanMakeReactionRd.getData1();

		if (actorCanMakeReaction == 1) {
			ResultData rd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-1", "좋아요 취소");
		} else if (actorCanMakeReaction == -1) {
			ResultData rd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			rd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);
			return ResultData.from("S-2", "싫어요 누른 상태입니다.");
		}

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
