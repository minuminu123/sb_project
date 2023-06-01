package com.KoreaIT.smw.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.KoreaIT.smw.demo.interceptor.BeforeActionInterceptor;
import com.KoreaIT.smw.demo.interceptor.NeedAdminInterceptor;
import com.KoreaIT.smw.demo.interceptor.NeedLoginInterceptor;
import com.KoreaIT.smw.demo.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {
	/* application.yml에 적어준 경로를 가져오기 위해 @Value 어노테이션을 활용 */
	@Value("${custom.genFileDirPath}")
	private String genFileDirPath;

	/* 웹 애플리케이션에서 정적 리소스(이미지, CSS 파일, JavaScript 파일 등)를 사용해야 할 때, 
	 * 이러한 리소스들을 웹 요청 경로에 매핑해야 한다. 
	 * addResourceHandlers 메서드를 사용하면 정적 리소스에 대한 핸들러를 등록하여 요청 경로와 실제 파일 경로를 매핑할 수 있다. */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/gen/**").addResourceLocations("file:///" + genFileDirPath + "/")
				.setCachePeriod(20);
	}
	
	// BeforeActionInterceptor 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// NeedLogoutInterceptor 불러오기
		@Autowired
		NeedAdminInterceptor needAdminInterceptor;
	
	// NeedLoginInterceptor 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// NeedLogoutInterceptor 불러오기
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;

	// /resource/common.css
	// 인터셉터 적용
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		InterceptorRegistration ir;

		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.addPathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");
		/* 로그인이 필요한 url들 */
		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/usr/article/write");
		ir.addPathPatterns("/usr/article/doWrite");
		ir.addPathPatterns("/usr/article/modify");
		ir.addPathPatterns("/usr/article/doModify");
		ir.addPathPatterns("/usr/article/doDelete");

		ir.addPathPatterns("/usr/member/myPage");
		ir.addPathPatterns("/usr/member/checkPw");
		ir.addPathPatterns("/usr/member/doCheckPw");
		ir.addPathPatterns("/usr/member/modify");
		ir.addPathPatterns("/usr/member/doModify");
		
		ir.addPathPatterns("/usr/reply/doWrite");
		ir.addPathPatterns("/usr/reply/modify");
		ir.addPathPatterns("/usr/reply/doModify");
		ir.addPathPatterns("/usr/reply/doDelete");

		ir.addPathPatterns("/usr/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doBadReaction");
		ir.addPathPatterns("/usr/reactionPoint/doCancelGoodReaction");
		ir.addPathPatterns("/usr/reactionPoint/doCancelBadReaction");
		ir.addPathPatterns("/usr/chat/list");
		ir.addPathPatterns("/usr/chat/createroom");
		ir.addPathPatterns("/usr/chat/room");
		ir.addPathPatterns("/chat/enterUser");
		ir.addPathPatterns("/chat/sendMessage");
		ir.addPathPatterns("/chat/userlist");
		ir.addPathPatterns("/chat/chatHistory");
		ir.addPathPatterns("/calendar.do");
		ir.addPathPatterns("/calendar.do");
		ir.addPathPatterns("schedule_add.do");
		ir.addPathPatterns("/usr/calender/delete");
		ir.addPathPatterns("/usr/calender/edit");


		
		
		ir.addPathPatterns("/adm/**");
		ir.addPathPatterns("/adm/member/login");
		ir.addPathPatterns("/adm/member/doLogin");
		ir.addPathPatterns("/adm/member/findLoginId");
		ir.addPathPatterns("/adm/member/doFindLoginId");
		ir.addPathPatterns("/adm/member/findLoginPw");
		ir.addPathPatterns("/adm/member/doFindLoginPw");
		/* 로그아웃이 필요한 url들 */
		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/usr/member/login");
		ir.addPathPatterns("/usr/member/doLogin");
		ir.addPathPatterns("/usr/member/join");
		ir.addPathPatterns("/usr/member/doJoin");
		ir.addPathPatterns("/usr/member/findLoginId");
		ir.addPathPatterns("/usr/member/doFindLoginId");
		ir.addPathPatterns("/usr/member/findLoginPw");
		ir.addPathPatterns("/usr/member/doFindLoginPw");
		
		/* 어드민계정으로 로그인시 필요한 url들 */
		ir = registry.addInterceptor(needAdminInterceptor);
		ir.addPathPatterns("/adm/**");
		ir.addPathPatterns("/adm/member/login");
		ir.addPathPatterns("/adm/member/doLogin");
		ir.addPathPatterns("/adm/member/findLoginId");
		ir.addPathPatterns("/adm/member/doFindLoginId");
		ir.addPathPatterns("/adm/member/findLoginPw");
		ir.addPathPatterns("/adm/member/doFindLoginPw");
	}

}
