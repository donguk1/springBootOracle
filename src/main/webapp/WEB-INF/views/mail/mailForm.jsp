<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>메일 작성하기</title>
    <link rel="stylesheet" href="/css/table.css">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // HTML 로딩 완료후 실행
        $(document).ready(function () {

            $("#btnSend").on("click", function (){
                // Ajax 호출해서 글 등록하기
                $.ajax({
                    url: "/mail/sendMail",
                    type: "post",   // 전송 방식
                    dataType: "JSON", // 전송 결과 JSON 수신
                    data: $("#f").serialize(),  // form 태그 내 input 등 객체를 자동으로 전송할 형태 변경
                    success: function (json) {  // /mail/sendMail 호출 성공시
                        alert(json.msg); // 앨럿으로 메시지 띄위기
                    }
                })
            })
        })
    </script>


</head>
<body>
<h2>메일 작성하기</h2>
<hr>
<br>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">받는사람</div>
                <div class="divTableCell"><input type="text" name="toMail" maxlength="100" style="width: 95%">
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">메일제목</div>
                <div class="divTableCell"><input type="text" name="title" maxlength="100" style="width: 95%">
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">메일내용</div>
                <div class="divTableCell"><textarea name="contents" maxlength="100" style="width: 95%; height: 400px"></textarea>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnSend" type="button">메일 발송</button>
        <button type="reset">다시 작성</button>
    </div>
</form>
</body>
</html>