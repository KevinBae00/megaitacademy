<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-07-12
  Time: AM 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<center>
    <h1>로그인</h1>
    <hr>
    <form action="login.do" method="post">
        <table border="1" cellpadding="0" cellspacing="0">
            <tr>
                <td bgcolor="orange">아이디</td>
                <td><input type="text" name="id" value="${userVO.id}"/>}</td>
            </tr>
            <tr>
                <td bgcolor="orange">비밀번호</td>
                <td><input type="password" name="password" value="${userVO.password}"/></td>
            </tr>
            <tr>
                <td colspan="2" align="center"><input type="submit"
                                                      value="로그인"/></td>
            </tr>
        </table>
    </form>
    <hr>
</center>
</body>
</html>
