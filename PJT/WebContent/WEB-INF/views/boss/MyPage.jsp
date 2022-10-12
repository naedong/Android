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
${sessionScope.sessionId }����  MYPAGE�� ���Ű� ȯ���մϴ�.
�������� ȸ���̽ʴϴ�.   

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
		<div class="p-2 bg-warning"><a href="${pageContext.request.contextPath}/boss/entry" class="nav-link text-white" id="item2">���� ���</a></div>
	</c:when>
<c:when test="${sho.ctf != null }">
�ڽ��� ���� 
<li>����ڵ�Ϲ�ȣ :  <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3"> ${sho.snum }</a></li>			
<li>���� �̸� :<a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3">  ${sho.sname } </a>	</li>					
<li>���� ���� : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm" id="item3">${sho.sloc }</a>		</li>				
<li>���� ��ġ : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3"> ${sho.scate } </a>	</li>							
<li>ī�װ� : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3">  ${sho.sname } </a>	</li>						
<li>���ο��� : <a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/UpdateForm"  id="item3"> ${sho.onoff } </a>	</li>				
<h1>��û��¥ : ${sho.sdate } </h1>			
<h1>���θ��� : ${sho.edate } </h1>			

<li>��� Ȯ�� : <a href="${pageContext.request.contextPath}/sinfo/sinfoList?snum=${sho.snum}"  id="item3">���Ȯ��</a>	</li>

</c:when>
</c:choose>
</body>
</html>