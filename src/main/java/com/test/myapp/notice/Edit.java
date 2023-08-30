package com.test.myapp.notice;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notice/edit.do")
public class Edit extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CheckMember cm = new CheckMember();
		cm.check(req, resp);
		
		// 할일
		// 1. 데이터 가져오기(seq)
		// 2. DB 작업 > DAO 위임 > select where seq
		// 3. BoardDTO 반환 > JSP 호출하기 + 전달하기
		
		// 1.
		String seq = req.getParameter("seq");
		
		// 2.
		NoticeDAO dao = new NoticeDAO();
		
		NoticeDTO dto = dao.get(seq);
		
		// 3.
		req.setAttribute("dto", dto);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/notice/edit.jsp");
		dispatcher.forward(req, resp);

	}

}
