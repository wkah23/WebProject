package com.test.myapp.ref;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ref/view.do")
public class View extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		
		String column = req.getParameter("column");
		String search = req.getParameter("search");
		
		
		
		// 할일
		// 1. 데이터 가져오기(seq)
		// 2. DB 작업 > DAO 위임 > select where seq
		// 3. BoardDTO 반환 > JSP 호출하기 + 전달하기
		
		HttpSession session = req.getSession();
		
		// 1.
		String seq = req.getParameter("seq");
		
		// 2.
		RefBoardDAO dao = new RefBoardDAO();
		
		if (session.getAttribute("read") != null & session.getAttribute("read").toString().equals("n")) {
			
			// 2.3 조회수 증가하기
			dao.updateReadCount(seq);
			
			session.setAttribute("read", "y");
			
		}
		
		RefBoardDTO dto = dao.get(seq);
		
		// 2.5 
		String subject = dto.getSubject();
		String content = dto.getContent();
		
		// 무조건 글 제목과 내용에 들어있는 <script>태그는 비활성화 시키기!!
		subject = subject.replace("<script", "&lt;script").replace("</script>", "&lt;/script&gt;");
		dto.setSubject(subject);
		
		content = content.replace("<script", "&lt;script").replace("</script>", "&lt;/script&gt;");
		dto.setContent(content);

		// 글 내용(content)에 개행 문자 처리하기
		content = content.replace("\r\n", "<br>");
		dto.setContent(content);
		
		
		// 내용으로 검색 중일 때 검색어 강조시키기
		if ( column != null && search != null && column.equals("content")) {
			content = content.replace(search, "<span style='color: tomato; background-color: yellow;'>" 
													+ search + "</span>");
		
			dto.setContent(content);
		}
		
		// 2.7
		// - 댓글 목록 가져오기
		ArrayList<CommentDTO> clist = dao.listComment(seq); // 현재 글번호(= 댓글의 부모글 번호)
		
		// 3.
		req.setAttribute("dto", dto);
		req.setAttribute("column", column);
		req.setAttribute("search", search);
		req.setAttribute("clist", clist);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/ref/view.jsp");
		dispatcher.forward(req, resp);

	}

}
