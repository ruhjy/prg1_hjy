// 아이디 중복확인 버튼이 클릭되면
$("#checkIdBtn").click(function() {
	const userId = $("#inputId").val();

	// 입력한 ID와 ajax 요청을 보내서
	$.ajax("/member/checkId/" + userId, {
		success: function(data) {
			// `{"available": true}`
			if (data.available) {
				// 사용 가능하다는 메세지 출력
				$("#availableIdMessage").removeClass("d-none");
				$("#notAvailableIdMessage").addClass("d-none");
			} else {
				// 사용 가능하지 않다는 메세지 출력
				$("#notAvailableIdMessage").removeClass("d-none")
				$("#availableIdMessage").addClass("d-none");
			}
		}
	});
});

// 별명 중복확인 버튼
$("#checkNickNameBtn").click(function() {
	const userNickName = $("#inputNickName").val();

	$.ajax("/member/checkNickName/" + userNickName, {
		success: function(data) {
			if (data.available) {
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");
			} else {
				$("#notAvailableNickNameMessage").removeClass("d-none");
				$("#availableNickNameMessage").addClass("d-none");
			}
		}
	});
});

// 이메일 중복확인 버튼
$("#checkEmailBtn").click(function() {
	const userEmail = $("#inputEmail").val();

	$.ajax("/member/checkEmail/" + userEmail, {
		success: function(data) {
			if (data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");
			} else {
				$("#notAvailableEmailMessage").addClass("d-none");
				$("#availableEmailMessage").removeClass("d-none");
			}
		}
	});
});


// 패스워드, 패스워드체크 인풋에 키업 이벤트 발생하면
$("#inputPassword, #inputPasswordCheck").keyup(function() {
	// 패스워드에 입력한 값
	const pw1 = $("#inputPassword").val();
	// 패스워드 확인에 입력한 값이
	const pw2 = $("#inputPasswordCheck").val();

	// 같으면
	if (pw1 === pw2) {
		// submit 버튼 활성화
		$("#signupSubmit").removeClass("disabled");
		// 패스워드가 같다는 메세지 출력
		$("#passwordSuccessText").removeClass("d-none");
		$("#passwordFailText").addClass("d-none");

	} else {
		// 그렇지 않으면
		// submit 버튼 비활성화
		$("#signupSubmit").addClass("disabled");
		// 패스워드가 다르다는 메세지 출력
		$("#passwordFailText").removeClass("d-none");
		$("#passwordSuccessText").addClass("d-none");
	}
});
