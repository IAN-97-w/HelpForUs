<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	#navDiv{
		background: #fbd14b; width: 100%; height: 50px;
		padding-top: 10px;
		font-family: 'MICEGothic Bold';
	}
	#navDiv a{
		font-size: 20px; text-decoration: none; color: black;
		padding-left: 100px; padding-right: 100px;
	}
	#navDiv a:hover{
		color: gray;
	}
	
	@font-face {
    font-family: 'MICEGothic Bold';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2206-01@1.0/MICEGothic Bold.woff2') format('woff2');
    font-weight: 700;
    font-style: normal;
	}
</style>
</head>
<body>
	<div class="container-fluid" id="navDiv">
		<ul class="nav justify-content-center">
			<li class="nav-item"><a  href="${contextPath }/donBoardList.do">기부하기</a></li>
			<li class="nav-item"><a href="${contextPath }/volBoardList.vo">봉사활동</a></li>
			<li class="nav-item"><a href="${contextPath }/revBoardList.re">모금후기</a></li>
		</ul>
	</div>
</body>
</html>