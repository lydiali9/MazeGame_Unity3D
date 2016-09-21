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

public class UpdateResource extends HttpServlet {

	private static final long serialVersionUID = -1194298956491324705L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int mapId = Integer.parseInt(request.getParameter("mapId"));
		int playerId = Integer.parseInt(request.getParameter("playerId"));
		String map = request.getParameter("map");
		int resourceType = Integer.parseInt(request.getParameter("resourceType"));
		
		MapService mapService = new MapServiceImpl();
		int result = mapService.UpdateResource(mapId,playerId,map,resourceType);
		
		writer.write(TransformUtil.toResult(result));
	}

}
