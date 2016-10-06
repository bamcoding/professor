package com.ktds.board.user.biz;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ktds.board.constants.Session;
import com.ktds.board.user.dao.UserDao;
import com.ktds.board.user.dao.UserDaoImpl;
import com.ktds.board.user.vo.UserVO;

public class UserBizImpl implements UserBiz{

	private UserDao userDao;
	
	public UserBizImpl() {
		userDao = new UserDaoImpl();
	}
	@Override
	public boolean signUpUser(UserVO userVO) {
		
		return userDao.signUpUser(userVO) > 0;
	}
	@Override
	public boolean signIn(UserVO user, HttpServletRequest request) {
		UserVO userInfo = userDao.getUserBy(user);
		
		//NullPointerException 발생 방지하기 위함 원래는 userInfo.getUserId().length() > 0 만 해주면됨!
		if(userInfo != null 
				&& userInfo.getUserId() != null 
				&& userInfo.getUserId().length() > 0) {
			HttpSession session = request.getSession();
			session.setAttribute(Session.USER_INFO, userInfo);
			
			userDao.updatePointsCount(userInfo, 20);
			return true;
		}
		return false;
	}
	@Override
	public boolean isExistsUserEmail(String userEmail) {
		return userDao.countUserEmail(userEmail) > 0;
	}
	
}
