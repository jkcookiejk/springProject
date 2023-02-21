package com.br.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.br.spring.member.model.service.MemberService;
import com.br.spring.member.model.vo.Member;

// @Component
@Controller // 해당 Controller클래스를 빈으로 등록하는 어노테이션 
public class MemberController {
	
	@Autowired // DI(Dependency Injection)
	private MemberService mService; 
	
	@Autowired 
	private BCryptPasswordEncoder bcryptPasswordEncoder; 
	
	
	/*
	@RequestMapping(value="login.me") // RequestMapping어노테이션 == HandlerMapping등록
	public void loginMember() {
		System.out.println("MemberController클래스의 loginMember메소드 실행"); 
	}
	*/ 
	
	// 파라미터(요청시 전달값)를 받는 방법(요청시 전달되는 값들 처리 방법)
	   /*
	    * 1. HttpServletRequest를 이용해서 전달받기 (기존의 jsp/servlet때의 방식)
	    *    메소드의 매개변수로 HttpServletRequest를 작성해두면
	    *    스프링컨테이너가 해당 메소드 호출 시 자동으로 해당 객체를 생성해서 주입해줌
	    */
	
	   
	   //@RequestMapping(/*value=*/"login.me")
	   /*
	   public void loginMember(HttpServletRequest request) {
	      String userId = request.getParameter("id");
	      String userPwd = request.getParameter("pwd");
	      
	      System.out.println("ID : " + userId);
	      System.out.println("PWD : " + userPwd);
	   }
	   */ 
	
	  /*
	   * 2) @RequestParam 어노테이션을 이용하는 방법
	   * 	request.getParameter("키"):밸류	의 역할을 대신해주는 어노테이션
	   * 
	   * 	defaultValue 속성으로 기본값 설정 가능 
	   */
	   /*
	   @RequestMapping("login.me")
	   public void loginMember(@RequestParam(value="id", defaultValue="aaa") String userId, 
			   				   @RequestParam("pwd") String userPwd) {
		   
		   System.out.println("ID : " + userId); 
		   System.out.println("PWD : " + userId); 
		   
	   }
	   */ 
	
	   /*
	    * 3) @RequestParam 어노테이션을 생략하는 방법 
	    * 
	    * ** 단, 매개변수명을 요청시절달되는키값(name속성값)과 동일하게 세팅해야됨 
	    *    => 자동으로 값 주입됨 
	    * 
	    */
	   /*
	   @RequestMapping("login.me")
	   public void loginMember(String userId, String userPwd) {
		   
		   // System.out.println("ID : " + userId); 
		   // System.out.println("PWD : " + userId); 
		   
		   Member m = new Member(); 
		   m.setUserId(userId); 
		   m.setUserPwd(userPwd);

	   }
	   */ 
	
	  /*
	   * 4) 커맨드 객체 방식
	   *    다수로 넘어온 값들을 곧바로 vo객체의 필드에 담고자 할 때의 방식 
	   *    
	   *    매개변수로 요청시 전달값들을 담고자하는 vo클래스 타입을 세팅 
	   *    요청시 전달값의 키값(name값)을 vo클래스에 담고자하는 필드명으로 작성
	   *    
	   *    ** 반드시 키값과 담고자하는 필드명 동일해야됨 !! ** 
	   *    
	   *    내부적으로 
	   *    스프링컨테이너가 해당 객체를 "기본생성자로 생성"후 
	   *    "setter메소드"찾아서 요청시 전달값을 해당 필드에 담아줌 
	   */
	/*
	@RequestMapping("login.me")
	public void loginMember(Member m) {
		//System.out.println("ID : " + m.getUserId()); 
		//System.out.println("PWD : " + m.getUserPwd()); 
		
		//MemberService mService = new MemberServiceImpl(); 
		// 개발자가 직접 new 라는 키워드로 객체를 생성하게되면 "결합도가 높아진다고 함" 
		
		// * 결합도가 높을 경우 발생할 수 있는 문제 
		// 1. 생성하는 객체의 클래스가 수정될 경우 현재 이 클래스에서도 일일히 찾아서 수정해야되는 일이 발생
		// 2. 요청때마다 매번 새로이 객체가 생성됨 == 메모리를 빈번하게 사용하게됨 
		
		// => MemberServiceImpl 클래스를 Spring이 관리할 수 있게끔 빈으로 등록 
		// => 해당 객체를 생성해서 가지고 있게됨 => 개발자는 DI로 생성된 객체를 받아서 사용만함
		
		Member loginUser = mService.loginMember(m); 
		
		if(loginUser == null) { // 조회결과 없음 == 로그인 실패 
			System.out.println("로그인 실패"); 
			// requestScope영역에 에러문구를 담고 
			// 에러페이지로 포워딩 
		}else { // 조회결과 있음 == 로그인 성공 
			System.out.println("로그인 성공"); 
			// sessionScope영역에 loginUser를 담기 
			// /spring으로 url 재요청 
		}
		
		
	}
	*/
	
	// * 요청 처리후 응답페이지 포워딩 또는 url재요청, 응답데이터를 Scope에 담는 방법 
	/*
	 * 1) 스프링에서 제공하는 Model객체를 사용 
	 *    포워딩할 응답뷰로 전달하고자 하는 데이터를 맵형식(key-value)으로 담을 수 있는 영역 
	 *    범위로 따지면 requestScope임 
	 *    단, setAttribute가 아닌 addAttribute메소드 이용 
	 *    
	 *    응답할 뷰에 대한 정보는 문자열로 반환 
	 */
	/*
	@RequestMapping("login.me")
	public String loginMember(Member m, Model model, HttpSession session) {
		Member loginUser = mService.loginMember(m); 
		if(loginUser == null) { // 로그인 실패 
			// request에 에러문구 담기 
			model.addAttribute("errorMsg", "로그인실패 ");
			// 에러페이지(/WEB-INF/views/common/errorPage.jsp)로 포워딩 
			return "common/errorPage"; // "/WEB-INF/views/" + "common/errorPage" + ".jsp" 
		}else { // 로그인 성공 
			// session에 Member객체 담기 
			session.setAttribute("loginUser", loginUser); 
			// url 재요청 
			return "redirect:/"; 
			
		}
	}
	*/
	
	/*
	 * 2) 스프링에서 제공하는 ModelAndView 객체를 이용 
	 *    Model은 requestScope영역에 데이터를 key-value세트로 담을 수 있는 영역 
	 *    View는 응답할 뷰에 대한 정보를 담을 수 있는 영역 
	 *    
	 *    단, view는 단독으로 사용 불가 ModelAndView로 같이 사용해야 됨 
	 */
	@RequestMapping("login.me")
	public ModelAndView loginMember(Member m, HttpSession session, ModelAndView mv) {
		
		Member loginUser = mService.loginMember(m); 
		
		if(loginUser == null) {
			mv.addObject("errorMsg", "로그인 실패"); 
			mv.setViewName("common/errorPage");
		}else {
			session.setAttribute("loginUser", loginUser); 
			mv.setViewName("redirect:/"); 
		}
		
		return mv; 
	}
	
	@RequestMapping("logout.me")
	public String logoutMember(HttpSession session) {
		// 세션무효화 
		session.invalidate(); 
		return "redirect:/";
		
	}
	
	@RequestMapping("enrollForm.me")
	public String enrollForm() {
		// /WEB-INF/views/member/memberEnrollForm.jsp
		return "member/memberEnrollForm";
	}
	
	@RequestMapping("insert.me")
	public String insertMember(Member m, Model model) {
		//System.out.println(m); 
		
		//form의 submit이 post방식일 때, 요청시 전달값에 한글이 있을 경우 한글이 깨짐
		
		// 1. 한글 깨짐 => 스프링에서 제공하는 인코딩 필터 등록 (web.xml)
		// 2. 나이를 입력하지 않았을 경우 "" 빈문자열이 넘어오는데 int형 필드에 담을 수 없어서 400에러 발생 
		// 	  => Member클래스의 age필드를 int형 --> String형으로 변경 
		// 3. 비밀번호가 사용자가 입력한 있는 그대로의 평문 
		
		/*
		 *         암호화 
		 * 평문 -------------> 암호문 
		 * 평문 <------------  암호문 
		 *         복호화 
		 *         
		 * 1) 단방향 암호화방식 : 암호화만 가능(즉, 암호문을 가지고 평문을 알아낼 수 없음)
		 * 2) 양방향 암호화방식 : 암호화, 복호화 둘 다 가능 
		 * 
		 *  SHA 방식 (단방향) 
		 *  => 매번 똑같은 평문을 입력하면 똑같은 암호문을 만들어줌 (문제점)
		 *  ex) 1111 => xcd23!34@sdfvber 
		 *      1111 => xcd23!34@sdfvber 
		 *  => 많은 샘플데이터가 쌓여서 어떤 평문입력시 어떤 암호문이 나올지 유추가능 
		 *     암호문만 넣으면 평문을 알려주는 레인보우테이블 같은 게 개발 
		 *     
		 *  솔팅(salting)기법이 추가된 암호화방식 
		 *  * 솔팅기법 
		 *    평문 + salt값(랜덤값) --- 암호화 --> 암호문 
		 *    1111 + 45621   -------------> @234sdf1231324
		 *    1111 + 79832   -------------> @234$#%Ssdfbdrt 
		 *    
		 *  스프링에서의 BCrypt방식의 암호화 == 솔팅기법 추가된 암호화 (보안에 엄청 집착하는 어떤 회사도 사용하는 방식)
		 *  
		 *  1) 스프링시큐리티모듈 관련 라이브러리 추가 (pom.xml)
		 *  2) BCryptPasswordEncoder 클래스를 빈으로 등록 (spring-security.xml)
		 *  3) spring-security.xml 파일을 web.xml 
		 *  
		 */
		
		// 암호화작업 (암호문을 만들어내는 과정)
		// System.out.println("평문 : " + m.getUserPwd()); 
		String encPwd = bcryptPasswordEncoder.encode(m.getUserPwd()); 
		// System.out.println("암호문 : " + encPwd ); 

		m.setUserPwd(encPwd); //암호문으로 덮어씌우기 
		
		int result = mService.insertMember(m); 
		
		if(result > 0) { // 성공 => 메인페이지 
			
			return "redirect:/"; 
			
		}else { // 실패 => 에러문구 담아서 에러페이지 포워딩 
			model.addAttribute("errorMsg", "회원가입 실패"); 
			return "common/errorPage"; 
		}
		
	}
	
}