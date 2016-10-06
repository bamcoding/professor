package com.ktds.board.articles.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.articles.vo.SearchArticleVO;
import com.ktds.board.support.DaoSupport;
import com.ktds.board.support.Query;
import com.ktds.board.support.QueryAndResult;
import com.ktds.board.user.vo.UserVO;

public class ArticlesDaoImpl extends DaoSupport implements ArticlesDao {

	@Override
	public int writeArticles(ArticlesVO articlesVO) {

		return insert(new Query() {

			@Override
			public PreparedStatement query(Connection conn) throws SQLException {

				StringBuffer query = new StringBuffer();
				query.append(" INSERT INTO ARTICLES ( ");
				query.append(" ATCL_ID, ATCL_SUBJECT, ATCL_CONTENT, ");
				query.append(" CRT_DT, HIT_CNT, RCMD_CNT, USR_ID, ");
				query.append(" FILE_NM ) ");
				query.append(" VALUES ( ");
				query.append(" 'UR-' || TO_CHAR(SYSDATE, 'YYYYMMDD') || '-' || LPAD(ARTICLES_ID_SEQ.NEXTVAL,6,0) ");
				query.append(" , ?, ?, SYSDATE, 0, 0, ?, ? ) ");

				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setString(1, articlesVO.getArticleSubject());
				pstmt.setString(2, articlesVO.getArticleContent());
				pstmt.setString(3, articlesVO.getUserId());
				pstmt.setString(4, articlesVO.getFileName());
				
				return pstmt;
			}
		});
	}
	
	@Override
	public int getCountOfArticles(SearchArticleVO searchArticle) {
		
		return (int) selectOne(new QueryAndResult() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {

				StringBuffer query = new StringBuffer();
				query.append("SELECT	COUNT(1) CNT ");
				query.append("FROM		 ARTICLES A ");
				query.append("		    , USR U ");
				query.append("WHERE		A.USR_ID = U.USR_ID ");
				
				if(searchArticle.getSearchType() == 1) {
					query.append("AND		( A.ATCL_SUBJECT LIKE '%' || ? || '%' ");
					query.append("OR		A.ATCL_CONTENT LIKE '%' || ? || '%' ) ");
				}
				else if(searchArticle.getSearchType() == 2){
					query.append("AND		A.ATCL_SUBJECT LIKE '%' || ? || '%' ");
				}
				else if(searchArticle.getSearchType() == 3){
					query.append("AND		A.ATCL_CONTENT LIKE '%' || ? || '%' ");
				}
				else if(searchArticle.getSearchType() == 4){
					query.append("AND		U.USR_NICK_NM LIKE '%' || ? || '%' ");
				}
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				if(searchArticle.getSearchType() == 1) {
					pstmt.setString(1, searchArticle.getSearchKeyword());
					pstmt.setString(2, searchArticle.getSearchKeyword());
				}
				else if(searchArticle.getSearchType() == 2){
					pstmt.setString(1, searchArticle.getSearchKeyword());
				}
				else if(searchArticle.getSearchType() == 3){
					pstmt.setString(1, searchArticle.getSearchKeyword());
				}
				else if(searchArticle.getSearchType() == 4){
					pstmt.setString(1, searchArticle.getSearchKeyword());
				}
				
				return pstmt;
			}
			
			@Override
			public Object makeObject(ResultSet rs) throws SQLException {
				rs.next();
				return rs.getInt("CNT");
			}
		});
	}

	@Override
	public List<ArticlesVO> getAllArticle(SearchArticleVO searchArticle) {
		return (List<ArticlesVO>) selectList(new QueryAndResult() {
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {

				StringBuffer query = new StringBuffer();
				query.append("SELECT	A.ATCL_ID");
				query.append("			,A.ATCL_SUBJECT, A.ATCL_CONTENT ");
				query.append("			,TO_CHAR(A.CRT_DT, 'YYYY-MM-DD-HH24:MI:SS') CRT_DT ");
				query.append("			, A.HIT_CNT, A.RCMD_CNT ");
				query.append("			,A.USR_ID, A.FILE_NM, U.USR_NICK_NM ");
				query.append("FROM		 ARTICLES A ");
				query.append("		    , USR U ");
				query.append("WHERE		A.USR_ID = U.USR_ID ");
				
				if(searchArticle.getSearchType() == 1) {
					query.append("AND		( A.ATCL_SUBJECT LIKE '%' || ? || '%' ");
					query.append("OR		A.ATCL_CONTENT LIKE '%' || ? || '%' ) ");
				}
				else if(searchArticle.getSearchType() == 2){
					query.append("AND		A.ATCL_SUBJECT LIKE '%' || ? || '%' ");
				}
				else if(searchArticle.getSearchType() == 3){
					query.append("AND		A.ATCL_CONTENT LIKE '%' || ? || '%' ");
				}
				else if(searchArticle.getSearchType() == 4){
					query.append("AND		U.USR_NICK_NM LIKE '%' || ? || '%' ");
				}
				
				query.append("ORDER 	BY	A.ATCL_ID DESC ");
				
				String pagingQuery = appendPagingQueryFormat(query.toString());
				
				PreparedStatement pstmt = conn.prepareStatement(pagingQuery);
				int index = 1;
				
				if(searchArticle.getSearchType() == 1) {
					pstmt.setString(index++, searchArticle.getSearchKeyword());
					pstmt.setString(index++, searchArticle.getSearchKeyword());
				}
				else if(searchArticle.getSearchType() == 2){
					pstmt.setString(index++, searchArticle.getSearchKeyword());
				}
				else if(searchArticle.getSearchType() == 3){
					pstmt.setString(index++, searchArticle.getSearchKeyword());
				}
				else if(searchArticle.getSearchType() == 4){
					pstmt.setString(index++, searchArticle.getSearchKeyword());
				}
				
				pstmt.setInt(index++, searchArticle.getEndRowNumber());
				pstmt.setInt(index++, searchArticle.getStartRowNumber());
				
				return pstmt;
			}

			
			@Override
			public Object makeObject(ResultSet rs) throws SQLException {
				
				UserVO userVO = null;
				List<ArticlesVO> articles = new ArrayList<ArticlesVO>();
				ArticlesVO articlesVO = null;
				
				while(rs.next()) {
					articlesVO = new ArticlesVO();
					userVO = articlesVO.getUserVO();
					
					articlesVO.setArticleId(rs.getString("ATCL_ID"));
					articlesVO.setArticleSubject(rs.getString("ATCL_SUBJECT"));
					articlesVO.setArticleContent(rs.getString("ATCL_CONTENT"));
					articlesVO.setCreatedDate(rs.getString("CRT_DT"));
					articlesVO.setHitCount(rs.getInt("HIT_CNT"));
					articlesVO.setRecommendCount(rs.getInt("RCMD_CNT"));
					articlesVO.setUserId(rs.getString("USR_ID"));
					articlesVO.setFileName(rs.getString("FILE_NM"));
					userVO.setUserNickname(rs.getString("USR_NICK_NM"));
					articles.add(articlesVO);
				}
				return articles;
			}
		} );
	}

	@Override
	public ArticlesVO getArticleBy(String articlesId) {
		return (ArticlesVO) selectOne(new QueryAndResult() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {
				StringBuffer query = new StringBuffer();
				query.append("SELECT	A.ATCL_ID");
				query.append("			,A.ATCL_SUBJECT, A.ATCL_CONTENT ");
				query.append("			,TO_CHAR(A.CRT_DT, 'YYYY-MM-DD-HH24:MI:SS') CRT_DT ");
				query.append("			, A.HIT_CNT, A.RCMD_CNT ");
				query.append("			,A.USR_ID, A.FILE_NM, U.USR_NICK_NM ");
				query.append("FROM		 ARTICLES A ");
				query.append("		    , USR U ");
				query.append("WHERE		A.USR_ID = U.USR_ID ");
				query.append("AND		A.ATCL_ID = ? ");
				query.append("ORDER 	BY	A.USR_ID DESC ");
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setString(1, articlesId);
				return pstmt;
			}
			
			@Override
			public Object makeObject(ResultSet rs) throws SQLException {
				ArticlesVO articlesVO = null;
				while(rs.next()) {
					articlesVO = new ArticlesVO();
					articlesVO.setArticleId(rs.getString("ATCL_ID"));
					articlesVO.setArticleSubject(rs.getString("ATCL_SUBJECT"));
					articlesVO.setArticleContent(rs.getString("ATCL_CONTENT"));
					articlesVO.setCreatedDate(rs.getString("CRT_DT"));
					articlesVO.setHitCount(rs.getInt("HIT_CNT"));
					articlesVO.setRecommendCount(rs.getInt("RCMD_CNT"));
					articlesVO.setUserId(rs.getString("USR_ID"));
					articlesVO.setFileName(rs.getString("FILE_NM"));
					articlesVO.setUserVO(new UserVO());
					articlesVO.getUserVO().setUserNickname(rs.getString("USR_NICK_NM"));
				}
				return articlesVO;
			}
		});
	}

	@Override
	public int deleteArticlesBy(String articleId) {
		return insert(new Query() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {
				
				StringBuffer query = new StringBuffer();
				query.append("DELETE	");
				query.append("FROM	ARTICLES ");
				query.append("WHERE	ATCL_ID = ? ");
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setString(1, articleId);
				
				return pstmt;
			}
		});
	}

	@Override
	public int updateHitCount(String articlesId) {
		
		return insert(new Query() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {

				StringBuffer query = new StringBuffer();
				
				query.append("UPDATE	ARTICLES ");
				query.append("SET		HIT_CNT = HIT_CNT + 1 ");
				query.append("WHERE		ATCL_ID = ? ");
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setString(1, articlesId);
				
				return pstmt;
			}
		});
	}

	@Override
	public void updateRecommendCount(String articleId) {

		insert(new Query() {
			
			@Override
			public PreparedStatement query(Connection conn) throws SQLException {

				StringBuffer query = new StringBuffer();
				
				query.append("UPDATE	ARTICLES ");
				query.append("SET		RCMD_CNT = RCMD_CNT + 1, ");
				query.append("			HIT_CNT = HIT_CNT - 1 ");
				query.append("WHERE		ATCL_ID = ? ");
				
				PreparedStatement pstmt = conn.prepareStatement(query.toString());
				
				pstmt.setString(1, articleId);
				
				return pstmt;

			}
		});
		
	}



}
