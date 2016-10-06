package com.ktds.board.user.web;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ktds.board.articles.biz.ArticlesBiz;
import com.ktds.board.articles.biz.ArticlesBizImpl;
import com.ktds.board.articles.vo.ArticlesVO;
import com.ktds.board.support.MultipartHttpServletRequest;
import com.ktds.board.support.MultipartHttpServletRequest.MultipartFile;
import com.ktds.board.support.Param;

public class DoModifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private ArticlesBiz articlesBiz;
    private Param param= new  Param();
    
    public DoModifyServlet() {
        super();
        articlesBiz = new ArticlesBizImpl();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		MultipartHttpServletRequest multipartRequest =
				new MultipartHttpServletRequest(request);
		
		String articleId = multipartRequest.getParameter("articleId");
		String articleSubject = multipartRequest.getParameter("articleSubject");
		String articleContent = multipartRequest.getParameter("articleContent");
		String fileDeleteBtn = multipartRequest.getParameter("fileDeleteBtn");
		
		articleContent = articleContent.replaceAll("\n", "<br/>")
										.replaceAll("\r", "");
		
		ArticlesVO article = new ArticlesVO();
		article.setArticleId(articleId);
		article.setArticleSubject(articleSubject);
		article.setArticleContent(articleContent);
		
		if(fileDeleteBtn != null && fileDeleteBtn.equals("delete")){
			String fileName = articlesBiz.getFileNameOfArticleBy(articleId);
			File file = new File("D:\\board\\uploadfiles\\" + fileName);
			file.delete();
			
			article.setFileName("");
		}
		
		MultipartFile uploadedFile = multipartRequest.getFile("file");
		//파일이 있다면
		if(uploadedFile.getFileSize() > 0) {
			
			//폴더 만들기
			File uploadFileDirectory = new File("D:\\board\\uploadfiles\\");
			uploadFileDirectory.mkdirs();
			if(!uploadFileDirectory.exists()) {
				uploadFileDirectory.mkdirs();
			}
			
			uploadedFile.write("D:\\board\\uploadfiles\\" + uploadedFile.getFileName());
			String fileName = uploadedFile.getFileName();
			article.setFileName(fileName);
			
		}
		
		boolean isSuccess = articlesBiz.updateArticle(article);
		if(isSuccess){
			response.sendRedirect("/Board/board/detail?articleId="+articleId);
		}
		
	}
}
