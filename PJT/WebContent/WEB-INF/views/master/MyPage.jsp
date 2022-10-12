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
<nav>

  			<div class="p-2 bg-warning"><a href="${pageContext.request.contextPath}/login/logout" class="nav-link text-white" id="item2">Logout</a></div>
  			<div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/mypage" class="nav-link text-white" id="item3">MyPage</a></div>
	
</nav>
${sessionScope.sessionId }����  MYPAGE�� ���Ű� ȯ���մϴ�.
���� ��ϵ� �ڿ����� �Դϴ�.  
${totalRecord } ���Դϴ�.
	
			<div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/shop/shopList" class="nav-link text-white" id="item3">���� ����Ʈ</a></div>
			<div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/user/userList" class="nav-link text-white" id="item3">������ ����Ʈ</a></div>
				<div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/log/logList" class="nav-link text-white" id="item3">�α� ���</a></div>		

        
        
        <iframe id="iframe" src="masterkorea" height="800" width="800"> </iframe>
        
</body>
</html>