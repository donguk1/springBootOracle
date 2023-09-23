<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ssId = CmmUtil.nvl((String) session.getAttribute("SS_ID"));
    String ssName = CmmUtil.nvl((String) session.getAttribute("SS_NAME"));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인 성공</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML 로딩이 완료되고, 실행됨
        $(document).ready(function () {

            $("#btnSend").on("click", function () {
                location.href = "/html/index.html";
            })

            $("#btnLoginInfo").on("click", function () {
                location.href = "/user/loginInfo";
            })
        })
    </script>
</head>
<body>
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자이름</div>
            <div class="divTableCell"><%=ssName%> 님이 로그인하였습니다.</div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자아이디</div>
            <div class="divTableCell"><%=ssId%> 입니다.</div>
        </div>
    </div>
</div>
<div></div>
<br><br>
<button id="btnSend" type="button">메인 화면 이동</button>
<button id="btnLoginInfo" type="button" >로그인 정보 이동</button>
</body>