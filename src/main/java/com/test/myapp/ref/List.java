package com.test.myapp.ref;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ref/list.do")
public class List extends HttpServlet {
	
	/**
	 * GET or POST
	 * - 페이지를 요청할 때 어느때 GET 요청? POST 요청?
	 * 	1. GET
	 * 		- 브라우저 주소창을 URL을 입력해서 요청할 때
	 * 		- <a>태그로 이동시
	 * 		- location.href 이동 시
	 * 		- resp.sendRedirect() 이동 시
	 * 		- <form method="GET">
	 * 	2. POST
	 * 		- POST는 form이 유일한 방법
	 * 		- <form method="POST">
	 *		- 폼태그의 POST가 아닌 이상, 전부 GET방식이다.
	 * 
	 */
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPostGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		doPostGet(req, resp);

	}

	private void doPostGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// list.do
		// 1. 목록보기 (게시판의 시작 페이지 역할)
		// 	- select ..
		// 2. 검색 결과보기(검색 버튼 눌러서 호출)
		//	- select where ..
		
		String column = req.getParameter("column");
		String search = req.getParameter("search");
		String isSearch = "n";
		
		if ( column != null && search != null 
				&& !column.equals("") 
				&& !search.equals(""))  {
			isSearch = "y";
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("column", column);
		map.put("search", search);
		map.put("isSearch", isSearch);
		
		// 페이징 처리
		// -> 보고 싶은 페이지를 정하기 위한 처리
		int nowPage = 0; 		// 현재 페이지번호
		int totalCount = 0;		// 총 게시물
		int pageSize = 10;		// 한 페이지당 출력할 게시물 수
		int totalPage = 0;		// 총 페이지 수
		int begin = 0;			// 가져올 게시물 시작 위치
		int end = 0;			// 가져올 게시물 끝 위치 
		int n = 0;				// 페이지바 제작
		int loop = 0;			// 페이지바 제작
		int blockSize = 10;		// 페이지바 제작
		
		
		String page = req.getParameter("page");
		
		if ( page == null || page.equals("")) {
			nowPage = 1;
		} else {
			nowPage = Integer.parseInt(page);
		}
		
		
		// nowPage > 지금 보게될 페이지 번호
		// 1page -> where rnum between 1 and 10
		// 2page -> where rnum between 11 and 20
		// 3page -> where rnum between 21 and 30
		
		begin = ( (nowPage - 1) * pageSize ) + 1;
		end = begin + pageSize - 1;
		
		map.put("begin", begin + "");
		map.put("end", end + "");
		
		// 1.
		RefBoardDAO dao = new RefBoardDAO();
		
		// 총 게시물 수 알아내기
		totalCount = dao.getTotalCount(map);
		// System.out.println(totalCount); -> 현재는 총 398개
		
		// 총 페이지 수 알아내기
		// 398 / 10 = 39.8 > 40
		totalPage = (int)Math.ceil( (double)totalCount / pageSize );
		// System.out.println(totalPage); -> 현재는 총 40페이지
		
		
		
		
		String pagebar = "<nav>\r\n"
				+ "			<ul class=\"pagination\">";
		
		
		
		/*
		for ( int i=1; i<=totalPage; i++) {
			if ( i == nowPage ) {
				pagebar += String.format(" <a href='#!' style='color: tomato;'>%d</a> ", i, i);
			} else {
				pagebar += String.format(" <a href='/myapp/board/list.do?page=%d'>%d</a> ", i, i);
			}
		}
		*/
		
		loop = 1; // while 루프 변수
		n = ( (nowPage - 1) / blockSize ) * blockSize + 1; // 출력되는 페이지 변수
		
		
		// 이전 10페이지
		/*if ( n == 1 ) {
			pagebar += String.format(" <a href='#!'>[◀ %d페이지]</a> ", blockSize);
		} else {
			pagebar += String.format(" <a href='/myapp/board/list.do?page=%d'>[◀ %d페이지]</a> ", n-1, blockSize);
		}*/
		
		if ( n == 1 ) {
			pagebar += String.format(" <li class='disabled'><a href='#!' aria-label='Previous'><span aria-hidden='true'>&laquo;</span></a></li> ");
		} else {
			pagebar += String.format(" <li><a href='/myapp/ref/list.do?page=%d'><span aria-hidden='true'>&laquo;</span></a></li> ", n-1, blockSize);
		}

		// 페이지 링크
		/*
		 * while ( !(loop > blockSize || n > totalPage ) ) { if ( n == nowPage ) {
		 * pagebar += String.format(" <a href='#!' style='color: tomato;'>%d</a> ", n);
		 * } else { pagebar +=
		 * String.format(" <a href='/myapp/board/list.do?page=%d'>%d</a> ", n, n); }
		 * 
		 * loop ++; n++; }
		 */
		
		// 페이지에 글이 아무것도 없을떄..
		if (totalPage == 0) {
			pagebar += " <li class='active'><a href='#!'>1</a></li> ";
		}
		
		while ( !(loop > blockSize || n > totalPage ) ) {
			if ( n == nowPage ) {
				pagebar += String.format(" <li class='active'><a href='#!'>%d</a></li> ", n);
			} else {
				pagebar += String.format(" <li><a href='/myapp/ref/list.do?page=%d'>%d</a></li> ", n, n);
			}
			
			loop ++;
			n++;
		}
		
		// 다음 10페이지
		/*
		 * if ( n > totalPage ) { pagebar +=
		 * String.format(" <a href='#!'>[▶ %d페이지]</a> ", blockSize); } else { pagebar +=
		 * String.format(" <a href='/myapp/board/list.do?page=%d'>[%d페이지 ▶]</a> ", n,
		 * blockSize); }
		 */
		
		if ( n > totalPage ) {
			pagebar += String.format(" <li class='disabled'><a href='#!' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li> ");
		} else {
			pagebar += String.format(" <li><a href='/myapp/ref/list.do?page=%d' aria-label='Next'><span aria-hidden='true'>&raquo;</span></a></li> ", n);
		}
		
		
		
		pagebar += "</ul>\r\n"
				+ "  		</nav>";
		

		// 날짜 자르는 업무
		// 1. SQL -> 나중에 쓸꺼임.. -> 처음부터 짜르면 뒤에도 안되기 때문.. 선택 X
		// 2. DAO -> DAO에서는 데이터 가공은 안한다.(원본만 구현), 순수 데이터 입출력만 전달 -> 선택 X
		// 3. Servlet -> 데이터 가공 및 조작 -> 선택 O
		// 4. JSP -> 화면만 구현 -> 선택 X
		
		// 할일
		// 1. DB 작업 > DAO 위임 > select
		// 2. ArrayList<BoardDTO> 반환하기
		// 3. JSP 호출하기 (2번 전달)
		
		// 2.
		ArrayList<RefBoardDTO> list = dao.list(map);
		
		// 2.5
		for ( RefBoardDTO dto : list ) {
			
			// 날짜 > 시,분초 제거
			String regdate = dto.getRegdate();
			regdate = regdate.substring(0, 10);
			dto.setRegdate(regdate);
			
			String subject = dto.getSubject();
			
			// 무조건 글 제목과 내용에 들어있는 <script>태그는 비활성화 시키기!!
			subject = subject.replace("<script", "&lt;script").replace("</script>", "&lt;/script&gt;");
			dto.setSubject(subject);
			
			// 제목이 길면 > 자르기
			if ( subject.length() > 40 ) {
				subject = subject.substring(0, 30) + "...";
				dto.setSubject(subject);
			}
			
			// 제목으로 검색중이면.. 검색어를 강조표시하기
			// "게시판입니다." > 
			// 검색어(게시판) > 
			// "<span style="color: tomato; background-color: yellow;">게시판</sapn>입니다."
			
			if ( isSearch.equals("y") && column.equals("subject")) {
				subject = subject.replace(search, "<span style='color: tomato; background-color: yellow;'>" 
														+ search + "</span>");
			
				dto.setSubject(subject);
			}

		}

		// 새로고침에 의한 조회수 증가 방지 티켓
		HttpSession session = req.getSession();
		
		session.setAttribute("read", "n");
		
		
		// 3.
		req.setAttribute("list", list);
		req.setAttribute("map", map); // *******
		
		
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("totalPage", totalPage);
		req.setAttribute("nowPage", nowPage);
		req.setAttribute("pagebar", pagebar);
		

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/ref/list.jsp");
		dispatcher.forward(req, resp);
	}

}
