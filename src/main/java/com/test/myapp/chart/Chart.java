package com.test.myapp.chart;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/chart/chart.do")
public class Chart extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// TODO
		// 1. DB 작업 > DAO 위임 > select
		// 2. ArrayList<ChartDTO> 반환 + JSP 호출하기
		
		ChartDAO dao = new ChartDAO();
		
		ArrayList<ChartDTO> list1 = dao.list1(); // 게시물 목록
		ArrayList<ChartDTO> list2 = dao.list2(); // 댓글 목록
		
		
		
		req.setAttribute("list1", list1);
		req.setAttribute("list2", list2);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/chart/chart.jsp");
		dispatcher.forward(req, resp);

	}

}

