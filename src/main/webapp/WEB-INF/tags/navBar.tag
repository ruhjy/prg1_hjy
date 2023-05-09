<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="current"%>

<!-- <nav class="navbar navbar-expand-lg bg-body-tertiary bg-dark mb-5" data-bs-theme="dark" > -->
<div style="margin-bottom: 80px"></div>
<nav class="navbar navbar-expand-lg bg-body-tertiary mb-5 fixed-top">
	<div class="container-lg">
		<a class="navbar-brand" href="/list">
			<img src="/img/spring.png" alt="" height="45" />
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link ${current eq 'list' ? 'active' : '' }" href="/list">목록</a>
				</li>

				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'add' ? 'active' : '' }" href="/add">글작성</a>
					</li>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'memberList' ? 'active' : '' }" href="/member/list">회원목록</a>
					</li>
				</sec:authorize>

				<sec:authorize access="isAnonymous()">
					<li class="nav-item">
						<a class="nav-link ${current eq 'signup' ? 'active' : '' }" href="/member/signup">회원가입</a>
					</li>
					<li class="nav-item">
						<a class="nav-link ${current eq 'login' ? 'active' : '' }" href="/member/login">로그인</a>
					</li>
				</sec:authorize>

				<sec:authorize access="isAuthenticated()">
					<li class="nav-item">
						<a class="nav-link" href="/member/logout">로그아웃</a>
					</li>
				</sec:authorize>
			</ul>

			<form class="d-flex" action="/list" role="search">

				<div class="input-group">
					<select class="form-select flex-grow-0" style="width: 100px;" name="type" id="">
						<option value="all">전체</option>
						<option value="title" ${param.type eq 'title' ? 'selected' : '' }>제목</option>
						<option value="body" ${param.type eq 'body' ? 'selected' : '' }>본문</option>
						<option value="writer" ${param.type eq 'writer' ? 'selected' : '' }>작성자</option>
					</select>

					<input name="search" value="${param.search }" class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
					<button class="btn btn-outline-success" type="submit">
						<i class="fa-brands fa-searchengin" style="color: #4cb62f;"></i>
					</button>
				</div>
			</form>
		</div>
	</div>
</nav>

<div>
	<sec:authentication property="principal" />
</div>
