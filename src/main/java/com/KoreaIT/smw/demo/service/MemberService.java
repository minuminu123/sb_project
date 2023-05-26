package com.KoreaIT.smw.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.KoreaIT.smw.demo.repository.MemberRepository;
import com.KoreaIT.smw.demo.util.Ut;
import com.KoreaIT.smw.demo.vo.Member;
import com.KoreaIT.smw.demo.vo.ResultData;

@Service
public class MemberService {
	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	@Value("${custom.siteName}")
	private String siteName;

	private MemberRepository memberRepository;
	private MailService mailService;

	public MemberService(MailService mailService, MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
		this.mailService = mailService;
	}

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email) {
		// 로그인 아이디 중복체크
		Member existsMember = getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-7", Ut.f("이미 사용중인 아이디(%s)입니다", loginId));
		}

		// 이름 + 이메일 중복체크
		existsMember = getMemberByNameAndEmail(name, email);

		if (existsMember != null) {
			return ResultData.from("F-8", Ut.f("이미 사용중인 이름(%s)과 이메일(%s)입니다", name, email));
		}

		// sha256으로 암호화
		loginPw = Ut.sha256(loginPw);
		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다", "id", id);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public ResultData modify(int id, String loginPw, String name, String nickname, String cellphoneNum, String email) {
		memberRepository.modify(id,loginPw, name, nickname, cellphoneNum, email);
		return ResultData.from("S-1", "회원 정보 수정이 완료되었습니다");
	}

	// 비밀번호 찾기시 임시 패스워드 발송해주는 함수
	public ResultData notifyTempLoginPwByEmail(Member actor) {
		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Ut.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteMainUri + "/usr/member/login\" target=\"_blank\">로그인 하러가기</a>";

		ResultData sendResultData = mailService.send(actor.getEmail(), title, body);

		if (sendResultData.isFail()) {
			return sendResultData;
		}

		setTempPassword(actor, tempPassword);

		return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
	}

	private void setTempPassword(Member actor, String tempPassword) {
		memberRepository.modify(actor.getId(), Ut.sha256(tempPassword), null, null, null, null);
	}
	
	public int getMembersCount(String authLevel, String searchKeywordTypeCode, String searchKeyword) {
		return memberRepository.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);
	}

	// 관리자 페이지에서 권한, 키워드 타입,키워드로 멤버들 가져오는 함수
	public List<Member> getForPrintMembers(String authLevel, String searchKeywordTypeCode, String searchKeyword,
			int itemsInAPage, int page) {

		int limitStart = (page - 1) * itemsInAPage;
		int limitTake = itemsInAPage;
		List<Member> members = memberRepository.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				limitStart, limitTake);

		return members;
	}

	public Member getMemberByEmail(String email) {
		return memberRepository.getMemberByEmail(email);
	}

	public int increaseFailCount(int actorId) {
		Member member = memberRepository.getMemberById(actorId);
		
		if(member.getFailCount() == 0) {
			memberRepository.failCountZero(actorId);
			return memberRepository.increaseFailCount(actorId, 1);	
		} else if(member.getFailCount() == 1) {
			memberRepository.failCountZero(actorId);
			return memberRepository.increaseFailCount(actorId, 2);	
		} else if(member.getFailCount() == 2) {
			memberRepository.failCountZero(actorId);
			return memberRepository.increaseFailCount(actorId, 3);	
		} else if(member.getFailCount() == 3) {
			memberRepository.failCountZero(actorId);
			return memberRepository.increaseFailCount(actorId, 4);	
		} else if(member.getFailCount() == 4){
			memberRepository.failCountZero(actorId);
			return memberRepository.increaseFailCount(actorId, 5);	
		}
			return 5;
	}

	public int lockAccount(int actorId) {
		return memberRepository.lockAccount(actorId);
		
	}

	public void getMinute(int actorId, int minute) {
		memberRepository.getMinute(actorId, minute);
		
	}

	public void failCountZero(int id) {
		memberRepository.failCountZero(id);
		
	}

	public int getFailCount(int id) {
		return memberRepository.getFailCount(id);
	}
	
	public void deleteMembers(List<Integer> memberIds) {
		for (int memberId : memberIds) {
			Member member = getMemberById(memberId);

			if (member != null) {
				deleteMember(member);
			}
		}
	}

	private void deleteMember(Member member) {
		memberRepository.deleteMember(member.getId());
	}
}