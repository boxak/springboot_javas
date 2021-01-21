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
    }

</script>
</body>
</html>