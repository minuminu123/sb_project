package com.KoreaIT.smw.demo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	/* Spring Boot 애플리케이션을 서블릿 컨테이너에 배포할 때, 서블릿 컨테이너의 설정을 구성하고 애플리케이션을 초기화하는 역할을 수행 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(DemoApplication.class);
	}

}
