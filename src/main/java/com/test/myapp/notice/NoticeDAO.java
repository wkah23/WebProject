package com.test.myapp.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.test.myapp.DBUtil;

public class NoticeDAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public NoticeDAO() {
		
		try {
			
			conn = DBUtil.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// AddOk 서블릿이 DTO를 줄테니, insert를 해주세요.
	public int add(NoticeDTO dto) {
		
		try {
			
			// thread, depth 추가!!
			String sql = "insert into tblNotice (seq, id, subject, content, regdate, readcount, thread, depth)"
					+ " values (seqNotice.nextVal, ?, ?, ?, default, default, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getSubject());
			pstat.setString(3, dto.getContent());
			
			pstat.setInt(4, dto.getThread());
			pstat.setInt(5, dto.getDepth());
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	// List 서블릿 - 게시판 목록을 보여주세요..
	public ArrayList<NoticeDTO> list(HashMap<String, String> map) {
		
		try {
			
			// 이 메소드의 집합
			// 1. 목록보기
			// 2. 검색하기
			
			String where ="";
			
			if ( map.get("isSearch").equals("y") ) {
				// 검색
				// where name like '%홍길동%'
				// where subject like '%날씨%'
				// where all like '%날씨%'
				
				/*
				if ( map.get("column").equals("all") ) {
					where = String.format(" and where subject like '%%%s%%' or content like '%%%s%%' "
							, map.get("search"), map.get("search"));
				} else {
					where = String.format(" and %s like '%%%s%%' "
							, map.get("column"), map.get("search"));
				}
				*/
				
	            if (map.get("column").equals("all")) {
	                where = String.format(" where subject like '%%%s%%' or content like '%%%s%%' "
	                		, map.get("search"), map.get("search"));
	             } else {
	                where = String.format(" where %s like '%%%s%%' "
	                		, map.get("column"), map.get("search"));
	             }

				
			}
			
			// 페이지 조건 <-> (분리) <-> 검색 조건
			/*
			String sql = String.format("select * from vwNotice where rnum between %s and %s %s order by thread desc"
										, map.get("begin")
										, map.get("end")
										, where); 
										
			*/
			
	         String sql = String.format("select * from (select b.*, rownum as rnum from vwNotice3 b %s) where rnum between %s and %s order by thread desc"
	                 , where
	                 , map.get("begin")
	                 , map.get("end"));

			
			pstat = conn.prepareStatement(sql);
			
			rs = pstat.executeQuery();
			
			// 옮겨 담을 큰상자
			ArrayList<NoticeDTO> list = new ArrayList<NoticeDTO>();
					
			while ( rs.next() ) {
				
				// 레코드 1줄 -> NoticeDTO 1개
				NoticeDTO dto = new NoticeDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setReadcount(rs.getString("readcount"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setIsnew(rs.getString("isnew")); // 글쓰고 난뒤 며칠이 지났는지 시간
				
				dto.setThread(rs.getInt("thread"));
				dto.setDepth(rs.getInt("depth"));
				
				list.add(dto);
				
			}
			
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	// View 서블릿이 글번호를 줄테니 레코드 내용 전부를 DTO에 담아서 돌려주세요!
	public NoticeDTO get(String seq) {
		
		try {
			
			String sql = "select b.*, (select name from tblUsers where id = b.id ) as name "
					+ "from tblNotice b where seq=?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			if ( rs.next() ) {
				
				NoticeDTO dto = new NoticeDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setReadcount(rs.getString("readcount"));
				dto.setRegdate(rs.getString("regdate"));
				
				// view.jsp에 thread와 depth를 넘겨주기위해 추가하기
				dto.setThread(rs.getInt("thread"));
				dto.setDepth(rs.getInt("depth"));
				
				return dto;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	// View 서블릿이 글번호를 줄테니 조회수를 +1 해주세요!
	public void updateReadCount(String seq) {
		
		try {
			
			String sql = "update tblNotice set readcount = readcount + 1 where seq=?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// EditOk 서블릿이 수정할 DTO를 줄테니 update 해주세요!
	public int edit(NoticeDTO dto) {
		
		try {
			
			String sql = "update tblNotice set subject=?, content=? where seq=?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getSubject());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getSeq());
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	
	// DelOk 서블릿이 글번호를 줄테니 글을 삭제해주세요!
	public int del(String seq) {
		
		try {
			
			String sql = "delete from tblNotice where seq=?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	// List 서블릿이 총 게시물 수 알려달라고 요청
	public int getTotalCount(HashMap<String, String> map) {
		
		
		try {
			
			String where ="";
			
			if ( map.get("isSearch").equals("y") ) {
				
				if ( map.get("column").equals("all") ) {
					where = String.format(" where subject like '%%%s%%' or content like '%%%s%%' "
							, map.get("search"), map.get("search"));
				} else {
					where = String.format(" where %s like '%%%s%%' "
							, map.get("column"), map.get("search"));
				}
				
			}
			
			String sql = String.format("select count(*) as cnt from tblNotice %s", where);
			
			pstat = conn.prepareStatement(sql);
			
			rs = pstat.executeQuery();
			
			if ( rs.next() ) {
				return rs.getInt("cnt");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	// AddOk 서블릿이 가장 큰 thread값을 알려달라고 요청
	public int getMaxThread() {
		
		try {
			
			// nullvalue = nvl 사용해서 쿼리작성
			// -> 안하면 그냥 null
			// -> 하면 1000
			String sql = "select nvl(max(thread), 0) + 1000 as thread from tblNotice";
			
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			if ( rs.next() ) {
				return rs.getInt("thread");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	// AddOk 서블릿이 답변 글쓰기에 필요한 업무를 위임
	public void updateThread(int parentThread, int previousThread) {
		
		try {
			
			// a. 현존 모든게시물의 thread값을 대상으로 현재 작성 중인 답변글인 부모글의 thread값보다 작고, 이전 새글의 thread값보다 큰 thread를 찾아서 모두 -1 한다.

			String sql = "update tblNotice set thread = thread - 1 where thread > ? and thread < ?";
			pstat = conn.prepareStatement(sql);
			
			pstat.setInt(1, previousThread);
			pstat.setInt(2, parentThread);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
