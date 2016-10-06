package com.ktds.board.user.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.board.user.biz.UserBiz;
import com.ktds.board.user.biz.UserBizImpl;

public class CheckDuplicateUserEmailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserBiz userBiz;
    public CheckDuplicateUserEmailServlet() {
        super();
        userBiz = new UserBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userEmail = request.getParameter("userEmail");
		
		boolean isExistsUserEmail = userBiz.isExistsUserEmail(userEmail);
		
		PrintWriter out = response.getWriter();
		out.write(isExistsUserEmail + "");
		out.flush();
		out.close();
	}

}
