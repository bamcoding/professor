package com.ktds.board.articles.dao;

import java.util.List;

import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.articles.vo.SearchArticleVO;

public interface ArticlesDao {
	
	public int writeArticles(ArticlesVO articlesVO);

	public int getCountOfArticles(SearchArticleVO searchArticle);
	
	public List<ArticlesVO> getAllArticle(SearchArticleVO searchArticle);
	
	public ArticlesVO getArticleBy(String articlesId);

	public int deleteArticlesBy(String articleId);
	
	public int updateHitCount(String articlesId);

	public void updateRecommendCount(String articleId);
}
