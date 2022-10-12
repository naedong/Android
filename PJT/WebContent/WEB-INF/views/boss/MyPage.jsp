<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>Insert title here</title>
</head>
<body>
${sessionScope.sessionId }님의  MYPAGE에 오신걸 환영합니다.
사업장관리 회원이십니다.   

			<div class="p-2 bg-warning">
				<a href="${pageContext.request.contextPath}/login/logout"
					class="nav-link text-white" id="item2">Logout</a>
			</div>
			<div class="p-2 bg-primary">
				<a
					href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/mypage"
					class="nav-link text-white" id="item3">MyPage</a>
			</div>
	

<c:choose>
	<c:when test="${sho.ctf == null }">
		<div class="p-2 bg-warning"><a href="${pageContext.request.contextPath}/boss/entry" class="nav-link text-white" id="item2">가게 등록</a></div>
	</c:when>
<c:when test="${sho.ctf != null }">
자신의 가게 
<li>사업자등록번호 :  <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3"> ${sho.snum }</a></li>			
<li>가게 이름 :<a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3">  ${sho.sname } </a>	</li>					
<li>가게 지역 : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm" id="item3">${sho.sloc }</a>		</li>				
<li>가게 위치 : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3"> ${sho.scate } </a>	</li>							
<li>카테고리 : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3">  ${sho.sname } </a>	</li>						
<li>승인여부 : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3"> ${sho.onoff } </a>	</li>				
<h1>신청날짜 : ${sho.sdate } </h1>			
<h1>승인마감 : ${sho.edate } </h1>			

<li>재고 확인 : <a href="${pageContext.request.contextPath}/sinfo/sinfoList?snum=${sho.snum}"  id="item3">재고확인</a>	</li>

</c:when>
</c:choose>
</body>
</html>