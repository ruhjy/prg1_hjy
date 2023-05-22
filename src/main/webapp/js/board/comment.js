listComment();

$("#sendCommentBtn").click(function() {
	const boardId = $("#boardIdText").text().trim();
	const content = $("#commentTextArea").val();
	const data = { boardId, content };

	$.ajax("/comment/add", {
		method: "post",
		contentType: "application/json",
		data: JSON.stringify(data),
		complete: function(jqXHR) {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		}
	});
});

$("#updateCommentBtn").click(function() {
	const commentId = $("#commentUpdateIdInput").val();
	const content = $("#commentUpdateTextArea").val();
	const data = {
		id: commentId,
		content: content
	}

	$.ajax("/comment/update", {
		method: "put",
		contentType: "application/json",
		data: JSON.stringify(data),
		complete: function() {
			listComment();
			$(".toast-body").text(jqXHR.responseJSON.message);
			toast.show();
		}
	});
});

function listComment() {
	const boardId = $("#boardIdText").text().trim();

	$.ajax("/comment/list?board=" + boardId, {
		method: "get", // get 생략 가능
		success: function(comments) {
			$("#commentListContainer").empty();
			for (const comment of comments) {
				const editButtons = `
					<button 
						id="commentUpdateBtn${comment.id}" 
						class="commentUpdateButton btn btn-secondary"
						data-comment-id="${comment.id}">수정
					</button>
					<button 
						id="commentDeleteBtn${comment.id}" 
						class="commentDeleteButton btn btn-danger"
						data-comment-id="${comment.id}">삭제
					</button>
				`;

				$("#commentListContainer").append(`
				<li class="list-group-item d-flex justify-content-between align-items-start">
					<div class="ms-2 me-auto">
      					<div class="fw-bold">
      						${comment.memberId}
      					</div>
      					<div class="d-flex d-100">
	      					<span>
								${comment.content} 
	      					</span>
	      					<span>
								${comment.editable ? editButtons : ''}
	      					</span>
      					</div>
  					</div>
  					<span class="badge bg-primary rounded-pill">
  						${comment.inserted}
  					</span>
				`);
			};
			// 수정
			$(".commentUpdateButton").click(function() {
				const id = $(this).attr("data-comment-id");

				$.ajax("/comment/id/" + id, {
					success: function(data) {
						$("#commentUpdateIdInput").val(data.id);
						$("#commentUpdateTextArea").val(data.content);
					}
				});
			});

			// 삭제
			$(".commentDeleteButton").click(function() {
				const commentId = $(this).attr("data-comment-id");

				$.ajax("/comment/id/" + commentId, {
					method: "delete",
					complete: function(jqXHR) {
						listComment();
						$(".toast-body").text(jqXHR.responseJSON.message);
						toast.show();
					}
				});
			});
		}
	});
}


