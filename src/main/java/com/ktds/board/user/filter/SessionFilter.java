package com.ktds.board.user.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ktds.board.constants.Session;
import com.ktds.board.user.dao.UserDao;
import com.ktds.board.user.dao.UserDaoImpl;
import com.ktds.board.user.vo.UserVO;


public class SessionFilter implements Filter {
	
	private UserDao userDao;
	
    public SessionFilter() {
    	userDao = new UserDaoImpl();
    }


	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		if(session.getAttribute(Session.USER_INFO) == null) {
			
			PrintWriter out = response.getWriter();
			out.print("<script type='text/javascript'>;");
			out.print("alert('로그인이 필요한 서비스입니다.');");
			out.print("location.href='/Board/signIn';");
			out.print("</script>");
			out.flush();
			out.close();
			
			return;
		}
		
		UserVO userVO = (UserVO) session.getAttribute(Session.USER_INFO);
		userVO = userDao.getUserBy(userVO);
		/*session.setAttribute(Session.USER_INFO, userVO);*/
		
		request.setAttribute("POINTS", userVO.getPoints());
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {

	}

}
