<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Board/css/layout.css" />
<link rel="stylesheet" type="text/css" href="/Board/css/grid.css" />
<script type="text/javascript" src="/Board/js/jquery-3.1.1.js"></script>

</head>
<body>
	<div id="wrapper">
	
<div id="header">

	<h1>BOARD</h1>
	<c:if test="${not empty sessionScope._USER_INFO_}">
			<div style="text-align: right;">
			${sessionScope._USER_INFO_.userNickname} (${POINTS})| 
			<a href="/Board/board/logout">로그아웃</a>
			</div>
	</c:if>
		<c:if test="${empty sessionScope._USER_INFO_}">
			<div style="text-align: right;">로그인</div>
		</c:if>
</div>
		