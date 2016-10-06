package com.ktds.board.articles.biz;

import java.util.List;

import com.ktds.board.articles.dao.ArticlesDao;
import com.ktds.board.articles.dao.ArticlesDaoImpl;
import com.ktds.board.articles.vo.ArticlesListVO;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.articles.vo.SearchArticleVO;
import com.ktds.board.support.pager.Pager;
import com.ktds.board.support.pager.PagerFactory;
import com.ktds.board.user.dao.UserDao;
import com.ktds.board.user.dao.UserDaoImpl;
import com.ktds.board.user.vo.UserVO;

public class ArticlesBizImpl implements ArticlesBiz{

	private ArticlesDao articlesDao;
	private UserDao userDao;
	
	public ArticlesBizImpl() {
		articlesDao = new ArticlesDaoImpl();
		userDao = new UserDaoImpl();
	}
	
	public boolean writeArticles(ArticlesVO articlesVO) {
		
		UserVO userVO  = new UserVO();
		userVO.setUserId(articlesVO.getUserId());
		
		userDao.updatePointsCount(userVO, 10);
		
		return articlesDao.writeArticles(articlesVO) > 0;
	}
	
	@Override
	public ArticlesListVO getAllArticles(SearchArticleVO searchArticle) {
		
		int totalCount = articlesDao.getCountOfArticles(searchArticle);
		Pager pager = PagerFactory.getPager(true);
		pager.setTotalArticleCount(totalCount);
		pager.setPageNumber(searchArticle.getPageNo());
		
		searchArticle.setStartRowNumber(pager.getStartArticleNumber());
		searchArticle.setEndRowNumber(pager.getEndArticleNumber());
		
		List<ArticlesVO> articles = articlesDao.getAllArticle(searchArticle);
		
		ArticlesListVO articlesList = new ArticlesListVO();
		articlesList.setPager(pager);
		articlesList.setArticles(articles);
		
		return articlesList;
	}

	@Override
	public ArticlesVO getArticleBy(UserVO userVO, String articlesId) {
		articlesDao.updateHitCount(articlesId);
		
		
		userDao.updatePointsCount(userVO, -1);
		
		return articlesDao.getArticleBy(articlesId);
	}

	@Override
	public String getFileNameOfArticleBy(String articleId) {
		ArticlesVO article = articlesDao.getArticleBy(articleId);
		return article.getFileName();
	}
	
	public ArticlesVO getArticleForModify(String articleId) {
		return articlesDao.getArticleBy(articleId);
	}

	public boolean deleteArticlesBy(String articleId) {
		return articlesDao.deleteArticlesBy(articleId) > 0;
	}

	@Override
	public void updateRecommendCount(String articleId) {
		articlesDao.updateRecommendCount(articleId);
		
	}

	@Override
	public boolean updateArticle(ArticlesVO article) {
		ArticlesVO originalArticle =
				articlesDao.getArticleBy(article.getArticleId());
		
		int modifyCount = 3;
		
		
		//원본과 수정본이 같다면 지워라
		if( originalArticle.getArticleSubject().equals(article.getArticleSubject())){
			article.setArticleSubject(null);
			modifyCount--;
		}
		if( originalArticle.getArticleContent().equals(article.getArticleContent())){
			article.setArticleContent(null);
			modifyCount--;
		}
		
		if( originalArticle.getFileName() == null){
			originalArticle.setFileName("");
		}
		
		if( originalArticle.getFileName().equals(article.getFileName())){
			article.setFileName(null);
			modifyCount--;
		}
		if( modifyCount == 0){
			return true;
		}
		
		return articlesDao.updateArticle(article) > 0;
	}

}
