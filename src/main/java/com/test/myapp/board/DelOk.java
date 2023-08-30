package com.test.myapp.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/delok.do")
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
		BoardDAO dao = new BoardDAO();
		
		HttpSession session = req.getSession();
		
		// 2.5 현재 글에 달린 댓글부터 삭제하기(***)
		dao.delAllComment(seq); // 부모글번호
		
		int result = dao.del(seq);
		
		// 3.
		// 수정할 글번호(seq) 가지고 넘어가기
		if ( result == 1 ) {
			resp.sendRedirect("/myapp/board/list.do");
		} else {
			resp.sendRedirect("/myapp/board/del.do?seq=" + seq);
		}


	}

}

