package com.club.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class UseJDBC {
	public Integer findKou(){
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT kou from zc_kou_info WHERE id = 1";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("kou");
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
	public List<dai> findAlldai() {
		Connection conn = null;
		List<dai> list = new ArrayList<dai>();
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from gm_admin_userinfo ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				dai d = new dai();
				d.setId(rs.getInt("id"));
				d.setBinding_playerId(rs.getInt("binding_playerId"));
				d.setScale(6);
				list.add(d);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public void addDai(dai d)  {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into zc_dail_info(dail_id,inviteCode,scale) values(?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,d.getId());
			ps.setInt(2,d.getBinding_playerId());
			ps.setInt(3,d.getScale());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
