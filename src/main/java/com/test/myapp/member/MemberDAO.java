package com.test.myapp.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.test.myapp.DBUtil;

public class MemberDAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public MemberDAO() {
		
		try {
			
			conn = DBUtil.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MemberDTO login(MemberDTO dto) {
		
		try {
			
			String sql = "select * from tblUsers where id=? and pw=?";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getId());
			pstat.setString(2, dto.getPw());
			
			rs = pstat.executeQuery();
			
			if ( rs.next() ) {
				// 있으면 객체(DTO) 반환
				MemberDTO result = new MemberDTO();

				result.setId(rs.getString("id"));
				result.setName(rs.getString("name"));
				result.setLv(rs.getString("lv"));
				result.setRegdate(rs.getString("regdate"));
				
				return result;
			}
			// 없으면 null 반환
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return null;
	}

}
