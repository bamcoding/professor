package com.ktds.board.user.dao;

import com.ktds.board.user.vo.UserVO;

public interface UserDao {
	
	public int signUpUser(UserVO userVO);

	public UserVO getUserBy(UserVO user);

	public int countUserEmail(String userEmail); 
	
	public int updatePointsCount(UserVO userVO, int points);

}
