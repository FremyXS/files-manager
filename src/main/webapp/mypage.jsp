<%@ page import="java.util.Locale" %><%--
  Created by IntelliJ IDEA.
  User: ivane
  Date: 02.10.2022
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File manager</title>
    <h1> ${currentTime}</h1>
    <form method="post">
        <input type="submit" name="exitBtn" value="Exit" />
    </form>
    <h1> ${currentPath}</h1>
    <h2>Files</h2>
    <form>${files}</form>
    <h2>Folders</h2>
    <form>${folders}</form>
</head>
<body>
</body>
</html>