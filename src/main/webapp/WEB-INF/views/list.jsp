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

	<my:navBar current="list" />
	<my:alert />
	<div class="container-lg">
		<h1>게시물 목록-수정1</h1>
		<a href="/member/signup">회원가입</a>
		게시물 수 : ${pageInfo['numOfRecords']}
		<c:url value="/list" var="pageLink">
			<c:param name="page" value="${pageInfo['currentPageNum'] }"></c:param>
		</c:url>

		<%-- 		<c:if test="${not empty param.search }">
			<c:param name="search" value="${param.search }" />
		</c:if> --%>
		<form action="${pageLink }">

			<select name="maxPageSize">
				<option value="15">15개씩 보기</option>
				<option value="5" ${param.maxPageSize eq 5 ? 'selected' : '' }>5개씩 보기</option>
				<option value="10" ${param.maxPageSize eq 10 ? 'selected' : '' }>10개씩 보기</option>
				<option value="20" ${param.maxPageSize eq 20 ? 'selected' : '' }>20개씩 보기</option>
				<option value="30" ${param.maxPageSize eq 30 ? 'selected' : '' }>30개씩 보기</option>
			</select>
			<input type="submit" value="보기" />
		</form>

		<table class="table table-hover">
			<thead>
				<tr>
					<th>번호</th>
					<th>
						<i class="fa-solid fa-thumbs-up"></i>
					</th>
					<th>제목</th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList }" var="board">
					<tr>
						<td>${board.id }</td>
						<td>${board.likeCount }</td>
						<td>
							<a href="/id/${board.id }"> ${board.title } </a>
							<c:if test="${board.fileCount > 0 }">
								<span class="badge rounded-pill text-bg-info">
									<i class="fa-regular fa-image"></i>
									${board.fileCount }
								</span>
							</c:if>
							(${board.commentCount })
						</td>
						<td>${board.writer }</td>
						<td>${board.hit }</td>
						<td>${board.inserted }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container-lg">
		<div class="row">
			<nav aria-label="Page navigation example">

				<ul class="pagination justify-content-center">


					<c:if test="${pageInfo['currentPageNum'] gt 1 }">
						<!-- 맨앞 버튼 -->
						<!-- 페이지 번호 : 1 -->
						<my:pageItem pageNum="1">
							<i class="fa-solid fa-angles-left"></i>
						</my:pageItem>
						<!-- 이전 버튼 -->
						<!-- 페이지 번호 : ${pageInfo['currentPageNum'] - 1} -->
						<my:pageItem pageNum="${pageInfo['currentPageNum'] - 1 }">
							<i class="fa-solid fa-chevron-left"></i>
						</my:pageItem>
					</c:if>

					<!-- 페이지 -->
					<c:forEach begin="${pageInfo['leftPageNum']}" end="${pageInfo['rightPageNum'] }" var="pageNum">
						<my:pageItem pageNum="${pageNum }">
							${pageNum }
						</my:pageItem>
					</c:forEach>

					<c:if test="${pageInfo['currentPageNum'] lt pageInfo['lastPageNum']}">
						<!-- 다음 버튼 -->
						<!-- 페이지 번호 : ${pageInfo['currentPageNum'] + 1} -->
						<my:pageItem pageNum="${pageInfo['currentPageNum'] + 1 }">
							<i class="fa-solid fa-chevron-right"></i>
						</my:pageItem>
						<!-- 맨뒤 버튼 -->
						<!-- 페이지 번호 : ${pageInfo['lastPageNum'] } -->
						<my:pageItem pageNum="${pageInfo['lastPageNum'] }">
							<i class="fa-solid fa-angles-right"></i>
						</my:pageItem>
					</c:if>


				</ul>
			</nav>
		</div>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

</body>
</html>