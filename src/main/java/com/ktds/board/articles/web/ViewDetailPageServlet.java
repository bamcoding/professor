package com.ktds.board.articles.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.constants.Session;
import com.ktds.board.support.Param;
import com.ktds.board.user.vo.UserVO;

public class ViewDetailPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticlesBiz articlesBiz;
	
	public ViewDetailPageServlet() {
		super();
		articlesBiz = new ArticlesBizImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String articlesId = Param.getStringParam(request, "articleId"); 
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Session.USER_INFO);
		
		ArticlesVO articles = articlesBiz.getArticleBy(userVO, articlesId);
		String viewPath = "/WEB-INF/view/articles/detail.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(viewPath);
		request.setAttribute("articles", articles);
		rd.forward(request, response);
	}

}
