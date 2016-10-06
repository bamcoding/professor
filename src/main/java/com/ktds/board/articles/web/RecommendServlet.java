package com.ktds.board.articles.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.support.Param;

public class RecommendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticlesBiz articlesBiz;
	
    public RecommendServlet() {
        super();
        articlesBiz = new ArticlesBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String articleId = Param.getStringParam(request, "articleId");
		
		articlesBiz.updateRecommendCount(articleId);
		
		response.sendRedirect("/Board/board/detail?articleId="+articleId);
	}

}
