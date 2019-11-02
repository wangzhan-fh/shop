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
        <p class=" navbar-brand">飞狐电商后台管理</p>
        <div class="navbar-header" id="menuNav">

            <%-- <a class="navbar-brand" href="/product/toList.jhtml">商品管理</a>
         </div>
         <div class="navbar-header">
             <a class="navbar-brand" href="/brand/toList.jhtml">品牌管理</a>
         </div>
         <div class="navbar-header">
             <ul class="nav navbar-nav">
                 <li class="dropdown">
                     <a href="#" class="dropdown-toggle navbar-brand" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">系统管理<span class="caret"></span></a>
                     <ul class="dropdown-menu">
                         <li><a href="/user/tolist.jhtml">用户管理</a></li>
                         <li role="separator" class="divider"></li>
                         <li><a href="/role/toList.jhtml">角色管理</a></li>
                         <li role="separator" class="divider"></li>
                         <li><a href="/resource/toList.jhtml">菜单管理</a></li>
                         <li role="separator" class="divider"></li>
                     </ul>
                 </li>
             </ul>
         </div>
         <div class="navbar-header">
             <a class="navbar-brand" href="#">分类管理</a>
         </div>
         <div class="navbar-header">
             <a class="navbar-brand" href="#">日志管理</a>
         </div>
         <div class="navbar-header">
             <a class="navbar-brand" href="/area/tolist.jhtml">地区管理</a>--%>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎${userinfo.realName}登录登录 ,今天是第${userinfo.loginCount}次上线</a></li>
                <c:if test="${!empty userinfo.loginTime}">
                    <li><a href=#">你上次登录时间是<fmt:formatDate value="${userinfo.loginTime}" pattern="yyyy-MM-dd HH:mm:ss" /></a></li>
                </c:if>
                <li><a href="/user/logout.jhtml">退出</a></li>
                <li><a href="/user/toUpdatePassword.jhtml">修改密码</a></li>
            </ul>
        </div>
    </div>
</nav>
<script src="/js/jquery-3.3.1.js"></script>

<script>
    $(function(){
        $.ajaxSetup({
            complete:function (result) {
                if(!!result.responseJSON){
                    if(result.responseJSON.code!=200){
                        bootbox.alert({
                            message:result.responseJSON.msg,
                            size:"small",
                            title:"提示信息",
                        })
                    }
                }

            }

        })
        list();
    })

    var v_data;
    function list(){
        $.ajax({
            url:"/resource/menu.jhtml",
            type:"post",
            success:function(res){
                if(res.code==200){
                    v_data=res.data;
                    console.log(v_data)
                    initMenu(1,1);
                    $("#menuNav").append(v_html);
                }
            }
        })
    }
    var v_html="";
    function initMenu(id,num){
        //获取顶吉菜单id
        var childArr = getChild(id);
        if(childArr.length>0){
            if(num==1){
                v_html+='<ul class="nav navbar-nav">';
            }else{
                v_html+=' <ul class="dropdown-menu">';
            }
            for (var i=0;i<childArr.length;i++) {
                var flag= hasChild(childArr[i].id)

                if(flag){
                    if(num==1){
                        v_html+= '<li><a href="#"  data-toggle="dropdown">'+childArr[i].menuName+'<span class="caret"></span></a>';
                    }else {
                        v_html+= '<li class="dropdown-submenu"><a href="'+childArr[i].menuUrl+'">'+childArr[i].menuName+'</a>';
                    }
                }else {
                    v_html+='<li><a href="'+childArr[i].menuUrl+'">'+childArr[i].menuName+'</a>';
                }
                initMenu(childArr[i].id,num+1);
                v_html+='</li>';

            }
            v_html+='</ul>';
        }
    }

    function getChild(id){
        var childId=[];
        for (var i=0;i<v_data.length;i++){
            if(v_data[i].pid==id){
                childId.push(v_data[i]);
            }
        }
        return childId;
    }

    function hasChild(id){
        for (var i=0;i<v_data.length;i++){
            if(v_data[i].pid == id){
                return true;
            }
        }
        return false;
    }

</script>
