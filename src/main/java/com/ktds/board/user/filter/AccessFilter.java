package com.ktds.board.user.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ktds.board.constants.Session;

public class AccessFilter implements Filter {

    public AccessFilter() {
    	
    }


	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		
		HttpSession session = ((HttpServletRequest)request).getSession();
		
		if(session.getAttribute(Session.USER_INFO) != null) {
			((HttpServletResponse)response).sendRedirect("/Board/board/list");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
