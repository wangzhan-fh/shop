<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-9-2
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>飞狐电商管理平台</title>
</head>
<jsp:include page="/common/head.jsp"></jsp:include>
<jsp:include page="/common/script.jsp"></jsp:include>
<style type="text/css">
    body{
        background: url("${pageContext.request.contextPath}/img/1.jpg");
        animation-name:myfirst;
        background-size: 100%;
        animation-duration:20s;
        /*变换时间*/
        animation-delay:5s;
        /*动画开始时间*/
        animation-iteration-count:infinite;
        /*下一周期循环播放*/
        animation-play-state:running;
        /*动画开始运行*/
    }
    @keyframes myfirst
    {
        0%   {background:url("${pageContext.request.contextPath}/img/1.jpg");background-size: 100%;}
        34%  {background:url("${pageContext.request.contextPath}/img/2.jpg");background-size: 100%;}
        67%  {background:url("${pageContext.request.contextPath}/img/3.jpg");background-size: 100%}
        100% {background:url("${pageContext.request.contextPath}/img/4.jpg");background-size: 100%}
    }
    .form{background: rgba(255,255,255,0.2);width:400px;margin:120px auto;}
    /*阴影*/
    .fa{display: inline-block;top: 27px;left: 6px;position: relative;color: #ccc;}
    input[type="text"],input[type="password"]{padding-left:26px;}
    .checkbox{padding-left:21px;}
</style>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<h1 style="text-align:center; color: #af0000">飞狐电商管理平台</h1>

</body>
</html>
