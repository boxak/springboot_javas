<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>notice Result</title>
</head>
<body>
<script>
    var msg = "${ msg }";
    var type = "${ type }"

    if (type == "unauthorized") {
        alert("접근권한이 없습니다.");
        window.location.href = document.referrer;
    } else if (type == "notLogin") {
        alert("로그인 하세요!");
        window.location.href = "/javas/";
    } else if (type== "alreadyLogin") {
        alert("현재 로그인 되어있습니다. 로그아웃 해주세요.");
        window.location.href = "/javas/"
    } else if (type == "loginError") {
        alert(msg);
        window.location.href = "/javas/login/form";
    } else if (type == "logoutSuccess") {
        alert("로그아웃 성공!");
        window.location.href = "/javas/main";
    } else if (type == "joinSuccess") {
        alert("회원가입 되었습니다.");
        window.location.href = "/javas/main";
    } else if (type == "joinFail") {
        alert("Error : 회원가입 실패했습니다.");
        window.location.href = document.referrer;
    } else if (type == "exitSuccess") {
        alert("회원 탈퇴 성공했습니다.");
        window.location.href = "/javas/main";
    } else if (type == "exitFail") {
        alert("Error : 회원 탈퇴에 실패했습니다.");
        window.location.href = document.referrer;
    } else if (type == "noName") {
        alert("해당하는 이름이 없습니다!");
        window.location.href = document.referrer;
    } else if (type == "notMatched") {
        alert("이름과 휴대폰 번호가 일치하지 않습니다!");
        window.location.href = document.referrer;
    } else if (type == "findID") {
        alert(msg);
        window.location.href = "/javas/login/form";
    } else if (type == "findPW") {
        alert(msg);
        window.location.href = "/javas/login/form";
    } else if (type == "noData") {
        alert(msg);
        window.location.href = "/javas/login/form";
    } else if (type == "meminfoupdate") {
        alert("수정에 성공했습니다.");
        window.location.href = "/javas/mypage";
    } else if (type == "modifyError") {
        alert("수정에 실패했습니다!");
        window.location.href = "/javas/mypage";
    }
    else {
        alert(msg);
        window.location.href = "/javas/board/${ boardType }";
    }

</script>
</body>
</html>