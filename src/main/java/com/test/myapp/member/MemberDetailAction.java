package com.test.myapp.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberDetailAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String member_id = request.getParameter("member_id");
		
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getDetailMember(member_id);
		request.setAttribute("dto", dto);
		
		ActionForward forward = new ActionForward();
		forward.setPath("member/member_detailForm.jsp");
		forward.setRedirect(false);
		return forward;
	}
}
