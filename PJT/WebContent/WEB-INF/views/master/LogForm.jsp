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
  			<div class="p-2 bg-warning"><a href="${pageContext.request.contextPath}/login/logout" class="nav-link text-white" id="item2">Logout</a></div>
  			<div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/${sessionScope.sessionTier}/mypage" class="nav-link text-white" id="item3">MyPage</a></div>
	
	
				
<table class="table table-bordered">
<thead>
   <tr>
		<th >번호</th>
		<th >아이피</th>
		<th >uagent</th>
		<th >idn</th>
		<th >sstime</th>	
		<th >eetime</th>
		<th> status</th>
	</tr>
</thead>
<tbody>
		<c:forEach var="e" items="${list }">
			<tr>
				<td class="align-middle text-center">${e.num }</td>
				<td class="align-middle text-center"> ${e.reip }</td>
				<td class="align-middle text-center">${e.uagent }</td>
				<td class="align-middle text-center"> ${e.idn }</td>
				<td class="align-middle text-center"> ${e.sstime }</td>
				<td class="align-middle text-center">${e.eetime }</td>
				<td class="align-middle text-center">${e.status }</td>
			<tr>
	</c:forEach>
</tbody>

<tfoot>
<tr>
<td colspan="5" >${startPage}/${endPage }
<ol class="paging" >
	<c:choose>
		<c:when test="${startPage < 6}">
			<li class="disable">이전으로</li>
		</c:when>
	<c:otherwise>
		<li><a href="${pageContext.request.contextPath}/log/logList?cPage=${nowPage-pagePerBlock}">이전으로</a></li>
	</c:otherwise>
	</c:choose>

 	<c:forEach varStatus="i" begin="${startPage}" end="${endPage }" step="1" >
		<c:choose>
			<c:when test="${i.index == nowPage}">
	 			<li class="now">${i.index}</li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/log/logList?cPage=${i.index}">${i.index}</a></li>
			</c:otherwise>
		</c:choose> 
 	</c:forEach>
 	<c:choose>
		<c:when test="${endPage >= totalPage }">
			<li class="disable">다음으로</li>
		</c:when>
		<c:when test="${totalPage > (nowPage+pagePerBlock)}">
			<li><li><a href="${pageContext.request.contextPath}/log/logList?cPage=${nowPage+pagePerBlock }">다음으로</a></li>
		</c:when>
		<c:otherwise>
     		<li><li><a href="${pageContext.request.contextPath}/log/logList?cPage=${totalPage }">다음으로</a></li>
		</c:otherwise>
 	</c:choose>
</ol>
</td>
</tr>
</tfoot>
</table>


</body>
</html>