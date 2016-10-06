package com.ktds.board.articles.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.support.Param;

public class ViewModifyPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Param param = new Param();
	ArticlesBiz articlesBiz;
	public ViewModifyPageServlet() {
		super();
		articlesBiz = new ArticlesBizImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String articleId = param.getStringParam(request, "articleId");
		ArticlesVO article = articlesBiz.getArticleForModify(articleId);
		
		String content = article.getArticleContent();
		content = content.replaceAll("<br/>", "\n");
		content = content.trim();
		article.setArticleContent(content);
		
		String viewPath = "/WEB-INF/view/articles/modify.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(viewPath);
		
		request.setAttribute("article", article);
		
		rd.forward(request, response);
	}

}
