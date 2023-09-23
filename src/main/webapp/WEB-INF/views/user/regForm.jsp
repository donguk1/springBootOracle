<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>회원가입 화면</title>
    <link rel="stylesheet" href="/css/table.css">
    <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        // 아이디 중복체크여부(중복 Y, / 중복 아님 N)
        let idCheck = "Y";

        // 이메일 중복체크 인증번호 발송 값
        let emailAuthNumber = "";

        // HTML로딩이 완료되고, 실행
        $(document).ready(function () {

            let f = document.getElementById("f");

            // 아이디 찾기
            $("#btnUserId").on("click", function () {
                idExists(f)
            })

            // 이메일 찾기
            $("#btnEmail").on("click", function () {
                emailExists(f)
            })

            // 우편번호 찾기
            $("#btnAddr").on("click", function () {
                kakaoPost(f)
            })

            // 회원가입
            $("#btnSend").on("click", function () {
                doSubmit(f)
            })
        })

        function idExists(f) {
            if (f.id.value === "") {
                alert("아이디를 입력하세요.")
                f.id.focus();
                return;
            }
            $.ajax({
                url: "/user/getIdExists",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {

                    if (json.existsYn === "Y") {
                        alert("이미 가입된 아이디가 존재합니다.")
                        f.id.focus();
                    } else {
                        alert("가입 가능한 아이디입니다.")
                        idCheck = "N";
                    }
                }
            })
        }

        function emailExists(f) {
            if (f.email.value === "") {
                alert("아이디를 입력하세요.")
                f.email.focus();
                return;
            }
            $.ajax({
                url: "/user/getEmailExists",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {

                    if (json.existsYn === "Y") {
                        alert("이미 가입된 이메일 주소가 존재합니다.")
                        f.email.focus();
                    } else {
                        alert("이메일로 인증번호가 발송되었습니다. \n받은 메일의 인증번호를 입력하기 바랍니다.")
                        emailAuthNumber = json.authNumber;
                    }
                }
            })
        }

        function kakaoPost(f) {
            new daum.Postcode({
                oncomplete: function (data) {
                    /**
                     * kakao에서 제공하는 data는 JSON구조로 주소 조회 결과괎을 전달함
                     * 주요 결과값
                     * 주소 : data.addressq
                     * 우편번호 : data.zonecode
                     */
                    let address = data.address;
                    let zonecode = data.zonecode;
                    f.addr1.value = "(" +zonecode + ")" + address
                }
            }).open();
        }

        function doSubmit(f) {

            if (f.id.value === "") {
                alert("아이디를 입력하세요.")
                f.id.focus();
                return;
            }

            if (idCheck !== "N") {
                alert("아이디 중복 체크 및 중복되지 않은 아이디로 가입 바랍니다.");
                f.id.focus();
                return;
            }

            if (f.name.value === "") {
                alert("이름를 입력하세요.")
                f.name.focus();
                return;
            }

            if (f.pw.value === "") {
                alert("비밀번호를 입력하세요.")
                f.pw.focus();
                return;
            }

            if (f.pw2.value === "") {
                alert("비밀번호 확인을 입력하세요.")
                f.pw2.focus();
                return;
            }

            if (f.pw.value !== f.pw2.value) {
                alert("비밀번호화 비밀번호 확인이 일치하지 않습니다.")
                f.pw.focus();
                return;
            }

            if (f.email.value === "") {
                alert("이메일을 입력하세요.")
                f.email.focus();
                return;
            }

            if (f.authNumber.value === "") {
                alert("이메일 인증번호를 입력하세요.")
                f.authNumber.focus();
                return;
            }

            if (f.authNumber.value != emailAuthNumber) {
                alert("이메일 인증번호가 일치하지 않습니다.")
                f.authNumber.focus();
                return;
            }

            if (f.addr1.value === "") {
                alert("주소를 입력하세요.")
                f.addr1.focus();
                return;
            }

            if (f.addr2.value === "") {
                alert("상세주소를 입력하세요.")
                f.addr2.focus();
                return;
            }

            $.ajax({
                url: "/user/insertUser",
                type: "post",
                dataType: "JSON",
                data: $("#f").serialize(),
                success: function (json) {

                    if (json.result === "1") {
                        alert(json.msg);
                        location.href = "/user/login";

                    } else {
                        alert(json.msg);

                    }
                }
            })
        }

    </script>
</head>
<body>
<h2>회원 가입하기</h2>
<hr>
<br>
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">* 아이디</div>
                <div class="divTableCell">
                    <input type="text" name="id" style="width: 80%" placeholder="아이디"/>
                    <button id="btnUserId" type="button">아이디 중복체크</button>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 이름</div>
                <div class="divTableCell">
                    <input type="text" name="name" style="width:95%" placeholder="이름"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 비밀번호</div>
                <div class="divTableCell">
                    <input type="password" name="pw" style="width:95%" placeholder="비밀번호"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 비밀번호확인</div>
                <div class="divTableCell">
                    <input type="password" name="pw2" style="width:95%" placeholder="비밀번호 확인"/>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 이메일</div>
                <div class="divTableCell">
                    <input type="email" name="email" style="width:40%" placeholder="이메일주소"/>
                    <input type="text" name="authNumber" style="width:30%" placeholder="메일로 발송된 인증번호"/>
                    <button id="btnEmail" type="button">이메일 중복체크</button>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 주소</div>
                <div class="divTableCell">
                    <input type="text" name="addr1" style="width:85%" placeholder="주소"/>
                    <button id="btnAddr" type="button">우편번호</button>
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">* 상세 주소</div>
                <div class="divTableCell">
                    <input type="text" name="addr2" style="width:95%" placeholder="상세주소"/>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnSend" type="button">회원가입</button>
    </div>


</form>
</body>
</html>