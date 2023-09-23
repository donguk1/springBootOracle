<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.UserDTO" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%
    // userController 함수에서 model 객체에 저장된 값 불러오기
    UserDTO rDTO = (UserDTO) request.getAttribute("rDTO");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>로그인된 유저정보</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script>

        const id = "<%=CmmUtil.nvl(rDTO.getId())%>"

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {

            if (id === "") {
                alert("로그인 정보가 없습니다. \n로그인하세요");
                location.href = "/user/login";
            }

        })
    </script>
</head>
<body>
<h2>로그인된 유저정보</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">회원 아이디</div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getId())%></div>
        </div>
        <div class="divTableRow"></div>
        <div class="divTableRow">
            <div class="divTableCell">이름</div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getName())%></div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">이메일</div>
            <div class="divTableCell"><%=EncryptUtil.decAES128CBC(CmmUtil.nvl(rDTO.getEmail()))%></div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">주소</div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getAddr1() + " " + rDTO.getAddr2())%></div>
        </div>
    </div>
</div>
<div>
</div>
</body>
</html>

