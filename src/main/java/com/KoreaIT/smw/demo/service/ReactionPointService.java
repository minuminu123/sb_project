package com.KoreaIT.smw.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.repository.ReactionPointRepository;
import com.KoreaIT.smw.demo.repository.ReplyRepository;
import com.KoreaIT.smw.demo.vo.ReactionPoint;
import com.KoreaIT.smw.demo.vo.Reply;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ReplyRepository replyRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;
	
	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 하고 오렴");
		}
		// 리액션을 할수 있는지 체크 (1이면 이미 했다는 거고 0이면 안했다)
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}
	
	public ResultData actorCanMakeReaction2(int actorId, String relTypeCode, String relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 하고 오렴");
		}
		// 리액션을 할수 있는지 체크 (1이면 이미 했다는 거고 0이면 안했다)
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId2(actorId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "추천 불가", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}
		return ResultData.from("S-1", "추천 가능", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	public ResultData addGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addGoodReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "좋아요 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReationPoint(relId);
			break;
		case "reply":
			replyService.increaseGoodReationPoint(relId);
		}

		return ResultData.from("S-1", "좋아요 처리 됨");

	}

	public ResultData addBadReactionPoint(int actorId, String relTypeCode, int relId) {
		int affectedRow = reactionPointRepository.addBadReactionPoint(actorId, relTypeCode, relId);

		if (affectedRow != 1) {
			return ResultData.from("F-2", "싫어요 실패");
		}

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리 됨");
	}

	public ResultData deleteGoodReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteGoodReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReationPoint(relId);
			break;
		case "reply":
			replyService.decreaseBadReactionPoint(relId);
		}

		return ResultData.from("S-1", "좋아요 취소 처리 됨");
	}

	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReationPoint(relId);
			break;
		case "reply":
			replyService.decreaseBadReactionPoint(relId);
		}

		return ResultData.from("S-1", "싫어요 취소 처리 됨");
	}
	// 해당 게시글에 "article"에 로그인한 멤버의 댓글을 리스트로받고로그인한 멤버의 id와 reactionPoint 같은 거 찾아서 있으면 reply 리턴 
	public List<Reply> actorCanCancelGoodReaction2(int relId, String relTypeCode) {
		return replyRepository.getForPrintReplies2(rq.getLoginedMemberId(), relTypeCode, relId);
	}
	
	public boolean actorCanCancelGoodReaction(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}
	
	public boolean actorCanCancelGoodReaction3(String relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId2(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
	}
	
	private int getSumReactionPointByMemberId2(int actorId, String relTypeCode, String relId) {
		int getSumRP = reactionPointRepository.getSumReactionPointByMemberId2(actorId, relTypeCode, relId);

		return (int) getSumRP;
	}

	public boolean actorCanCancelBadReaction(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);
		if (getPointTypeCodeByMemberId < 0) {
			return true;
		}
		return false;
	}

	private int getSumReactionPointByMemberId(int actorId, String relTypeCode, int relId) {
        int getSumRP = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode, relId);

		return (int) getSumRP;
	}

	public List<ReactionPoint> getReactionPointsByLoginMember(int actorId, String relTypeCode) {
		return reactionPointRepository.getReactionPointsByLoginMember(actorId, relTypeCode);
	}



	









}