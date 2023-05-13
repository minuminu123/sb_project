package com.KoreaIT.smw.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.KoreaIT.smw.demo.service.MemberService;
import com.KoreaIT.smw.demo.util.Ut;
import com.KoreaIT.smw.demo.util.Validator;
import com.KoreaIT.smw.demo.vo.Member;
import com.KoreaIT.smw.demo.vo.ResultData;
import com.KoreaIT.smw.demo.vo.Rq;

@Controller
public class UsrMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	@RequestMapping("/usr/member/join")
	public String showJoin() {
		return "usr/member/join";
	}
	
	@RequestMapping("/usr/member/getLoginIdDup")
	@ResponseBody
	public ResultData getLoginIdDup(String loginId) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디를 입력해주세요");
		}

		Member existsMember = memberService.getMemberByLoginId(loginId);

		if (existsMember != null) {
			return ResultData.from("F-2", "해당 아이디는 이미 사용중이야", "loginId", loginId);
		}
		
		return ResultData.from("S-1", "사용 가능!", "loginId", loginId);
	}
	
	@RequestMapping("/usr/member/getEmailDup")
	@ResponseBody
	public ResultData getEmailDup(String email) {

		if (Ut.empty(email)) {
			return ResultData.from("F-1", "email을 입력해주세요");
		}
		
		boolean isValid = Validator.isValidEmail(email);
        if (!isValid) {
        	return ResultData.from("F-3", "이메일 형식에 맞춰 작성해 주세요(gmail)", "email", email);
        }

		Member existsMember = memberService.getMemberByEmail(email);

		if (existsMember != null) {
			return ResultData.from("F-2", "해당 이메일은 이미 사용중이야", "email", email);
		}
		
		return ResultData.from("S-1", "사용 가능!", "email", email);
	}
	
	@RequestMapping("/usr/member/chkcellPhoneNum")
	@ResponseBody
	public ResultData chkcellPhoneNum(String cellphoneNum) {

		if (Ut.empty(cellphoneNum)) {
			return ResultData.from("F-1", "cellphoneNum 입력해주세요");
		}
		
		boolean isValid = Validator.validatePhoneNumber(cellphoneNum);
        if (!isValid) {
        	return ResultData.from("F-3", "전화번호 형식에 맞게 작성해주세요", "cellphoneNum", cellphoneNum);
        }
		
		return ResultData.from("S-1", "사용 가능!", "cellphoneNum", cellphoneNum);
	}
	
	@RequestMapping("/usr/member/getLoginPwDup")
	@ResponseBody
	public ResultData getLoginPwDup(String loginPw) {

		if (Ut.empty(loginPw)) {
			return ResultData.from("F-1", "비밀번호를 입력해주세요");
		}
		
		boolean isValid = Validator.isValidPassword(loginPw);
        if (!isValid) {
        	return ResultData.from("F-3", "비밀번호 형식에 맞게 작성헤주세요", "loginPw", loginPw);
        }
		
		return ResultData.from("S-1", "사용 가능!", "loginPw", loginPw);
	}
	
	@RequestMapping("/usr/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNum,
			String email, @RequestParam(defaultValue = "/") String afterLoginUri) {

//		if (Ut.empty(loginId)) {
//			return rq.jsHistoryBack("F-1", "아이디를 입력해주세요");
//		}
//		if (Ut.empty(loginPw)) {
//			return rq.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
//		}
//		if (Ut.empty(name)) {
//			return rq.jsHistoryBack("F-3", "이름을 입력해주세요");
//		}
//		if (Ut.empty(nickname)) {
//			return rq.jsHistoryBack("F-4", "닉네임을 입력해주세요");
//		}
//		if (Ut.empty(cellphoneNum)) {
//			return rq.jsHistoryBack("F-5", "전화번호를 입력해주세요");
//		}
//		if (Ut.empty(email)) {
//			return rq.jsHistoryBack("F-6", "이메일을 입력해주세요");
//		}

		boolean isValid = Validator.isValidEmail(email);
        if (!isValid) {
        	return rq.jsHistoryBack("F-1", "이메일 형식에 맞춰 작성해 주세요");
//            System.out.println("유효한 이메일입니다.");
        }
		
		isValid = Validator.validatePhoneNumber(cellphoneNum);
        if (!isValid) {
        	return rq.jsHistoryBack("F-2", "전화번호 형식에 맞춰 작성해 주세요");
        }
        
		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNum, email);

		if (joinRd.isFail()) {
			return rq.jsHistoryBack(joinRd.getResultCode(), joinRd.getMsg());
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		String afterJoinUri = "../member/login?afterLoginUri=" + Ut.getEncodedUri(afterLoginUri);

		return Ut.jsReplace("S-1", Ut.f("회원가입이 완료되었습니다"), afterJoinUri);
	}

	@RequestMapping("/usr/member/login")
	public String showLogin(HttpSession httpSession) {
		return "usr/member/login";
	}

	@RequestMapping("/usr/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (rq.isLogined()) {
			return Ut.jsHistoryBack("F-5", "이미 로그인 상태입니다");
		}

		if (Ut.empty(loginId)) {
			return Ut.jsHistoryBack("F-1", "아이디를 입력해주세요");
		}
		if (Ut.empty(loginPw)) {
			return Ut.jsHistoryBack("F-2", "비밀번호를 입력해주세요");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-3", Ut.f("%s는 존재하지 않는 아이디입니다", loginId));
		}

		System.out.println(Ut.sha256(loginPw));

		if (member.getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return Ut.jsHistoryBack("F-4", Ut.f("비밀번호가 일치하지 않습니다!!!!!"));
		}

		rq.login(member);
		
		// 우리가 갈 수 있는 경로를 경우의 수로 표현 
		// 인코딩
		// 그 외에는 처리 불가 -> 메인으로 보내자

		return Ut.jsReplace("S-1", Ut.f("%s님 환영합니다", member.getName()), afterLoginUri);
	}

	@RequestMapping("/usr/member/doLogout")
	@ResponseBody
	public String doLogout(@RequestParam(defaultValue = "/") String afterLogoutUri) {

		if(rq.isNotLogined()) {
			return rq.jsReplace("로그인 후 이용해주세요", "/usr/member/login");
		}
		
		rq.logout();

		return Ut.jsReplace("S-1", "로그아웃 되었습니다", afterLogoutUri);
	}

	@RequestMapping("/usr/member/myPage")
	public String showMyPage() {

		return "usr/member/myPage";
	}

	@RequestMapping("/usr/member/checkPw")
	public String showCheckPw() {

		return "usr/member/checkPw";
	}

	@RequestMapping("/usr/member/doCheckPw")
	@ResponseBody
	public String doCheckPw(String loginPw, String replaceUri) {
		System.out.println("========================================" + loginPw);
		System.out.println("========================================" + replaceUri);
		if (Ut.empty(loginPw)) {
			return rq.jsHitoryBackOnView("비밀번호 입력해");
		}

		if (rq.getLoginedMember().getLoginPw().equals(Ut.sha256(loginPw)) == false) {
			return rq.jsHistoryBack("", "비밀번호 틀림");
		}

		return rq.jsReplace("", replaceUri);
	}

	@RequestMapping("/usr/member/modify")
	public String showModify() {

		return "usr/member/modify";
	}

	@RequestMapping("/usr/member/doModify")
	@ResponseBody
	public String doModify(String loginId, String loginPw, String name, String nickname, String cellphoneNum, String email) {

		Member member = memberService.getMemberByLoginId(loginId);
		
		if (Ut.empty(loginPw)) {
			loginPw = member.getLoginPw();
		} else {
			loginPw = Ut.sha256(loginPw);
		}

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellphoneNum,
				email);

		return rq.jsReplace(modifyRd.getMsg(), "../member/myPage");
	}

	@RequestMapping("/usr/member/findLoginId")
	public String showFindLoginId() {

		return "usr/member/findLoginId";
	}

	@RequestMapping("/usr/member/doFindLoginId")
	@ResponseBody
	public String doFindLoginId(@RequestParam(defaultValue = "/") String afterFindLoginIdUri, String name,
			String email) {

		Member member = memberService.getMemberByNameAndEmail(name, email);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "너는 없는 사람이야");
		}

		return Ut.jsReplace("S-1", Ut.f("너의 아이디는 [ %s ] 야", member.getLoginId()), afterFindLoginIdUri);
	}

	@RequestMapping("/usr/member/findLoginPw")
	public String showFindLoginPw() {

		return "usr/member/findLoginPw";
	}

	@RequestMapping("/usr/member/doFindLoginPw")
	@ResponseBody
	public String doFindLoginPw(@RequestParam(defaultValue = "/") String afterFindLoginPwUri, String loginId,
			String email) {

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return Ut.jsHistoryBack("F-1", "너는 없는 사람이야");
		}

		if (member.getEmail().equals(email) == false) {
			return Ut.jsHistoryBack("F-2", "일치하는 이메일이 없는데?");
		}

		ResultData notifyTempLoginPwByEmailRd = memberService.notifyTempLoginPwByEmail(member);

		return Ut.jsReplace(notifyTempLoginPwByEmailRd.getResultCode(), notifyTempLoginPwByEmailRd.getMsg(),
				afterFindLoginPwUri);
	}

// +============================= V2 ==========================	
//	@RequestMapping("/usr/member/doFindLoginId")
//	@ResponseBody
//	public String doFindLoginId(String name, String email, @RequestParam(defaultValue = "/") String afterLoginUri) {
//
//		if (rq.isLogined()) {
//			return Ut.jsHistoryBack("F-5", "이미 로그인 상태입니다");
//		}
//
//		if (Ut.empty(name)) {
//			return Ut.jsHistoryBack("F-1", "이름을 입력해주세요");
//		}
//		if (Ut.empty(email)) {
//			return Ut.jsHistoryBack("F-2", "이메일을 입력해주세요");
//		}
//
//		Member member = memberService.getMemberByNameAndEmail(name, email);
//
//		if (member == null) {
//			return Ut.jsHistoryBack("F-4", Ut.f("해당 사용자는 존재하지 않습니다."));
//		}
//
//		
//		// 우리가 갈 수 있는 경로를 경우의 수로 표현
//		// 인코딩
//		// 그 외에는 처리 불가 -> 메인으로 보내자
//
//		return Ut.jsReplace("S-1", Ut.f("인증완료%s", member.getLoginId()), Ut.f("/usr/member/yes?name=%s&email=%s", member.getName(), member.getEmail()));
//	}
//	
//	
//	@RequestMapping("/usr/member/yes")
//	public String yes(Model model, String name, String email) {
//		
//		Member member = memberService.getMemberByNameAndEmail(name, email);
//
//		model.addAttribute("member", member);
//		return "usr/member/yes";
//	}
//
//	
}