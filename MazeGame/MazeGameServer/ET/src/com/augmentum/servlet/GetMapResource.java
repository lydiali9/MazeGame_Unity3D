package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.bean.MapResource;
import com.augmentum.service.MapService;
import com.augmentum.service.Impl.MapServiceImpl;

public class GetMapResource extends HttpServlet {

	private static final long serialVersionUID = 4597817832190794973L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		float longitude = Float.parseFloat(request.getParameter("longitude"));
		float latitude = Float.parseFloat(request.getParameter("latitude"));
		int playId = Integer.parseInt(request.getParameter("playerId"));

		MapService mapService = new MapServiceImpl();
		MapResource mapResource = mapService.GetMapResources(longitude,latitude, playId);

		writer.write(mapResource.mapResourceToJson());
	}
}
