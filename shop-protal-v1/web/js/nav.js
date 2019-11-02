var loginFlag=false;

var loginDivHTML = '<div type="centen">\n' +
    '    <!-- Nav tabs -->\n' +
    '    <ul class="nav nav-tabs" role="tablist">\n' +
    '        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">账号登陆</a></li>\n' +
    '        <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">免密登陆</a></li>\n' +
    '    </ul>\n' +
    '\n' +
    '    <!-- Tab panes -->\n' +
    '    <div class="tab-content">\n' +
    '        <div role="tabpanel" class="tab-pane active" id="home">\n' +
    '            账号：<input type="text"class="form-control" id="userName" >\n' +
    '            密码：<input type="password" class="form-control"id="password">\n' +
    '        </div>\n' +
    '        <div role="tabpanel" class="tab-pane" id="profile">\n' +
    '            手机号：<input id="phone" class="form-control"><br>\n' +
    '            验证码：<input id="code" class="form-control">\n' +
    '            <button type="button" class="btn btn-info" id="sendcode" onclick="yanzhen()">发送验证码</button>\n' +
    '            <br>\n' +
    '            <input type="button" value="免密登陆"  class="btn btn-primary" onclick="login2()" >\n' +
    '            <input type="reset" value="重置" class="btn btn-primary >\n' +
    '        </div>\n' +
    '    </div>\n' +
    '</div>\n';
$(function(){
    $.ajaxSetup({
        beforeSend:function(xhr){
            var header=$.cookie("fh_auth");
            xhr.setRequestHeader("x_auth",header);
        }
    })

    var v_navDiv='<nav class="navbar navbar-inverse">\n' +
        '    <div class="container-fluid">\n' +
        '        <p class=" navbar-brand">飞狐电商</p>\n' +
        '        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">\n' +
        '            <ul class="nav navbar-nav navbar-right">\n' +
        '                <li id="loginDiv"><a href="#" onclick="login_user()">登陆</a></li>\n' +
        '                <li><a href="zhuce.html">注册</a></li><li><a href="/cart.html">我的购物车</a></li>\n' +
        '                <li><a href="index.html"><font size="3">首页</font> </a></li>\n'+
        '            </ul>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '\n' +
        '</nav>'

    $("#navDiv").html(v_navDiv);


    //登录展示
    $.ajax({
        type:"get",
        url:"http://localhost:8081/members/findMember",
        async:false,
        success:function (res) {
            if(res.code==200){
                loginFlag=true;
                $("#loginDiv").html("<a href='#'  onclick='loginOut()'><font color='red'size='3'>欢迎"+res.data+"登陆成功</font>退出</a>")
            }
        }
    })
})

//退出
function loginOut(){
    $.ajax({
        type:"get",
        url:"http://localhost:8081/members/loginOut",

        success:function (res) {
            if(res.code==200) {
                $.removeCookie("fh_auth");
                location.href="/"
            }else{
                alert(res.msg)
            }
        }
    })
}

//购买
function byProduct(id,action,flag){
    var count;

    if(action=='jian'){
        count=-1;
    }else {
        count=1;
    }
        if(loginFlag) {
        if(flag==1){
            $.ajax({
                type: "post",
                url: "http://localhost:8081/carts/add",
                data: {productId: id, count: count},
                success: function (res) {
                    if (res.code == 200) {
                        findCrat()
                    }
                }
            })
        }else {
            $.ajax({
            type: "post",
            url: "http://localhost:8081/carts/add",
            data: {productId: id, count: count},
            success: function (res) {
                if (res.code == 200) {
                    location.href = "cart.html";
                }
            }
        })
        }

    }else if(!loginFlag){
            login_user(id);
        }



}

//登录
function login_user(id) {
    bootbox.dialog({
        title: '用户登录',
        message: loginDivHTML,
        backdrop:false,
        buttons: {
            cancel: {
                label: "关闭",
                className: 'btn-danger',
            },
            confirm: {
                label: "确定",
                className: 'btn-primary',
                callback: function () {
                    var userName = $("#userName").val();
                    var password = $("#password").val();
                    if(userName == "" || password == ""){
                        bootbox.alert("用户名或密码不能为空");
                        return false;
                    }
                    $.post({
                        url: "http://localhost:8081/members/login",
                        data:{
                            "memberName":userName,
                            "password":password
                        },
                        type:"post",
                        success: function (result) {
                            if(result.code==200){
                                var data = result.data;
                                $.cookie("fh_auth",data);
                                if(id){
                                    $.ajax({
                                        type: "post",
                                        url: "http://localhost:8081/carts/add",
                                        data: {productId: id, count: 1},
                                        success: function (res) {
                                            if (res.code == 200) {
                                                location.href = "cart.html";
                                            }
                                        }
                                    })
                                }else{
                                    location.href = "cart.html";
                                }

                            } else {
                                alert(result.msg)
                            }
                        }
                    })
                }
            }
        }
    });
}


//验证码
function yanzhen() {
    //再走ajax
    var phone = $("#phone").val();
    $.ajax({
        url:"http://127.0.0.1:8081/sms?phone="+phone,
        type: "get",
        success:function (res) {
            if(res.code==200){
                bootbox.alert({
                    message: "<span class='glyphicon glyphicon-exclamation-sign'></span>发送成功",
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
//登陆=============================================
function login2() {
    var phone = $("#phone").val();
    var code = $("#code").val();

    $.ajax({
        type:"post",
        url: "http://localhost:8081/members/login2",
        data:{"phone":phone,"phoneCode":code},
        success:function (result) {
            if (result.code == 200){
                var data = result.data;
                $.cookie("fh_auth",data);
                //登陆成功
                location.href = "cart.html";
            } else {
                alert(result.msg)
            }
        }
    })

}
