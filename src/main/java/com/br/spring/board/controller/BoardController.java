package com.br.spring.board.controller;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.br.spring.board.model.service.BoardService;
import com.br.spring.board.model.vo.Board;
import com.br.spring.board.model.vo.Reply;
import com.br.spring.common.model.vo.PageInfo;
import com.br.spring.common.template.FileUpload;
import com.br.spring.common.template.Pagination;
import com.google.gson.Gson;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService bService;
	
	/*
	@RequestMapping("list.bo")
	public String selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, Model model) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		ArrayList<Board> list = bService.selectList(pi);
		
		model.addAttribute("pi", pi);
		model.addAttribute("list", list);
		
		// 포워딩할 응답뷰 => /WEB-INF/views/board/boardListView.jsp
		return "board/boardListView";
		
	}
	*/
	
	// ModelAndView 이용하기
	@RequestMapping("list.bo")
	public ModelAndView selectList(@RequestParam(value="cpage", defaultValue="1") int currentPage, ModelAndView mv) {
		
		int listCount = bService.selectListCount();
		
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 10, 5);
		ArrayList<Board> list = bService.selectList(pi);
		
		/*
		mv.addObject("pi", pi);
		mv.addObject("list", list);
		mv.setViewName("board/boardListView");
		*/
		
		mv.addObject("pi", pi)
		  .addObject("list", list)
		  .setViewName("board/boardListView");
		
		return mv;
	}
	
	@RequestMapping("enrollForm.bo")
	public String enrollForm() {
		// /WEB-INF/views/board/boardEnrollForm.jsp
		return "board/boardEnrollForm";
		
	}
	
	@RequestMapping("insert.bo")
	public String insertBoard(Board b, MultipartFile upfile, 
							HttpSession session, Model model) {
		
		//System.out.println(b);
		//System.out.println(upfile); // 첨부파일 선택했든 안했든 생성된 객체 (단, filename에 원본명이 있냐, ""이냐)
		
		// 전달된 파일이 있을 경우 => 파일명 수정작업 후 서버 업로드 => 원본명, 서버업로드된경로를 b에 이어서 담기
		if(!upfile.getOriginalFilename().equals("")) {
			
			// 파일명 수정 작업 후 서버에 업로드 시키기 ("flower.png" => "2023022312213034512.png")
			/*
			String originName = upfile.getOriginalFilename();
			
			String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			int ranNum = (int)(Math.random() * 90000 + 10000);
			String ext = originName.substring(originName.lastIndexOf("."));
			
			String changeName = currentTime + ranNum + ext;
			
			// 업로드 시키고자 하는 폴더의 물리적인 경로 알아내기 
			String savePath = session.getServletContext().getRealPath("resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(savePath + changeName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			*/
			String saveFilePath = FileUpload.saveFile(upfile, session, "resources/uploadFiles/");
			
			// 원본명, 서버업로드된경로(resources/xxxxxx/xxxxx.jpg)를 b에 이어서 담기
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName(saveFilePath);
		}
		
		// 넘어온 첨부파일 있을 경우 b : 제목, 작성자, 내용, 파일원본명, 파일저장경로
		// 넘어온 첨부파일 없을 경우 b : 제목, 작성자, 내용,  null   ,  null
		int result = bService.insertBoard(b);
		
		if(result > 0) { // 성공 => 알람문구 담아서 => 게시글 리스트페이지
			session.setAttribute("alertMsg", "성공적으로 게시글이 등록되었습니다.");
			return "redirect:list.bo";
		}else { // 실패 => 에러문구 담아서 => 에러페이지
			model.addAttribute("errorMsg", "게시글 등록 실패");
			return "common/errorPage";
		}
		
		
	}
	
	
	@RequestMapping("detail.bo")
	public ModelAndView selectBoard(int no, ModelAndView mv) {
		
		int result = bService.increaseCount(no);
		
		if(result > 0) {
			Board b = bService.selectBoard(no);
			mv.addObject("b", b).setViewName("board/boardDetailView");
		}else {
			mv.addObject("errorMsg", "상세조회 실패").setViewName("common/errorPage");
		}
		
		return mv;
	}
	
	@RequestMapping("delete.bo")
	public String deleteBoard(int no, String filePath, HttpSession session, Model model) {
		// filePath : 해당 게시글의 첨부파일 저장경로 ("resources/xxxxx/xxxxx.jpg" | "")
		
		int result = bService.deleteBoard(no);
		
		if(result > 0) { // 삭제성공 
			
			// 첨부파일이 있었을 경우 => 파일 삭제
			if(!filePath.equals("")) {
				new File(session.getServletContext().getRealPath(filePath)).delete();
			}
			
			// 알람문구 담아서 리스트페이지
			session.setAttribute("alertMsg", "성공적으로 게시글이 삭제되었습니다.");
			return "redirect:list.bo";
			
		}else { // 삭제실패
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("updateForm.bo")
	public String updateForm(int no, Model model) {
		model.addAttribute("b", bService.selectBoard(no));
		return "board/boardUpdateForm";
	}
	
	@RequestMapping("update.bo")
	public String updateBoard(Board b, MultipartFile reupfile, HttpSession session, Model model) {
		
		// 새로 넘어온 첨부파일이 있을 경우
		if(!reupfile.getOriginalFilename().equals("")) {
			
			// 기존의 첨부파일이 있었을 경우 => 기존의 파일 지우기
			if(b.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(b.getChangeName())).delete();
			}
			
			// 새로 넘어온 첨부파일 서버 업로드
			String saveFilePath = FileUpload.saveFile(reupfile, session, "resources/uploadFiles/");
			
			// b에 새로운 첨부파일에대한 원본명, 저장경로 이어서 담기
			b.setOriginName(reupfile.getOriginalFilename());
			b.setChangeName(saveFilePath);
			
		}
		
		/*
		 * b에 boardNo, boardTitle, boardContent 무조건 담겨있음
		 * 
		 * 1. 새로 첨부된 파일 x, 기존 첨부 파일 x
		 *    => originName : null, changeName : null
		 *    
		 * 2. 새로 첨부된 파일 x, 기존 첨부 파일 o
		 *    => originName : 기존첨부파일원본명, changeName : 기존첨부파일저장경로
		 *    
		 * 3. 새로 첨부된 파일 o, 기존 첨부 파일 x
		 *    => 새로 전달된 파일 서버에 업로드
		 *    => originName : 새로운첨부파일원본명, changeName : 새로운첨부파일저장경로
		 *    
		 * 4. 새로 첨부된 파일 o, 기존 첨부 파일 o
		 *    => 기존 파일 삭제
		 *    => 새로 전달된 파일 서버에 업로드
		 *    => originName : 새로운첨부파일원본명, changeName : 새로운첨부파일저장경로
		 *    
		 */
		int result = bService.updateBoard(b);
		
		if(result > 0) {
			// 알람문구 담아서 => 해당 게시글의 상세페이지
			session.setAttribute("alertMsg", "성공적으로 게시글이 수정되었습니다.");
			return "redirect:detail.bo?no=" + b.getBoardNo();
		}else {
			// 에러문구 담아서 => 에러페이지 
			model.addAttribute("errorMsg", "게시글 수정 실패");
			return "common/errorPage";
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value="rlist.bo", produces="application/json; charset=utf-8")
	public String ajaxSelectReplyList(int no) {
		
		ArrayList<Reply> list = bService.selectReplyList(no);
		return new Gson().toJson(list);
		
	}
	
	@ResponseBody
	@RequestMapping("rinsert.bo")
	public String ajaxInsertReply(Reply r) {
		int result = bService.insertReply(r);
		return result > 0 ? "success" : "fail";
	}
	
	@ResponseBody
	@RequestMapping(value="topList.bo", produces="application/json; charset=utf-8")
	public String ajaxSelectTopList() {
		ArrayList<Board> list = bService.selectTopList();
		return new Gson().toJson(list);
	}
	

}






