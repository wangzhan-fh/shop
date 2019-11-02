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
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <p class=" navbar-brand">飞狐电商后台管理</p>
        <div class="navbar-header" id="nav_html">
           <ul class="nav navbar-nav" >
                <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
                <li><a href="#">Link</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">Separated link</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="#">One more separated link</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎${user.realName}登录 ,今天是第${user.loginCount}次上线</a></li>
             <c:if test="${!empty user.loginTime}">
                 <li><a href="#">你上次登录时间是<fmt:formatDate value="${user.loginTime}"  pattern="yyyy-MM-dd HH:mm:ss" /></a></li>
             </c:if>
                <li><a class="navbar-brand" href="/user/logout.jhtml">退出</a></li>
            </ul>
        </div>
    </div>

</nav>
<script src="/js/jquery-3.3.1.js"></script>
<%--
<script>
    $(function () {
        List();
    });
    var v_menuList;
    function List(){
        $.ajax({
            url:"/resource/menu.jhtml",
            type:"post",
            success:function(res) {
                if(res.code==200){
                    v_menuList=res.data;
                    initHtml();
                }
            }
        })
    }
    function initHtml() {
        //获取顶级菜单
        var v_menuHtml=getTopHtml();
        var v_html="<li>";
        for (var i=0;i<v_menuHtml.length;i++) {
            v_html+=' <a href="'+v_menuHtml[i].menuUrl+'">'+v_menuHtml[i].name+'</a>';
        }
        v_html+="</li>"
        $("#nav_html").html(v_html);

        //获取顶级菜单id;
    }
    function getTopHtml(){
        var v_menuHtml=[];
        for (var i=0;i<v_menuList.length;i++) {
            if(v_menuList[i].pId==1){
                v_menuHtml.push(v_menuList[i])
            }
        }
        return v_menuHtml;
    }

</script>--%>
