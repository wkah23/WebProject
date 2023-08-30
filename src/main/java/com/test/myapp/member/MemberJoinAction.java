package com.test.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberJoinAction implements Action{
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//String member_id = request.getParameter("member-id");
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setLv(request.getParameter("lv"));
		dto.setRegdate(request.getParameter("regdate"));
		dto.setAge(Integer.parseInt(request.getParameter("age")));
		dto.setGender(request.getParameter("gender"));
		dto.setEmail(request.getParameter("email"));
		
		MemberDAO dao = new MemberDAO();
		boolean result = dao.Join(dto);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if(result == false) {
			out.println("<script>alert('회원가입 실패!');");
			out.println("location.href='memberJoin.me';</script>");
			return null;
		} else {
			out.println("<script>alert('회원가입 성공!');");
			out.println("location.href='memberLogin.me';</script>");
			return null;
		}
	}
}
