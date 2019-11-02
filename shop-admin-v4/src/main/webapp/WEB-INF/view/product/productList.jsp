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
                    <form class="form-horizontal" id="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">商品名称</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="productName" placeholder="请输入商品名..." name="productName">
                            </div>
                            <label class="col-sm-2 control-label">价格</label>
                            <div class="col-sm-4">
                                <input type="email" class="form-control" id="price" placeholder="请输入价格..." name="price">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生产日期</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minTime" placeholder="开始日期..." aria-describedby="basic-addon2" name="minTime">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxTime" placeholder="结束日期..." aria-describedby="basic-addon2"  name="maxTime">
                                </div>
                            </div>
                            <label  class="col-sm-2 control-label">品牌</label>
                            <div class="col-sm-4" id="search_brand">

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
        <button class="btn btn-primary" onclick="add()"><i class="glyphicon glyphicon-plus"></i>添加</button>
        <button class="btn btn-primary" onclick="downExcel()"><i class="glyphicon glyphicon-save"></i>导出excel</button>
        <button class="btn btn-primary" onclick="downPdf()"><i class="glyphicon glyphicon-save"></i>导出PDF</button>
        <button class="btn btn-primary" onclick="downWord()"><i class="glyphicon glyphicon-save"></i>导出Word</button>
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
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">是否热销</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">品牌</th>
                        <th style="text-align: center;">操作</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">id</th>
                        <th style="text-align: center;">商品名称</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">价格</th>
                        <th style="text-align: center;">商品主图</th>
                        <th style="text-align: center;">生产日期</th>
                        <th style="text-align: center;">是否热销</th>
                        <th style="text-align: center;">库存</th>
                        <th style="text-align: center;">品牌</th>
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
            <label  class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_stock" placeholder="请输入库存量...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">是否热销</label>
            <div class="col-sm-4">
                <input type="radio" name="add_hot" value="1">是
                <input type="radio" name="add_hot" value="2">否
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4" id="add_brand">

            </div>
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
                <input id="update_inputFile" type="file" class="file-loading required" data-preview-file-type="text" name=" myfile"  class="input-xlarge"   >
                <input type="hidden" class="form-control"  id="update_photo">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">库存</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_stock" placeholder="请输入库存量...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">是否热销</label>
            <div class="col-sm-4">
                <input type="radio" name="update_hot" value="1">是
                <input type="radio" name="update_hot" value="2">否
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">品牌</label>
            <div class="col-sm-4" id="update_brand">

            </div>
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
        upload("add_inputFile","add_photo");
        upload("update_inputFile","update_photo");
        addTime("add_producedDate");
        addTime("update_producedDate");
        addTime("minTime");
        addTime("maxTime");
        initBrand("search_brand","search_brandName");
    })
    var productTable;

    //导出excel
    function downExcel(){
       var form= document.getElementById("form");
        form.action="/product/downExcel.jhtml";
        form.method="post";
        form.submit();
    }

    //导出pdf
    function downPdf(){
        var form= document.getElementById("form");
        form.action="/product/downPdf.jhtml";
        form.method="post";
        form.submit();
    }


    //导出word
    function downWord(){
        var form= document.getElementById("form");
        form.action="/product/downWord.jhtml";
        form.method="post";
        form.submit();
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
            {"data":"shelves",
                "render":function(data,type,row,meta){
                    return data==1?'正常':'已下架'
                }
            },

            {"data":"price"},
            {
                "data":"mainImagePath",
                "render":function(data,type,row,meta){
                    return '<img src="/upload/img/'+data+'" width="80px"/>'
                }
            },
            {"data":"producedDate"},
            {"data":"hotProduct",
                "render":function(data,type,row,meta){
                    return data==1?'是':'否'
                }},
            {"data":"stock"},
            {"data":"brandName"},
            {
                "data":"id",
                "render":function(data,type,row,meta){
                    return '<div class="btn-group" role="group" aria-label="...">' +
                        '<button class="btn btn-danger btn-sm" onclick="deleteProduct(\''+data+'\',\''+type.mainImagePath+'\')"><span class="glyphicon glyphicon-trash" >删除</span></button>'+
                        '<button class="btn btn-info btn-sm"   onclick="toUpdateProduct(\''+data+'\')"><span class="glyphicon glyphicon-pencil"></span>修改</button>' +
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

    /*这是刷新方法*/
    function search(){
        var productName = $("#productName").val();
        var price = $("#price").val();
        var minTime = $("#minTime").val();
        var maxTime = $("#maxTime").val();
        var b = $("#search_brandName").val();
        var v_param = {};
        v_param.productName = productName;
        v_param.price = price;
        v_param.minTime = minTime;
        v_param.maxTime = maxTime;
        v_param.brandId = b;
        productTable.settings()[0].ajax.data = v_param;
        productTable.ajax.reload();
    }
    /*添加商品*/
    var dialog;
    function add(){
        initBrand("add_brand","add_brandName");
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
                        var stock = $("#add_stock",dialog).val();
                        var brandId = $("#add_brandName",dialog).val();
                        var hot = $("[name='add_hot']:checked",dialog).val();

                        var v_param = {};
                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.stock = stock;
                        v_param.hotProduct = hot;
                        v_param.brandId = brandId;
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
        upload("add_inputFile","add_photo");
        addTime("add_producedDate");;
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
                            if(res.code=="200") {
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
                    var data = res.data;
                    $("#id").val(data.id);
                    $("#update_productName").val(data.productName);
                    $("#update_price").val(data.price);
                    $("#update_producedDate").val(data.producedDate);
                    $("#update_photo").val(data.mainImagePath);
                    $("#update_stock").val(data.stock);
                    $("#update_brandName").val(data.brandId);
                    $("[name='update_hot']").each(function(){
                        if(this.value==data.hotProduct){
                           this.checked=true
                        }
                    })
                    updateProduct();
                }
            }
        })
    }
    /*修改*/
    function updateProduct(){
        initBrand("update_brand","update_brandName");
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
                        var brandId = $("#update_brandName",dialog).val();
                        var v_param = {};
                        v_param.productName = v_productName;
                        v_param.price = v_price;
                        v_param.mainImagePath = v_mainImagePath;
                        v_param.producedDate = v_producedDate;
                        v_param.id = id;
                        v_param.brandId = brandId;
                        $.ajax({
                            url:"/product/updateProduct.jhtml",
                            data:v_param,
                            dataType:"json",
                            type:"post",
                            success:function(res){
                                if(res.code==200){
                                    search();
                                }
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
