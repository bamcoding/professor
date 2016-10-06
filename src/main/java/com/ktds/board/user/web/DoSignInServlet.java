package com.ktds.board.user.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.board.support.Param;
import com.ktds.board.user.biz.UserBiz;
import com.ktds.board.user.biz.UserBizImpl;
import com.ktds.board.user.vo.UserVO;

public class DoSignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserBiz userBiz;
	
    public DoSignInServlet() {
        super();
        userBiz = new UserBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//TODO Parameter Validation Check..
		
		String userEmail = Param.getStringParam(request, "userEmail");
		String userPassword = Param.getStringParam(request, "userPassword");
		
		if(userEmail == null || userEmail.length() == 0) {
			response.sendRedirect("/Board/signIn?errorCode=2");
		}
		
		if(userPassword == null || userPassword.length() == 0) {
			response.sendRedirect("/Board/signIn?errorCode=2");
		}
		
		UserVO user = new UserVO();
		user.setUserEmail(userEmail);
		user.setUserPassword(userPassword);
		
		
		boolean isSuccess = userBiz.signIn(user, request);
		
		if(isSuccess) {
			response.sendRedirect("/Board/board/list");
		}
		else {
			response.sendRedirect("/Board/signIn?errorCode=1");
		}
		
	}

}
