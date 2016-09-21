package com.augmentum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.augmentum.bean.LoginData;
import com.augmentum.service.PlayerService;
import com.augmentum.service.Impl.PlayerServiceImpl;
import com.augmentum.util.StringUtil;
import com.augmentum.util.TransformUtil;

public class Login extends HttpServlet {

	private static final long serialVersionUID = 8256900935853894607L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		
		if (StringUtil.isNullOrEmpty(account) || StringUtil.isNullOrEmpty(password)) {
			
			//write.print("{result:0}");
			writer.write(TransformUtil.toResultZero());
			return;
		}
		
		//Gets all information of player by account.
		PlayerService playerService = new PlayerServiceImpl();
		LoginData playerInfo = playerService.getLoginData(account,password);
		
		if (playerInfo != null)	{
			
			if (playerInfo.getIsOnline() == 0) {
				
				//Updates the state of playerState.
				playerService.updatePlayerState(playerInfo.getId());
				writer.write(TransformUtil.toIdAndName(playerInfo.getId(), playerInfo.getName()));
				return;
			} else {
				
				writer.write(TransformUtil.toResultNegative());
				return;
			}
		} else {

			writer.write(TransformUtil.toResultZero());
			return;
		}
	}
}
