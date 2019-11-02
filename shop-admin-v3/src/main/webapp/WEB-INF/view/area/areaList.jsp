<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-8-25
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>地区展示页面</title>
    <link rel="stylesheet" href="/js/bootstrap3/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/js/DataTables/css/dataTables.bootstrap.min.css"/>
    <link rel="stylesheet" href="/js/ztree/css/zTreeStyle/zTreeStyle.css">
</head>

<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<%--展示--%>
<div class="container">
    <div style="background-color: #0facd2">

    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">地区展示
                    <button class="btn btn-primary" onclick="addArea()"><i class="glyphicon glyphicon-plus"></i>添加</button>
                    <button class="btn btn-danger" onclick="deleteArea()"><i class="glyphicon glyphicon-trash"></i>删除</button>
                    <button class="btn btn-primary" onclick="updateArea()"><i class="glyphicon glyphicon-pencil" ></i>修改</button>
                </div>
                <ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addArea" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">地区</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_areaName" placeholder="请输入地区...">
            </div>
        </div>
    </form>
</div>

<%--修改--%>
<div id="updateArea" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">地区</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_areaName" placeholder="请输入地区...">
            </div>
        </div>
    </form>
</div>


<script type="text/javascript" src="/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="/js/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/bootbox/bootbox.min.js"></script>
<script type="text/javascript" src="/js/bootbox/bootbox.locales.min.js"></script>
<script type="text/javascript" src="/js/ztree/js/jquery.ztree.core.min.js"></script>
<script type="text/javascript">
    var addDiv;
    var updateDiv;
    $(function(){
        queryList();
        addDiv=$("#addArea").html();
        updateDiv=$("#updateArea").html();
    })

    //查询所有
    function queryList(){
        $.ajax({
            url:"/area/queryList.jhtml",
            type:"post",
            success:function(res){
                var setting = {
                    data: {
                        simpleData: {
                            enable: true,
                            pIdKey:"fatherId",
                        }
                    }
                };
                var zNodes=res.data;
                    $.fn.zTree.init($("#tree"), setting, zNodes);
            },
        })
    }

    //新增
    var dialog;
    function addArea(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
            var id = nodes[0].id;
        if(nodes.length==1){
            var id = nodes[0].id;
            dialog= bootbox.dialog({
            title: '添加地区',
            message: $("#addArea form"),
            size: 'large',
            buttons: {
                cancel: {
                    label: "关闭",
                    className: 'btn-danger',
                },
                confirm: {
                    label: "确定",
                    className: 'btn-primary',
                    callback: function(){
                        var v_areaName = $("#add_areaName",dialog).val();
                        $.ajax({
                            url:"/area/addArea.jhtml",
                            data:{
                                "name":v_areaName,
                                "fatherId":id
                            },
                            type:"post",
                            dataType:"json",
                            success:function(res){
                                if(res.code=="200"){
                                  var newNodes={id:res.data,name:v_areaName,fatherId:id};
                                    treeObj.addNodes(nodes[0],newNodes)
                                }
                            }
                        })
                    }
                }
            }
        });
        }else if(nodes.length>1 ||  nodes.length ==0){
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选一个操作",
                size: 'small',
                title: "提示信息"
            });
        }
        $("#addArea").html(addDiv);
    }


    //修改
    function updateArea(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length>0){
            var id = nodes[0].id;
            $("#update_areaName").val(nodes[0].name);
        }
        if(nodes.length==1){
            dialog= bootbox.dialog({
                title: '修改地区',
                message: $("#updateArea form"),
                size: 'large',
                buttons: {
                    cancel: {
                        label: "关闭",
                        className: 'btn-danger',
                    },
                    confirm: {
                        label: "确定",
                        className: 'btn-primary',
                        callback: function(){
                            var v_areaName = $("#update_areaName",dialog).val();
                            $.ajax({
                                url:"/area/updateArea.jhtml",
                                data:{
                                    "name":v_areaName,
                                    "id":id
                                },
                                type:"post",
                                dataType:"json",
                                success:function(res){
                                    if(res.code=="200"){
                                        if (nodes.length>0) {
                                            nodes[0].name = v_areaName;
                                            treeObj.updateNode(nodes[0]);
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
            });
        }else if(nodes.length>1 || nodes.length ==0){
            bootbox.alert({
                message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选一个操作",
                size: 'small',
                title: "提示信息"
            });
        }
        $("#updateArea").html(updateDiv);
    }



//删除
    function deleteArea(){//通过id删除当前节点 并删除该节点下所有节点
        bootbox.confirm({//弹出确认框
            size: "small",
            message: "确定要删除该地区吗？(该地区下所属地区都会被删除)",
            buttons: {
                confirm: {
                    label: '<i class="glyphicon glyphicon-ok"></i>确定',
                    className: 'btn-danger',
                },
                cancel: {
                    label: '<i class="glyphicon glyphicon-remove"></i>取消',
                    className: 'btn-success',
                }
            },
            callback: function (result) {
                if(result){
                    var treeObj = $.fn.zTree.getZTreeObj("tree");
                    var nodes = treeObj.getSelectedNodes();
                    if(nodes.length>=1){
                     var  ztreeArr= treeObj.transformToArray(nodes);
                     var str = [];
                    for(var i=0;i<ztreeArr.length;i++){
                        str.push(ztreeArr[i].id);
                    }
                    $.ajax({
                        url:"/area/deleteArea.jhtml",
                        data:{"str":str.toString()},
                        success:function(ref){
                            if(ref.code == 200){
                               for(var i=ztreeArr.length-1;i>=0;i--){
                                   treeObj.removeNode(nodes[i]);
                               }
                            }
                        }
                    })
                }else if(nodes.length==0 ){
                        bootbox.alert({
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>只能选一个操作操作",
                            size: 'small',
                            title: "提示信息"
                        });
                    }
            }
            }
        })
    }

</script>
</body>
</html>