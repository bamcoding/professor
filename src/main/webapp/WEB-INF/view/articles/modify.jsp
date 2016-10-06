<!-- 모디 페이지의 폼은 write페이지와 같다 id만 바꾸면 된다.
	업로드한 파일을 지우고 다른 파일을 올릴 수도 있다. -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

<script type="text/javascript">

/**
 * 다른 사용자가 봤을때 수정과 삭제가 안보이게 했지만
 해커들은 주소를 알아두고 복붙함으로써 수정이 가능하게 한다. 이것을 막아줘야 한다.
 */
 <c:if test="${sessionScope._USER_INFO_.userId ne article.userId}">
 alert("허용되지 않은 접근입니다.");
 location.href="/Board/board/list/init";
 </c:if>
 
	$(document).ready(function(){
		
		var errorCode = "${param.errorCode}";
		if(errorCode == 1) {
			$("div.warning").html("<p>내용을 입력해주세요!</p>");
		}
		else if(errorCode == 2) {
			$("div.warning").html("<p>글 수정에 실패했습니다!</p>");
		}
		
		$("#writeBtn").click(function () {
			if($("#articleSubject").val() == "") {
				alert("제목을 입력해주세요!");
				return;
			}
			if($("#articleContent").val() == "") {
				alert("내용을 입력해주세요!");
				return;
			}
				$("#modifyForm").attr({
					"method":"post",
					"action":"/Board/board/doModify"
				}).submit(); 
		});
		
		
		$("#goBackBtn").click(function() {
			location.href="/Board/board/detail?articleId=${article.articleId}";
		});
	});
</script>

		<form id="modifyForm" name="modifyForm" enctype="multipart/form-data">
		<!-- post형식으로 보낼때 추가 -->
		<input type="hidden" name="articleId" value="${article.articleId }"/>
			<div>
				<input type="text" id="articleSubject" name="articleSubject" placeholder="제목을 입력하세요." 
				value="${article.articleSubject }" />
			</div>
			<div>
				<textarea id="articleContent" name="articleContent" placeholder="내용을 입력하세요.">${article.articleContent }</textarea>
			</div>
			<c:if test="${not empty article.fileName }">
			<div style="padding-top: 10px; padding-bottom: 10px;">
				<input type="checkbox" id="fileDeleteBtn" name="fileDeleteBtn" value="delete" />
				<img src="/Board/img/text-file-3-xxl.png" style="width: 12px;" /> 첨부파일.exe
				<!-- 이것을 체크하고 보내면 파일을 지우겠다. 체크박스의 벨류는 보이지 않지만 값으로 전달된다. fileDeletBtn = delete
				링크나 get 같은 경우에는 <input type hidden> input type text와 똑같은데 화면에 보이지 않지만 폼으로 데이터를 전송해 준다.
				파일이 없다면 안 보이도록 한다. -->
			</div>
			</c:if>
			<div>
				<div class="left">
					<input type="file" id="file" name="file" />
				</div>
				<div class="right">
					<div class="inline">
						<input type="button" id="goBackBtn" value="뒤로가기" />
					</div>
					<div class="inline">
						<input type="button" id="writeBtn" value="글쓰기" />
					</div>
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</body>
</html>