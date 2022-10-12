<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
     
   <!DOCTYPE html>
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

<table class="table table-bordered">
<thead>
   <tr>
		<th >가게 번호</th>
		<th >사장 번호</th>
		<th >가게 이름</th>
		<th >가게 지역</th>
		<th >카테고리</th>	
		<th >온라인 오프라인 유무</th>
		<th> 가입 승인 유 무</th>
		<th >가입 신청상태</th>
		<th >가입 등록 일</th>
		<th >가입 마감 일</th>
	</tr>
</thead>
<tbody>
	
			<tr>
				<td class="align-middle text-center" id="snum" name="snum"> ${shop.snum }</td>
				<td class="align-middle text-center"id="bnum" name="bnum"> ${shop.bnum }</td>
				<td class="align-middle text-center"> ${shop.sname }</td>
				<td class="align-middle text-center">${shop.sloc }</td>
				<td class="align-middle text-center"> ${shop.scate }</td>
				<td class="align-middle text-center">${shop.onoff }</td>	
				<td class="align-middle text-center">${shop.ctf }</td>
				<td class="text-center"> ${shop.sdate }</td>
				<td class="align-middle">${shop.edate }</td>
			</tr>
	
</tbody>
</table>




</body>
</html>