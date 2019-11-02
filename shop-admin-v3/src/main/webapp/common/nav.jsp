<%@ page import="com.fh.shop.util.DistributedSession" %>
<%@ page import="com.fh.shop.util.RedisUtil" %>
<%@ page import="com.fh.shop.util.KeyUtil" %>
<%@ page import="com.alibaba.fastjson.JSONObject" %>
<%@ page import="com.fh.shop.po.user.User" %>
<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-8-28
  Time: 12:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu > .dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }

    .dropdown-submenu:hover > .dropdown-menu {
        display: block;
    }

    .dropdown-submenu > a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }

    .dropdown-submenu:hover > a:after {
        border-left-color: #fff;
    }

    .dropdown-submenu.pull-left {
        float: none;
    }

    .dropdown-submenu.pull-left > .dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">飞狐电商后台管理</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="menuNav">
            <ul class="nav navbar-nav navbar-right">
                <%
                String sessionId = DistributedSession.getSessionId(request, response);
                    String value = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
                    User users = JSONObject.parseObject(value, User.class);
                %>


                <li><a href="#">欢迎<%=users.getRealName()%>登录 ,今天是第<%=users.getLoginCount()%>次上线</a></li>
            <c:if test="<%=users.getLoginTime()!=null%>">
                    <li><a href="#">你上次登录时间是<fmt:formatDate value="<%=users.getLoginTime()%>"  pattern="yyyy-MM-dd HH:mm:ss" /></a></li>
            </c:if>
                <li><a class="navbar-brand" href="/user/logout.jhtml">退出</a></li>
                <li><a class="navbar-brand" href="/user/topassword.jhtml">修改密码</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<script src="/js/jquery-3.3.1.js"></script>
<script>
    $(function () {
        List();
        $.ajaxSetup({
            complete:function (result) {
                if (result.responseJSON.code) {
                    if (result.responseJSON.code != '200') {
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>" + result.responseJSON.msg,
                            size: 'small',
                            title: "提示信息"
                        });
                    }
                }
            }
        })

    });

    var v_data;
    function List(){
        $.ajax({
            url:"/resource/menu.jhtml",
            type:"post",
            success:function(res) {
                if(res.code==200){
                    v_data = res.data;
                    console.log(v_data)
                    buildHtml(1,1);
                    $("#menuNav").append(v_html);

                }
            }
        })
    }

  var v_html="";
    function buildHtml(id,jj){
        //获取顶级菜单
        var arr = getIds(id);
        if(arr.length>0){
            if(jj==1){
                v_html+='<ul class="nav navbar-nav">';
            }else{
                v_html+='<ul class="dropdown-menu">';
            }

            for (var i=0;i<arr.length ;i++ )
            {
                var flag=childFlag(arr[i].id);
                if(flag && jj==1){
                    v_html+='<li><a href="#" data-toggle="dropdown" >'+arr[i].menuName+'<span class="caret"></span></a>'
                }else{
                    if(flag){
                        v_html+='<li class="dropdown-submenu"><a tabindex="-1" href="#">'+arr[i].menuName+'</a>';
                    }
                   else{
                        v_html+='<li><a href="'+arr[i].menuUrl+'">'+arr[i].menuName+'</a>';
                    }

                }
                buildHtml(arr[i].id,jj+1);
                v_html+="</li>";
            }
            v_html+="</ul>";


        }

    }

    function getIds(id){
        var ids=[];
        for (var i =0 ;i<v_data.length;i++ )
        {
            if(v_data[i].fatherId==id){
                ids.push(v_data[i])
            }
        }
        return ids;
    }


    function childFlag(id){
        for (var i=0;i<v_data.length;i++) {
            if(v_data[i].fatherId==id){
                return true;
            }
        }
        return false;
    }
</script>