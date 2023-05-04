<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<style>
.form-check-input:checked {
	background-color: red;
	board-color: red;
}
</style>
</head>
<body>

	<my:navBar />

	<my:alert />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">
				<h1>${board.id }번게시물 수정</h1>
				<form action="" method="post" enctype="multipart/form-data">
					<input type="hidden" name="id" value="${board.id }" />

					<div class="mb-3">
						<label for="titleInput" class="form-label">제목</label>
						<input id="titleInput" type="text" class="form-control" name="title" value="${board.title }" />
					</div>

					<!-- 그림 파일 출력 -->
					<div class="mb-3">
						<c:forEach items="${board.fileName}" var="fileName" varStatus="status">
							<!-- 삭제 체크박스 -->
							<div class="form-check form-switch">
								<input name="removeFiles" value="${fileName }" class="form-check-input" type="checkbox" role="switch" id="removeCheckBox${status.index }">
								<label class="form-check-label" for="removeCheckBox${status.index }">
									<i class="fa-solid fa-trash-can text-danger"></i>
								</label>
							</div>

							<div class="mb-3">
								<%-- http://localhost:8080/image/게시물번호/fileName --%>
								<%-- <img id="image01" class="img-thumbnail img-fluid" src="http://localhost:8080/image/${board.id }/${fileName}" alt="" /> --%>
								<%-- <c:set var="bucketUrl" value="https://bucket0503-zxc1234zxc.s3.ap-northeast-2.amazonaws.com/board" /> --%>
								<img class="img-thumbnail img-fluid" src="${bucketUrl }/${board.id }/${fileName}" alt="" />
							</div>
						</c:forEach>
					</div>

					<div class="mb-3">
						<label for="bodyTextarea" class="form-label">본문</label>
						<textarea id="bodyTextarea" class="form-control" name="body" rows="10">${board.body }</textarea>
					</div>
					<div class="mb-3">
						<label for="writerInput" class="form-label">작성자</label>
						<input id="writerInput" type="text" class="form-control" name="writer" value="${board.writer }" />
					</div>
					<div class="mb-3">
						<label for="" class="form-label">작성일시</label>
						<input type="text" class="form-control" name="inserted" value="${board.inserted }" readonly />
					</div>

					<!-- 새 그림 파일 추가 input -->
					<div class="mb-3">
						<label for="fileInput" class="form-label">그림 파일</label>
						<input id="fileInput" class="form-control" type="file" name="files" accept="/image/*" multiple />
						<div class="form-text">
							총 10MB, 하나의 파일은 1MB를 초과할 수 없습니다. 
						</div>
					</div>

					<div>
						<input class="btn btn-secondary" type="submit" value="수정" />
						<a href="/id/${board.id}" class="btn btn-warning">취소</a>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>