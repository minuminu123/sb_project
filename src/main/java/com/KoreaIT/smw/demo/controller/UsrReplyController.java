package com.KoreaIT.smw.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.smw.demo.service.ArticleService;
import com.KoreaIT.smw.demo.service.ReplyService;
import com.KoreaIT.smw.demo.util.Ut;
import com.KoreaIT.smw.demo.vo.Article;
import com.KoreaIT.smw.demo.vo.Reply;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@Controller
public class UsrReplyController {

	@Autowired
	private Rq rq;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private ArticleService articleService;

	/* 댓글 작성하는 url */
	@RequestMapping("/usr/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("F-1", "relTypeCode 을(를) 입력해주세요");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("F-2", "relId 을(를) 입력해주세요");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("F-3", "body 을(를) 입력해주세요");
		}

		/* 댓글을 작성하는 함수. 로그인한 멤버의 아이디와 reltypecode,relId, body를 넣어서 작성 */
		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);

		int id = (int) writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.f("../article/detail?id=%d", relId);
		}

		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}

	/* 댓글을 삭제하는 url */
	@RequestMapping("/usr/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}
		/* 만약 작성한 댓글의 멤버아이디와 로그인한 멤버의 아이디가 같지 않으면 권한없음으로 fail */
		if (reply.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("F-2", Ut.f("%d번 댓글에 대한 권한이 없습니다", id));
		}

		/* id번 댓글을 삭제하는 함수 */
		ResultData deleteReplyRd = replyService.deleteReply(id);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return Ut.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}

	@RequestMapping("/usr/reply/modify")
	public String showModify(Model model, int id, String replaceUri) {

		Reply reply = replyService.getForPrintReply(rq.getLoginedMemberId(), id);

		if (reply == null) {
			return rq.jsHitoryBackOnView(Ut.f("%d번 댓글은 존재하지 않습니다!", id));
		}

		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHitoryBackOnView(actorCanModifyRd.getMsg());
		}

		Article article = articleService.getArticle(reply.getRelId());

		model.addAttribute("reply", reply);
		model.addAttribute("article", article);

		return "usr/reply/modify";
	}
	/* 댓글을 수정하는 url */
	@RequestMapping("/usr/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {

		Reply reply = replyService.getReply(id);

		if (reply == null) {
			return rq.jsHistoryBack("F-1", Ut.f("%d번 댓글은 존재하지 않습니다", id));
		}

		/* 권한 체크 */
		ResultData actorCanModifyRd = replyService.actorCanModify(rq.getLoginedMemberId(), reply);

		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getResultCode(), actorCanModifyRd.getMsg());
		}

		ResultData modifyReplyRd = replyService.modifyReply(id, body);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.f("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return rq.jsReplace(modifyReplyRd.getMsg(), replaceUri);
	}
	
	/* 댓글수정 폼(ajax) */
	@RequestMapping("/usr/reply/getModifyForm")
	@ResponseBody
	public ResultData getModifyForm(int id) {
		
		Reply reply = replyService.getForPrintReply2(id);

		if(reply == null) {
			return ResultData.from("F-1", "해당 댓글은 존재하지 않습니다");
		}

		return ResultData.from("S-1", "댓글 정보 조회 성공", "reply", reply);
	}

}