package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.bean.LoginData;
import com.augmentum.service.PlayerService;
import com.augmentum.service.Impl.PlayerServiceImpl;
import com.augmentum.util.TransformUtil;

public class GetBreakTime extends HttpServlet {

	private static final long serialVersionUID = -1703384396030783790L;

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
		Date breakTime = playerService.getBreakTime(id);
		Date nowTime = new Date();
		int time = (nowTime.getMinutes() - breakTime.getMinutes()) * 60 + (nowTime.getSeconds() - breakTime.getSeconds());

		writer.write(TransformUtil.toTime(180 - time));
	}
}
