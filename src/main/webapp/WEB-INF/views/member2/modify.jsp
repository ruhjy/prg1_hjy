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
</head>
<body>

	<my:navBar />

	<my:alert />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">

				<h1>${member.id }님 회원 정보 수정</h1>

				<form id="modifyForm" action="" method="post">
					<div class="mb-3">
						<label for="inputId" class="form-label">아이디</label>
						<input id="inputId" type="text" class="form-control" value="${member.id }" readonly />
					</div>
					<div class="mb-3">
						<label for="inputPassword" class="form-label">비밀번호</label>
						<input id="inputPassword" type="password" class="form-control" name="password" />
						<div class="form-text">
							입력하지 않으면 기존 패스워드를 유지합니다.
						</div>
					</div>
					<div class="mb-3">
						<label for="inputnickName" class="form-label">별명</label>
						<input id="inputnickName" type="text" class="form-control" name="nickName" value="${member.nickName }" />
					</div>
					<div class="mb-3">
						<label for="inputEmail" class="form-label">이메일</label>
						<input id="inputEmail" type="text" class="form-control" name="email" value="${member.email }" />
					</div>
					<div>
						<input type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#confirmModal" value="수정" />
						<a class="btn btn-secondary" href="/member/info?id=${member.id }">취소</a>
					</div>
				</form>

			</div>
		</div>
	</div>

	<!-- 수정 확인 Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">수정 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<label for="inputOldPassword" class="form-label">이전 비밀번호</label>
					<input form="modifyForm" id="inputOldPassword" class="form-control" type="text" name="oldPassword" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="submit" form="modifyForm" class="btn btn-primary">확인</button>
				</div>
			</div>
		</div>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>