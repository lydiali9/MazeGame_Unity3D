package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.bean.Resource;
import com.augmentum.service.ResourceService;
import com.augmentum.service.Impl.ResourceServiceImpl;

public class GetPlayerResource extends HttpServlet {

	private static final long serialVersionUID = -5580336944123474919L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int playerId = Integer.parseInt(request.getParameter("playerId"));
		
		ResourceService resourceService = new ResourceServiceImpl();
		Resource resource = resourceService.getPlayerResource(playerId);
		
		writer.write(resource.resourceToString());
	}

}
