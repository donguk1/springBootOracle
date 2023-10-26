<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.OcrDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    // userController 함수에서 model 객체에 저장된 값 불러오기
    List<OcrDTO> rList = (List<OcrDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ocr 리스트</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript">

        //상세보기 이동
        function doDetail(seq) {
            location.href = "/ocr/ocrInfo?seq=" + seq;
        }

    </script>
</head>
<body>
<h2>Ocr 리스트</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">순번</div>
            <div class="divTableHead">회원아이디</div>
            <div class="divTableHead">파일명</div>
            <div class="divTableHead">작성일</div>
            <div class="divTableHead">크기</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (OcrDTO dto : rList) {
        %>
        <div class="divTableRow" onclick="doDetail('<%=CmmUtil.nvl(dto.getSeq())%>')">
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getSeq())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getRegId())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getOrgFileName())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getRegDt())%></div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getFileSize() + "byte")%></div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
