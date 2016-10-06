package com.ktds.board.articles.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.support.DownloadUtil;
import com.ktds.board.support.Param;

public class DoDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private ArticlesBiz articlesBiz;
	
    public DoDownloadServlet() {
        super();
        articlesBiz = new ArticlesBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String articleId = Param.getStringParam(request, "articleId");
		
		String fileName = articlesBiz.getFileNameOfArticleBy(articleId);
		
		if(fileName != null || fileName.length() > 0) {
			
			DownloadUtil downloadUtil = DownloadUtil.getInstance("D:\\board\\uploadfiles");
			
			downloadUtil.download(request, response, fileName, fileName);
		}
	}

}
