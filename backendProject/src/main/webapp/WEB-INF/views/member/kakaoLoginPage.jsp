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
            url: '/api/member/getKaKaoAccessToken',
            data: { code: code }, // code 값을 객체 형태로 전달
            type: 'GET', // POST 요청 사용
            success: function onData(data) {
                console.log(data);
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
</body>
</html>
