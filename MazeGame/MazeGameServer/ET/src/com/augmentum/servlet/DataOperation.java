package com.augmentum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.thread.EmptyData;

public class DataOperation extends HttpServlet {

	private static final long serialVersionUID = -7751138218709529638L;

	@Override
	public void init() throws ServletException {

		super.init();
		Thread emptyPosition = new EmptyData();
		emptyPosition.start();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
