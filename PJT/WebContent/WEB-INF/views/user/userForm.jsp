<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">

</head>

<body>
 <form method="post" action="userIn">
     <table style="width: 80%; margin: auto">
   		<tr>
   		   <td>아이디</td>
   		   <td>
   		   <input type="text" name="id" id="id" maxlength="10" 
   		   style="width: 130px"> 
   		   
   		 </tr>
   		 <tr><td colspan="2" id="target"></td></tr>
   		  <tr>
   		   <td>비밀번호</td>
   		   <td><input type="password" name="pwd" id="pwd" maxlength="10"> 
   		   </td>
   		 </tr>
   		  <tr>
   		   <td>이름</td>
   		   <td><input type="text" name="name" id="name"> 
   		   </td>
   		 </tr>
   		 <tr>
   		   <td>이메일</td>
   		   <td><input type="email" name="email" id="email"> 
   		   </td>
   		 </tr>
   		 
   		 
   		 
   		 
   		  <tr><td colspan="2">
   		  <input type="submit" value="가입">
   		  </td></tr>
   </table>
     </form>
</body>

</html>