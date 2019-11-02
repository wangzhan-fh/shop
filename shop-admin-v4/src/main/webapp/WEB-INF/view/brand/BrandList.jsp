<%--
  Created by IntelliJ IDEA.
  User: wangzhan
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
    <title>品牌列表</title>
    <link href="/js/bootstrap3/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="/js/DataTables/css/dataTables.bootstrap.min.css"/>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<%--展示--%>
<div class="container">
    <div style="background-color: #0facd2">
        <button class="btn btn-primary" onclick="addBrand()"><i class="glyphicon glyphicon-plus"></i>添加</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">品牌</div>
                <table id="brandTable" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addBrand" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_brandName" placeholder="请输入品牌名称...">
            </div>
        </div>
    </form>
</div>
<%--修改--%>
<div id="updateBrand" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_brandName" placeholder="请输入品牌名称...">
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
<script>
    var addBranDiv;
    var updateBrandDiv;
    $(function(){
        findBrandByList();
        addBranDiv = $("#addBrand").html();
        updateBrandDiv = $("#updateBrand").html();
    });
    var brandTable;
    /*刷新方法*/
    function search(){
        brandTable.ajax.reload();
    }
    /*这是查询方法*/
    function findBrandByList(){
        var columName  = [
            {"data":function(data){
                    return '<input type="checkbox" value=""+data.id+""/>'
                }},
            {"data":"id"},
            {"data":"brandName"},
            {
                "data":"id",
                "render":function(data,type,row,meta){
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteBrand(\''+data+'\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>'+
                        '<button class="btn btn-info btn-sm" data-toggle="modal" onclick="toUpdateBrand(\''+data+'\')" data-target="#myModal"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        brandTable = $('#brandTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url":"/brand/findBrandByList.jhtml",
                "type": "POST"
            },
            searching: false,
            "lengthMenu": [ 5, 10, 20 ],
            language: {
                "url": "/js/DataTables/Chinese.json"
            },
            columns:columName,//设置列的初始化属性
        } );
    }
    /*这是添加角色的方法*/
    function addBrand(){
        var add = bootbox.dialog({
            title: '添加品牌',
            message: $("#addBrand form"),
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
                        var v_brandName = $("#add_brandName",add).val();
                        $.ajax({
                            url:"/brand/addBrand.jhtml",
                            data:{
                                "brandName":v_brandName
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
        $("#addBrand").html(addBranDiv);
    }
    /*这是回显的方法*/
    function toUpdateBrand(id){
        $.ajax({
            url:"/brand/toUpdateBrand.jhtml",
            data:{
                "id":id
            },
            dataType:"json",
            type:"post",
            success:function(res){
                if(res.code=="200"){
                    console.log(res.msg);
                    $("#update_brandName").val(res.data.brandName);
                    updateBrand(res.data.id);
                }
            }
        })
    }
    /*这是修改角色的方法*/
    function updateBrand(id){
        var update = bootbox.dialog({
            title: '修改角色',
            message: $("#updateBrand form"),
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
                        var v_brandName = $("#update_brandName",update).val();
                        $.ajax({
                            url:"/brand/updateBrand.jhtml",
                            data:{
                                "brandName":v_brandName,
                                "id":id
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
        $("#updateBrand").html(updateBrandDiv);
    }
    /*删除角色的方法*/
    function deleteBrand(id){
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
                        url:"/brand/deleteBrand.jhtml",
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
