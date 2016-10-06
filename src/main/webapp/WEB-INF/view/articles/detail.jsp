<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    

<jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

<script type="text/javascript">
	$(document).ready(function() {
		$("#deleteBtn").click(function() {
			if(confirm("\"${articles.articleSubject}.\"를 삭제하시겠습니까?")) {
				location.href="/Board/board/doDelete?articleId=${articles.articleId}";
			}
		});
	});
</script>
		<div id="article">
			<div id="articleHeader">
				<p>${articles.articleSubject}</p>
				<div id="articleInfo">
					<span>${articles.userVO.userNickname}</span>
					<span>${articles.createdDate}</span>
					<span><img src="/Board/img/eye-icon.png" />${articles.hitCount}</span>
					<span><a href="/Board/board/recommend?articleId=${articles.articleId}">
						<img src="/Board/img/heart-24-128.png" /></a>${articles.recommendCount}</span>
				</div>
				<div id="attachedFile">
					<span>
						<img src="/Board/img/text-file-3-xxl.png" />
						<a href="/Board/board/download?articleId=${articles.articleId}">${articles.fileName}</a></span>
				</div>
			</div>
			<hr/>
			<div id="articleBody">
				${articles.articleContent}
			</div>
		</div>
		<div id="articleFooter">
			<c:if test="${articles.articleId eq sessionScope._USER_INFO_.userId}">
			<a href="javascript:void(0);" id="deleteBtn">삭제</a>
			<a href="/Board/board/modify">수정</a>
			</c:if>
			<a href="/Board/board/list">목록보기</a>
		</div>
	</div>
</body>
</html>