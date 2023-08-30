package com.test.myapp.member;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberLoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));

		MemberDAO dao = new MemberDAO();
		int result = dao.isMember(dto);
		
		response.setContentType("text/html; charset=utf-8"); 
		PrintWriter out = response.getWriter();
		if(result == 0) {
			out.println("<script>");
				out.println("alert('비밀번호가 일치하지 않습니다!');");
				out.println("location.href = 'memberLogin.me';");
			out.println("</script>");
			return null;
		} else if(result == -1) {
			out.println("<script>");
				out.println("alert('아이디가 존재하지 않습니다!');");
				out.println("location.href = 'memberLogin.me';");
			out.println("</script>");
			return null;
		} else {	//로그인 성공
			HttpSession session = request.getSession();
			session.setAttribute("id", dto.getId());
			session.setAttribute("name", dto.getName());
			session.setAttribute("lv", dto.getLv());
			session.setAttribute("regdate", dto.getRegdate());
			
			ActionForward forward = new ActionForward();
			forward.setPath("/myapp/index.do");
			forward.setRedirect(false);
			return forward;
		}
	}
}
