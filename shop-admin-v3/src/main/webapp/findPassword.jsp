<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-9-10
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>找回密码</title>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-success">
                <div class="panel-heading">找回密码</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="form">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control"  placeholder="请输入邮箱查找..." id="email">
                            </div>
                        </div>
                        <div style="text-align: center">
                            <button class="btn btn-primary" type="button" onclick="search()"><i class="glyphicon glyphicon-ok"></i>发送密码到邮箱</button>
                            <button class="btn btn-default" type="reset"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    function search() {
         var email =$("#email").val();
         if(!!email){
             $.ajax({
                 url:"/user/sendPasswordByEmail.jhtml",
                 data:{"email":email},
                 type:"post",
                 success:function (res) {
                     if(res.code==200){
                         bootbox.alert({
                             message: "<span class='glyphicon glyphicon-exclamation-sign'></span>新密码已经发送您的邮箱，请查收！！",
                             size: 'small',
                             title: "提示信息"
                         });
                     }else{
                         bootbox.alert({
                             message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+res.msg,
                             size: 'small',
                             title: "提示信息"
                         });
                     }
                 }

             })
         }
    }
    
</script>

</body>
</html>
