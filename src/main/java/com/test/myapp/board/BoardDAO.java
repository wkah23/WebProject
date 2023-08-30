package com.test.myapp.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.test.myapp.DBUtil;

public class BoardDAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public BoardDAO() {
		
		try {
			
			conn = DBUtil.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// AddOk 서블릿이 DTO를 줄테니, insert를 해주세요.
	public int add(BoardDTO dto) {
		
		try {
			
			// thread, depth 추가!!
			String sql = "insert into tblBoards (seq, id, subject, content, regdate, readcount, tag, thread, depth)"
					+ " values (seqBoards.nextVal, ?, ?, ?, default, default, ?, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getSubject());
			pstat.setString(3, dto.getContent());
			pstat.setString(4, dto.getTag());
			
			pstat.setInt(5, dto.getThread());
			pstat.setInt(6, dto.getDepth());
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return 0;
	}
	
	// List 서블릿 - 게시판 목록을 보여주세요..
	public ArrayList<BoardDTO> list(HashMap<String, String> map) {
		
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
			String sql = String.format("select * from vwBoard where rnum between %s and %s %s order by thread desc"
										, map.get("begin")
										, map.get("end")
										, where); 
										
			*/
			
	         String sql = String.format("select * from (select b.*, rownum as rnum from vwBoard3 b %s) where rnum between %s and %s order by thread desc"
	                 , where
	                 , map.get("begin")
	                 , map.get("end"));

			
			pstat = conn.prepareStatement(sql);
			
			rs = pstat.executeQuery();
			
			// 옮겨 담을 큰상자
			ArrayList<BoardDTO> list = new ArrayList<BoardDTO>();
					
			while ( rs.next() ) {
				
				// 레코드 1줄 -> BoardDTO 1개
				BoardDTO dto = new BoardDTO();
				
				dto.setSeq(rs.getString("seq"));
				// dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubject(rs.getString("subject"));
				dto.setReadcount(rs.getString("readcount"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setIsnew(rs.getString("isnew")); // 글쓰고 난뒤 며칠이 지났는지 시간
				dto.setCcnt(rs.getString("ccnt")); // 현재 글에 달린 댓글 갯수
				
				
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
	public BoardDTO get(String seq) {
		
		try {
			
			String sql = "select b.*, (select name from tblUsers where id = b.id ) as name "
					+ "from tblBoards b where seq=?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			if ( rs.next() ) {
				
				BoardDTO dto = new BoardDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setSubject(rs.getString("subject"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setReadcount(rs.getString("readcount"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setTag(rs.getString("tag"));
				
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
			
			String sql = "update tblBoards set readcount = readcount + 1 where seq=?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// EditOk 서블릿이 수정할 DTO를 줄테니 update 해주세요!
	public int edit(BoardDTO dto) {
		
		try {
			
			String sql = "update tblBoards set subject=?, content=?, tag=? where seq=?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getSubject());
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getTag());
			pstat.setString(4, dto.getSeq());
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	
	// DelOk 서블릿이 글번호를 줄테니 글을 삭제해주세요!
	public int del(String seq) {
		
		try {
			
			String sql = "delete from tblBoards where seq=?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	
	// AddComment 서블릿이 댓글을 작성해달라고 요청
	public int addComment(CommentDTO dto) {
		
		try {
			
			String sql = "insert into tblComment (seq, id, content, regdate, pseq)"
					+ " values (seqBoards.nextVal, ?, ?, default, ?)";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, dto.getId());		// id?
			pstat.setString(2, dto.getContent());
			pstat.setString(3, dto.getPseq());
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	// View 서블릿이 댓글 목록 달라고 요청
	public ArrayList<CommentDTO> listComment(String seq) {
		
		try {
			
			// 부모글 번호를 조건으로 받기
			String sql = "select c.*, (select name from tblUsers where id = c.id) as name "
					+ "from tblComment c where pseq = ? order by seq asc";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);
			
			rs = pstat.executeQuery();
			
			ArrayList<CommentDTO> clist = new ArrayList<CommentDTO>();
			
			while ( rs.next() ) {
				
				CommentDTO dto = new CommentDTO();
				
				dto.setSeq(rs.getString("seq"));
				dto.setContent(rs.getString("content"));
				dto.setId(rs.getString("id"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setPseq(rs.getString("pseq"));
				dto.setName(rs.getString("name"));
				
				clist.add(dto);
				
			}
			
			return clist;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	
	// DelComment 서블릿이 글번호를 줄테니 댓글을 삭제해주세요.
	public int delComment(String seq) {
		
		try {
			
			String sql = "delete from tblComment where seq = ?";
			
			pstat = conn.prepareStatement(sql);
			
			pstat.setString(1, seq);		// id?
			
			return pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	// DelOk 서블릿이 글번호를 줄테니 글번호를 부모로 하는 모든 댓글을 삭제해주세요
	public void delAllComment(String seq) {
		
		try {
			
			String sql = "delete from tblComment where pseq = ?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, seq);		// id?
			pstat.executeUpdate(); // 성공시 1 실패시 0
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
			
			String sql = String.format("select count(*) as cnt from tblBoards %s", where);
			
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
			String sql = "select nvl(max(thread), 0) + 1000 as thread from tblBoards";
			
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

			String sql = "update tblBoards set thread = thread - 1 where thread > ? and thread < ?";
			pstat = conn.prepareStatement(sql);
			
			pstat.setInt(1, previousThread);
			pstat.setInt(2, parentThread);
			
			pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
