package com.KoreaIT.smw.demo.vo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.KoreaIT.smw.demo.service.ChatRoomService;
import com.KoreaIT.smw.demo.service.ChatService;
import com.KoreaIT.smw.demo.service.MemberService;
import com.KoreaIT.smw.demo.util.Ut;

import lombok.Getter;
import lombok.Setter;
/* 세션값을 저장해두는 클래스로 전역에서사용가능하도록 설정 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {
	@Getter
	boolean isAjax;
	@Getter
	private boolean isLogined;
	@Getter
	private int loginedMemberId;
	@Getter
	private Member loginedMember;
	@Getter
	private String loginedMemberLoginId;
	@Getter
	@Setter
	private int PunReadCount;
	
	@Getter
	@Setter
	private int CunReadCount;
	
	@Autowired
	ChatRoomService chatRoomService;
	
	@Autowired
	ChatService chatService;
	
	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;

	private Map<String, String> paramMap;

	/* Rq 클래스의 생성자이며, HttpServletRequest, HttpServletResponse, MemberService를 매개변수로 받는다
	 * 변수들을 this를 통해 할당하고 로그인 상태인지 여부(isLogined), 로그인된 회원의 아이디(loginedMemberId), 
	 * 로그인된 회원의 정보(loginedMember)를 저장하고 isAjax 변수를 초기화한다. 초기값은 false이고
	요청이 Ajax 요청인지 확인한다. Ajax 요청은 URI가 "Ajax"로 끝나는 경우를 말한다. 그 외에도 ajax 또는 isAjax 파라미터 값이 "Y"인 경우도 Ajax 요청으로 간주한다.
	isAjax 값을 멤버 변수에 할당한다 */
	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {
		this.req = req;
		this.resp = resp;

		this.session = req.getSession();

		paramMap = Ut.getParamMap(req);

		boolean isLogined = false;
		int loginedMemberId = 0;
		Member loginedMember = null;
		String loginedMemberLoginId = null;


		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
			loginedMember = memberService.getMemberById(loginedMemberId);
			loginedMemberLoginId = (String) session.getAttribute("loginedMemberLoginId");

			
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		this.loginedMemberLoginId = loginedMemberLoginId;

		this.req.setAttribute("rq", this);

		String requestUri = req.getRequestURI();

		boolean isAjax = requestUri.endsWith("Ajax");

		if (isAjax == false) {
			if (paramMap.containsKey("ajax") && paramMap.get("ajax").equals("Y")) {
				isAjax = true;
			} else if (paramMap.containsKey("isAjax") && paramMap.get("isAjax").equals("Y")) {
				isAjax = true;
			}
		}
		if (isAjax == false) {
			if (requestUri.contains("/get")) {
				isAjax = true;
			}
		}
		this.isAjax = isAjax;

	}

	/* historyback() 하는 함수로 resp.setContentType("text/html; charset=UTF-8"); 를 통해 인코딩 해준다.
	 * 
 */
	public void printHitoryBackJs(String msg) throws IOException {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsHistoryBack("F-B", msg));
	}
	/* 			resp.getWriter().append(str);를 통해 스트링을 덧붙인다. */
	public void print(String str) {
		try {
			resp.getWriter().append(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void println(String str) {
		print(str + "\n");
	}

	// 로그인이 되었다면 세션에 loginedMemberId라는 속성을 설정해준다.
	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
		session.setAttribute("loginedMemberLoginId", member.getLoginId());

	}
	// 로그아웃하면 loginedMemberId 속성값을 삭제한다.
	public void logout() {
		session.removeAttribute("loginedMemberId");
	}
	// js.jsp 에서 스크립트문을 통해 historyback이 되도록 설정한 함수
	public String jsHitoryBackOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";

	}
	
	public String jsHistoryBackOnView(String resultCode, String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "usr/common/js";

	}

	public String jsHistoryBack(String resultCode, String msg) {
		return Ut.jsHistoryBack(resultCode, msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}

	public String getCurrentUri() {
		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		System.out.println(currentUri);
		System.out.println(queryString);

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		System.out.println(currentUri);
		return currentUri;
	}

	// Rq 객체 생성 유도
	// 삭제 x, BeforeActionInterceptor 에서 강제 호출
	public void initOnBeforeActionInterceptor() {
		// 해당 memberId가 속하는 개인 채팅방 가져오기
		List<PersonalChatRoom> PList = chatRoomService.getPersonalChatRoomsByMemberId(getLoginedMemberId());
		
		int PunReadCount = 0;
		// 개인채팅방에서 상대방의 이름과 읽지 않은 채팅 수를 가져오기 위한 반복문
		for (PersonalChatRoom room : PList) {
			if (room.getMemberId1() == getLoginedMemberId()) {
				int tmp1 = room.getMemberId1();
				room.setMemberId1(room.getMemberId2());
				room.setMemberId2(tmp1);

				String tmp2 = room.getMember1_name();
				room.setMember1_name(room.getMember2_name());
				room.setMember2_name(tmp2);
			}

			String roomType = "Personal";

			int lastReadId = chatService.getLastReadId(room.getId(), getLoginedMemberId(), roomType);

			int unreadCount = chatService.getPersonalChatUnreadCount(room.getId(), getLoginedMemberId(), roomType,
					lastReadId);

			room.setUnreadCount(unreadCount);
			
			PunReadCount += unreadCount;
		}
		
		setPunReadCount(PunReadCount);

		// 해당 memberId가 속하는 동호회 채팅방 가져오기
		List<ClubChatRoom> CList = chatRoomService.getClubChatRoomsByMemberId(getLoginedMemberId());
		
		int CunReadCount = 0;
		// 동호회 채팅방에서 읽지 않은 채팅의 수를 가져오는 것
		for (ClubChatRoom room : CList) {
			String roomType = "Club";

			int lastReadId = chatService.getLastReadId(room.getId(), getLoginedMemberId(), roomType);

			int unreadCount = chatService.getClubChatUnreadCount(room.getId(), getLoginedMemberId(), roomType,
					lastReadId);

			room.setUnreadCount(unreadCount);
			
			CunReadCount += unreadCount;
		}
		
		setCunReadCount(CunReadCount);
	}
	/* 로그인하지 않았다면 true를 리턴하는 함수 */
	public boolean isNotLogined() {
		return !isLogined;
	}

	/* 메인화면에 접속할때 rq를 실행하는함수 */
	public void run() {
		System.out.println("===========================run A");
	}

	/* 돌아갈 replaceUri와 메시지를 받는 함수(인코딩) */
	public void jsprintReplace(String msg, String replaceUri) {
		resp.setContentType("text/html; charset=UTF-8");
		print(Ut.jsReplace(msg, replaceUri));
	}
	/* 회원가입후 돌아갈 uri적은 함수 */
	public String getJoinUri() {
		return "/usr/member/join?afterLoginUri=" + getAfterLoginUri();
	}
	/* 로그인후 돌아갈 uri적은 함수 */
	public String getLoginUri() {
		return "/usr/member/login?afterLoginUri=" + getAfterLoginUri();
	}
	/* 로그아웃 후 돌아갈 uri 적은 함수 */
	public String getLogoutUri() {
		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/article/write":
			return "../member/doLogout?afterLogoutUri=" + "/";
		case "/adm/member/list":
			return "../member/doLogout?afterLogoutUri=" + "/";
		}

		return "../member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}

	public String getAfterLogoutUri() {
		return getEncodedCurrentUri();
	}

	public String getAfterLoginUri() {
//		로그인 후 접근 불가 페이지

		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/usr/member/login":
		case "/usr/member/join":
			return Ut.getEncodedUri(Ut.getAttr(paramMap, "afterLoginUri", ""));
		}

		return getEncodedCurrentUri();
	}

	public String getEncodedCurrentUri() {
		return Ut.getEncodedCurrentUri(getCurrentUri());
	}

	public String getArticleDetailUriFromArticleList(Article article) {
		return "/usr/article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}

	public String getFindLoginIdUri() {
		return "/usr/member/findLoginId?afterFindLoginIdUri=" + getAfterFindLoginIdUri();
	}

	private String getAfterFindLoginIdUri() {
		return getEncodedCurrentUri();
	}
	/* 패스워드를 찾은후 돌아갈 uri를 적은 함수 */
	public String getFindLoginPwUri() {
		return "/usr/member/findLoginPw?afterFindLoginPwUri=" + getAfterFindLoginPwUri();
	}

	private String getAfterFindLoginPwUri() {
		return getEncodedCurrentUri();
	}

	public boolean isAdmin() {
		if (isLogined == false) {
			return false;
		}

		return loginedMember.isAdmin();
	}

	public String getImgUri(int id) {
		return "/common/genFile/file/article/" + id + "/extra/Img/1";
	}

	public String getProfileFallbackImgUri() {
		return "https://via.placeholder.com/150/?text=*^_^*";
	}

	public String getProfileFallbackImgOnErrorHtml() {
		return "this.src = '" + getProfileFallbackImgUri() + "'";
	}
	
	public String getRemoveProfileImgIfNotExitOnErrorHtmlAttr() {
		return "$(this).remove()";
	}


}