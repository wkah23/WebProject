package com.test.myapp.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/board/addok.do")
public class AddOk extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CheckMember cm = new CheckMember();
		cm.check(req, resp);
		
		// 할일
		// 1. 데이터 가져오기
		// 2. DB 작업 > DAO 위임 > insert
		// 3. 결과 > 후처리
		
		
		// req.setCharacterEncoding("UTF-8");
		// 필터 처리를 했으므로 인코딩작업 안해도됨
		
		// 1.
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		String tag = req.getParameter("tag");
		
		
		// 2.
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = new BoardDTO();
		
		// 로그인한 아이디를 가져오기 위해 session을 가져온다.
		HttpSession session = req.getSession();
		
		dto.setId(session.getAttribute("id").toString());
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setTag(tag);
		
		
		// 새 글쓰기 vs 답변 글쓰기
		String reply = req.getParameter("reply"); // 1 or 0
		int thread = -1;		// 현재글
		int depth = -1;
		int parentThread = -1;	// 부모글
		int parentDepth = -1;
		
		if ( reply.equals("0") ) {
			
			// 새 글쓰기
			// a. 현존 모든 게시물 중에서 가장 큰 thread값을 찾는다. > 0 > 그 찾은 thread값에 +1000 한 값을 현재 새글의 thread값으로 사용한다.
			thread = dao.getMaxThread();

			// b. 현재 새글의 depth는 0을 넣는다.
			depth = 0;
			
		} else {
			
			// 답변 글쓰기
			
			// addok.jsp에서 넘겨준 부모글의 thread, depth 받기
			parentThread = Integer.parseInt(req.getParameter("thread"));
			parentDepth = Integer.parseInt(req.getParameter("depth"));
			
			// a. 현존 모든게시물의 thread값을 대상으로 현재 작성 중인 답변글인 부모글의 thread값보다 작고, 이전 새글의 thread값보다 큰 thread를 찾아서 모두 -1 한다.
			
			// 이전 새글의 thread는 얼마인데???
			int previousThread = (int)Math.floor( (parentThread - 1) / 1000 ) * 1000; // 공식
			
			dao.updateThread(parentThread, previousThread);
			
			// b. 현재 작성중인 답변글의 thread값을 부모글의 thread - 1을 넣는다.
			thread = parentThread - 1;
			
			// c. 현재 작성중인 답변글의 depth값을 부모글의 depth + 1을 넣는다.
			depth = parentDepth + 1;
			
		}
		
		dto.setThread(thread);
		dto.setDepth(depth);
		
		int result = dao.add(dto);
		
		// 3.
		if ( result == 1 ) {
			resp.sendRedirect("/myapp/board/list.do");
		} else {
			resp.sendRedirect("/myapp/board/add.do");
		}
		
		
	}

}
