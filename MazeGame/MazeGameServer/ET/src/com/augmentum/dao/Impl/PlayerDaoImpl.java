package com.augmentum.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.augmentum.bean.LoginData;
import com.augmentum.dao.PlayerDao;
import com.augmentum.util.DBUtil;
import com.augmentum.util.FloatUtil;

public class PlayerDaoImpl implements PlayerDao {

	// Gets all information of player by account.
	public LoginData getLoginData(String account, String password) {

		LoginData loginData = null;
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT id, name, is_online FROM `player`"
				+ " WHERE account = ? AND password = ?";
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, account);
			pstm.setString(2, password);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				
				loginData = new LoginData();
				loginData.setId(rs.getInt(1));
				loginData.setName(rs.getString(2));
				loginData.setIsOnline(rs.getInt(3));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			DBUtil.close(rs, pstm, conn);
		}
		
		return loginData;
	}

	// Display player online.
	public void updatePlayerState(int id) {

		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE player p SET p.is_online = 1 WHERE p.id = ?";

		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(null, pstm, conn);
		}
	}

	// Checks whether other player to enter.
	public int CheckState(int id) {
		
		int flag = 0;
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT is_break FROM player p WHERE p.id = ?";

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();
			
			if (rs.next()) {
				
				flag = rs.getInt(1);
			}
		} catch (SQLException e) {
	
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}
		
		return flag;
	}

	// Gets other player to enter the time.
	public Date getBreakTime(int id) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT break_time FROM player WHERE id = ? AND is_break = 1";
		Date result = null;
		
		try {
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			rs = pstm.executeQuery();

			if (rs.next()) {

				result = rs.getTime(1);
			} else {
				
				result = new Date(0);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}
		
		return result;
	}

	// Checks whether player is busy.
	public boolean isPlayerBusy(int targetPlayerId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT is_online, is_break FROM `player` WHERE id = ?";
		boolean result = false;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, targetPlayerId);
			rs = pstm.executeQuery();

			if (rs.next()) {

				if (rs.getInt(1) == 1 || rs.getInt(2) == 1) {

					result = true;
				} else if (rs.getInt(1) == 0 && rs.getInt(2) == 0) {

					result = false;
				}
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(rs, pstm, conn);
		}

		return result;
	}

	// Checks whether there are player in current place.
	public boolean isPlayerInLocal(int targetPlayerId, float longitude, float latitude) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "SELECT current_place FROM `position` WHERE player_id = ?"
				+ " AND longitude BETWEEN ? AND ?"
				+ " AND latitude BETWEEN ? AND ?";
		boolean result = false;

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, targetPlayerId);
			pstm.setFloat(2, FloatUtil.toTwoDecimalPlaces(longitude - 0.01f));
			pstm.setFloat(3, FloatUtil.toTwoDecimalPlaces(longitude + 0.01f));
			pstm.setFloat(4, FloatUtil.toTwoDecimalPlaces(latitude - 0.01f));
			pstm.setFloat(5, FloatUtil.toTwoDecimalPlaces(latitude + 0.01f));
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

	// Changes player`s state.
	public void changePlayerState(int targetPlayerId) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `player` SET is_break = 1, break_time = now() WHERE id = ?";

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

	public void logout(int id) {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `player` SET is_online = 0 WHERE id = ?";

		try {

			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, id);
			pstm.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			
			DBUtil.close(null, pstm, conn);
		}
	}
	
	public void restorePlayerBreakState() {
		
		Connection conn = DBUtil.getConn();
		PreparedStatement pstm = null;
		String sql = "UPDATE `player` SET is_break = 0";

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