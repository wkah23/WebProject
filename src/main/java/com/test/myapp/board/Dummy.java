package com.test.myapp.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;

import com.test.myapp.DBUtil;

public class Dummy {
	
	public static void main(String[] args) {
		
		
		String[] id = { "hong", "kim", "admin" };
		String[] subject = { "TEST 제목입니다.",
								"안녕하세요.ㅎㅇ",
								"오늘 날씨 무지덥다",
								"ㅠㅠㅠㅠㅠ아",
								"저는 JAVA개발자 수업 중입니다.",
								"안녕하세요 저는 27살입니다.",
								"ㅎㅇㅎㅇㅎㅇㅎㅇ",
								"다들 뭐하세요",
								"오늘은 수요일이네요",
								"저는 국비수업 80퍼 들었음",
								"아 진짜 너무 덥네",
								"더워서 짜증난다",
								"님들 화이팅ㅇ",
								"머하세요 지금 ㅋㅋ" };
		String content = "내용입니다.";
		String tag = "n";
		
		
		Random rnd = new Random();
		
		Connection conn = null;
		PreparedStatement stat = null;
		
		try {
			
			String sql = "insert into tblBoards (seq, id, subject, content, regdate, readcount, tag, thread, depth)"
					+ " values (seqBoards.nextVal, ?, ?, ?, default, default, ?, ?, ?)";
			conn = DBUtil.open();
			stat = conn.prepareStatement(sql);
			
			int thread = 1000;
			
			for ( int i=0; i<50; i++ ) {
				
				thread += 1000;
				
				stat.setString(1, id[rnd.nextInt(id.length)]);
				stat.setString(2, subject[rnd.nextInt(subject.length)]);
				stat.setString(3, content);
				stat.setString(4, tag);
				stat.setInt(5, thread);
				stat.setInt(6, 0);
				
				stat.executeQuery();
				
				System.out.println(i +"번째 게시물 작성 완료");
				
			}
			
			stat.close();
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
