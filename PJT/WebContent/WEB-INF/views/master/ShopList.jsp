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


<script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script>
<script src="http://code.highcharts.com/highcharts.js"></script> 
<script src="http://code.highcharts.com/modules/data.js"></script>   
</head>
<body>
<table class="table table-bordered" id="datatable">
<thead>
   <tr>
 		<th>예시용</th>
		<th >가게 번호</th>
		<th >사장 번호</th>
	<th >사장 번호</th>
	<th >사장 번호</th>
	
	</tr>
</thead>
<tbody>
		<c:forEach var="e" items="${list }">
			<tr>
				<th>${e.sname }</th>
				<td>${e.snum }</td>
				<td>${e.bnum }</td>
			</tr>
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
		<li><a href="${pageContext.request.contextPath}/sinfo/sinfoList?cPage=${nowPage-pagePerBlock}">이전으로</a></li>
	</c:otherwise>
	</c:choose>

 	<c:forEach varStatus="i" begin="${startPage}" end="${endPage }" step="1" >
		<c:choose>
			<c:when test="${i.index == nowPage}">
	 			<li class="now">${i.index}</li>
			</c:when>
			<c:otherwise>
				<li><a href="${pageContext.request.contextPath}/sinfo/sinfoList?cPage=${i.index}">${i.index}</a></li>
			</c:otherwise>
		</c:choose> 
 	</c:forEach>
 	<c:choose>
		<c:when test="${endPage >= totalPage }">
			<li class="disable">다음으로</li>
		</c:when>
		<c:when test="${totalPage > (nowPage+pagePerBlock)}">
			<li><li><a href="${pageContext.request.contextPath}/sinfo/sinfoList?cPage=${nowPage+pagePerBlock }">다음으로</a></li>
		</c:when>
		<c:otherwise>
     		<li><li><a href="${pageContext.request.contextPath}/sinfo/sinfoList?cPage=${totalPage }">다음으로</a></li>
		</c:otherwise>
 	</c:choose>
</ol>
</td>
</tr>
</tfoot>
</table>
        
        
 
<div id="container" style="width: 550px; height: 400px; margin: 0 auto"></div>
<script language="JavaScript">
$(document).ready(function() { 
   var data = {
      table: 'datatable'
   };
   var chart = {
      type: 'column'
   };
   var title = {
      text: '여기다가 가게 제목'   
   };      
   var yAxis = {
      allowDecimals: false,
      title: {
         text: '차트 뭘로하지?'
      }
   };
   var tooltip = {
      formatter: function () {
         return '<b>' + this.series.name + '</b><br/>' +
            this.point.y + ' ' + this.point.name.toLowerCase();
      }
   };
   var credits = {
      enabled: false
   };  
      
   var json = {};   
   json.chart = chart; 
   json.title = title; 
   json.data = data;
   json.yAxis = yAxis;
   json.credits = credits;  
   json.tooltip = tooltip;  
   $('#container').highcharts(json);
});

</script>

        
        
</body>
</html>