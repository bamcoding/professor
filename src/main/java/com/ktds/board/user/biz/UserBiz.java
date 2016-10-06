package com.ktds.board.user.biz;

import javax.servlet.http.HttpServletRequest;

import com.ktds.board.user.vo.UserVO;

public interface UserBiz {
	
	public boolean signUpUser(UserVO userVO);

	public boolean signIn(UserVO user, HttpServletRequest request);

	public boolean isExistsUserEmail(String userEmail); 
}
