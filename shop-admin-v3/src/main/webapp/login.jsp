<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/8/29
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zxx">
<head>
    <title>用户登录页面</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta charset="utf-8">
    <meta name="keywords" content="Key Login Form a Responsive Web Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible Web Template, Smartphone Compatible Web Template, Free Webdesigns for Nokia, Samsung, LG, Sony Ericsson, Motorola Web Design">
    <link rel="stylesheet" href="/js/bootstrap3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/js/DataTables/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" href="/js/login/css/style.css" type="text/css" media="all">
    <link rel="stylesheet" href="/js/login/css/font-awesome.min.css" type="text/css" media="all">
    <link href="//fonts.googleapis.com/css?family=Quattrocento+Sans:400,400i,700,700i" rel="stylesheet">
    <link href="//fonts.googleapis.com/css?family=Mukta:200,300,400,500,600,700,800" rel="stylesheet">
</head>
<body>
<script src="/js/jquery-3.3.1.js"></script>
<script src="/js/DataTables/js/jquery.dataTables.min.js"></script>
<script src="/js/DataTables/js/dataTables.bootstrap.min.js"></script>
<script src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="/js/bootbox/bootbox.locales.min.js"></script>

<script>
    addEventListener("load", function () {
        setTimeout(hideURLbar, 0);
    }, false);

    function hideURLbar() {
        window.scrollTo(0, 1);
    }

    function login_user() {
        var userName = $("#userName").val();
        var password = $("#password").val();
        var code = $("#code").val();
        if(userName == "" || password == "" || code=="" ){
            bootbox.alert("验证码用户名或密码不能为空");
            return false;
        }
        $.post({
            url: "/user/login.jhtml",
            data:{
                "userName":userName,
                "password":password,
                "code":code
            },
            dataType: "json",
            success: function (result) {
                if(result.code==200){
                    location.href="/index.jhtml";
                } else {
                    bootbox.alert({
                        message: result.msg,
                        size: 'small'
                    })
                }
            }
        })
    }

    function reloadImg(){
        var t =new Date().getTime();
        document.getElementById("img").src="/img/code?"+t;
    }
</script>
<section class="main">
    <div class="layer">
        <div class="bottom-grid">
            <div class="logo">
                <h1> <a href="#"><span class="fa fa-key"></span>飞狐教育</a></h1>
            </div>

        </div>
        <div class="content-w3ls">
            <div class="text-center icon">
                <span class="fa">后台管理系统</span>
            </div>
            <div class="content-bottom">
                <form action="#" method="post">
                    <div class="field-group">
                        <span class="fa fa-user" aria-hidden="true"></span>
                        <div class="wthree-field">
                            <input  id="userName" type="text" autocomplete="off" placeholder="请输入用户名">
                        </div>
                    </div>
                    <div class="field-group">
                        <span class="fa fa-lock" aria-hidden="true"></span>
                        <div class="wthree-field">
                            <input id="password" type="Password" placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="wthree-field">

                    </div>
                    <div class="field-group">
                        <span class="fa fa-user" aria-hidden="true"></span>
                        <div class="wthree-field">
                            <input id="code" type="text"  autocomplete="off" placeholder="请输入验证码">
                        </div>
                    </div>
                    <div>
                            <img id="img" src="/img/code">
                            <a href="#" onclick="reloadImg()"><font color="red">看不清换一张</font></a>
                    </div>
                    <div class="wthree-field">
                        <button type="button" class="btn" onclick="login_user()">登录</button>
                      <a style="color: #af0000" href="findPassword.jsp">找回密码？</a>
                    </div>
                </form>
            </div>
        </div>
        <div class="bottom-grid1">
            <div class="links">
                <ul class="links-unordered-list">
                    <li class="">
                        <a href="#" class="">一入Java深似海</a>
                    </li>
                    <li class="">
                        <a href="#" class="">海量头发满地飘</a>
                    </li>
                    <li class="">
                        <a href="#" class="">一飘飘到地中海</a>
                    </li>
                    <li class="">
                        <a href="#" class="">来到飞狐</a>
                    </li>
                    <li class="">
                        <a href="#" class="">头发秃秃</a>
                    </li>
                </ul>
            </div>

        </div>
    </div>

</section>
</body>
</html>
