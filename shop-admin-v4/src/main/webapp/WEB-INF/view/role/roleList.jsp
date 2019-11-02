<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/21
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>角色模块</title>
    <link href="/js/bootstrap3/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="/js/DataTables/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" href="/js/ztree/css/zTreeStyle/zTreeStyle.css"/>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<%--展示--%>
<div class="container">
    <div style="background-color: #0facd2">
        <button class="btn btn-primary" onclick="addRole()"><i class="glyphicon glyphicon-plus"></i>添加</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">角色列表</div>
                <table id="roleTable" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">角色名称</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">角色名称</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addRole" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">角色名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_roleName" placeholder="请输入角色名称...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">菜单管理</label>
            <div class="col-sm-4">
                <ul id="add_tree" class="ztree" style="width:230px; overflow:auto;"></ul>
            </div>
        </div>
    </form>
</div>
<%--修改--%>
<div id="updateRole" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">角色名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_roleName" placeholder="请输入角色名称...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">菜单管理</label>
            <div class="col-sm-4">
                <ul id="update_tree" class="ztree" style="width:230px; overflow:auto;"></ul>
            </div>
        </div>
    </form>
</div>
</body>
<script src="/js/jquery-3.3.1.js"></script>
<script src="/js/DataTables/js/jquery.dataTables.min.js"></script>
<script src="/js/DataTables/js/dataTables.bootstrap.min.js"></script>
<script src="/js/bootstrap3/js/bootstrap.js"></script>
<script src="/js/bootbox/bootbox.min.js"></script>
<script src="/js/bootbox/bootbox.locales.min.js"></script>
<script src="/js/ztree/js/jquery.ztree.core.min.js"></script>
<script src="/js/ztree/js/jquery.ztree.excheck.min.js"></script>
<script>
    var addRoleDiv;
    var updateRoleDiv;
    $(function(){
        findRoleByList();
        addRoleDiv = $("#addRole").html();
        updateRoleDiv = $("#updateRole").html();
    });

    //查询菜单管理
    function ztreeList(pattern){
        $.ajax({
            url:"/resource/resourceList.jhtml",
            type:"post",
            async:false,
            success:function(res){
                var setting = {
                    check: {
                        enable: true,
                        checkboxType:{ "Y" : "ps", "N" : "s" }
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    }
                };
                var zNodes=res.data;
                   $.fn.zTree.init($("#add_tree"), setting, zNodes);
                   $.fn.zTree.init($("#update_tree"), setting, zNodes);
                    var treeObj = $.fn.zTree.getZTreeObj(""+pattern);
                    treeObj.expandAll(true);

            },
        })
    }

    var roleTable;
    /*刷新方法*/
    function search(){
        roleTable.ajax.reload();
    }
    /*这是查询角色的方法*/
    function findRoleByList(){
        var columName  = [
            {"data":function(data){
                    return '<input type="checkbox" value=""+data.id+""/>'
                }},
            {"data":"id"},
            {"data":"roleName"},
            {
                "data":"id",
                "render":function(data,type,row,meta){
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteRole(\''+data+'\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>'+
                        '<button class="btn btn-info btn-sm" data-toggle="modal" onclick="toUpdateRole(\''+data+'\')" data-target="#myModal"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        roleTable = $('#roleTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url":"/role/findRoleByList.jhtml",
                "type": "POST"
            },
            searching: false,
            "lengthMenu": [ 5, 10, 20, 40, 80 ],
            language: {
                "url": "/js/DataTables/Chinese.json"
            },
            columns:columName,//设置列的初始化属性
        } );
    }
    /*这是添加角色的方法*/
    var dialog;
    function addRole(){
        ztreeList("add_tree");
        dialog = bootbox.dialog({
            title: '添加角色',
            message: $("#addRole form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function(){
                        //console.log('Custom cancel clicked');
                    }
                },
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function(){
                        var v_roleName = $("#add_roleName",dialog).val();
                        var treeObj = $.fn.zTree.getZTreeObj("add_tree");
                        var nodes = treeObj.getCheckedNodes(true);
                        console.log(nodes)
                        var ids=[];
                        $(nodes).each(function(){
                            ids.push(this.id)
                        })
                        $.ajax({
                          url:"/role/addRole.jhtml",
                            data:{
                                "roleName":v_roleName,
                                "ids":ids,
                            },
                            type:"post",
                            dataType:"json",
                            success:function(res){
                                if(res.code=="200"){
                                    console.log(res.msg);
                                    //刷新
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
        //还原
        $("#addRole").html(addRoleDiv);

    }
    /*这是回显的方法*/
    function toUpdateRole(id){
        $.ajax({
            url:"/role/toUpdateRole.jhtml",
            data:{
                "id":id
            },
            dataType:"json",
            type:"post",
            success:function(res){
                if(res.code=="200"){
                    ztreeList("update_tree");
                    console.log(res.msg);
                    $("#update_roleName").val(res.data.roleName);
                    var treeObj = $.fn.zTree.getZTreeObj("update_tree");
                    var ids=res.data.ids;
                    $(ids).each(function(){
                        var node = treeObj.getNodeByParam("id", this, null);
                        treeObj.checkNode(node, true);
                    })

                    var treeObj = $.fn.zTree.getZTreeObj("update_tree");
                    var nodes = treeObj.getCheckedNodes(true);
                    console.log(nodes)
                    var ids=[];
                    $(nodes).each(function(){
                        ids.push(this.id)
                    })
                    updateRole(res.data.id);
                }
            }
        })
    }
    /*这是修改角色的方法*/
    var dialogs;
    function updateRole(id){
        dialogs = bootbox.dialog({
            title: '修改角色',
            message: $("#updateRole form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function(){
                        //console.log('Custom cancel clicked');
                    }
                },
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function(){
                        var v_roleName = $("#update_roleName",dialogs).val();
                        var treeObj = $.fn.zTree.getZTreeObj("update_tree");
                        var nodes = treeObj.getCheckedNodes(true);
                        console.log(nodes)
                        var ids=[];
                        $(nodes).each(function(){
                            ids.push(this.id)
                        })
                        $.ajax({
                            url:"/role/updateRole.jhtml",
                            data:{
                                "roleName":v_roleName,
                                "id":id,
                                "ids":ids
                            },
                            type:"post",
                            dataType:"json",
                            success:function(res){
                                if(res.code=="200"){
                                    console.log(res.msg);
                                    //刷新
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
          $("#updateRole").html(updateRoleDiv);
    }
    /*删除角色的方法*/
    function deleteRole(id){
        bootbox.confirm({
            message: "你确定删除吗?",
            size: 'small',
            title: "提示信息",
            buttons: {
                confirm: {
                    label: '<span class="glyphicon glyphicon-ok"></span>确定',
                    className: 'btn-success'
                },
                cancel: {
                    label: '<span class="glyphicon glyphicon-remove"></span>取消',
                    className: 'btn-danger'
                }
            },
            callback: function (result) {
                if(result){
                    $.ajax({
                        url:"/role/deleteRole.jhtml",
                        data:{
                            "id":id
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
        })
    }
</script>
</html>
