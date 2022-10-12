<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/style.css" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://d3js.org/d3.v4.min.js"></script>
<title>Insert title here</title>
</head>
<body>
	<c:choose>
		<c:when test="${sessionScope.sessionId == null }">
			<div class="p-2 bg-warning">
				<a href="${pageContext.request.contextPath}/login/loginForm"
					class="nav-link text-white" id="item2">Login</a>
			</div>
			<div class="p-2 bg-primary">
				<a href="${pageContext.request.contextPath}/user/userForm"
					class="nav-link text-white" id="item3">회원가입</a>
			</div>
		</c:when>
		<c:when test="${sessionScope.sessionId != null }">
			<div class="p-2 bg-warning">
				<a href="${pageContext.request.contextPath}/login/logout"
					class="nav-link text-white" id="item2">Logout</a>
			</div>
			<div class="p-2 bg-primary">
					<a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/mypage" class="nav-link text-white" id="item3">MyPage</a>
			</div>
		</c:when>
	
	</c:choose>
	

	<h1>${sessionScope.sessionId }님 환영합니다.</h1>
	<h1>회원번호는 ${sessionScope.sessionNum }</h1>
	<h1>등급은 ${sessionScope.sessionTier }입니다</h1>
	
	<iframe id="iframe" src="korea" height="800" width="800"> </iframe>
	
	

</body>
</html>