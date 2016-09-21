package com.augmentum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.MapService;
import com.augmentum.service.Impl.MapServiceImpl;

public class QuitOtherMap extends HttpServlet {

	private static final long serialVersionUID = 2324336638096217797L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		int targetPlayerId = Integer.parseInt(request.getParameter("targetPlayerId"));
		
		MapService mapService = new MapServiceImpl();
		mapService.quitOtherMap(targetPlayerId);
	}
}
