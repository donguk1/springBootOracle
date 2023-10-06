<%@ page import="kopo.poly.dto.UserDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    UserDTO rDTO = (UserDTO) request.getAttribute("rDTO");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>비밀번호 찾기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            let f = document.getElementById("f")

            // 이메일 중복체크 인증번호 발송 값
            let emailAuthNumber = "";

            // 로그인 화면 이동
            $("#btnLogin").on("click", function () {
                location.href = "/user/login";
            })

            // 이메일 찾기
            $("#btnEmail").on("click", function () {
                emailExists(f)
            })

            $("#btnEmailCheck").on("click", function () {
                emailCheck(f)
            })

            // 아이디 찾기
            $("#btnSearchPassword").on("click", function () {
                let f = document.getElementById("f")

                if (f.id.value === "") {
                    alert("아이디를 입력하세요.");
                    f.id.focus();
                    return

                }

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
                f.action = "/user/searchPasswordProc";

                f.submit();
            })

            function emailCheck(f) {
                if (f.authNumber.value != emailAuthNumber) {
                    alert("이메일 인증번호가 일치하지 않습니다.")
                    f.authNumber.focus();
                    return;

                } else {
                    alert("확인되었습니다.")
                }
            }

            function emailExists(f) {
                if (f.email.value === "") {
                    alert("이메일을 입력하세요.")
                    f.email.focus();
                    return;
                }
                $.ajax({
                    url: "/user/updatePwEmail",
                    type: "post",
                    dataType: "JSON",
                    data: $("#f").serialize(),
                    success: function (json) {

                        if (json.existsYn === "N") {
                            alert("가입한 이메일이 없습니다.")
                            f.email.focus();
                        } else {
                            alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.")
                            emailAuthNumber = json.authNumber;
                        }
                    }
                })
            }



        })
    </script>
</head>
<body>
<h2>비밀번호 찾기</h2>
<hr>
<br>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">아이디</div>
                <div class="divTableCell">
                    <input type="text" name="id" id="id" style="width:95%"/>
                </div>
            </div>
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
                    <button id="btnEmail" type="button">인증번호 발송</button>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">인증번호</div>
                <div class="divTableCell">
                    <input type="text" name="authNumber" style="width:30%" placeholder="메일로 발송된 인증번호"/>
                    <button id="btnEmailCheck" type="button">인증번호 확인</button>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnSearchPassword" type="button">비밀번호 찾기</button>
        <button id="btnLogin" type="button">로그인</button>
    </div>
</form>

</body>

