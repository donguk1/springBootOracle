<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인하기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // 회원가입
            $("#btnUserReg").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                location.href = "/user/userRegForm";
            })

            // 아이디 찾기
            $("#btnSearchUserId").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                location.href = "/user/searchUserId";
            })

            // 비밀번호 찾기
            $("#btnSearchPassword").on("click", function () { // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
                location.href = "/user/searchPassword";
            })


        })

        function checkInputData() {
            let f = document.getElementById("f"); // form 태그

            if (f.id.value === "") {
                alert("아이디를 입력하세요.");
                f.id.focus();
                return false;
            }

            if (f.pw.value === "") {
                alert("비밀번호를 입력하세요.");
                f.pw.focus();
                return false;
            }

        }
    </script>
</head>
<body>
<h2>로그인하기</h2>
<hr/>
<br/>
<form action="/user/loginProc" method="post" onsubmit="checkInputData()" id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">아이디
                </div>
                <div class="divTableCell">
                    <input type="text" name="id" id="id" style="width:95%"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">비밀번호
                </div>
                <div class="divTableCell">
                    <input type="password" name="pw" id="pw" style="width:95%"/>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnLogin" type="submit">로그인</button>
        <button id="btnUserReg" type="button">회원가입</button>
        <button id="btnSearchUserId" type="button">아이디 찾기</button>
        <button id="btnSearchPassword" type="button">비밀번호 찾기</button>
    </div>
</form>
</body>
</html>