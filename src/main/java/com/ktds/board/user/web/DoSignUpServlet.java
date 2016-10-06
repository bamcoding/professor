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
import com.sun.xml.internal.ws.util.StringUtils;

public class DoSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private UserBiz userBiz;
	
    public DoSignUpServlet() {
        super();
        userBiz = new UserBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		//TODO Parameter Validation Check..
		
		String userEmail = Param.getStringParam(request, "userEmail");
		String userNickname = Param.getStringParam(request, "userNickname");
		String userPassword1 = Param.getStringParam(request, "userPassword1");
		String userPassword2 = Param.getStringParam(request, "userPassword2");
		
		//데이터가 비어있는지 체크
		if(userEmail == null || userEmail.length() == 0) {
			response.sendRedirect("/Board/signUp?errorCode=2");
			return;
		}
		if(userNickname == null || userNickname.length() == 0) {
			response.sendRedirect("/Board/signUp?errorCode=2");
			return;
		}
		if(userPassword1 == null || userPassword1.length() == 0) {
			response.sendRedirect("/Board/signUp?errorCode=2");
			return;
		}
		if(userPassword2 == null || userPassword2.length() == 0) {
			response.sendRedirect("/Board/signUp?errorCode=2");
			return;
		}
		
		//이메일 중복체크
		if(userBiz.isExistsUserEmail(userEmail)) {
			response.sendRedirect("/Board/signUp?errorCode=3");
			return;
		}
		
		//비밀번호 2개가 같은지 체크
		if(!userPassword1.equals(userPassword2)) {
			response.sendRedirect("/Board/signUp?errorCode=4");
			return;
		}
		
		UserVO user = new UserVO();
		user.setUserEmail(userEmail);
		user.setUserNickname(userNickname);
		user.setUserPassword(userPassword1);
		
		boolean isSuccess = userBiz.signUpUser(user);
		if(isSuccess) {
			response.sendRedirect("/Board/signIn");
		}
		else {
			response.sendRedirect("/Board/signUp?errorCode=1");
		}
		
	}

}
