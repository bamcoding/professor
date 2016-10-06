package com.ktds.board.support;

import javax.servlet.http.HttpServletRequest;

public class Param {
	
	public static String getStringParam(HttpServletRequest request, String paramName) {
		
		//요청받은 값을 getStringParam에서 처리해준다.
		return getStringParam(request, paramName, "");
	}
	
	/**
	 * String형식의 값이 널이거나 0이면 default값으로 처리한다.
	 * 아니면 값을 리턴시켜준다.
	 * @param request
	 * @param paramName
	 * @param defaultValue
	 * @return
	 */
	public static String getStringParam(HttpServletRequest request, String paramName, String defaultValue) {
		
		//요청받은 파라미터를 String 형식의 값으로 넣어준다. 
		String value = request.getParameter(paramName);
		//만약 값이 null이거나 0이면
		if(value == null || value.length() == 0) {
			//값이 default값이 된다.
			value = defaultValue;
		}
		//그대로 값을 리턴시켜준다.
		return value;
	}
	
	public static int getIntParam(HttpServletRequest request, String paramName) {
		//요청받은 값을 getIntParam에서 처리해준다.
		return getIntParam(request, paramName, 0);
	}
	
	public static int getIntParam(HttpServletRequest request, String paramName, int defaultValue) {
		//요청받은 파라미터를 String형식의 값으로 넣어준다.
		String value = getStringParam(request, paramName);
		
		//NumberFormatException을 처리해준다
		try {
			//String 형식의 값을 int형식으로 바꿔주고 intValue에 넣어준다.
			int intValue = Integer.parseInt(value);
			//intValue를 리턴해준다.
			return intValue;
		} catch(NumberFormatException nfe) { //String형식의 defaultValue값을 잡아준다.
			return defaultValue;
		}
	}
}
