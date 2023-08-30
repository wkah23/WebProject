package com.test.myapp.map;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/map/addok.do")
public class AddOk extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// TODO
		// 1. 데이터 가져오기
		// 2. DB 작업 > DAO 위임 > insert
		// 3. 결과 처리
		
		// 1.
		String lat = req.getParameter("lat");
		String lng = req.getParameter("lng");
		String name = req.getParameter("name");
		
		// 2.
		MapDAO dao = new MapDAO();
		
		MapDTO dto = new MapDTO();
		
		dto.setLat(lat);
		dto.setLng(lng);
		dto.setName(name);
		
		dao.add(dto);
		
		// 3.
		resp.sendRedirect("/myapp/map/map.do?no=ex04");
		

	}

}
