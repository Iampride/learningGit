<%--
  Created by IntelliJ IDEA.
  User: nc
  Date: 2019/9/24
  Time: 下午7:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Statement" %>
<html>
<head>
    <title>注册界面02</title>
</head>
<body>
<!--center-->
<form action="doRegister02.jsp" method="post" name="register02" >
    &nbsp; 用户名:&nbsp;<input name="user" type="text" size="40"> <br>
    &nbsp; 密码: &nbsp;&nbsp;&nbsp; <input name="password" type="password" size="40"> <br>
    &nbsp; 性别: &nbsp;&nbsp;&nbsp;
    <select name="sex">
        <option>男</option>
        <option>女</option>
    </select>
    <br><br>
    &nbsp; <input type="submit" name="submit1" value="注册" style="margin-left:150px;"> <br>
</form>
<!--center-->
</body>
</html>
