package com.ktds.board.articles.biz;

import java.util.List;

import com.ktds.board.articles.vo.ArticlesListVO;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.articles.vo.SearchArticleVO;
import com.ktds.board.user.vo.UserVO;

public interface ArticlesBiz {
	
	public boolean writeArticles(ArticlesVO articlesVO);

	public ArticlesListVO getAllArticles(SearchArticleVO searchArticle);
	
	public ArticlesVO getArticleBy(UserVO userVO, String articlesId);
	
	public String getFileNameOfArticleBy(String articleId);

	public boolean deleteArticlesBy(String articleId);

	public void updateRecommendCount(String articleId);
	
	

}
