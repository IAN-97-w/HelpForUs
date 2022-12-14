<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>마이페이지</title>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js" ></script>
<!-- iamport.payment.js -->
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.1.8.js"></script>
<style>
	.mypage{padding-top:15px; text-align:left; font-weight:bold;}
	.category{font-weight:bold; text-align:left; font-size:22px; padding-top:5px;}
	.menu{text-align:left; font-size:13px; margin:1px;}
	.menu:hover{font-weight:bold; cursor:pointer; text-decoration:underline;}
	.seletedCategory{font-weight:bold; text-align:center; font-size:22px; padding-top:5px;}
	
	.chargeRose{font-weight:bold; text-align:center; font-size:30px; padding-top:25px;}
	.underline{width:360px; text-align:center;}
	.rose{font-weight:bold; font-size:20px; }
	#allRose{border: none; width: 100px; height: 40px; text-align: right;}
	button{width: 100px; height: 40px; text-align: justify;}
	.col-5{padding-bottom: 30px;}
	.row{padding-left: 55px}
	
</style>
</head>
<body>
	<jsp:include page="../common/top.jsp"/>
	
	
	<div class="container text-center">
		<c:if test="${loginUser.memberRight =='B'}">
			<h3 class='mypage'>My Page</h3>
		</c:if>
		
		<c:if test="${loginUser.memberRight =='C' || loginUser.memberRight == 'D'}">
			<h3 class='mypage'>단체 정보 페이지</h3>
		</c:if>
		
		<br>
		
		<div class="row">
    		<div class="col-2 border border-dark border-2">
   				
   					<p class="category">내 정보</p>
   				
   				<ul type="circle">
   						<li><p class="menu" onclick="location.href='${contextPath}/myInfo.me'">내 정보 확인 / 수정</p></li>
   						<li><p class="menu" onclick="location.href='${contextPath}/rose.me'">장미 구입</p></li>
   				</ul>
   				
   				
   				<p class="category">기부 현황</p>
   				<ul type="circle">
   					<li><p class="menu" onclick="location.href='${contextPath}/donBoard.me'">기부 내역</p></li>
   					<li><p class="menu" onclick="location.href='${contextPath}/cheerDBoard.me'">응원한 모금 게시글</p></li>
   				</ul>
   				
   				
   				<p class="category">봉사 활동 현황</p>
   				<ul type="circle">
	   				<li><p class="menu" onclick="location.href='${contextPath}/volBoard.me'">신청한 봉사활동</p></li>
   					<li><p class="menu" onclick="location.href='${contextPath}/cheerVBoard.me'">응원한 봉사활동</p></li>
				</ul>
				
				
				<p class="category">쪽지함<img id="msgBox" src='https://cdn-icons-png.flaticon.com/512/6188/6188613.png' style='display: none;'></p>
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
   				
   				<!-- 선택된 제목-->
                <h4 class="chargeRose">보유 중인 장미 :<input id="allRose"  value="${loginUser.memberRose}" readonly>송이</h4>
               
                <h4 class="chargeRose">장미 구입</h4>
                <br>
                
	              	<div class="row"> 
	              	 
		                <div class="col-5">
		                	<div class="rose">장미 1 송이    
		                		<button type="button" class="btn btn-danger roseNum" value="1">100원</button>
		                	</div>
		                </div>
			            
			            <div class="col-5">
		                	<div class="rose">장미 100 송이
		                		<button type="button" class="btn btn-danger roseNum" value="100">10,000원</button>
		                	</div>
		                </div>
	                
	                </div>
	                
		            <div class="row"> 
		                <div class="col-5">
		                	<div class="rose">장미 10 송이
		                		<button type="button" class="btn btn-danger roseNum" value="10">1,000원</button>
		                	</div>
		                </div>
			            
			            <div class="col-5">
		                	<div class="rose">장미 300 송이
		                		<button type="button" class="btn btn-danger roseNum"  value="300">30,000원</button>
		                	</div>
		                </div>
	                </div>
                	
                	 <div class="row"> 
		                <div class="col-5">
		                	<div class="rose">장미 30 송이
		                		<button type="button" class="btn btn-danger roseNum" value="30">3,000원</button>
		                	</div>
		                </div>
			            
			            <div class="col-5">
		                	<div class="rose">장미 500 송이
		                		<button type="button" class="btn btn-danger roseNum" value="500">50,000원</button>
		                	</div>
		                </div>
	                </div>
	                
	                
	                <div class="row"> 
		                <div class="col-5">
		                	<div class="rose">장미 50 송이
		                		<button type="button" class="btn btn-danger roseNum" value="50">5,000원</button>
		                	</div>
		                </div>
			        
			        
			         
			       		<div class="col-5">
		            		<div class="rose" >장미 1000 송이
		                		<button type="button" class="btn btn-danger roseNum" value="1000">100,000원</button>
		               		</div>
		            	</div>
	                </div>
	                
	                <div class="row">
	               		<div class="col-12">
		                	<div class="rose">
		                		<span></span>
		                		<button id="pay" class="btn btn-outline-primary">결제하기</button>
		                		<button id="reset" class="btn btn-outline-danger" style="margin-right: 30px">취소</button>
		                	</div>
		                </div>
	                </div>
	            </div> 
            </div>
            <form id="roseForm">
            	<input calss="roseInfo" type="hidden" name="roseNum">
            </form>
   		</div>
	<jsp:include page="../common/footer.jsp"/>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>

	<script type="text/javascript">
		window.onload = () =>{
			
			const btns = document.getElementsByClassName('roseNum')
			const price = document.querySelectorAll('span');
			var num = 0;
			var won = 0;

			for(const i of btns){
				i.addEventListener('click',()=>{
					//console.log(Number(i.value)+Number(i.value));
					num += Number(i.value);
					won += Number(i.value*100);
					console.log(num);
					console.log(won);
					
					price[1].innerText = num + '개'+'('+ won +'원)';
				});
			}
			
			document.getElementById('reset').addEventListener('click', function reset() {
					price[1].innerText = " ";
					num=0;
					won=0;
			} )
			
			
			
			document.getElementById('pay').addEventListener('click',function requestPay() {
			      	
				const loginUser = '${loginUser}';
				if(loginUser == ''){
					alert('로그인 후 이용해주세요.');
					location.href='${contextPath}/loginView.me';
				}
				
				var IMP = window.IMP; // 생략 가능
					IMP.init("imp04858826"); // 예: imp00000000
				//	console.log(roseNum.value);
				// IMP.request_pay(param, callback) 결제창 호출
			    IMP.request_pay({ // param
			    	  pg: "html5_inicis",
			          pay_method: "card",
			          merchant_uid: new Date().getTite,
			          name: "장미결제",
			          amount: won,
			          buyer_name: "${loginUser.memberName}",
			     }, function (rsp) { // callback
			          if (rsp.success) {
			        	$.ajax({ type: "GET", 
			                    url: "${contextPath}/updateRose.me", 
			                    data: {"roseNum" : won/100}
			                	});
			        		location.reload();
							swal('장미를 구매했어요!');
			          } else {
			        		swal('장미 구매에 실패했어요. 다시 시도해주세요');
			          }
			      });
			    });
			
			setInterval(
			function alarm() {
				$.ajax({
					url: '${ contextPath }/msgAlarm.me',
					success: (data) => {
						if(data>0){
							document.getElementById('msgBox').style="width: 40px; height:30px; padding-left: 10px;"
						}
						
					},
					error: (data) => {
						console.log(data);
					}
				});
		 },1000);
			
			
		}
  </script>
</body>
</html>