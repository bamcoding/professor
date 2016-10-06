<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

<script type="text/javascript">
	$(document).ready(function(){
		$("#goBackBtn").click(function() {
			location.href="/Board/board/detail";
		});
	});
</script>

		<form id="writeForm" name="writeForm">
			<div>
				<input type="text" id="articleSubject" name="articleSubject" placeholder="제목을 입력하세요." value="첫 번째 게시글 입니다." />
			</div>
			<div>
				<textarea id="articleContent" name="articleContent" placeholder="내용을 입력하세요.">이런글 저런글

ㅎㅎ
ㅎㅎ
ㅎㅎ</textarea>
			</div>
			<div style="padding-top: 10px; padding-bottom: 10px;">
				<input type="checkbox" id="fileDeleteBtn" name="fileDeleteBtn" value="delete" />
				<img src="/Board/img/text-file-3-xxl.png" style="width: 12px;" /> 첨부파일.exe
			</div>
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