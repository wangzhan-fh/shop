<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-8-14
  Time: 20:59
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>用户展示页面</title>

</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-success">
                    <div class="panel-heading">用户查询</div>
                    <div class="panel-body">
                        <form class="form-horizontal">
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">用户名</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control"  placeholder="请输入..." id="userName" name="username">
                                </div>
                                <label  class="col-sm-2 control-label">真实姓名</label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control"  placeholder="请输入..." id="realName">
                                </div>
                            </div>

                            <div class="form-group">
                                <label  class="col-sm-2 control-label">年龄区间</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="最小年龄..." id="minAge" name="minAge">
                                        <span class="input-group-addon" ><i class="glyphicon glyphicon-resize-horizontal"></i></span>
                                        <input type="text" class="form-control" placeholder="最大年龄..." id="maxAge" name="maxAge">
                                    </div>
                                </div>
                                <label  class="col-sm-2 control-label">薪资区间</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="最低工资..." id="minPrice" name="minPrice">
                                        <span class="input-group-addon" ><i class="glyphicon glyphicon-yen"></i></span>
                                        <input type="text" class="form-control" placeholder="最高工资..." id="maxPrice" name="maxPrice">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label  class="col-sm-2 control-label">入职日期</label>
                                <div class="col-sm-4">
                                    <div class="input-group">
                                        <input type="text" class="form-control" placeholder="开始时间..." id="begin">
                                        <span class="input-group-addon" ><i class="glyphicon glyphicon-calendar"></i></span>
                                        <input type="text" class="form-control" placeholder="结束时间..." id="end">
                                    </div>
                                </div>
                                <label  class="col-sm-2 control-label">角色信息</label>
                                <div class="col-sm-4">
                                    <div  id="search_box">

                                    </div>
                                </div>
                            </div>
                            <div style="text-align: center">
                                <button class="btn btn-primary" type="button" onclick="search()"><i class="glyphicon glyphicon-search"></i>查询</button>
                                <button class="btn btn-default" type="reset"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <div style="width:100%;background-color: #979797">
            <button class="btn btn-primary" type="button" onclick="add()"><i class="glyphicon glyphicon-plus"></i>新增</button>
            <button class="btn btn-danger" type="button" onclick="deleteUserByIds()"><i class="glyphicon glyphicon-trash"></i>批量删除</button>
        </div>
        <div class="row">
            <div class="col-md-12">
                <div class="panel panel-success">
                    <div class="panel-heading">用户列表</div>
                        <table id="userList" class="table table-striped table-bordered" style="width:100%">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>用户名</th>
                                <th>真实姓名</th>
                                <th>年龄</th>
                                <th>性别</th>
                                <th>电话</th>
                                <th>薪资</th>
                                <th>入职时间</th>
                                <th>email</th>
                                <th>用户角色</th>
                                <th>头像</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tfoot>
                            <tr>
                                <th>ID</th>
                                <th>用户名</th>
                                <th>真实姓名</th>
                                <th>年龄</th>
                                <th>性别</th>
                                <th>电话</th>
                                <th>薪资</th>
                                <th>入职时间</th>
                                <th>email</th>
                                <th>用户角色</th>
                                <th>头像</th>
                                <th>状态</th>
                                <th>操作</th>

                            </tr>
                            </tfoot>
                        </table>
                </div>
            </div>
        </div>

    </div>


<div id="add-html" style="display: none">
<form class="form-horizontal" id="add-form">
    <div class="form-group">
        <label  class="col-sm-2 control-label">用户名</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入..." id="add_userName" name="username">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">真实姓名</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入..." id="add_realName">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">年龄</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入..." id="add_age"  name="age">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">密码</label>
        <div class="col-sm-5">
            <input type="password" class="form-control"  placeholder="请输入..." id="add_password" name="password">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">确认密码</label>
        <div class="col-sm-5">
            <input type="password" class="form-control"  placeholder="请输入..." id="add_confirmpassword" name="qrpassword">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">电话</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入..." id="add_phone" name="phones">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">email</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入..." id="add_email"  name="email">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">薪资</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入薪资..." id="add_pay"  name="pay">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">入职时间</label>
        <div class="col-sm-5">
            <input type="text" class="form-control"  placeholder="请输入入职时间..." id="add_entryTime">
        </div>
    </div>
    <div class="form-group">
        <label  class="col-sm-2 control-label">性别</label>
        <div class="col-sm-5">
            <input type="radio"  name="add_sex" value="0"> 女
            <input type="radio"  name="add_sex"  value="1"> 男
        </div>
    </div>
    <div class="form-group" >
        <label  class="col-sm-2 control-label">用户角色</label>
        <div class="col-sm-5" id="add_box">

        </div>
    </div>
    <div class="form-group" >
        <label  class="col-sm-2 control-label">个人头像</label>
        <div class="col-sm-5" >
            <input id="add_inputFile" type="file" class="file-loading required" data-preview-file-type="text" name=" myfile"  class="input-xlarge"   >
            <input type="hidden" class="form-control"  id="add_photo">

        </div>
    </div>


</form>
</div>

<div id="update-html" style="display: none">
    <form class="form-horizontal" id="update-form">
        <input type="hidden" id="update_id">
        <div class="form-group">
            <label  class="col-sm-2 control-label">用户名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入用户名..." id="update_userName" name="username">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">真实姓名</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入真实姓名..." id="update_realName">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">年龄</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入年龄..." id="update_age" name="updateage">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">密码</label>
            <div class="col-sm-4">
                <input type="password" class="form-control"  placeholder="请输入密码..." id="update_password" >
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">电话</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入电话..." id="update_phone" name="updatephones">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">email</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入email..." id="update_email" name="updateemail">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">薪资</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入薪资..." id="update_pay" name="updatepay">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">入职时间</label>
            <div class="col-sm-4">
                <input type="text" class="form-control"  placeholder="请输入入职时间..." id="update_entryTime">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">性别</label>
            <div class="col-sm-4">
                <input type="radio"  name="update_sex" value="0"> 女
                <input type="radio"  name="update_sex"  value="1"> 男
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">入职时间</label>
            <div class="col-sm-4" id="update_box">
            </div>
        </div>
        <div class="form-group" >
            <label  class="col-sm-2 control-label">个人头像</label>
            <div class="col-sm-5" >
                <img src="" id="img" width="100px">
                <input id="update_inputFile" type="file" class="file-loading required" data-preview-file-type="text" name=" myfile"  class="input-xlarge"   >
                <input type="hidden" class="form-control"  id="update_photo">

            </div>
        </div>

    </form>
</div>
<jsp:include page="/common/script.jsp"></jsp:include>


<script>


    var addUserDiv;
    var updateUserDiv;
    $(function() {
        findRoleList("add_box","add");
        findRoleList("update_box","update");
        findRoleList("search_box","searchs");
        findList();
        addupdate("add_entryTime");
        addupdate("update_entryTime");
        initDate();
        addUserDiv=$("#add-html").html();
        updateUserDiv=$("#update-html").html();
        upload("add_inputFile","add_photo");
        upload("update_inputFile","update_photo");
    } );

    function initDate(){
        $("#begin").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
        $("#end").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
    }
    function addupdate(result){
        $("#"+result).datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
    }

    //新增表单验证
    function changeFrom(){
            $("#add-form").bootstrapValidator({
            fields: {
                username: {
                    message: '用户名验证失败',
                    trigger:"blur",
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 18,
                            message: '用户名长度必须在6到18位之间'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: '用户名只能包含大写、小写、数字和下划线'
                        },
                        remote: {
                            //ajax验证。server result:{"valid",true or false} （返回前台类型）
                            url: "/user/findserName.jhtml",
                            message: '用户名已存在,请重新输入',
                            delay: 500, //ajax刷新的时间是0.5秒一次
                            type: 'POST',

                        },
                    },
                },
                email: {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        emailAddress: {
                            message: '邮箱地址格式有误'
                        }
                    }
                },
                password: {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 8,
                            message: '密码长度在6到8之间'
                        },
                        different: {
                            field: 'username',
                            message: '密码不能和用户名相同'
                        }
                    }
                },
                qrpassword: {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 8,
                            message: '密码长度在6到8之间'
                        },
                        identical: {
                            field: 'password',
                            message: '两次密码不同请重新输入'
                        }
                    }
                },
                add_sex: {
                    validators: {
                        notEmpty: {
                            message: '性别是必须的'
                        }
                    }
                },
                age: {
                    message: '年龄验证失败',
                    validators: {
                        notEmpty: {
                            message: '年龄不能为空'
                        },
                        regexp: {
                            regexp: /^(?:[1-9][0-9]?|1[01][0-9]|120)$/,
                            message: '年龄是1-120之间有效'
                        }
                    }
                },

                pay: {
                    validators: {
                        notEmpty: {
                            message: '请输入薪资'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '请输入正确的薪资'
                        }

                    }
                },
                phones:{
                    validators: {
                        notEmpty: {
                            message: '手机号不能为空'
                        },
                        regexp: {
                            regexp:  /^1\d{10}$/,
                            message: '手机号格式错误'
                        }
                    }
                }
            }
        });
    }

    //修改表单验证
    function updatechangeFrom(){
        $("#update-form").bootstrapValidator({
            fields: {
                username: {
                    message: '用户名验证失败',
                    trigger:"blur",
                    validators: {
                        notEmpty: {
                            message: '用户名不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 18,
                            message: '用户名长度必须在6到18位之间'
                        },
                        regexp: {
                            regexp: /^[a-zA-Z0-9_]+$/,
                            message: '用户名只能包含大写、小写、数字和下划线'
                        },
                        remote: {
                            //ajax验证。server result:{"valid",true or false} （返回前台类型）
                            url: "/user/findserName.jhtml",
                            message: '用户名已存在,请重新输入',
                            delay: 500, //ajax刷新的时间是0.5秒一次
                            type: 'POST',

                        },
                    },
                },
                updateemail: {
                    validators: {
                        notEmpty: {
                            message: '邮箱不能为空'
                        },
                        emailAddress: {
                            message: '邮箱地址格式有误'
                        }
                    }
                },
                update_sex: {
                    validators: {
                        notEmpty: {
                            message: '性别是必须的'
                        }
                    }
                },
                updateage: {
                    message: '年龄验证失败',
                    validators: {
                        notEmpty: {
                            message: '年龄不能为空'
                        },
                        regexp: {
                            regexp: /^(?:[1-9][0-9]?|1[01][0-9]|120)$/,
                            message: '年龄是1-120之间有效'
                        }
                    }
                },

                updatepay: {
                    validators: {
                        notEmpty: {
                            message: '请输入薪资'
                        },
                        regexp: {
                            regexp: /^[0-9]*$/,
                            message: '请输入正确的薪资'
                        }

                    }
                },
                updatephones:{
                    validators: {
                        notEmpty: {
                            message: '手机号不能为空'
                        },
                        regexp: {
                            regexp:  /^1\d{10}$/,
                            message: '手机号格式错误'
                        }
                    }
                }
            }
        });
    }
    //查询角色
    function findRoleList(boxDiv,pattern){
        $.ajax({
            url:"/role/findList.jhtml",
            type:"post",
            success:function (result) {
                var res =result.data;
                var str = "";
                str += '<select  title="请选择角色" id="'+pattern+'" multiple>';
                for (var i = 0; i < res.length; i++) {
                    str += '<option value="' + res[i].id + '">'+res[i].roleName+'</option>';
                }
                str += '</select>';
                $('#' + boxDiv).html(str);
                $( '#' + pattern).selectpicker();

            }
        })
    }


    //条件查询
    function search(){
        // 获取参数信息
        var v_userName = $("#userName").val();
        var v_realName = $("#realName").val();
        var v_minPrice = $("#minPrice").val();
        var v_maxPrice = $("#maxPrice").val();
        var v_minAge = $("#minAge").val();
        var v_maxAge = $("#maxAge").val();
        var v_minInsertTime = $("#begin").val();
        var v_maxInsertTime = $("#end").val();

        // 传递参数
        var v_param = {};
        v_param.roleIds=$("#searchs").val().join(",");

        console.log( v_param.roleIds)
        v_param.userName = v_userName;
        v_param.realName = v_realName;
        v_param.minPrice = v_minPrice;
        v_param.maxPrice = v_maxPrice;
        v_param.minAge = v_minAge;
        v_param.maxAge = v_maxAge;
        v_param.minDate = v_minInsertTime;
        v_param.maxDate = v_maxInsertTime;
        userTable.settings()[0].ajax.data=v_param;
        userTable.ajax.reload();
    }

    //查询用户
    var userTable;
    function  findList(){
        userTable= $('#userList').DataTable( {
                    "language": {
                        "url": "/js/DataTables/Chinese.json"
                    },
                    "aLengthMenu" : [5, 10, 15,20], //更改显示记录数选项
                    "bFilter" : false, //是否启动过滤、搜索功能
                    "processing": true,
                    "serverSide": true,
                    "ajax": {
                        "url":"/user/findList.jhtml",
                        "type": "POST"
                    },
                    "columns": [
                        { "data": "id",
                            "render" : function(data) {
                                return '<input type="checkbox" name="box" value="'+data+'" />'
                            }
                            },
                        { "data": "userName"},
                        { "data": "realName"},
                        { "data": "age"},
                        { "data":function(data){
                                return data.sex==0?"女":"男"
                            }
                        },
                        { "data": "phone"},
                        { "data": "pay"},
                        { "data": "entryTime"},
                        { "data": "email"},
                        { "data": "roleNames"},
                        { "data": "photo",
                            "render" : function(data) {
                                return '<img src="<%=request.getContextPath()%>/upload/img/'+data+'" width="60"" />'
                            }
                            },
                        { "data": "status",
                            "render" : function(data) {
                                return data?"锁定":"正常"
                            }
                        },
                        {"data": function(data){
                            return '<button class="btn btn-primary" onclick="showUser('+data.id+')"><i class="glyphicon glyphicon-pencil"></i>修改</button>'+
                                '<button class="btn btn-danger" onclick="deleteByid('+data.id+')"><i class="glyphicon glyphicon-trash"></i>删除</button>'+
                                '<button class="btn btn-danger" onclick="updateLock('+data.id+')"><i class="glyphicon glyphicon-lock"></i>解锁</button>'+
                                '<button class="btn btn-danger" onclick="updatePassword('+data.id+')"><i class="glyphicon glyphicon-lock"></i>密码重置</button>';
                            }}
                    ],
                    "drawCallback":function(settings){
                        $("#userList tr").each(function(){
                            var v_checkbox = $(this).find("[name='box']");
                            if(v_checkbox){
                                for(var i =0;i<ids.length;i++){
                                    if(v_checkbox.val()==ids[i]){
                                        v_checkbox.prop("checked",true);
                                        $(this).css("background-color","red");
                                    }
                                }
                            }
                        })
                     }
                } );
    }

    //重置密码
    function  updatePassword(id){
        event.stopPropagation();
        bootbox.confirm({
            message: "确认要重置密码吗?",
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
                if(result==true ){
                    $.ajax({
                        url:"/user/updatePasswordByGlY.jhtml",
                        data:{
                            id:id,
                        },
                        type:"post",
                        success:function(res){
                            if(res.code==200){
                                bootbox.alert({
                                    message:"密码重置成功！",
                                    size:"small",
                                    title:"提示信息",
                                })
                            }
                        }
                    })
                }
            }
        });
    }

    //解除锁定
    function updateLock(id) {
        event.stopPropagation();
        bootbox.confirm({
            message: "确认要解除锁定吗?",
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
                if(result==true ){
                    $.ajax({
                        url:"/user/updateLock.jhtml",
                        data:{
                            id:id,
                        },
                        type:"post",
                        success:function(res){
                            if(res.code==200){
                                search();
                            }
                        }
                    })
                }
            }
        });
    }

    //删除
    function  deleteByid(id){
        event.stopPropagation();
        bootbox.confirm({
            message: "确认要删除吗?",
            size:"small",
            buttons: {
                confirm: {
                    label: '确认',
                    className: 'btn-danger'
                },
                cancel: {
                    label: '取消',
                    className: 'btn-success'
                }
            },
            callback: function (result) {
                if(result==true ){
                    $.ajax({
                        url:"/user/deleteById.jhtml",
                        data:{
                            id:id,
                        },
                        type:"post",
                        success:function(res){
                            if(res.code==200){
                                search();
                            }

                        }
                    })
                }
            }
        });
    }

    //新增
    var dialog;
    function add() {
        changeFrom();
        dialog = bootbox.dialog({
            title: '新增用户信息',
            message: $("#add-html form"),
            buttons: {
                cancel: {
                    label: "<span class='glyphicon glyphicon-remove'></span>取消",
                    className: 'btn-danger',
                },

                ok: {
                    label: "<span class=\"glyphicon glyphicon-ok\"></span>提交",
                    className: 'btn-info',
                    callback: function () {
                        $("#add-form",dialog).data("bootstrapValidator").validate();//手动触发全部验证
                        var flag = $("#add-form",dialog).data("bootstrapValidator").isValid();//获取当前表单验证状态
                        if (!flag) {
                            return false;
                        }


                            var user = {};
                            user.userName = $("#add_userName", dialog).val();
                            user.password = $("#add_password", dialog).val();
                            user.conpassword = $("#add_confirmpassword", dialog).val();
                            user.realName = $("#add_realName", dialog).val();
                            user.age = $("#add_age", dialog).val();
                            user.phone = $("#add_phone", dialog).val();
                            user.email = $("#add_email", dialog).val();
                            user.pay = $("#add_pay", dialog).val();
                            user.photo = $("#add_photo", dialog).val();
                            user.entryTime = $("#add_entryTime", dialog).val();
                            user.sex = $("input[name='add_sex']:checked", dialog).val();
                            user.roleIds = $("#add", dialog).val().join(",");

                            if (user.password == user.conpassword) {
                                $.post({
                                    url: "/user/addUser.jhtml",
                                    data: user,
                                    dataType: "json",
                                    success: function (res) {
                                        if (res.code == 200) {
                                            search();
                                        }
                                    }
                                })
                            } else {
                                bootbox.alert({
                                    message: "密码不一致",
                                    size: "small",
                                })
                            }
                        }
                    }
                }

        });

        $("#add-html").html(addUserDiv);
        addupdate("add_entryTime");
        findRoleList("add_box","add");
        upload("add_inputFile","add_photo");
    }

    //回显角色
    function hxcheckbox(id){
        $("[name='update_checkbox']").each(function(){
           if(this.value==id){
               this.checked=true;
               return;
           }
        })
    }
    //展示、修改
    function showUser(id){
        //阻止冒泡
        event.stopPropagation();
        $.ajax({
            url:"/user/queryById.jhtml",
            data:{id:id},
            type:"post",
            dataType:"json",
            success:function (res) {
                updatechangeFrom();
                if(res.code==200) {
                    var data = res.data;
                    $("#update_id").val(data.id);
                    $("#update_userName").val(data.userName);
                    $("#update_realName").val(data.realName);
                    $("#update_password").val(data.password);
                    $("#update_age").val(data.age);
                    $("#update_phone").val(data.phone);
                    $("#update_email").val(data.email);
                    $("#update_pay").val(data.pay);
                    $("#update_entryTime").val(data.entryTime);
                    $("#update_photo").val(data.photo);
                    $("#img").attr("src", "/upload/img/" + data.photo);
                    $("[name='update_sex']").each(function () {
                        if ($(this).val() == data.sex) {
                            $(this).attr("checked", true)
                        }
                    });
                    var ids = data.ids;
                    $("#update option").each(function () {
                        for (var i = 0; i < ids.length; i++) {
                            if ($(this).val() == ids[i]) {
                                $(this).attr("selected", true);
                                return;
                            }
                        }
                    })
                    $("#update").selectpicker("refresh")

                    dialog = bootbox.dialog({
                        title: '修改用户信息',
                        message: $("#update-html form"),
                        buttons: {
                            cancel: {
                                label: "<span class='glyphicon glyphicon-remove'></span>取消",
                                className: 'btn-danger',
                            },

                            ok: {
                                label: "<span class='glyphicon glyphicon-ok'></span>提交",
                                className: 'btn-info',
                                callback: function () {
                                    $("#update-form",dialog).data("bootstrapValidator").validate();//手动触发全部验证
                                    var flag = $("#update-form",dialog).data("bootstrapValidator").isValid();//获取当前表单验证状态
                                    alert(flag)
                                    if (!flag) {
                                        return false;
                                    }
                                    var updateUser = {};
                                    updateUser.userName = $("#update_userName", dialog).val();
                                    updateUser.id = $("#update_id", dialog).val();
                                    updateUser.realName = $("#update_realName", dialog).val();
                                    updateUser.password = $("#update_password", dialog).val();
                                    updateUser.age = $("#update_age", dialog).val();
                                    updateUser.phone = $("#update_phone", dialog).val();
                                    updateUser.email = $("#update_email", dialog).val();
                                    updateUser.pay = $("#update_pay", dialog).val();
                                    updateUser.entryTime = $("#update_entryTime", dialog).val();
                                    updateUser.photo = $("#update_photo", dialog).val();
                                    updateUser.sex = $("[name='update_sex']:checked", dialog).val();

                                    updateUser.roleIds = "";
                                    $("#update option:selected", dialog).each(function () {
                                        updateUser.roleIds += "," + this.value;
                                    })
                                    if (updateUser.roleIds.length > 0) {
                                        updateUser.roleIds = updateUser.roleIds.substring(1)
                                    }
                                    $.post({
                                        url: "/user/updateUser.jhtml",
                                        data: updateUser,
                                        dataType: "json",
                                        success: function (res) {
                                            if (res.code == 200) {
                                                search();
                                            }
                                        }
                                    })

                                }
                            }
                        }

                    });
                    $("#update-html").html(updateUserDiv);
                    addupdate("update_entryTime");
                    findRoleList("update_box", "update");
                    upload("update_inputFile", "update_photo");

                }
            }
        })

    }

    //批量删除
    var ids = [];
    function mybox(){
        $("#userList").on("click","tr",function(){
            var v_checkbox=$(this).find("[name='box']");
            console.log(ids)
           var v_check =  $(this).attr("test")
            if(!!v_check){
                v_checkbox.prop("checked",false);
                $(this).css("background-color","");
               for(var i=ids.length-1;i >=0;i--){
                   if(v_checkbox.val()==ids[i]){
                       ids.splice(i,1);
                   }
               }
                $(this).attr("test","");
            }
            else{
                v_checkbox.prop("checked",true);
                $(this).css("background-color","red");
                $(this).attr("test","ok");
                ids.push(v_checkbox.val())
            }
        })
    }

    /*批量删除数据*/
    function deleteUserByIds(){
        if(ids.length>0){
            bootbox.confirm({
                message: "确认要删除吗?",
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
                        $.ajax({
                            url:"/user/deleteUserByIds.jhtml",
                            data:{
                                "ids":ids.toString()
                            },
                            dataType:"json",
                            type:"post",
                            success:function(res){
                                if(res.code=="200"){
                                    console.log(res.msg);
                                    search();
                                }
                            }
                        })
                    }
                }
            });
        }else{
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择需要删除的数据",
                size: 'small',
                title: "提示信息"
            });
        }
    }

    //图片上传
    function  upload(inputLoad,inputValue){
        $("#" + inputLoad,dialog).fileinput({
            language: 'zh', //设置语言
            uploadUrl:"/file/uploadimg.jhtml",
            showUpload : false, //是否显示上传按钮,跟随文本框的那个
            showRemove : false, //显示移除按钮,跟随文本框的那个
            allowedFileExtensions: ['jpg', 'gif', 'png','jpeg','bmp'],//接收的文件后缀
        })

        //异步上传返回结果处理
        $("#"+ inputLoad,dialog).on("fileuploaded", function(event, data, previewId, index) {
            $("#" + inputValue,dialog).val(data.response.data);
        });

    }

</script>
</body>
</html>
