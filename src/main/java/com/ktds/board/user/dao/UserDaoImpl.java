package com.ktds.board.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ktds.board.support.Query;
import com.ktds.board.support.QueryAndResult;
import com.ktds.board.support.DaoSupport;
import com.ktds.board.user.vo.UserVO;

public class UserDaoImpl extends DaoSupport implements UserDao{

	@Override
	public int signUpUser(final UserVO userVO) { //final:절대 변경이 불가하다는것을 말해준다
		return insert(new Query() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException{
				
				StringBuffer query = new StringBuffer();
				query.append(" INSERT INTO USR ( ");
				query.append(" USR_ID, USR_EMAIL, USR_NICK_NM, ");
				query.append(" USR_PWD, POINTS, LTST_PWD_MDFY_DT, ");
				query.append(" CRT_DT ) ");
				query.append(" VALUES ( ");
				query.append(" 'UR-' || TO_CHAR(SYSDATE, 'YYYYMMDD') || '-' || LPAD(USR_ID_SEQ.NEXTVAL,6,0) ");
				query.append(" , ?, ?, ?, 100, SYSDATE, SYSDATE ) ");

				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, userVO.getUserEmail());
				pstmt.setString(2, userVO.getUserNickname());
				pstmt.setString(3, userVO.getUserPassword());
				
				return pstmt;
			}
		});
	}

	@Override
	public UserVO getUserBy(final UserVO user) {
		UserVO userInfo = (UserVO)selectOne(new QueryAndResult() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {
				StringBuffer query = new StringBuffer();
				query.append(" SELECT		USR_ID ");
				query.append(" 				, USR_EMAIL ");
				query.append(" 				, USR_NICK_NM ");
				query.append(" 				, USR_PWD ");
				query.append(" 				, POINTS ");
				query.append(" 				, TO_CHAR(LTST_PWD_MDFY_DT, 'YYYY-MM-DD HH24:MI:SS') LTST_PWD_MDFY_DT ");
				query.append(" 				, TO_CHAR(CRT_DT, 'YYYY-MM-DD HH24:MI:SS' ) CRT_DT ");
				query.append(" FROM			USR ");
				query.append(" WHERE		USR_EMAIL = ? ");
				query.append(" AND			USR_PWD = ? ");
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				pstmt.setString(1, user.getUserEmail());
				pstmt.setString(2, user.getUserPassword());
				
				return pstmt;
			}
			
			@Override
			public Object makeObject(ResultSet rs) throws SQLException {
				
				UserVO userInfo = null;
				if ( rs.next()) {
					userInfo = new UserVO();
					userInfo.setUserId(rs.getString("USR_ID"));
					userInfo.setUserEmail(rs.getString("USR_EMAIL"));
					userInfo.setUserNickname(rs.getString("USR_NICK_NM"));
					userInfo.setUserPassword(rs.getString("USR_PWD"));
					userInfo.setPoints(rs.getInt("POINTS"));
					userInfo.setLatestPasswordModifyDate(rs.getString("LTST_PWD_MDFY_DT"));
					userInfo.setCreatedDate(rs.getString("CRT_DT"));
				}
				return userInfo;
			}
		});
		return userInfo;
	}

	@Override
	public int countUserEmail(String userEmail) {
		
		return (int) selectOne(new QueryAndResult() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {
				
				StringBuffer query = new StringBuffer();
				query.append(" SELECT	COUNT(1) CNT ");
				query.append(" FROM		USR ");
				query.append(" WHERE	USR_EMAIL = ?" );
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setString(1, userEmail);
				
				return pstmt;
			}
			
			@Override
			public Object makeObject(ResultSet rs) throws SQLException {
				if(rs.next()) {
					return rs.getInt("CNT");
				}
				
				return 0;
			}
		});
		
	}

	@Override
	public int updatePointsCount(UserVO userVO, int points) {
		
		return insert(new Query() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {
				
				StringBuffer query = new StringBuffer();
				query.append(" UPDATE	USR ");
				query.append(" SET		POINTS = POINTS + (?) ");
				query.append(" WHERE	USR_ID = ?" );
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setInt(1, points);
				pstmt.setString(2, userVO.getUserId());
				
				return pstmt;
			}
		});
	}


}
