<%--
  Created by IntelliJ IDEA.
  User: panzhi
  Date: 2019/8/21
  Time: 20:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>品牌列表</title>

</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<%--展示--%>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">自定义排序</div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">品牌名排序</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="brandName" placeholder="请输入品牌名排序...">
                            </div>
                        </div>
                        <div class="form-group">
                            <div align="center">
                                <button class="btn btn-primary" type="button" onclick="search()"><i class="glyphicon glyphicon-search"></i>查询</button>
                                <button class="btn btn-default" type="reset"><i class="glyphicon glyphicon-refresh"></i>重置</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>



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
                        <th style="text-align: center;">Logo</th>
                        <th style="text-align: center;">热销</th>
                        <th style="text-align: center;">排序</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">Logo</th>
                        <th style="text-align: center;">热销</th>
                        <th style="text-align: center;">排序</th>
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
        <div class="form-group">
            <label  class="col-sm-2 control-label">排序</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_sort" placeholder="请输入排序...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">热销</label>
            <div class="col-sm-4">
                <input type="radio" name="add_hot" value="1" >是
                <input type="radio" name="add_hot" value="2" >否
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">主图</label>
            <div class="col-sm-5">
                <input id="add_inputFile" type="file" class="file-loading required"  name=" myfile">
                <input type="hidden" class="form-control"  id="add_logo">
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
        <div class="form-group">
            <label class="col-sm-2 control-label">主图</label>
            <div class="col-sm-5">
                <input id="update_inputFile" type="file" class="file-loading required"  name="myfile">
                <input type="hidden" class="form-control"  id="update_logo">
                <input type="hidden" class="form-control"  id="update_oldlogo">
            </div>
        </div>

    </form>
</div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var addBranDiv;
    var updateBrandDiv;
    $(function(){
        findBrandByList();
        addBranDiv = $("#addBrand").html();
        updateBrandDiv = $("#updateBrand").html();
    });

    //图片上传
    var v_mainImage=[];
    var resout;
    function  upload(inputLoad,inputValue) {
        if(inputLoad=="update_inputFile"){
            v_mainImage=[resout.mainImagePath];
        }
            $("#" + inputLoad, dialog).fileinput({
                language: 'zh', //设置语言
                uploadUrl: "/file/uploadimg.jhtml",
                showUpload: false, //是否显示上传按钮,跟随文本框的那个
                showRemove: false, //显示移除按钮,跟随文本框的那个
                allowedFileExtensions: ['jpg', 'gif', 'png','jpeg','bmp'],//接收的文件后缀
                initialPreview: [
                    v_mainImage
                ],
                initialPreviewAsData: true,
            })

            //异步上传返回结果处理
            $("#" + inputLoad, dialog).on("fileuploaded", function (event, data, previewId, index) {
                if(data.response.code==200){
                    $("#" + inputValue,dialog).val(data.response.data);
                }else{
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>"+data.response.msg,
                        size: 'small',
                        title: "提示信息"
                    });
                }
            });

        }

        var brandTable;

        /*刷新方法*/
        function search() {
            var v_param = {};
            v_param.brandName = $("#brandName").val();
            brandTable.settings()[0].ajax.data = v_param;
            brandTable.ajax.reload();
        }

        /*这是查询方法*/
        function findBrandByList() {
            var columName = [
                {
                    "data": function (data) {
                        return '<input type="checkbox" value=""+data.id+""/>'
                    }
                },
                {"data": "id"},
                {"data": "brandName"},
                {
                    "data": "logo",
                    "render": function (data, type, row, meta) {
                        return '<img src="' + data + '" width="100px" height="100px">'
                    }
                },
                {
                    "data": "hotBrand",
                    "render": function (data, type, row, meta) {
                        return data == 1 ? "是" : "否"
                    }
                },
                {
                    "data": "sort",
                    "render": function (data, type, row, meta) {
                        var v_id = row.id;
                        return '<input type="text" class="form-control"  id="sort_' + v_id + '"  value="' + data + '">' +
                            ' <button class="btn btn-primary" onclick="updateSort(' + v_id + ')"><i class="glyphicon glyphicon-refresh"></i>更新</button>'
                    }
                },
                {
                    "data": "id",
                    "render": function (data, type, row, meta) {
                        return '<div class="btn-group" role="group" aria-label="...">' +
                            '<button class="btn btn-danger btn-sm" onclick="deleteBrand(\'' + data + '\',\'' + row.logo + '\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>' +
                            '<button class="btn btn-primary btn-sm" data-toggle="modal" onclick="toUpdateBrand(\'' + data + '\')" data-target="#myModal"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                            '<input type="button" class="' + (row.hotBrand == 1 ? "btn btn-warning btn-sm" : "btn btn-success btn-sm") + '"  value="' + (row.hotBrand == 2 ? "热销" : "普通") + '"  onclick="ishotBrand(' + data + ')">' +
                            '</div>'
                    }
                },
            ];
            /* 渲染datatables */
            brandTable = $('#brandTable').DataTable({
                "processing": true,
                "serverSide": true,
                "ajax": {
                    "url": "/brand/findBrandByList.jhtml",
                    "type": "POST",
                },
                searching: false,
                "lengthMenu": [5, 10, 20],
                language: {
                    "url": "/js/DataTables/Chinese.json"
                },
                columns: columName,//设置列的初始化属性
            });
        }


        function updateSort(id) {
            var sort = $("#sort_" + id).val();
            bootbox.confirm({
                message: "你确定修改吗?",
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
                    if (result) {
                        $.ajax({
                            url: "/brand/updateSort.jhtml",
                            type: "post",
                            data: {
                                "id": id,
                                "sort": sort,
                            },
                            success: function (res) {
                                if (res.code == "200") {
                                    search();
                                }
                            }
                        })
                    }
                }
            })
        }

        //是否热销
        function ishotBrand(id) {
            bootbox.confirm({
                message: "你确定修改吗?",
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
                    if (result) {
                        $.ajax({
                            url: "/brand/updateByhotBrand.jhtml",
                            type: "post",
                            data: {
                                "id": id,
                            },
                            success: function (res) {
                                if (res.code == "200") {
                                    search();
                                }
                            }
                        })
                    }
                }
            })
        }


        /*这是添加角色的方法*/
        var dialog;

        function addBrand() {
            upload("add_inputFile","add_logo");
            dialog = bootbox.dialog({
                title: '添加品牌',
                message: $("#addBrand form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                    },
                    confirm: {
                        label: "确定",
                        className: 'btn-primary',
                        callback: function () {
                            var v_brandName = $("#add_brandName", dialog).val();
                            var v_logo = $("#add_logo", dialog).val();
                            var v_sort = $("#add_sort", dialog).val();
                            var v_hot = $("[name='add_hot']:checked", dialog).val();
                            $.ajax({
                                url: "/brand/addBrand.jhtml",
                                data: {
                                    "brandName": v_brandName,
                                    "logo": v_logo,
                                    "sort": v_sort,
                                    "hotBrand": v_hot,
                                },
                                type: "post",
                                dataType: "json",
                                success: function (res) {
                                    if (res.code == "200") {
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
            upload("add_inputFile", "add_logo");

        }

        /*这是回显的方法*/
        function toUpdateBrand(id) {
            $.ajax({
                url: "/brand/toUpdateBrand.jhtml",
                data: {
                    "id": id
                },
                dataType: "json",
                type: "post",
                success: function (res) {
                    if (res.code == "200") {
                        resout = res.data;
                        $("#update_brandName").val(res.data.brandName);
                        $("#update_oldlogo").val(res.data.logo);

                        updateBrand(res.data.id);
                        upload("update_inputFile", "update_logo");
                    }
                }
            })
        }

        /*这是修改角色的方法*/
        function updateBrand(id) {
            dialog = bootbox.dialog({
                title: '修改角色',
                message: $("#updateBrand form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                        callback: function () {
                            //console.log('Custom cancel clicked');
                        }
                    },
                    confirm: {
                        label: "确定",
                        className: 'btn-primary',
                        callback: function () {
                            var v_brandName = $("#update_brandName", dialog).val();
                            var v_logo = $("#update_logo", dialog).val();
                            var v_oldlogo = $("#update_oldlogo", dialog).val();
                            $.ajax({
                                url: "/brand/updateBrand.jhtml",
                                data: {
                                    "brandName": v_brandName,
                                    "id": id,
                                    "logo": v_logo,
                                    "oldlogo": v_oldlogo,
                                },
                                type: "post",
                                dataType: "json",
                                success: function (res) {
                                    if (res.code == "200") {
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
            upload("update_inputFile", "update_logo");
        }

        /*删除角色的方法*/
        function deleteBrand(id, pathImg) {
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
                    if (result) {
                        $.ajax({
                            url: "/brand/deleteBrand.jhtml",
                            data: {
                                "id": id,
                                "pathImg": pathImg,
                            },
                            dataType: "json",
                            type: "post",
                            success: function (res) {
                                if (res.code == "200") {
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
