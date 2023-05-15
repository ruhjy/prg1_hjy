let checkPassword = true; // 비밀번호를 입력하지 않으면 기존비밀번호 유지 정책
let checkNickName = true;
let checkEmail = true;

// 비밀번호, 별명, 이메일 중복이 확인되어야 버튼 활성화
function enableSubmit() {
	if (checkPassword && checkNickName && checkEmail) {
		$("#modifyButton").removeAttr("disabled");
	} else {
		$("#modifyButton").attr("disabled", "");
	}
}

$("#inputNickName").keyup(function() {
	checkNickName = false;
	$("#availableNickNameMessage").addClass("d-none");
	$("#notAvailableNickNameMessage").addClass("d-none");
	enableSubmit();
});

$("#inputEmail").keyup(function() {
	checkNickName = false;
	$("#availableEmailMessage").addClass("d-none");
	$("#notAvailableEmailMessage").addClass("d-none");
	enableSubmit();
});

$("#inputPassword, #inputPasswordCheck").keyup(function() {

	const pw1 = $("#inputPassword").val();
	const pw2 = $("#inputPasswordCheck").val();

	if (pw1 === pw2) {
		$("#modifyButton").removeClass("disabled");
		$("#passwordSuccessText").removeClass("d-none");
		$("#passwordFailText").addClass("d-none");

		checkPassword = true;

	} else {
		$("#modifyButton").addClass("disabled");
		$("#passwordFailText").removeClass("d-none");
		$("#passwordSuccessText").addClass("d-none");

		checkPassword = false;
	}

	enableSubmit();
});

$("#checkNickNameBtn").click(function() {
	const modifyNickName = $("#inputNickName").val();

	$.ajax("/member/checkNickName/" + modifyNickName, {
		success: function(data) {
			if (data.available) {
				$("#availableNickNameMessage").removeClass("d-none");
				$("#notAvailableNickNameMessage").addClass("d-none");

				checkNickName = true;
			} else {
				$("#notAvailableNickNameMessage").removeClass("d-none");
				$("#availableNickNameMessage").addClass("d-none");

				checkNickName = false;
			}
		},
		complete: enableSubmit
	});
});

$("#checkEmailBtn").click(function() {
	const modifyEmail = $("#inputEmail").val();

	$.ajax("/member/checkEmail/" + modifyEmail, {
		success: function(data) {
			if (data.available) {
				$("#availableEmailMessage").removeClass("d-none");
				$("#notAvailableEmailMessage").addClass("d-none");

				checkEmail = true;
			} else {
				$("#notAvailableEmailMessage").removeClass("d-none");
				$("#availableEmailMessage").addClass("d-none");

				checkEmail = false;
			}
		},
		complete: enableSubmit
	});
});













