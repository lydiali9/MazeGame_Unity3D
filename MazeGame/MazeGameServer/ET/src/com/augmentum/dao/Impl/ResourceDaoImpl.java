package com.augmentum.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.augmentum.bean.Resource;
import com.augmentum.dao.ResourceDao;
import com.augmentum.util.DBUtil;

public class ResourceDaoImpl implements ResourceDao {

	public int getPlayerMedal(int playerId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT medal FROM `player` a, `resource` b"
				+ " WHERE a.resource_id = b.id" + " AND a.id = ?";
		int result = 0;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			rs = pstm.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}
		
		return result;
	}

	// Gets player`s resource.
	public Resource getPlayerResource(int playerId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT b.medal, b.bdiamonds_number, b.mdiamonds_number, b.sdiamonds_number"
				+ " FROM `player` a, `resource` b"
				+ " WHERE a.resource_id = b.id" + " AND a.id = ?";
		Resource result = new Resource();

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			rs = pstm.executeQuery();

			if (rs.next()) {

				result.setMedal(rs.getInt(1));
				result.setBdiamondsNumber(rs.getInt(2));
				result.setMdiamondsNumber(rs.getInt(3));
				result.setSdiamondsNumber(rs.getInt(4));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}

		return result;
	}
}
