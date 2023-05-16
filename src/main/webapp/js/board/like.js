const toast = new bootstrap.Toast(document.querySelector("#liveToast"));

// 좋아요 아이콘을 누르면 
$("#likeIcon").click(function() {
	const boardId = $("#boardIdText").text().trim();
	const data = { boardId: boardId };
	// const data = {boardId} - 키와 값의 이름이 같으면 이렇게 사용 가능

	// 게시물 번호 request body에 추가
	$.ajax("/like", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		success: function(data) {
			if (data.like) {
				// on
				$("#likeIcon").html(`<i class="fa-solid fa-thumbs-up"></i>`);
			} else {
				// off
				$("#likeIcon").html(`<i class="fa-regular fa-thumbs-up"></i>`);
			}
			$("#likeNumber").text(data.count); // get.jsp에 있는 likeNumber 값 변경
		},
		error: function(jqXHR) {
			//console.log("좋아요 실패");
			//console.log(jqXHR.responseJSON);
			//$("body").prepend(jqXHR.responseJSON.message);
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		}
	});
});

