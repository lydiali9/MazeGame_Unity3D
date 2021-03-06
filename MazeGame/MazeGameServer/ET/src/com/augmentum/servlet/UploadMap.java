package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.MapService;
import com.augmentum.service.Impl.MapServiceImpl;
import com.augmentum.util.TransformUtil;

public class UploadMap extends HttpServlet {

	private static final long serialVersionUID = 7639551661912546867L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int playerId = Integer.parseInt(request.getParameter("playerId"));
		String map = request.getParameter("map");
		
		MapService mapService = new MapServiceImpl();
		int mapId = mapService.UploadMap(playerId,map);
		
		writer.write(TransformUtil.toMapid(mapId));	
	}

}
