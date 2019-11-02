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
    <title>修改密码页面</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-success">
                <div class="panel-heading">修改密码</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="form">
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">原密码</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control"  placeholder="请输入原密码..." id="oldPassword">
                            </div>
                        </div>
                        <div class="form-group">
                            <label  class="col-sm-2 control-label">新密码</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control"  placeholder="请输入新密码..." id="newPassword">
                            </div>
                        </div>
                            <div class="form-group">
                            <label  class="col-sm-2 control-label">确认新密码</label>
                            <div class="col-sm-4">
                                <input type="password" class="form-control"  placeholder="请输入确认新密码..." id="confirmPassword" >
                            </div>
                        </div>
                        <div style="text-align: center">
                            <button class="btn btn-primary" type="button" onclick="updatePassword()"><i class="glyphicon glyphicon-ok"></i>修改</button>
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
    function updatePassword() {
        bootbox.confirm({
            message: "确认要修改吗?",
            size:"small",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-success'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if(result){
                    var oldPassword=$("#oldPassword").val();
                    var newPassword=$("#newPassword").val();
                    var confirmPassword=$("#confirmPassword").val();
                    var userId= '${userinfo.id}';
                    if(!oldPassword && !newPassword && !confirmPassword){
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>密码不允许为空！请填写！",
                            size: 'small',
                            title: "提示信息"
                        });
                    }else if(oldPassword==newPassword){
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>新密码与原密码一致！请修改！",
                            size: 'small',
                            title: "提示信息"
                        });
                    }else if(newPassword!=confirmPassword){
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>新密码与确认密码不一致！请修改！",
                            size: 'small',
                            title: "提示信息"
                        });
                    }else{
                    $.ajax({
                        url:"/user/updatePassword.jhtml",
                        data:{
                            "oldPassword":oldPassword,
                            "newPassword":newPassword,
                            "confirmPassword":confirmPassword,
                            "userId":userId,
                        },
                        type:"post",
                        success:function(res){
                            if(res.code=="200"){
                                bootbox.alert({
                                    message: "<span class='glyphicon glyphicon-exclamation-sign'></span>修改密码成功请重新登录！！",
                                    size: 'small',
                                    title: "提示信息"
                                });

                            }
                        }
                    })
                }
            }
            }
        });
    }
    
</script>

</body>
</html>
