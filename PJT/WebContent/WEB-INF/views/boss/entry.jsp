<%@page import="java.sql.Date"%>
<%@page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>회원가입</title>
<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>
.btn-space {
	margin-right: 5px;
}
</style>
</head>

<body>
<article class="container">
	<div class="page-header">
		<center>
			<h1>
				<a onclick="return false; location.reload()" style="cursor: pointer;"><b>회원가입</b></a>
			</h1>
		</center>
		<div class="col-md-6 col-md-offset-3"></div>
	</div>

	<hr>
<form action="entryShop" method="post">
		<div class="col-sm-6 col-md-offset-3">

			<div class="form-group">
				<label for="${ShopDTO.sname }">가게명</label> <input type="text"
					class="form-control" id="${ShopDTO.sname }" name="${ShopDTO.sname }" placeholder="이름을 입력해 주세요">
					
			</div>

			<div class="form-group">
				<label for="${ShopDTO.snum }">사업자등록번호</label> <input type="text"
					class="form-control" id="${ShopDTO.snum }" name="${ShopDTO.snum }" placeholder="사업자등록번호 입력">
			</div>

			<div class="form-group">
				<label for="${ShopDTO.scate }">카테고리</label> <input type="text"
					class="form-control" id="${ShopDTO.scate }" name="${ShopDTO.scate }" placeholder="카테고리">
			</div>

			<div class="form-group">
				<label>가입 등급</label> 
				<input type="hidden" id="${ShopDTO.onoff }" name="${ShopDTO.onoff }" value="">
			<%-- onoff --%>
				<div class="form-control">
					<label class="radio-inline"> <input type="radio"
						name="ck_onoff" value="on"> 온라인
					</label> <label class="radio-inline"> <input type="radio"
						name="ck_onoff" value="off"> 오프라인
					</label> <label class="radio-inline"> <input type="radio"
						name="ck_onoff" value="on/off"> 모두
					</label>
				</div>
			</div>

			<div class="form-group">
				<input type="hidden" id="sloc" name="sloc" value="" />
			<%-- sloc --%>
				<label>주소</label> <input type="text" class="form-control" id="zipNo"
					name="post" placeholder="우편번호">
				<button class="btn btn-default" type="button" onClick="goPopup();">
					<i class="fa fa-search"></i>주소검색
				</button>

			</div>

			<div class="form-group" style="margin-top: 0px;">
				<input type="text" class="form-control" id="roadFullAddr"
					name="address" placeholder="상세주소" />
			</div>


			<div class="form-group text-center">
				<button type="submit" id="join-submit"
					class="btn btn-primary btn-space">
					회원가입<i class="fa fa-check spaceLeft"></i>
				</button>

				<button type="button" class="btn btn-danger"
					onClick="location.href='index.jsp'">
					취소<i class="fa fa-check spaceLeft"></i>
				</button>

			</div>
		</div>
	</form>
	</article>
		<hr>
		
	<script>
		function goPopup() {
			// 주소검색을 수행할 팝업 페이지를 호출합니다.
			// 호출된 페이지(addressAPIPopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrLinkUrl.do)를 호출하게 됩니다.
			var pop = window.open("adressPopup", "pop",
					"width=570,height=420, scrollbars=yes, resizable=yes");

			// 모바일 웹인 경우, 호출된 페이지(addressAPIPopup.jsp)에서 실제 주소검색URL(https://www.juso.go.kr/addrlink/addrMobileLinkUrl.do)를 호출하게 됩니다.
			//var pop = window.open("/popup/addressAPIPopup.jsp","pop","scrollbars=yes, resizable=yes"); 
		}

		function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail,
				roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn,
				bdMgtSn, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm,
				rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
			// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.

			var address1 = document.querySelector("#zipNo");
			address1.value = zipNo;

			var address2 = document.querySelector("#roadFullAddr");
			address2.value = roadFullAddr;

			var address3 = document.querySelector('#sloc');
			address3.value = zipNo + ", " + roadFullAddr;
		}

		$('input:radio[name=ck_onoff]').change(function() {

			if (this.checked) {
				$('#onoff').attr('value', $(this).val());
			}
		});
	</script>
	</body>
</html>