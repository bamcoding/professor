package com.ktds.board.articles.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.articles.vo.ArticlesListVO;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.articles.vo.SearchArticleVO;
import com.ktds.board.constants.Session;
import com.ktds.board.support.Param;
import com.ktds.board.support.pager.ClassicPageExplorer;
import com.ktds.board.support.pager.ListPageExplorer;
import com.ktds.board.support.pager.PageExplorer;
import com.ktds.board.user.vo.UserVO;

public class ViewListPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticlesBiz articlesBiz;
	
	public ViewListPageServlet() {
		super();
		articlesBiz = new ArticlesBizImpl();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		int pageNo = Param.getIntParam(request, "pageNo", -1);
		int searchType = Param.getIntParam(request, "searchType");
		String searchKeyword = Param.getStringParam(request, "searchKeyword");
		
		SearchArticleVO searchArticle = null;
		if(pageNo == -1) {
			searchArticle = (SearchArticleVO) session.getAttribute(Session.SEARCH_INFO);
			if(searchArticle == null) { //세션이 없으면 다시 처음부터
				searchArticle = new SearchArticleVO();
				searchArticle.setPageNo(0);
			}
		}
		else { //페이지 번호가 있다면
			searchArticle = new SearchArticleVO();
			searchArticle.setPageNo(pageNo);
			searchArticle.setSearchType(searchType);
			searchArticle.setSearchKeyword(searchKeyword);
		}
		
		session.setAttribute(Session.SEARCH_INFO, searchArticle);		
		ArticlesListVO articles = articlesBiz.getAllArticles(searchArticle);
		
		String viewPath = "/WEB-INF/view/articles/list.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(viewPath);
		
		request.setAttribute("articles", articles.getArticles());
		request.setAttribute("pager", articles.getPager());
		
		PageExplorer pageExplorer = new ClassicPageExplorer(articles.getPager());
		String paper = pageExplorer.getPagingList("pageNo", "[@]", "이전", "다음", "searchForm");
		
		request.setAttribute("paging", paper);
		request.setAttribute("searchArticle", searchArticle);
		
		rd.forward(request, response);
	}

}
