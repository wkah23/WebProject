package com.test.myapp.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/notice/delok.do")
public class DelOk extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CheckMember cm = new CheckMember();
		cm.check(req, resp);
		
		// 할일
		// 1. 데이터 가져오기(seq)
		// 2. DB 작업 > DAO 위임 > delete
		// 3. 결과 처리
		
		// 1.
		String seq = req.getParameter("seq");
		
		// 2.
		NoticeDAO dao = new NoticeDAO();
		
		HttpSession session = req.getSession();
		
		int result = dao.del(seq);
		
		// 3.
		// 수정할 글번호(seq) 가지고 넘어가기
		if ( result == 1 ) {
			resp.sendRedirect("/myapp/notice/list.do");
		} else {
			resp.sendRedirect("/myapp/notice/del.do?seq=" + seq);
		}


	}

}

