package com.augmentum.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.augmentum.dao.PositionDao;
import com.augmentum.util.DBUtil;
import com.augmentum.util.FloatUtil;

public class PositionDaoImpl implements PositionDao{

	//Checks whether there are player by playerId.
	public boolean hasPlayer(int playerId) {
			  
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM `position` WHERE player_id = ?";
		boolean result = false;

		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			rs = pstm.executeQuery();
   
			if (rs.next()) {

				if (rs.getInt(1) != 0) {
				    
					result = true;
				}
			}
		} catch (SQLException e) {
   
			e.printStackTrace();
		} finally {
   
			DBUtil.close(rs, pstm, conn);
		}
		
		return result;
	}

		//Checks whether there are player by playerId,longitude,latitude.
	public boolean hasPlayerPosition(float longitude, float latitude, int playerId) {
			 
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM `position` WHERE player_id = ?"
				+ " AND CONCAT(longitude, '') BETWEEN ? AND ?"
			    + " AND CONCAT(latitude, '') BETWEEN ? AND ?";
		boolean result = false;
			  
		try {
			   
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude - 0.01f));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(longitude + 0.01f));
			pstm.setFloat(4, FloatUtil.toTwoDecimalPlaces(latitude - 0.01f));
			pstm.setFloat(5, FloatUtil.toTwoDecimalPlaces(latitude + 0.01f));
			rs = pstm.executeQuery();
			   
			if (rs.next()) {

				if(rs.getInt(1) != 0) {
				   
					result = true;
				}
			}
		} catch (SQLException e) {
			   
			e.printStackTrace();
		} finally {
			   
			DBUtil.close(rs, pstm, conn);
		}
			  
		return result;
	}

	public float[] getCurrentPosition(float longitude, float latitude, int playerId) {

		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT longitude, latitude FROM `position` WHERE player_id = ?"
				+ " AND CONCAT(longitude, '') BETWEEN ? AND ?"
			    + " AND CONCAT(latitude, '') BETWEEN ? AND ?";
		float[] result = new float[2];
		
		try {
	
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude - 0.01f));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(longitude + 0.01f));
			pstm.setFloat(4, FloatUtil.toTwoDecimalPlaces(latitude - 0.01f));
			pstm.setFloat(5, FloatUtil.toTwoDecimalPlaces(latitude + 0.01f));
			rs = pstm.executeQuery();
			   
			if (rs.next()) {

				result[0] = rs.getFloat(1);
				result[1] = rs.getFloat(2);
			}
		} catch (SQLException e) {
			   
			e.printStackTrace();
		} finally {
			   
			DBUtil.close(rs, pstm, conn);
		}
			  
		return result;
	}

		//Checks whether player enter to current position.
	public boolean isCurrentPosition(float longitude, float latitude, int playerId) {
			
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT current_place FROM `position` WHERE player_id = ?"
				+ " AND concat(longitude,'') = ? AND concat(latitude,'') = ?";
		boolean result = false;
			  
		try {
			   
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(latitude));
			rs = pstm.executeQuery();
			   
			if (rs.next()) {
				
				if (rs.getInt(1) == 1) {
				    
					result = true;
				}
			}
		} catch (SQLException e) {
			   
			e.printStackTrace();
		} finally {
			   
			DBUtil.close(rs, pstm, conn);
		}
			  
		return result;
	}

	public void changePlayerPosition(int playerId) {
			
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `position` SET current_place = 0 WHERE player_id = ?";
			  
		try {
			   
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.executeUpdate();
		} catch (SQLException e) {
   
			e.printStackTrace();
		} finally {
			
			DBUtil.close(null, pstm, conn);
		}
	}

		//Changes player current position.
	public void changePlayerCurrentPosition(float longitude, float latitude, int playerId) {
	 
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `position` SET current_place = 1 WHERE player_id = ?"
				+ " AND concat(longitude,'') = ? AND concat(latitude,'') = ?";
	  
		try {
	   
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(latitude));
			pstm.executeUpdate();
		} catch (SQLException e) {
	  
			e.printStackTrace();
		} finally {
	   
			DBUtil.close(null, pstm, conn);
		}
	}

	//Adds the position of player.
	public void addPlayerPosition(float longitude, float latitude, int playerId) {
	 
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "INSERT INTO `position`(player_id, map_id, longitude, latitude, current_place)"
				+ " VALUES(?, ?, ?, ?, ?)";
	
		try {
	   
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setInt(2, 0);
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(longitude));
			pstm.setFloat(4, FloatUtil.toTwoDecimalPlaces(latitude));
			pstm.setInt(5, 1);
			pstm.executeUpdate();
		} catch (SQLException e) {
	  
			e.printStackTrace();
		} finally {
	   
			DBUtil.close(null, pstm, conn);
		}
	}
	
	public void emptyPosition() {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "DELETE FROM `position`";
	  
		try {
	   
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (SQLException e) {
	  
			e.printStackTrace();
		} finally {
	   
			DBUtil.close(null, pstm, conn);
		}
	}
}
