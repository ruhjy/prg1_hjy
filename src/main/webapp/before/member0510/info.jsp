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

	<my:navBar current="memberInfo" />

	<my:alert />

	<div class="container-lg">
		<div class="row justify-content-center">
			<div class="col-12 col-md-8 col-lg-6">

				<h1>${member.id }님 회원 정보</h1>

				<div class="mb-3">
					<label for="" class="form-label">아이디</label>
					<input type="text" class="form-control" name="id" value="${member.id }" readonly />
				</div>

				<div class="mb-3">
					<label for="" class="form-label">별명</label>
					<input type="text" class="form-control" value="${member.nickName }" readonly />
				</div>

				<div class="mb-3">
					<label for="" class="form-label">이메일</label>
					<input type="text" class="form-control" value="${member.email }" readonly />
				</div>

				<div>
					<a class="btn btn-secondary" href="/member/modify?id=${member.id }">수정</a>
					<button class="btn btn-danger" type="button" data-bs-toggle="modal" data-bs-target="#confirmModal">탈퇴</button>
					<a class="btn btn-warning" href="/member/list">회원목록</a>
				</div>

			</div>
		</div>
	</div>



	<!-- 탈퇴 확인 Modal -->
	<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h1 class="modal-title fs-5" id="exampleModalLabel">회원 탈퇴 확인</h1>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<form action="/member/remove" method="post" id="removeForm">
						<input type="hidden" name="id" value="${member.id}" />
						<label for="passwordInput">비밀번호</label>
						<input id="passwordInput" type="password" name="password" class="form-control" />
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
					<button type="submit" class="btn btn-danger" form="removeForm">확인</button>
				</div>
			</div>
		</div>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>