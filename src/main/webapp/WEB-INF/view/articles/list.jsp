<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

<script type="text/javascript">
			$().ready(function() {
				$("#searchType").change(function() {
					
					//선택된 select option의 value를 가져옴
					//alert($(this).val());
					
					//선택된 Select option의 Text를 가져옴
					alert($("#searchType option:selected").text());
				});
			});
</script>

	

		<div id="list">
			<table class="grid">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>조회수</th>
					<th>추천수</th>
				</tr>
				<c:if test="${empty articles }">
					<tr>
						<td>등록된 글이 없습니다.</td>
					</tr>
				</c:if>
				<c:forEach items="${articles}" var="article">
					<tr>
						<c:set var="number" value="${fn:split(article.articleId,'-')[2]}"/>
						<fmt:parseNumber var="number" type="number" value="${number}" />
						<td>${number}</td>
						<td><a href="/Board/board/detail?articleId=${article.articleId}">${article.articleSubject}</a></td>
						<td>${article.userVO.userNickname}</td>
						<td>${article.createdDate}</td>
						<td>${article.hitCount}</td>
						<td>${article.recommendCount}</td>
					</tr>
				</c:forEach>
			</table>
			
			<form id="searchForm" name="searchForm">
			${paging}
			
			
			<div style="padding-top: 5px;">
				<div class="left">
					<a href="/Board/board/write">글쓰기</a>
				</div>
				<div class="right">
						<select id="searchType" name="searchType">
							<option value="1" ${searchArticle.searchType eq 1 ? 'selected' : ''}>제목+내용</option>
							<option value="2" ${searchArticle.searchType eq 2 ? 'selected' : ''}>제목</option>
							<option value="3" ${searchArticle.searchType eq 3 ? 'selected' : ''}>내용</option>
							<option value="4" ${searchArticle.searchType eq 4 ? 'selected' : ''}>작성자</option>
						</select>
						<input type="text" id="searchKeyword" name="searchKeyword" value="${searchArticle.searchKeyword}"/>
						<input type="button" id="searchBtn" value="검색 " onclick="movePage(0)" />
						<a href="/Board/board/list/init">검색 초기화</a>
				</div>
				<div class="clear"></div>
			</div>
			</form>
		</div>
	</div>
</body>
</html>