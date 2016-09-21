package com.augmentum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.PlayerService;
import com.augmentum.service.Impl.PlayerServiceImpl;

public class Logout extends HttpServlet {
	
	private static final long serialVersionUID = 1351317076312337906L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		int playerId = Integer.parseInt(request.getParameter("playerId"));
		
		PlayerService playerService = new PlayerServiceImpl();
		playerService.logout(playerId);
	}

}
