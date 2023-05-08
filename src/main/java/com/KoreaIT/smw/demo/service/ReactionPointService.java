package com.KoreaIT.smw.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.repository.ReactionPointRepository;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private Rq rq;
	
	public ResultData actorCanMakeReaction(int actorId, String relTypeCode, int relId) {
		if (actorId == 0) {
			return ResultData.from("F-1", "로그인 하고 오렴");
		}
		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(actorId, relTypeCode,
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
		}

		return ResultData.from("S-1", "좋아요 취소 처리 됨");
	}

	public ResultData deleteBadReactionPoint(int actorId, String relTypeCode, int relId) {
		reactionPointRepository.deleteBadReactionPoint(actorId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReationPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 취소 처리 됨");
	}
	
	public boolean actorCanCancelGoodReaction(int relId, String relTypeCode) {
		int getPointTypeCodeByMemberId = getSumReactionPointByMemberId(rq.getLoginedMemberId(), relTypeCode, relId);

		if (getPointTypeCodeByMemberId > 0) {
			return true;
		}
		return false;
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

}