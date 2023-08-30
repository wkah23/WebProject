package com.test.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
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
		int succ = dao.updateMember(dto);

		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		if (succ > 0) {
			out.println("<script>alert('수정 성공');");
			out.println("location.href='member/main.jsp'</script>");
		} else {
			out.println("<script>alert('수정 실패');");
			out.println("location.href='member/main.jsp'</script>");
		}
		return null;
	}
}
