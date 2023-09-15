<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

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
    <title>유저 상세보기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script>
        // 현재 유저 정보 id
        const id = "<%=CmmUtil.nvl(rDTO.getId())%>";
        

        // HTML로딩이 완료되고, 실행됨
        $(document).ready(function () {
            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnEdit").on("click", function () {
                doEdit(); // 공지사항 수정하기 실행
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnDelete").on("click", function () {
                doDelete(); // 공지사항 수정하기 실행
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnDelete").on("click", function () {
                location.href = "/user/userList"; // 공지사항 리스트 이동
            })
        })

    </script>
</head>
<body>
<h2>유저정보 상세보기</h2>
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
    <button id="btnDelete" type="button">삭제</button>
    <button id="btnList" type="button">목록</button>
</div>
</body>
</html>

