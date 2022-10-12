<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<form action="upset" method="get">
	<table class="table table-bordered">
<thead>
   <tr>
   		<th >사업자 번호</th>
		<th >가게 번호</th>
		<th >가게 이름</th>
		<th >가게 지역</th>
		<th >카테고리</th>	
		<th >온라인 오프라인 유무</th>
	</tr>
</thead>
<tbody>
			<tr>
				<td class="align-middle text-center">  <input type="text" id="${ShopDTO.snum }" name="snum"></td>
				<td class="align-middle text-center">  <input type="text" id="${ShopDTO.sname }" name="sname"></td>
				<td class="align-middle text-center"> <input type="text" id="${ShopDTO.sloc }" name="sloc"></td>
				<td class="align-middle text-center">  <input type="text" id="${ShopDTO.scate }" name="scate"></td>
				<td class="align-middle text-center"> <input type="text" id="${ShopDTO.onoff }" name="onoff"></td>
				<td class="align-middle text-center"> <input type="text" id="${ShopDTO.ctr }" name="ctf"></td>
			</tr>	
</tbody>
</table>
<input type="submit" value="수정">
<input type="button" id="delete" value="삭제" onclick="location='${pageContext.request.contextPath}/boss/deleteForm'">

</form>


</body>
</html>