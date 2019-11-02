<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-9-2
  Time: 23:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>你没权限访问</title>

</head>
<body background="/img/error.jpg">
<jsp:include page="/common/nav.jsp"></jsp:include>
<h1 style="text-align: center;color: #080808">你没权限访问,请联系管理员。</h1>
<jsp:include page="/common/script.jsp"></jsp:include>
</body>
</html>
