<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<jsp:include page="/WEB-INF/view/common/header.jsp"></jsp:include>

<script type="text/javascript">
	$(document).ready(function(){
		
		var errorCode = "${param.errorCode}";
		if(errorCode == 1) {
			$("div.warning").html("<p>내용을 입력해주세요!</p>");
		}
		else if(errorCode == 2) {
			$("div.warning").html("<p>다시 입력해주세요!</p>");
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
				$("#writeForm").attr({
					"method":"post",
					"action":"/Board/doWrite"
				}).submit(); 
		});

		$("#goBackBtn").click(function() {
			location.href="/Board/board/list";
		});
	});
</script>

		<form id="writeForm" name="writeForm" enctype="multipart/form-data">
			<div>
				<input type="text" id="articleSubject" name="articleSubject" placeholder="제목을 입력하세요." />
			</div>
			<div>
				<textarea id="articleContent" name="articleContent" placeholder="내용을 입력하세요."></textarea>
			</div>
			<div style="margin-top:5px;">
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