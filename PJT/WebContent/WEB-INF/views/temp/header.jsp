<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
 <head>
  <TITLE> New Document </TITLE>
  <meta charset="euc-kr">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
 <style>
select.btn-mini {
    height: auto;
    width : 200px;
    line-height: 14px;
}
.card {
    
    border: none;
    box-shadow: 5px 6px 6px 2px #e9ecef;
    border-radius: 4px;
}
.dots{
  height: 4px;
  width: 4px;
  margin-bottom: 2px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
}

.badge{
    padding: 7px;
    padding-right: 9px;
    padding-left: 16px;
    box-shadow: 5px 6px 6px 2px #e9ecef;
}

.user-img{
    margin-top: 4px;
}

.check-icon{
    font-size: 17px;
    color: #c3bfbf;
    top: 1px;
    position: relative;
    margin-left: 3px;
}
.form-check-input{
    margin-top: 6px;
    margin-left: -24px !important;
    cursor: pointer;
}
.form-check-input:focus{
    box-shadow: none;
}


.icons i{
    margin-left: 8px;
}
.reply{
    margin-left: 12px;
}

.reply small{
    color: #b7b4b4;
}
.reply small:hover{
    color: green;
    cursor: pointer;
}
.bgGr1 {
	background-color: #0d6efd
}
.bgGr2 {
	background-color: #ffc107
}
.bgGr3 {
	background-color: #0dcaf0
}
#colTitle{
	background-color: #000000;
}

.mytable-condensed{
  font-size: 11px;
}
 </style>
<script>
$(function(){
	$('#searchv').focusin(function() {
		$(this).val("검색어 입력")
	});
	$('#searchv').focusout(function() {
		$(this).val("")
	});
	
});
</script>
 </head>
 <body>
<header class="text-white text-center" >
<!-- header 상단 메뉴 -->
<div class="d-flex flex-row-reverse mybgColor">
  <c:choose>
  <c:when test="${sessionScope.sessionId == null }">
  <div class="p-2 bg-warning"><a href="${pageContext.request.contextPath}/login/loginForm" class="nav-link text-white" id="item2">Login</a></div>
  <div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/member/memberForm" class="nav-link text-white" id="item3">회원가입</a></div>
  </c:when>
  <c:when test="${sessionScope.sessionId != null }">
  <div class="p-2 bg-warning"><a href="${pageContext.request.contextPath}/login/logout" class="nav-link text-white" id="item2">Logout</a></div>
  <div class="p-2 bg-primary"><a href="${pageContext.request.contextPath}/member/memberForm" class="nav-link text-white" id="item3">MyPage</a></div>
  </c:when>
  </c:choose>
  <!-- 관리자는 <회원관리> <상품재고관리> <배송관리>  -->
  <div class="p-2 bg-info"><a href="#" class="nav-link text-white" id="item1">장바구니</a></div>
  <div class="p-2 bg-info"><a href="#" class="nav-link text-white" id="item1">구매목록</a></div>
  <div class="p-2 bg-info"><a href="#" class="nav-link text-white" id="item1">내정보수정</a></div>
</div>
        <!-- Carousel -->
<div id="demo" class="carousel slide" data-bs-ride="carousel">
  <!-- Indicators/dots -->
  <div class="carousel-indicators">
    <button type="button" data-bs-target="#demo" data-bs-slide-to="0" class="active"></button>
    <button type="button" data-bs-target="#demo" data-bs-slide-to="1"></button>
    <button type="button" data-bs-target="#demo" data-bs-slide-to="2"></button>
  </div>
  
  <!-- The slideshow/carousel -->
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="${pageContext.request.contextPath}/resources/image/d1.jpg" alt="Kosmo113" class="d-block" style="width:100%">
      <div class="carousel-caption">
        <h3><c:if test="${sessionScope.sessionName !=null}">${sessionScope.sessionName }님 반갑습니다 </c:if> </h3>
        <h3>여기는  BT 홈페이지 입니다. </h3>
        <p>jQuery 를 이용하여 제공받은 샘플파일에서 아래 요구사항대로 구현합니다.</p>
      </div>
    </div>
    <div class="carousel-item">
      <img src="${pageContext.request.contextPath}/resources/image/d2.jpg" alt="Carousel" class="d-block" style="width:100%">
      <div class="carousel-caption">
        <h3><c:if test="${sessionScope.sessionName !=null}">${sessionScope.sessionName }님 반갑습니다</c:if></h3>
        <h3>여기는  BT 홈페이지 입니다..</h3>
        <p>Thank you, CSS3!</p>
      </div> 
    </div>
    <div class="carousel-item">
      <img src="${pageContext.request.contextPath}/resources/image/d3.jpg" alt="JSP" class="d-block" style="width:100%">
      <div class="carousel-caption">
        <h3><c:if test="${sessionScope.sessionName !=null}">${sessionScope.sessionName }님 반갑습니다</c:if> </h3>
        <h3>여기는  BT 홈페이지 입니다.</h3>
        <p>jQuery 를 이용하여 제공받은 샘플파일에서 아래 요구사항대로 구현합니다.</p>
      </div>  
    </div>
  </div>
  
  <!-- Left and right controls/icons -->
  <button class="carousel-control-prev" type="button" data-bs-target="#demo" data-bs-slide="prev">
    <span class="carousel-control-prev-icon"></span>
  </button>
  <button class="carousel-control-next" type="button" data-bs-target="#demo" data-bs-slide="next">
    <span class="carousel-control-next-icon"></span>
  </button>
</div>  
</header>
<%--bg-dark navbar-dark --%>
    <nav class="navbar navbar-expand-sm mybgColor" >
     <div class="container-fluid" >
        <ul class="navbar-nav" >
            <li class="nav-item"><a href="${pageContext.request.contextPath}/main" class="nav-link active" >Home</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/board/boardlist" class="nav-link">남성의류</a></li>
            <li class="nav-item"><a href="${pageContext.request.contextPath}/upload/uplist" class="nav-link">여성의류</a></li>
            <li class="nav-item"><a href="#" class="nav-link">잡화</a></li>
            <li class="nav-item"><a href="#" class="nav-link">고객센터</a></li>
        </ul>
        <form class="d-flex">
        <input class="form-control me-2" type="text" placeholder="Search" name="searchv" id="searchv">
        <button class="btn btn-primary" type="button">Search</button>
        </form>
      </div>
    </nav>