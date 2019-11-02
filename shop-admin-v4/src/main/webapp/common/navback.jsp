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
        <div class="navbar-header">
            <ul class="nav navbar-nav" id="nav">


            </ul>
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
            </ul>
        </div>
    </div>
</nav>
<script src="/js/jquery-3.3.1.js"></script>

<script>
    $(function(){
        list();
    })

    var menuArr;
    function list(){
        $.ajax({
            url:"/resource/menu.jhtml",
            type:"post",
            success:function(res){
                if(res.code==200){
                    menuArr=res.data;
                    initMenu();
                }
            }
        })
    }
    function initMenu(){
        //获取顶级菜单
        var v_getTopHTML= getTopHtml();
        console.log(v_getTopHTML)
        var getObj=$(v_getTopHTML);
        //获取顶级菜单的id
        var v_getTopHTMLIds= getTopHtmlIds();
        for (var i=0;i<v_getTopHTMLIds.length;i++) {
            var childs=getChilds(v_getTopHTMLIds[i])
            if(childs.length>0){
                var href= getObj.find("a[data-id='"+menuArr[i].id+"']");
                href.attr("data-toggle","dropdown");
                href.append('<span class="caret"></span>')
                var chuldHtml=getchildHtml(childs);
                href.parent().append(chuldHtml)
            }
        }
        $("#nav").html(getObj);

    }

    function  getTopHtml(){
        var topHtml="";
        for (var i=0;i<menuArr.length;i++) {
            if(menuArr[i].pid==1){
                topHtml+='<li><a href="'+menuArr[i].menuUrl+'" data-id="'+menuArr[i].id+'">'+menuArr[i].menuName+'</a></li>';
            }
        }
        return topHtml;
    }

    function getTopHtmlIds(){
        var menuids=[];
        for(var i=0; i<menuArr.length;i++){
            menuids.push(menuArr[i].id)
        }
        return menuids;
    }

    function  getChilds(id){
        var ids=[];
        for(var i=0;i<menuArr.length;i++){
            if(menuArr[i].pid==id){
                ids.push(menuArr[i]);
            }
        }
        return ids;
    }

    function getchildHtml(childs){
        var aa="<ul class=\"dropdown-menu\">";
        for (var i=0;i<childs.length;i++){
            aa+='<li><a href="'+childs[i].menuUrl+'">'+childs[i].menuName+'</a></li>'
        }
        aa+="</ul>";
        return aa;
    }

</script>