package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.PositionService;
import com.augmentum.service.Impl.PositionServiceImpl;
import com.augmentum.util.TransformUtil;

public class CheckPosition extends HttpServlet {


	private static final long serialVersionUID = -7233564742383466727L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		float latitude = Float.parseFloat(request.getParameter("latitude"));
		float longitude = Float.parseFloat(request.getParameter("longitude"));
		
		PositionService positionService = new PositionServiceImpl();
		int  mapId = positionService.checkPosition(longitude, latitude, id);
		
		if(mapId == 0){
			
			writer.write(TransformUtil.toResultZero());
		} else {
			
			writer.write(TransformUtil.toResultAndMapid(1, mapId));
		}
	}
}
