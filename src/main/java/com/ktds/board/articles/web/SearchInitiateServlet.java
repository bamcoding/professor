package com.ktds.board.articles.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktds.board.constants.Session;

public class SearchInitiateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchInitiateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		session.removeAttribute(Session.SEARCH_INFO);
		
		response.sendRedirect("/Board/board/list");
	}

}
