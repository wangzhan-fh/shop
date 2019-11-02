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

    <title>菜单展示页面</title>
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
                <div class="panel-heading">菜单展示
                    <button class="btn btn-primary" onclick="addResource()"><i class="glyphicon glyphicon-plus"></i>添加</button>
                    <button class="btn btn-danger" onclick="deleteResource()"><i class="glyphicon glyphicon-trash"></i>删除</button>
                    <button class="btn btn-primary" onclick="updateResource()"><i class="glyphicon glyphicon-pencil" ></i>修改</button>
                </div>
                <ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
            </div>
        </div>
    </div>
</div>
<%--新增--%>
<div id="addResource" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">管理模块</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_menuName" placeholder="请输入管理模块...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">菜单类型</label>
            <div class="col-sm-4">
                <input type="radio"  name="add_menuType"  value="1">菜单
                <input type="radio"  name="add_menuType"  value="2" >按钮
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">菜单路径</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_menuUrl" placeholder="请输入菜单路径...">
            </div>
        </div>
    </form>
</div>

<%--修改--%>
<div id="updateResource" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">管理模块</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_menuName" placeholder="请输入管理模块...">
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">菜单类型</label>
            <div class="col-sm-4">
                <input type="radio" name="update_menuType"  value="1">菜单
                <input type="radio" name="update_menuType"  value="2" >按钮
            </div>
        </div>
        <div class="form-group">
            <label  class="col-sm-2 control-label">菜单路径</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_menuUrl" placeholder="请输入菜单路径...">
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
        addDiv=$("#addResource").html();
        updateDiv=$("#updateResource").html();
    })

    //查询所有
    function queryList(){
        $.ajax({
            url:"/resource/resourceList.jhtml",
            type:"post",
            success:function(res){
                var setting = {
                    data: {
                        simpleData: {
                            enable: true
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
    function addResource(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length==1){
            var id = nodes[0].id;
            dialog= bootbox.dialog({
            title: '添加地区',
            message: $("#addResource form"),
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
                        var v_resourceName = $("#add_menuName",dialog).val();
                        var v_resourceType = $("[name='add_menuType']:checked",dialog).val();
                        var v_resourceUrl = $("#add_menuUrl",dialog).val();
                        $.ajax({
                            url:"/resource/addResource.jhtml",
                            data:{
                                "menuName":v_resourceName,
                                "pid":id,
                                "menuType":v_resourceType,
                                "menuUrl":v_resourceUrl
                            },
                            type:"post",
                            dataType:"json",
                            success:function(res){
                                if(res.code=="200"){
                                    var newNode = {id:res.data,name:v_resourceName,pId:id,menuType:v_resourceType,menuUrl:v_resourceUrl};
                                    treeObj.addNodes(nodes[0],newNode);
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
        $("#addResource").html(addDiv);
    }


    //修改
    function updateResource(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length>0){
            var id = nodes[0].id;
            $("#update_menuName").val(nodes[0].name);
            $("#update_menuUrl").val(nodes[0].menuUrl);
            $("[name='update_menuType']").each(function () {
                if ($(this).val() ==nodes[0].menuType) {
                    $(this).attr("checked", true)
                }
            })
        }
        console.log(nodes)

        if(nodes.length==1){
            dialog= bootbox.dialog({
                title: '修改地区',
                message: $("#updateResource form"),
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
                            var update_menuName = $("#update_menuName",dialog).val();
                            var update_menuUrl = $("#update_menuUrl",dialog).val();
                            var update_menuType = $("[name='update_menuType']:checked",dialog).val();
                            $.ajax({
                                url:"/resource/updateResource.jhtml",
                                data:{
                                    "menuName":update_menuName,
                                    "id":id,
                                    "menuUrl":update_menuUrl,
                                    "menuType":update_menuType
                                },
                                type:"post",
                                dataType:"json",
                                success:function(res){
                                    if(res.code=="200"){
                                        if (nodes.length>0) {
                                            nodes[0].name = update_menuName;
                                            nodes[0].menuType = update_menuType;
                                            nodes[0].menuUrl = update_menuUrl;
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
        $("#updateResource").html(updateDiv);
    }



//删除
    function deleteResource(){//通过id删除当前节点 并删除该节点下所有节点
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
                        console.log(ztreeArr)
                     var str = [];
                    for(var i=0;i<ztreeArr.length;i++){
                        str.push(ztreeArr[i].id);
                    }
                    $.ajax({
                        url:"/resource/deleteResource.jhtml",
                        data:{"str":str.toString()},
                        success:function(ref){
                            if(ref.code == 200){
                                for (var i=ztreeArr.length-1;i>=0;i--) {
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