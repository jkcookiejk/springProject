package com.br.spring.ajax.controller;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller 
public class AjaxController {

	/*
	 * 1. HttpServletResponse 객체로 응답데이터 응답하기 
	 */
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
		System.out.println(name); 
		System.out.println(age); 
		
		// 요청처리를 위한 서비스 호출  
		
		// 요청처리가 다 됐다는 가정하에 그 페이지에 돌려줄 응답데이터가 있을 경우? 
		String responseData = "응답문자열 : " + name + "은(는)" + age+ "살 입니다."; 

		response.setContentType("text/html; charset=UTF-8");
		
		response.getWriter().print(responseData); 
		
	}	
	*/
	
	/*
	 * 2. 응답할 데이터를 문자열로 바로 리턴하는 방법 (HttpServletResponse 객체 안 쓸 수 있음) 
	 * 단, 내가 리턴하는 문자열이 포워딩할 응답뷰가 아니라 응답데이터야 라는걸 선언해주는 
	 * @ResponseBody 를 반드시 붙여야됨 
	 */
	/*
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="text/html; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		return "응답문자열 : " + name + "은(는)" + age+ "살 입니다.";
	*/ 
	
	/*
	 * * 다수의 응답데ㅣ터가 있을 경우(HttpServletResponse 객체) 
	 * 
	 */
	
	/*
	@RequestMapping("ajax1.do")
	public void ajaxMethod1(String name, int age, HttpServletResponse response) throws IOException {
	
		//response.setContentType("text/html; charset=UTF-8"); 
		//response.getWriter().print(name); 
		//response.getWriter.print(age); 
		// 연이어서 출력하는 데이터가 하나의 문자열로 연이어져있음 => 따로 접근하기 어려움 
		
		// JSON형태로 담아서 응답 
		// JSONArray	=> [값 값, 값, ...] 			=> 자바에서의 ArrayList와 유사 
		// JSONObject	=> {키:값, 키:값, 키:값, ..} 	=> 자바에서의 HashMap과 유사 
		
		// 방법1.JSONArray로 담아서 응답 
		//JSONArray jArr = new JSONArray(); 
		//jArr.add(name); // ["홍길동"]  
		//jArr.add(age);  // ["홍길동", 12] 
		
		// 방법2.JSONObject로 담아서 응답 
		JSONObject jObj = new JSONObject(); 
		jObj.put("name", name); 
		jObj.put("age", age); // {name:"홍길동", age:12} 
		
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(jObj); 
		
		
	}
	*/ 
	
	/*
	 * * 다수의 응답데이터가 있을 경우 (@ResponseBody 활용) 
	 */
	@ResponseBody
	@RequestMapping(value="ajax1.do", produces="application/json; charset=UTF-8")
	public String ajaxMethod1(String name, int age) {
		JSONObject jObj = new JSONObject(); 
		jObj.put("name", name); 
		jObj.put("age", age); // {name:'홍길동', age:10}
		
		return jObj.toJSONString(); // "{name:'홍길동', age:10}" 
	}
}
