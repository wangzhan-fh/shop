<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-9-21
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>分类列表</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">分类展示
                    <button class="btn btn-primary" onclick="addType()"><i class="glyphicon glyphicon-plus"></i>添加</button>
                    <button class="btn btn-danger" onclick="deleteType()"><i class="glyphicon glyphicon-trash"></i>删除</button>
                    <button class="btn btn-primary" onclick="updateType()"><i class="glyphicon glyphicon-pencil" ></i>修改</button>
                </div>
                <ul id="tree" class="ztree" style="width:230px; overflow:auto;"></ul>
            </div>
        </div>
    </div>
</div>

<div id="addtype" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">类型名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="add_typename" placeholder="请输入类型名称...">
            </div>
        </div>
    </form>
</div>

<div id="updatetype" style="display: none;">
    <form class="form-horizontal">
        <div class="form-group">
            <label  class="col-sm-2 control-label">类型名称</label>
            <div class="col-sm-4">
                <input type="text" class="form-control" id="update_typename" placeholder="请输入类型名称...">
            </div>
        </div>
    </form>
</div>

<jsp:include page="/common/script.jsp"></jsp:include>
<script>
    var addDiv;
    var updateDivw;
    $(function(){
        initztree();
       addDiv= $("#addtype").html();
       updateDiv= $("#updatetype").html();
    })

    function initztree(){
        $.ajax({
            url:"/types/list.jhtml",
            type:"post",
            success:function (res) {
                if(res.code==200){
                    var setting = {
                        data: {
                            simpleData: {
                                enable: true
                            }
                        }
                    };
                    var zNodes =res.data;
                        $.fn.zTree.init($("#tree"), setting, zNodes);
                }
            }

        })






    }

    //添加分类
    var dialog;
    function addType(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length==1){
            dialog= bootbox.dialog({
                title: '添加分类',
                message: $("#addtype form"),
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
                            var name= $("#add_typename",dialog).val();
                            var pid= nodes[0].id
                            $.ajax({
                                url:"/types/addtype.jhtml",
                                data:{
                                    "name":name,
                                    "pId":pid
                                },
                                type:"post",
                                dataType:"json",
                                success:function(res){
                                    if(res.code=="200"){
                                        var newNode = {id:res.data,name:name,pId:pid};
                                     treeObj.addNodes(nodes[0], newNode);
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

        $("#addtype").html(addDiv);
    }


    //修改分类
    function updateType(){
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var nodes = treeObj.getSelectedNodes();
        if(nodes.length==1){
            $("#update_typename").val(nodes[0].name);
            dialog= bootbox.dialog({
                title: '修改分类',
                message: $("#updatetype form"),
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
                            var name= $("#update_typename",dialog).val();
                            var id= nodes[0].id
                            $.ajax({
                                url:"/types/updatetype.jhtml",
                                data:{
                                    "name":name,
                                    "id":id
                                },
                                type:"post",
                                dataType:"json",
                                success:function(res){
                                    if(res.code=="200"){
                                        if (nodes.length>0) {
                                            nodes[0].name = name;
                                            treeObj.updateNode(nodes[0]);
                                        }
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

         $("#updatetype").html(updateDiv);
    }


    function deleteType(){//通过id删除当前节点 并删除该节点下所有节点
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
                            url:"/types/deletetype.jhtml",
                            data:{"str":str},
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
                            message: "<span class='glyphicon glyphicon-exclamation-sign'></span>请选择",
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
