package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.service.MapService;
import com.augmentum.service.Impl.MapServiceImpl;
import com.augmentum.util.StringUtil;
import com.augmentum.util.TransformUtil;

public class GetMap extends HttpServlet {

	private static final long serialVersionUID = 3572215409601880523L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		int mapId = Integer.parseInt(request.getParameter("mapId"));
		
		MapService mapService = new MapServiceImpl();
		String map = mapService.getMap(mapId);
		
		if (StringUtil.isNullOrEmpty(map)) {
			
			map = TransformUtil.toResultZero();
		}
		
		writer.write(map);
	}
}
