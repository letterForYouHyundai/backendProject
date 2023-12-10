<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script>
    const CLIENT_ID = 'ad117e5251ddb446c15d829ce0967079';
    const REDIRECT_URI = 'http://localhost:8081/api/member/kakaoLoginPage';
    const kakaoLoginURL = 'https://kauth.kakao.com/oauth/authorize?client_id=' + CLIENT_ID + '&redirect_uri=' + REDIRECT_URI + '&response_type=code';
    let code = "";
    function loginTest() {
       window.location.href = kakaoLoginURL;
    }
    function active() {
        $.ajax({
            url: '/api/member/kakaoLogin',
            data: { code: code },
            type: 'GET',
            success: function onData(data) {
                // 예시: 사용자 정보 출력
                const userInfo = data.userInfo;
          		console.log(data);
          		if (userInfo) {
                    const userInfoDiv = document.getElementById('userInfo');
                    console.log(userInfo);
                    userInfoDiv.innerText = 'Welcome, ' + userInfo.userNickname;
                    userInfoDiv.style.display = 'block'; // 닉네임이 있는 경우 해당 div 보이도록 설정
                }
            },
            error: function onError(error) {
                console.error(error);
            }
        });
    }

    // 페이지 로드 시 실행되는 함수 =>로그인 메인화면과 로그인 이후 리액트 화면을 분리해야할거같다.
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        code = urlParams.get("code");
        if (code) {
            console.log(code);
            
            //로그인 이후 실행
            active();
            //그 이후 로그인으로 redirect하기
        }
    };
    </script>
</head>
<body>
    <h2>카카오로그인페이지</h2>
    <a href="${kakaoLoginURL}">카카오 로그인</a>
    <button id="loginBtn" onclick="loginTest()">로그인 테스트</button>
  	<div id="userInfo" style="display: none;"></div>
</body>
</html>
