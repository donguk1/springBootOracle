<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.UserDTO" %>
<%@ page import="kopo.poly.util.EncryptUtil" %>
<%
    // userController 함수에서 model 객체에 저장된 값 불러오기
    List<UserDTO> rList = (List<UserDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>유저 리스트</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript">

        //상세보기 이동
        function doDetail(id) {
            location.href = "/user/userInfo?id=" + id;
        }

    </script>
</head>
<body>
<h2>유저 리스트</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순번</div>
            <div class="divTableHead">회원아이디</div>
            <div class="divTableHead">이름</div>
            <div class="divTableHead">이메일</div>
            <div class="divTableHead">주소</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (UserDTO dto : rList) {
        %>
        <div class="divTableRow" onclick="doDetail('<%=CmmUtil.nvl(dto.getId())%>')">
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getUserSeq())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getId())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getName())%></div>
            <div class="divTableCell"><%=EncryptUtil.decAES128CBC(CmmUtil.nvl(dto.getEmail()))%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getAddr1() + " " + dto.getAddr2())%></div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
