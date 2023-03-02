package com.br.spring.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

/*
 * * Interceptor (정확히는 HandlerInterceptor)
 * - 해당 Controller가 실행되기전, 실행된 후에 낚아채서 실행할 내용 작성 가능 
 * ex) 해당 요청을 할 수 있는 회원이 맞는지 "로그인여부판단"
 *     해당 요청을 할 수 있는 권한의 회원이 맞는지 "권한 체크할때"
 *     
 * preHandle(전처리) : DispatcherServlet이 Controller를 호출하기 전에 낚아채는 영역
 * postHandle(후처리) : Controller에서 요청처리 후 DispatcherServlet으로 뷰 정보가 리턴되는 순간 낚아채는 영역
 */
public class LoginInterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// true 리턴시 => 기존 요청 흐름대로 해당 Controller 제대로 실행
		// false 리턴시 => Controller 실행되지 않음
		
		HttpSession session = request.getSession();
		
		// 현재 요청을 보낸 사람이 로그인이 되어있을 경우 => 정상적으로 Controller 실행
		if(session.getAttribute("loginUser") != null) {
			return true;
		}else {
		// 로그인이 되어있지 않을 경우 => Controller 실행 안되게
			session.setAttribute("alertMsg", "로그인 후 이용가능한 서비스입니다.");
			response.sendRedirect(request.getContextPath());
			return false;		
		}
	}
	
}
