package com.augmentum.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.augmentum.dao.MapDao;
import com.augmentum.util.DBUtil;
import com.augmentum.util.FloatUtil;

public class MapDaoImpl implements MapDao {

	// Gets mapId by playerId.
	public int getMapId(int playerId) {

		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT map_id FROM `position` WHERE player_id = ? AND current_place = 1";
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

	// Gets map.
	public String getMap(int mapId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT content FROM map WHERE id = ?";
		String result = null;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, mapId);
			rs = pstm.executeQuery();

			if (rs.next()) {
				
				result = rs.getString(1);
			}

		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}
		
		return result;
	}

	// Gets around player count.
	public int getAroundPlayerCount(float longitude, float latitude, int playerId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(*) FROM `position` WHERE player_id <> ?"
				+ " AND CONCAT(longitude, '') BETWEEN ? AND ?"
			    + " AND CONCAT(latitude, '') BETWEEN ? AND ?";
		int result = 0;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude - 0.01f));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(longitude + 0.01f));
			pstm.setFloat(4, FloatUtil.toTwoDecimalPlaces(latitude - 0.01f));
			pstm.setFloat(5, FloatUtil.toTwoDecimalPlaces(latitude + 0.01f));
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

	// Gets around player list.
	public int[] getAroundPlayerList(float longitude, float latitude, int playerId, int count) {

		if (count > 10) {
			
			count = 10;
		}
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT player_id FROM `position` WHERE player_id <> ?"
				+ " AND CONCAT(longitude, '') BETWEEN ? AND ?"
			    + " AND CONCAT(latitude, '') BETWEEN ? AND ?" + " ORDER BY RAND() LIMIT ?";
		int[] result = new int[count];

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, playerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude - 0.01f));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(longitude + 0.01f));
			pstm.setFloat(4, FloatUtil.toTwoDecimalPlaces(latitude - 0.01f));
			pstm.setFloat(5, FloatUtil.toTwoDecimalPlaces(latitude + 0.01f));
			pstm.setInt(6, count);
			rs = pstm.executeQuery();

			int i = 0;
			while (rs.next()) {

				result[i] = rs.getInt(1);
				i++;
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}
		
		return result;
	}

	// Inserts map.
	public int insertMap(String map) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "INSERT INTO map(content) VALUES(?)";
		int result = 0;

		try {

			pstm = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, map);
			pstm.executeUpdate();
			rs = pstm.getGeneratedKeys();

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

	// Changes the mapId of player.
	public int changePlayerMapId(int playerId, int mapId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `position` SET map_id = ? WHERE player_id = ? AND current_place = 1";
		int result = 0;
		
		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, mapId);
			pstm.setInt(2, playerId);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(null, pstm, conn);
		}
		
		return result;
	}

	// Updates map.
	public int updateMap(int mapId, String map) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `map` SET content = ? WHERE id = ?";
		int result = 0;
		
		try {

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, map);
			pstm.setInt(2, mapId);
			result = pstm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(null, pstm, conn);
		}
		
		return result;
	}

	public void updatePlayerResource(int playerId, int resourceType) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `player` a, `resource` b SET ";

		if (resourceType == 2) {

			sql += "b.bdiamonds_number = b.bdiamonds_number + 1";
		} else if (resourceType == 3) {

			sql += "b.medal = b.medal + 1";
		} else if (resourceType == 6) {

			sql += "b.mdiamonds_number = b.mdiamonds_number + 1";
		} else if (resourceType == 7) {

			sql += "b.sdiamonds_number = b.sdiamonds_number + 1";
		}
		
		sql += " WHERE a.resource_id = b.id AND a.id = ?";

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

	// Quits other map.
	public void quitOtherMap(int targetPlayerId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `player` SET is_break = 0 WHERE id = ?";

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, targetPlayerId);
			pstm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(null, pstm, conn);
		}
	}
	
	public void emptyMap() {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "DELETE FROM `map`";
	  
		try {
	   
			pstm = conn.prepareStatement(sql);
			pstm.executeUpdate();
		} catch (SQLException e) {
	  
			e.printStackTrace();
		} finally {
	   
			DBUtil.close(null, pstm, conn);
		}
	}
	
	public static void main(String[] args) {
		
		MapDaoImpl m = new MapDaoImpl();
		
		System.out.println(m.updateMap(11, "12"));
	}
}
