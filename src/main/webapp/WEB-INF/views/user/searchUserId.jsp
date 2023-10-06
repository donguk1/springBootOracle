<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>아이디 찾기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            // 로그인 화면 이동
            $("#btnLogin").on("click", function () {
                location.href = "/user/login";
            })

            // 아이디 찾기
            $("#btnSearchUserId").on("click", function () {
                let f = document.getElementById("f")

                if (f.name.value === "") {
                    alert("이름을 입력하세요.");
                    f.name.focus();
                    return

                }

                if (f.email.value === "") {
                    alert("이메일을 입력하세요.");
                    f.email.focus();
                    return

                }

                f.method = "POST";
                f.action = "/user/searchUserIdProc";

                f.submit();
            })
        })
    </script>
</head>
<body>
<h2>아이디 찾기</h2>
<hr>
<br>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">이름</div>
                <div class="divTableCell">
                    <input type="text" name="name" id="name" style="width:95%"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">이메일</div>
                <div class="divTableCell">
                    <input type="email" name="email" id="email" style="width:95%"/>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnSearchUserId" type="button">아이디 찾기</button>
        <button id="btnLogin" type="button">로그인</button>
    </div>
</form>

</body>
