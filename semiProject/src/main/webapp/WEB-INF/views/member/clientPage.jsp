<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 페이지</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<style>
	.mypage{padding-top:15px; text-align:left; font-weight:bold;}
	.category{font-weight:bold; text-align:left; font-size:22px; padding-top:5px;}
	.menu{text-align:left; font-size:13px; margin:1px;}
	.menu:hover{font-weight:bold; cursor:pointer; text-decoration:underline;}
	.seletedCategory{font-weight:bold; text-align:center; font-size:22px; padding-top:5px;}
</style>
</head>
<body>
	<jsp:include page="../common/top.jsp"/>
	
	
	<div class="container text-center">
		<h3 class='mypage'>Admin Page</h3>
		
		<br>
		
		<div class="row">
    		<div class="col-2 border border-dark border-2">
   				
   				<p class="category">게시물 관리</p>
   				<ul type="circle">
   					<li><p class="menu" onclick="location.href='${contextPath}'">모금 게시글</p></li>
   					<li><p class="menu" onclick="location.href='${contextPath}'">봉사 게시글</p></li>
   				</ul>
   				
   				<p class="category">후기 관리</p>
   				<ul type="circle">
   					<li><p class="menu" onclick="location.href='${contextPath}'">모금 후기</p></li>
   					<li><p class="menu" onclick="location.href='${contextPath}'">봉사 후기</p></li>
   				</ul>
   				
   				<p class="category">댓글 관리</p>
   				<ul type="circle">
   					<li><p class="menu" onclick="location.href='${contextPath}'">모금 댓글</p></li>
   					<li><p class="menu" onclick="location.href='${contextPath}'">후기 댓글</p></li>
   				</ul>
   				
   				<p class="category">회원 관리</p>
   				<ul type="circle">
   					<li><p class="menu" onclick="location.href='${contextPath}/client.me'">회원 목록</p></li>
   				</ul>
   				
   				
   				<p class="category">단체 관리</p>
   				<ul type="circle">
	   				<li><p class="menu" onclick="location.href='${contextPath}/group.me'">단체 목록</p></li>
				</ul>
				
				<p class="category">쪽지함</p>
   				<ul type="circle">
	   				<li><p class="menu" onclick="location.href='${contextPath}/message.me'">쪽지함</p></li>
				</ul>
   			</div>
   			
   			<%--공백생성 --%>
   			<div class="col-1">
   			</div>
   			<%--공백생성 --%>
   			
   			<%-- 메뉴 선택하면 나오는 화면 컨테이너 --%>
   			<div class="col-8 border border-dark border-2">
   				<h4 style="padding:10px;">회원 관리창</h4>
   				
   				<table class="table table-hover">
				  <thead>
				    <tr>
				      <th scope="col">#</th>
				      <th scope="col">아이디</th>
				      <th scope="col">닉네임</th>
				      <th scope="col">이름</th>
				      <th scope="col">전화번호</th>
				      <th scope="col">이메일</th>
				      <th scope="col">가입일</th>
				      <th scope="col">STATUS</th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr>
				      <th scope="row">1</th>
				      <td>Bae</td>
				      <td>Hanseong</td>
				      <td>배한성</td>
				      <td>010</td>
				      <td>naver</td>
				      <td>SYSDATE</td>
				      <td>M</td>
				    </tr>
				    <tr>
				      <th scope="row">2</th>
				      <td>Jung</td>
				      <td>Jaehoon</td>
				      <td>정재훈</td>
				      <td>010</td>
				      <td>naver</td>
				      <td>SYSDATE</td>
				      <td>M</td>
				    </tr>
				    <tr>
				      <th scope="row">3</th>
				      <td>Kim</td>
				      <td>Jiho</td>
				      <td>김지호</td>
				      <td>010</td>
				      <td>naver</td>
				      <td>SYSDATE</td>
				      <td>M</td>
				    </tr>
				    <tr>
				      <th scope="row">4</th>
				      <td>Choi</td>
				      <td>Wonchang</td>
				      <td>최원창</td>
				      <td>019</td>
				      <td>naver</td>
				      <td>SYSDATE</td>
				      <td>M</td>
				    </tr>
				    <tr>
				      <th scope="row">5</th>
				      <td>Baek</td>
				      <td>Jangmi</td>
				      <td>백장미</td>
				      <td>010</td>
				      <td>naver</td>
				      <td>SYSDATE</td>
				      <td>MZ</td>
				    </tr>
				    
				  </tbody>
				</table>
				<ul class="pagination" style="justify-content: center;">
					<c:if test="${ pi.currentPage > 1 }">
					<li class="page-item"><c:url var="goBack" value="${ loc }">
							<c:param name="page" value="${ pi.currentPage-1 }"></c:param>
						</c:url> <a class="page-link" href="${ goBack }" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
					</a></li>
					</c:if>
					<c:forEach begin="${ pi.startPage }" end="${ pi.endPage }" var="p">
						<c:url var="goNum" value="${ loc }">
							<c:param name="page" value="${ p }"></c:param>
						</c:url>
						<li class="page-item"><a class="page-link" href="${ goNum }">${ p }</a></li>
					</c:forEach>
					<c:if test="${ pi.currentPage < pi.maxPage }">
					<li class="page-item"><c:url var="goNext" value="${ loc }">
							<c:param name="page" value="${ pi.currentPage+1 }"></c:param>
						</c:url> <a class="page-link" href="${ goNext }" aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
					</c:if>
				</ul>
            </div>
    	</div>
   	</div>
   			
	<jsp:include page="../common/footer.jsp"/>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>