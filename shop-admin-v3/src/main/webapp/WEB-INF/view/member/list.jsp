<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-10-21
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>会员列表</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">会员查询</div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">会员名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="memberName" placeholder="请输入会员名...">
                            </div>
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" placeholder="请输入真实姓名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">生日</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minTime" placeholder="开始日期..." aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxTime" placeholder="结束日期..." aria-describedby="basic-addon2">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" id="search_area">
                            <label  class="col-sm-2 control-label">地区</label>

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

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info" >
                <div class="panel-heading">会员列表</div>
                <table id="memberTable" class="table table-striped table-hover table-striped table-bordered" >
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">会员名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">手机号</th>
                        <th style="text-align: center;">email</th>
                        <th style="text-align: center;">生日</th>
                        <th style="text-align: center;">籍贯</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">会员名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">手机号</th>
                        <th style="text-align: center;">email</th>
                        <th style="text-align: center;">生日</th>
                        <th style="text-align: center;">籍贯</th>
                    </tr>
                    </tfoot>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/common/script.jsp"></jsp:include>
<script>

    $(function () {
        findList();
        initDate();
        finArea(0);
    })
    function initDate(){
        $("#minTime").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
        $("#maxTime").datetimepicker({
            format: 'YYYY-MM-DD',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
    }
    // 查询sanji下拉框
    function finArea(id,obj){
        if(obj){
            $(obj).parent().nextAll().remove();
        }
        $.ajax({
            url: "/area/findAllById.jhtml",
            type: "post",
            data: {"id": id},
            success: function (res) {
                if (res.code == "200") {
                    var data = res.data;
                    if (data.length == 0) {
                        return;
                    }
                    var addTAreaDiv = '<div class="col-sm-3">';
                    addTAreaDiv += '<select name="searchArea" class="form-control" onchange="finArea(this.value,this)">';
                    addTAreaDiv += '<option value="-1">===请选择===</option>';
                    for (var i = 0; i < data.length; i++) {
                        addTAreaDiv += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }
                    addTAreaDiv += '</select></div>';
                    $("#search_area").append(addTAreaDiv);

                }
            }
        })

    }

    //条件查询
    function search(){
        // 获取参数信息
        var v_userName = $("#memberName").val();
        var v_realName = $("#realName").val();
        var v_minTime = $("#minTime").val();
        var v_maxTime = $("#maxTime").val();
        var a1=$($("select[name='searchArea']")[0]).val();
        var a2=$($("select[name='searchArea']")[1]).val();
        var a3=$($("select[name='searchArea']")[2]).val();

        // 传递参数
        var v_param = {};
        v_param.memberName = v_userName;
        v_param.realName = v_realName;
        v_param.maxTime = v_maxTime;
        v_param.minTime = v_minTime;
        v_param.a1 = a1;
        v_param.a2 = a2;
        v_param.a3 = a3;

        memberTable.settings()[0].ajax.data=v_param;
        memberTable.ajax.reload();
    }
    var memberTable;
    function findList(){
        /* 渲染datatables */
        memberTable = $('#memberTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "aLengthMenu": [5, 10, 15, 20], //更改显示记录数选项
            "bFilter": false, //是否启动过滤、搜索功能
            "processing": true,
            "serverSide": true,
            "ajax": {
                "url": "/member/findList.jhtml",
                "type": "POST"
            },
            "columns": [
                {"data": "memberName"},
                {"data": "realName"},
                {"data": "phone"},
                {"data": "email"},
                {"data": "birthday"},
                {"data": "areaName"},
            ]
        })
    }
</script>
</body>
</html>
