package com.test.myapp.map;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/map/map.do")
public class Map extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 서블릿 : JSP
		//      1 : 1
		//      1 : N
		//      N : 1
		//      N : N
		
		
		// 1. /map/map.do
		// 2. /map/map.do?no=ex01
		
		String jsp = "";
		String no = req.getParameter("no");

		if ( no == null ) {
			jsp = "map";
		} else {
			jsp = no;
		}
		
		if ( jsp.equals("ex04") ) {
			
			// DB작업
			MapDAO dao = new MapDAO();
			ArrayList<MapDTO> list = dao.list();
			
			req.setAttribute("list", list);
		}
		
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/map/" + jsp + ".jsp");
		dispatcher.forward(req, resp);

	}

}
 