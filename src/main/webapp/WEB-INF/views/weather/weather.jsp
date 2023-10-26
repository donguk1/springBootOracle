<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ page import="kopo.poly.dto.FoodDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    String msg = CmmUtil.nvl((String) request.getAttribute("msg"));
%>
<!DOCTYPE>
<html>
<head>
    <meta charset="UTF-8">
    <title>날씨</title>
    <link rel="stylesheet" href="/css/table.css">
</head>
<body>
<h2>날씨</h2>
<hr>
<br>
<%=msg%>
</body>
</html>