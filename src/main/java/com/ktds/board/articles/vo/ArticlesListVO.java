package com.ktds.board.articles.vo;

import java.util.List;

import com.ktds.board.support.pager.Pager;

public class ArticlesListVO {
	
	private List<ArticlesVO> articles;
	private Pager pager;
	
	public List<ArticlesVO> getArticles() {
		return articles;
	}
	public void setArticles(List<ArticlesVO> articles) {
		this.articles = articles;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	
	

}
