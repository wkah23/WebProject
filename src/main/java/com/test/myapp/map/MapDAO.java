package com.test.myapp.map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.test.myapp.DBUtil;

public class MapDAO {
	
	private Connection conn;
	private Statement stat;
	private PreparedStatement pstat;
	private ResultSet rs;
	
	public MapDAO() {
		
		try {
			
			conn = DBUtil.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void add(MapDTO dto) {
		
		try {
			
			String sql = "insert into tblPlace (seq, lat, lng, name) values (seqPlace.nextVal, ?, ?, ?)";
			
			pstat = conn.prepareStatement(sql);
			pstat.setString(1, dto.getLat());
			pstat.setString(2, dto.getLng());
			pstat.setString(3, dto.getName());
			
			pstat.executeQuery();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<MapDTO> list() {
		
		try {
			
			String sql = "select * from tblPlace order by name asc";
			
			stat = conn.createStatement();
			rs = stat.executeQuery(sql);
			
			ArrayList<MapDTO> list = new ArrayList<MapDTO>();
			
			while ( rs.next() ) {
				
				MapDTO dto = new MapDTO();
				dto.setLat(rs.getString("lat"));
				dto.setLng(rs.getString("lng"));
				dto.setName(rs.getString("name"));
				
				list.add(dto);
				
			}
			
			return list;
					
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
