package com.ktds.board.user.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.constants.Session;
import com.ktds.board.support.MultipartHttpServletRequest;
import com.ktds.board.support.MultipartHttpServletRequest.MultipartFile;
import com.ktds.board.support.Param;
import com.ktds.board.user.vo.UserVO;

public class DoWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ArticlesBiz articlesBiz;
	
    public DoWriteServlet() {
        super();
        articlesBiz = new ArticlesBizImpl();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
/*		String articleSubject = Param.getStringParam(request, "articleSubject");
		String articleContent = Param.getStringParam(request, "articleContent");*/
		
		MultipartHttpServletRequest multipartRequest = 
				new MultipartHttpServletRequest(request);
		
		String articleSubject = multipartRequest.getParameter("articleSubject");
		String articleContent = multipartRequest.getParameter("articleContent");
		
		String fileName ="";		
		
		MultipartFile uploadFile = multipartRequest.getFile("file");
		
		//사용자가 파일을 업로드 했다면
		if(uploadFile.getFileSize() > 0) {
			
			//폴더 만들기
			File uploadFileDirectory = new File("D:\\board\\uploadfiles\\");
			uploadFileDirectory.mkdirs();
			if(!uploadFileDirectory.exists()) {
				uploadFileDirectory.mkdirs();
			}
			
			uploadFile.write("D:\\board\\uploadfiles\\" + uploadFile.getFileName());
			fileName = uploadFile.getFileName();
			
		}
		
		
		if(articleSubject.length() == 0) {
			response.sendRedirect("/Board/doWrite?errorCode=2");
			return;
		}
		if(articleContent.length() == 0) {
			response.sendRedirect("/Board/doWrite?errorCode=2");
		}
		
		articleContent = articleContent.replaceAll("/n","<br/>")
									   .replaceAll("/r","");
		
		HttpSession session = request.getSession();
		UserVO userVO = (UserVO) session.getAttribute(Session.USER_INFO);
		
		ArticlesVO articles = new ArticlesVO();
		articles.setUserId(userVO.getUserId());
		articles.setArticleSubject(articleSubject);
		articles.setArticleContent(articleContent);
		articles.setFileName(fileName);
		
		boolean isSuccess = articlesBiz.writeArticles(articles);
		if(isSuccess) {
			response.sendRedirect("/Board/board/list");
		}
		else {
			response.sendRedirect("/Board/signUp?errorCode=1");
		}
		
	}

}
