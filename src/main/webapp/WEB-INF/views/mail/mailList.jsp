<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.MailDTO" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="java.util.List" %>
<%
    // NoticeController 함수에서 model 객체에 저장된 값 불러오기
    List<MailDTO> rList = (List<MailDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>메일 발송 리스트</title>
    <link rel="stylesheet" href="/css/table.css">
</head>
<body>
<h2>메일 발송 리스트</h2>
<hr/>
<br/>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">발송순번</div>
            <div class="divTableHead">받는사람</div>
            <div class="divTableHead">메일제목</div>
            <div class="divTableHead">메일내용</div>
            <div class="divTableHead">발송시각</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (MailDTO dto : rList) {
        %>
        <div class="divTableRow">

            <div class="divTableCell"><%=CmmUtil.nvl(dto.getMailSeq())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getToMail())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getTitle())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getContents())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getSendTime())%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>
