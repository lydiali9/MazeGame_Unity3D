package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.ResourceService;
import com.augmentum.service.Impl.ResourceServiceImpl;
import com.augmentum.thread.AutomaticChangePlayerState;
import com.augmentum.util.TransformUtil;

public class GetOtherState extends HttpServlet {

	private static final long serialVersionUID = 4792971094780049627L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int targetPlayerId = Integer.parseInt(request.getParameter("targetPlayerId"));
		int playerId = Integer.parseInt(request.getParameter("playerId"));
		float longitude = Float.parseFloat(request.getParameter("longitude"));
		float latitude = Float.parseFloat(request.getParameter("latitude"));

		ResourceService resourceService = new ResourceServiceImpl();
		int mapId = resourceService.GetOtherState(targetPlayerId, playerId,	longitude, latitude);
		
		if (mapId <= 0) {
			
			writer.write(TransformUtil.toResult(mapId + 1));
		} else {
			
			Thread automaticChangePlayerState = new AutomaticChangePlayerState(targetPlayerId);
			automaticChangePlayerState.start();
			writer.write(TransformUtil.toResultAndMapid(2, mapId));
		}
	}
}
