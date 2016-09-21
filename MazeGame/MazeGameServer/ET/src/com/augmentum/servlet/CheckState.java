package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.PlayerService;
import com.augmentum.service.Impl.PlayerServiceImpl;
import com.augmentum.util.TransformUtil;

public class CheckState extends HttpServlet {

	private static final long serialVersionUID = -3211974413993420072L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int id = Integer.parseInt(request.getParameter("id"));
		
		PlayerService playerService = new PlayerServiceImpl();
		int isBreak = playerService.CheckState(id);
		
		writer.write(TransformUtil.toIsBreak(isBreak));
	}
}
