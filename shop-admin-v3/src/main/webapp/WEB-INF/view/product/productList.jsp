<%--
  Created by IntelliJ IDEA.
  User: Ming
  Date: 2019/8/23
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>商品展示页面</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品查询</div>
                <div class="panel-body">
                    <form class="form-horizontal" id="i">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商品名称</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="productName"  name="productName" placeholder="请输入商品名...">
                            </div>
                            <label class="col-sm-2 control-label">价格</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="price"  name="price"  placeholder="请输入价格...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生产日期</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minTime" placeholder="开始日期..."   name="price" aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxTime"  name="price" placeholder="结束日期..." aria-describedby="basic-addon2">
                                </div>
                            </div>
                            <label class="col-sm-2 control-label">品牌名称</label>
                            <div class="col-sm-4" id="brand"  name="brandId" >

                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">按品牌名排序</label>
                            <div class="col-sm-4">
                                    <input type="text" class="form-control"  id="brandName" placeholder="请输入品牌名排序..." aria-describedby="basic-addon2">
                            </div>
                        </div>
                        <div class="form-group" id="typeSelect">
                            <label class="col-sm-2 control-label">分类</label>
                            <input type="hidden" name="type1" id="type1"/>
                            <input type="hidden" name="type2" id="type2"/>
                            <input type="hidden" name="type3" id="type3"/>
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
        <button class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i>添加</button>
        <button class="btn btn-primary" onclick="downExcel()"><i class="glyphicon glyphicon-save"></i>导出excel</button>
        <button class="btn btn-primary" onclick="downPdf()"><i class="glyphicon glyphicon-save"></i>导出PDF</button>
        <button class="btn btn-primary" onclick="downWord()"><i class="glyphicon glyphicon-save"></i>导出Word</button>
        <button class="btn btn-primary" onclick="deleteCache()"><i class="glyphicon glyphicon-trash"></i>手动清缓存</button>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">商品列表</div>
                <table id="productTable" class="table table-striped table-hover table-striped table-bordered">
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">是否热销</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">品牌名称</th>
                        <th style="text-align: center;">分类名称</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">是否热销</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">品牌名称</th>
                        <th style="text-align: center;">分类名称</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>
<%--添加--%>
<div id="addProduct" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_productName" placeholder="请输入商品名称...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">价格</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_price" placeholder="请输入价格...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生产日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_producedDate" placeholder="请选择生产日期...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">产品主图</label>
            <div class="col-sm-5">
                <input id="add_inputFile" type="file" class="file-loading required" data-preview-file-type="text" name=" myfile"  class="input-xlarge"   >
                <input type="hidden" class="form-control"  id="add_photo">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">是否热销</label>
            <div class="col-sm-4">
                <input type="radio"  name="add_hotproduct"  value="1">是
                <input type="radio"  name="add_hotproduct"  value="2">否
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_stock" placeholder="请输入库存量...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4" id="add_brand">

            </div>
        </div>
        <div class="form-group" id="add_type" >
            <label class="col-sm-2 control-label">分类</label>

        </div>
    </form>
</div>
<%--修改--%>
<div id="updateProduct" style="display: none;">
    <form class="form-horizontal">
        <input type="hidden" id="id">
        <div class="form-group">
            <label  class="col-sm-2 control-label">商品名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_productName" placeholder="请输入商品名称...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">价格</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_price" placeholder="请输入价格...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">生产日期</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_producedDate" placeholder="请选择生产日期...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">产品主图</label>
            <div class="col-sm-5">
                <input id="update_inputFile" type="file" class="file-loading required"  name=" myfile"    >
                <input type="hidden" class="form-control"  id="update_photo">
                <input type="hidden" class="form-control"  id="update_oldPath">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_stock" placeholder="请输入库存...">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">是否热销</label>
            <div class="col-sm-4">
                <input type="radio"  name="update_hotproduct" value="1">是
                <input type="radio"  name="update_hotproduct"  value="2">否
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4" id="update_brand">

            </div>
        </div>
        <div class="form-group" id="update_type">
            <label class="col-sm-2 control-label">分类</label>

        </div>
    </form>
</div>
</body>
<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var addProductDiv;
    var updateProductDiv;
    $(function(){
        toList();
        //备份
        addProductDiv = $("#addProduct").html()
        updateProductDiv = $("#updateProduct").html();
        addTime("minTime");
        addTime("maxTime");
        initBrand("brand","brandselect");
        findAllSearch(0);

    })

    //手动清缓存
    function deleteCache(){
        $.ajax({
            url:"/cache/deleteCache.jhtml",
            type:"post",
            success:function(res){
                if(res.code=="200") {
                    bootbox.alert({
                        message: "<span class='glyphicon glyphicon-exclamation-sign'></span>操作成功",
                        size: 'small',
                        title: "提示信息"
                    });
                }
            }
        })
    }



    //导出excel
    function downExcel(){
        var t1=  $($("select[name='addType']",$("#i"))[0]).val();
        var t2= $($("select[name='addType']",$("#i"))[1]).val();
        var t3= $($("select[name='addType']",$("#i"))[2]).val();
        $("#type1").val(t1);
        $("#type2").val(t2);
        $("#type3").val(t3);

        var form= document.getElementById("i");
        form.action="/product/downExcel.jhtml";
        form.method="post";
        form.submit();
    }

    //导出pdf
    function downPdf(){
        var t1=  $($("select[name='addType']",$("#i"))[0]).val();
        var t2= $($("select[name='addType']",$("#i"))[1]).val();
        var t3= $($("select[name='addType']",$("#i"))[2]).val();
        $("#type1").val(t1);
        $("#type2").val(t2);
        $("#type3").val(t3);
        var form= document.getElementById("i");
        form.action="/product/downPdf.jhtml";
        form.method="post";
        form.submit();
    }


    //导出word
    function downWord(){
        var t1=  $($("select[name='addType']",$("#i"))[0]).val();
        var t2= $($("select[name='addType']",$("#i"))[1]).val();
        var t3= $($("select[name='addType']",$("#i"))[2]).val();
        $("#type1").val(t1);
        $("#type2").val(t2);
        $("#type3").val(t3);
        var form= document.getElementById("i");
        form.action="/product/downWord.jhtml";
        form.method="post";
        form.submit();
    }

    //查询三级下拉框
    function findAllSearch(id,obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            url:"/types/findAllById.jhtml",
            type:"post",
            data:{"id":id},
            success:function(res) {
                if (res.code == "200") {
                    var data=res.data;
                    if(data.length==0){
                       return;
                    }
                   var addTypeDiv = '<div class="col-sm-3">';
                    addTypeDiv += '<select name="addType" class="form-control" onchange="findAllSearch(this.value,this)">';
                    addTypeDiv+='<option value="-1">===请选择===</option>';
                    for(var i=0;i<data.length;i++){
                        addTypeDiv+='<option value="'+data[i].id+'">'+data[i].name+'</option>';
                    }
                    addTypeDiv += '</select></div>';
                    $("#typeSelect").append(addTypeDiv);
                }
            }
    })
    }

    //add三级下拉框
    function findAll(id,obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            url:"/types/findAllById.jhtml",
            type:"post",
            data:{"id":id},
            success:function(res) {
                if (res.code == "200") {
                    var data=res.data;
                    if(data.length==0){
                        return;
                    }
                    var addTypeDiv = '<div class="col-sm-3">';
                    addTypeDiv += '<select name="addType" class="form-control" onchange="findAll(this.value,this)">';
                    addTypeDiv+='<option value="-1">===请选择===</option>';
                    for(var i=0;i<data.length;i++){
                        addTypeDiv+='<option value="'+data[i].id+'">'+data[i].name+'</option>';
                    }
                    addTypeDiv += '</select></div>';
                    $("#add_type",dialog).append(addTypeDiv);
                }
            }
        })
    }

    //修改三级下拉框
    function findAllUpdate(id,obj) {
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            url:"/types/findAllById.jhtml",
            type:"post",
            data:{"id":id},
            success:function(res) {
                if (res.code == "200") {
                    var data=res.data;
                    if(data.length==0){
                        return;
                    }
                    var addTypeDiv = '<div class="col-sm-2">';
                    addTypeDiv += '<select name="addType" class="form-control" onchange="findAllUpdate(this.value,this)">';
                    addTypeDiv+='<option value="-1">请选择</option>';
                    for(var i=0;i<data.length;i++){
                        addTypeDiv+='<option value="'+data[i].id+'">'+data[i].name+'</option>';
                    }

                    addTypeDiv += '</select></div>';

                    $("#update_type",dialog).append(addTypeDiv);

                }
            }
        })
    }


    var productTable;
    /*查询商品*/
    function toList(){
        var columName  = [
            {
                "data":"id",
                "render":function(data){//这个data就是咱们查到的pageInfo中的数据集合里的对象
                    return '<input name="ids" type="checkbox" value="'+data+'"/>'
                }
            },
            {"data":"id"},
            {"data":"productName"},
            {"data":"price"},
            {"data":"shelves",
                "render":function(data,type,row,meta){
                    return data==1?"正常":"已下架"
                }
            },
            {"data":"hotProduct",
                "render":function(data,type,row,meta){
                    return data==1?"是":"否"
                }
            },
            {"data":"mainImagePath",
                "render":function(data,type,row,meta){
                    return '<img src="'+data+'" width="80px"/>'
                }
            },
            {"data":"producedDate"},
            {"data":"stock"},
            {"data":"brandName"},
            {"data":"typeName"},
            {
                "data":"id",
                "render":function(data,type,row,meta){
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteProduct(\''+data+'\',\''+type.mainImagePath+'\')"><span class="glyphicon glyphicon-trash" style="color: #ffffff;"></span>删除</button>'+
                        '<button class="btn btn-info btn-sm" onclick="toUpdateProduct(\''+data+'\')"><span class="glyphicon glyphicon-pencil" style="color: #ffffff;"></span>修改</button>' +
                        '<input type="button" class="'+(row.shelves==1?"btn btn-warning btn-sm":"btn btn-success btn-sm")+'"  value="'+(row.shelves==1 ?"下架":"上架")+'"  onclick="isShelves('+data+')">' +
                        '</div>'
                }
            },
        ];
        /* 渲染datatables */
        productTable = $('#productTable').DataTable({
            "processing": true,
            "serverSide": true,
            "ordering": false,//是否启用排序
            "ajax": {
                "url":"/product/productList.jhtml",
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
    /*这是刷新方法*/
    function search(){
        var productName = $("#productName").val();
        var price = $("#price").val();
        var minTime = $("#minTime").val();
        var maxTime = $("#maxTime").val();
        var brandId= $("#brandselect").val();
        var brandName= $("#brandName").val();
        var t1=  $($("select[name='addType']",$("#i"))[0]).val();
        var t2= $($("select[name='addType']",$("#i"))[1]).val();
        var t3= $($("select[name='addType']",$("#i"))[2]).val();
        var v_param = {};
        v_param.productName = productName;
        v_param.price = price;
        v_param.minTime = minTime;
        v_param.maxTime = maxTime;
        v_param.brandId = brandId;
        v_param.brandName = brandName;
        v_param.type1 = t1;
        v_param.type2 = t2;
        v_param.type3 = t3;
        productTable.settings()[0].ajax.data = v_param;
        productTable.ajax.reload();

    }

    //查询品牌
    function initBrand(htmlDiv,patter){
        $.ajax({
            url:"/brand/allBrand.jhtml",
            type:"post",
            async:false,
            success:function (res) {
                var data=res.data;
                var selectHtml='<select  class="form-control" id="'+patter+'"><option value="-1">====请选择品牌====</option>'
                for (var i=0;i<data.length;i++) {
                    selectHtml+='<option value="'+data[i].id+'">'+data[i].brandName+'</option>';
                }
                selectHtml+="</select>";

                $("#"+htmlDiv).html(selectHtml);
            }
        })
    }




    /*添加商品*/
    var dialog;
    function add(){

        findAll(0);
        initBrand("add_brand","add_brandselect");
        addTime("add_producedDate");
        upload("add_inputFile","add_photo");
        dialog= bootbox.dialog({
            title: '添加商品',
            message: $("#addProduct form"),
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
                    label: "添加",
                    className: 'btn-primary',
                    callback: function(){

                        var v_productName = $("#add_productName",dialog).val();
                        var v_price = $("#add_price",dialog).val();
                        var v_producedDate = $("#add_producedDate",dialog).val();
                        var v_mainImagePath = $("#add_photo",dialog).val();
                        var v_stock = $("#add_stock",dialog).val();
                        var v_hot = $("[name='add_hotproduct']:checked",dialog).val();
                        var brandid = $("#add_brandselect",dialog).val();
                        var t1=  $($("select[name='addType']",dialog)[0]).val();
                        var t2= $($("select[name='addType']",dialog)[1]).val();
                        var t3= $($("select[name='addType']",dialog)[2]).val();

                        var v_param = {};
                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.hotProduct = v_hot;
                        v_param.stock = v_stock;
                        v_param.brandId = brandid;
                        v_param.type1 = t1;
                        v_param.type2 = t2;
                        v_param.type3 = t3;
                        $.ajax({
                            url:"/product/add.jhtml",
                            data:v_param,
                            type:"post",
                            dataType:"json",
                            success:function(res){
                                if(res.code=="200"){
                                    console.log(res.msg);
                                    search();
                                }
                            }
                        })
                    }
                }
            }
        });
        //还原
        $("#addProduct").html(addProductDiv);
        addTime("add_mainImagePath");
        upload("add_inputFile","add_photo");
    }


    //上下架
    function isShelves(id){
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
                    //删除
                    $.ajax({
                        url:"/product/updateByShelves.jhtml",
                        type:"post",
                        data:{
                            "id":id,
                        },
                        success:function(res){
                            if(res.code=="200") {
                                search();
                            }
                        }
                    })
                }
            }
        })
    }

    //图片上传
    var v_mainImage=[];
    function  upload(inputLoad,inputValue){
        if(inputLoad=="update_inputFile"){
             v_mainImage=[resout.mainImagePath];
        }

        $("#" + inputLoad,dialog).fileinput({
            language: 'zh', //设置语言
            uploadUrl:"/file/uploadimg.jhtml",
            showUpload : false, //是否显示上传按钮,跟随文本框的那个
            showRemove : false, //显示移除按钮,跟随文本框的那个
            initialPreview :v_mainImage,
            initialPreviewAsData: true,
        })

        //异步上传返回结果处理
        $("#"+ inputLoad,dialog).on("fileuploaded", function(event, data, previewId, index) {
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
    /*时间插件*/
    function addTime(name){
        $("#"+name).datetimepicker({
            format: 'YYYY-MM-DD',
            locale: 'zh-CN',
            showClear: true
        })
    }
    /*删除*/
    function deleteProduct(id,mainImagePath){
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
                //这个result是boolean类型的 点确认是true 反之false
                if (result) {
                    //删除
                    $.ajax({
                        url:"/product/deleteProduct.jhtml",
                        type:"post",
                        data:{
                            "id":id,
                            "mainImagePath":mainImagePath
                        },
                        dataType:"json",
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
    /*回显数据*/
    //备份查出来列表信息
    var v_typeName;
    var resout;
    function toUpdateProduct(id){
        $.ajax({
            url:"/product/toUpdateProduct.jhtml",
            data:{
                "id":id
            },
            dataType:"json",
            type:"post",
            success:function(res){
                if(res.code=="200"){
                    initBrand("update_brand","update_brandselect");
                    addTime("update_producedDate");
                    resout = res.data;
                    var data = res.data;
                    v_typeName=data.typeName
                    $("#id").val(data.id);
                    $("#update_productName").val(data.productName);
                    $("#update_price").val(data.price);
                    $("#update_producedDate").val(data.producedDate);
                    $("#update_oldPath").val(data.mainImagePath);
                    $("#update_stock").val(data.stock);
                    $("#update_brandselect").val(data.brandId);
                    $("#update_type").append("<div>"+v_typeName+"<button class=\"btn btn-primary\" onclick=\"by(this)\"><i class=\"glyphicon glyphicon-pencil\"></i>编译</button></div>");
                    $("[name='update_hotproduct']").each(function () {
                        if($(this).val()==data.hotProduct){
                            $(this).attr("checked",true)
                        }
                    });
                    upload("update_inputFile","update_photo");
                    updateProduct(data);
                }
            }
        })
    }

    function by (obj){
        //清除编译div
        $(obj).parent().remove();

        findAllUpdate(0);

        $("#update_type",dialog).append('<button class="btn btn-primary" onclick="qx(this)"><i class="glyphicon glyphicon-pencil"></i>取消编译</button>');

    }

    function qx(){
        $("#update_type",dialog).html("<label class=\"col-sm-2 control-label\">分类</label><div>"+v_typeName+"<button class=\"btn btn-primary\" onclick=\"by(this)\"><i class=\"glyphicon glyphicon-pencil\"></i>编译</button></div>");
    }

    /*修改*/
    function updateProduct(data){
        dialog = bootbox.dialog({
            title: '修改用户',
            message: $("#updateProduct form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                    callback: function(){
                        console.log('Custom cancel clicked');
                    }
                },
                confirm: {
                    label: "修改",
                    className: 'btn-primary',
                    callback: function(){
                        var id = $("#id",dialog).val();
                        var v_productName = $("#update_productName",dialog).val();
                        var v_price = $("#update_price",dialog).val();
                        var v_producedDate = $("#update_producedDate",dialog).val();
                        var v_mainImagePath = $("#update_photo",dialog).val();
                        var v_stock = $("#update_stock",dialog).val();
                        var v_hot = $("[name='update_hotproduct']:checked",dialog).val();
                        var brandid=$("#update_brandselect",dialog).val();
                        var oldPath=$("#update_oldPath",dialog).val();

                       var typeSize= $("select[name='addType']",dialog).length;
                        var t1;
                        var t2;
                        var t3;
                       if(typeSize==3){
                            t1=  $($("select[name='addType']",dialog)[0]).val();
                            t2= $($("select[name='addType']",dialog)[1]).val();
                            t3= $($("select[name='addType']",dialog)[2]).val();
                       }else{
                           t1=data.type1;
                           t2=data.type2;
                           t3=data.type3;
                       }

                        var v_param = {};
                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.id = id;
                        v_param.stock = v_stock;
                        v_param.hotProduct = v_hot;
                        v_param.brandId = brandid;
                        v_param.type1 = t1;
                        v_param.type2 = t2;
                        v_param.type3 = t3;
                        v_param.oldPath = oldPath;
                        $.ajax({
                            url:"/product/updateProduct.jhtml",
                            data:v_param,
                            dataType:"json",
                            type:"post",
                            success:function(res){
                                search();
                            }
                        })
                    }
                }
            }
        })
        //还原
        $("#updateProduct").html(updateProductDiv);
        upload("update_inputFile","update_photo");
        addTime("update_producedDate");
    }
</script>
</html>
