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
				$("#likeIcon").html(`<i class="fa-regular fa-thumbs-up"></i>`);
			}
		}
	});
});

