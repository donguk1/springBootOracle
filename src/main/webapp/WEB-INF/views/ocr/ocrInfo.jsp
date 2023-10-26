<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.OcrDTO" %>
<%
    // NoticeController 함수에서 model 객체에 저장된 값 불러오기
    OcrDTO rDTO = (OcrDTO) request.getAttribute("rDTO");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Ocr 상세보기</title>
    <link rel="stylesheet" href="/css/table.css"/>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script>
        // Controller에서 받은 세션에 저장된 값
        const session_user_id = "<%=CmmUtil.nvl((String)session.getAttribute("SESSION_USER_ID"))%>";

        // 공지사항 게시글 작성자 아이디
        const user_id = "<%=CmmUtil.nvl(rDTO.getRegId())%>";

        // 현재 글번호, 자바에서 받을 변수들은 자바스크립트 변수로 저장하면 편함
        const seq = "<%=CmmUtil.nvl(rDTO.getSeq())%>";

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
                location.href = "/ocr/ocrList"; // 공지사항 리스트 이동
            })

            // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
            $("#btnDown").on("click", function (seq) {
                location.href = "/ocr/download?seq=" + "<%=CmmUtil.nvl(rDTO.getSeq())%>" ; // 공지사항 리스트 이동
            })
        })



        //수정하기
        function doEdit() {
            if (session_user_id === user_id) {
                location.href = "/ocr/ocrEditInfo?seq=" + seq;

            } else if (session_user_id === "") {
                alert("로그인 하시길 바랍니다.");

            } else {
                alert("본인이 작성한 글만 수정 가능합니다.");

            }
        }

    </script>
</head>
<body>
<h2>Ocr 상세보기</h2>
<hr/>
<br/>
<input type="hidden" name="seq" value="<%=CmmUtil.nvl(rDTO.getSeq())%>">
<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">파일명
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getOrgFileName())%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">작성일
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getRegDt())%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">용량
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getFileSize())%>
            </div>
        </div>
        <div class="divTableRow">
            <div class="divTableCell">내용
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(rDTO.getOcrText())%>
            </div>
        </div>
    </div>
</div>
<div>
    <button id="btnEdit" type="button">수정</button>
    <button id="btnDelete" type="button">삭제</button>
    <button id="btnList" type="button">목록</button>
    <button id="btnDown" type="button">다운로드</button>
</div>
</body>
</html>

