<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/Board/css/layout.css" />
<script type="text/javascript" src="/Board/js/jquery-3.1.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		moveToCenter();
		isVisibleJoinButton();
		
		var errorCode = "${errorCode}";
		if(errorCode == 1) {
			$("div.warning").html("<p>회원가입에 실패했습니다!</p>");
		}
		else if(errorCode == 2) {
			$("div.warning").html("<p>필수 입력값을 적지 않았습니다!</p>");
		}
		else if(errorCode == 3) {
			$("div.warning").html("<p>이미 존재하는 회원 아이디입니다!</p>");
		}
		else if(errorCode == 4) {
			$("div.warning").html("<p>비밀번호가 일치하지 않습니다!</p>");
		}
		
		
		$("#joinBtn").click(function() {
			
			//$("#registForm").attr("method","post");
			//$("#registForm").attr("action","/Board/doWrite");
			//$("#registForm").submit();
			
			
			$("#registForm").attr({
				"method":"post",
				"action":"/Board/doSignUp"
			}).submit(); 
			
		});
		
		$("#userEmail").blur(function () {
			$("#userEmail").keyup();
		});
		
		$("#userEmail").keyup(function () {
			$.post("/Board/duplicateUserEmail",
					{"userEmail":$("#userEmail").val()},
					function(data) {
						if(data == "false") {
							$("#userEmail").addClass("pass");
							$("#userEmail").removeClass("warning");
						}
						else {
							$("#userEmail").removeClass("pass");
							$("#userEmail").addClass("warning");
						}
						
						isVisibleJoinButton();
					});//ajax요청보냄
		});
		
		
		$("#userNickname").keyup(function () {
			//alert($(this).val());
			if($(this).val() == "") {
				$(this).addClass("warning");
				$(this).removeClass("pass");
			}
			else {
				$(this).addClass("pass");
				$(this).removeClass("warning");
			}
			
			isVisibleJoinButton();
		});
		
		$("#userPassword1").keyup(function () {
			if($(this).val() != $("#userPassword2").val()) {
				$(this).addClass("warning");
				$(this).removeClass("pass");
				$("#userPassword2").addClass("warning");
				$("#userPassword2").removeClass("pass");
			}
			else if(($(this).val() && $("#userPassword2").val()) == "") {
				$(this).addClass("warning");
				$(this).removeClass("pass");
				$("#userPassword2").addClass("warning");
				$("#userPassword2").removeClass("pass");
			}
			else {
				$(this).removeClass("warning");
				$(this).addClass("pass");
				$("#userPassword2").removeClass("warning");
				$("#userPassword2").addClass("pass");
			}
			
			isVisibleJoinButton();
		});
		
		$("#userPassword2").keyup(function () {
			if($(this).val() != $("#userPassword1").val()) {
				$(this).addClass("warning");
				$(this).removeClass("pass");
				$("#userPassword1").addClass("warning");
				$("#userPassword1").removeClass("pass");
			}
			else if(($(this).val() && $("#userPassword2").val()) == "") {
				$(this).addClass("warning");
				$(this).removeClass("pass");
				$("#userPassword2").addClass("warning");
				$("#userPassword2").removeClass("pass");
			}
			else {
				$(this).removeClass("warning");
				$(this).addClass("pass");
				$("#userPassword1").removeClass("warning");
				$("#userPassword1").addClass("pass");
			}
			
			isVisibleJoinButton();
		});
		
	});
	
	
	$(window).resize(function() {
		moveToCenter();
	});
	
	function moveToCenter() {
		//body 높이를 가져온다
		var windowHeight = $(window).height();
		 //wrapper 높이를 가져온다
		var wrapperHeight = $("#wrapper").height();
		var middlePosition = (parseInt(windowHeight) / 2) 
								- (parseInt(wrapperHeight) / 2);
		
		$("#wrapper").css({
			"position": "relative"
			, "top": middlePosition + "px"
		});
	}
	
	function isVisibleJoinButton() {
		
		if($(".pass").length == 4) {
			$("#joinBtn").show(500);
		}
		else {
			$("#joinBtn").hide();
		}
	}
</script>
</head>
<body>

	<div id="wrapper">
		<div class="warning"></div>
		<form id="registForm">
			<input type="text" id="userEmail" name="userEmail" placeholder="Email or ID" /><br/>
			<input type="text" id="userNickname" name="userNickname" placeholder="Username" /><br/>
			<input type="password" id="userPassword1" name="userPassword1" placeholder="Password" /><br/>
			<input type="password" id="userPassword2" name="userPassword2" placeholder="Password Confirm" /><br/>
			<div style="margin-top: 5px;">
				<div class="left">
					<input type="button" id="joinBtn" value="Join" />
				</div>
				<div class="right">
					<input type="button" id="cancelBtn" value="Cancel" />
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>

</body>
</html>