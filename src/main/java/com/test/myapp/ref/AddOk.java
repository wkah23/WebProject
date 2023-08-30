package com.test.myapp.ref;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

@WebServlet("/ref/addok.do")
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
		
		RefBoardDAO dao = new RefBoardDAO();
		RefBoardDTO dto = new RefBoardDTO();
		// 1.
		String subject = req.getParameter("subject");
		String content = req.getParameter("content");
		
		// 로그인한 아이디를 가져오기 위해 session을 가져온다.
		HttpSession session = req.getSession();
		
		dto.setId(session.getAttribute("id").toString());
		dto.setSubject(subject);
		dto.setContent(content);
		
		// 파일 업로드
		String saveDirectory = req.getServletContext().getRealPath("/Uploads");
		ServletContext application = getServletContext();
		int maxPostSize = Integer.parseInt(
				application.getInitParameter("maxPostSize"));
		MultipartRequest mr = FileUtil.uploadFile(req, saveDirectory, maxPostSize);
		if (mr == null) {
			JSFunction.alertLocation(resp, "첨부 파일이 제한 용량을 초과합니다.",
					"/myapp/ref/add.do");
			return;
		}
		String fileName = mr.getFilesystemName("ofile");
		// 서버에 저장된 파일이 있는 경우에만 파일명 변경 처리를 한다.
		// 만약 첨부하지 않았더라면 아래코드는 실행하지 않는다.
		if (fileName != null) {
			// 날짜와 시간을 이용해서 파일명을 생성한다.
			String now = new SimpleDateFormat("yyyyMMdd_HmsS").format(new Date());
			// 파일명의 마지막에 있는 .(닷)의 인덱스를 찾은 후 확장자를 걸러낸다.
			String ext = fileName.substring(fileName.lastIndexOf("."));
			// 파일명과 확장자를 연결해서 새로운 파일명을 생성한다.
			String newFileName = now + ext;
			// 파일객체를 생성한 후 파일명을 변경한다.
			File oldFile = new File(saveDirectory + File.separator + fileName);
			File newFile = new File(saveDirectory + File.separator + newFileName);
			oldFile.renameTo(newFile);
			// DTO객체에 원본파일명과 저장된 파일명을 저장한다.
			dto.setOfile(fileName);
			dto.setSfile(newFileName);
		}
		// 2.
		
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
			resp.sendRedirect("/myapp/ref/list.do");
		} else {
			resp.sendRedirect("/myapp/ref/add.do");
		}
	}

}
