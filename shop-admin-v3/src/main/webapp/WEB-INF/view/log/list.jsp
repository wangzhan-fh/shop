<%--
  Created by IntelliJ IDEA.
  User: whangzhan
  Date: 2019-9-8
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="/common/head.jsp"></jsp:include>
    <title>日志记录</title>
</head>
<body>
<jsp:include page="/common/nav.jsp"></jsp:include>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info">
                <div class="panel-heading">日志查询</div>
                <div class="panel-body">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">用户名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="userName" placeholder="请输入用户名...">
                            </div>
                            <label class="col-sm-2 control-label">真实姓名</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="realName" placeholder="请输入真实姓名...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">状态</label>
                            <div class="col-sm-4">
                                <select id="status" class="form-control">
                                    <option value="-1">===请选择===</option>
                                    <option value="1">===成功===</option>
                                    <option value="2">===失败===</option>
                                </select>
                            </div>
                            <label class="col-sm-2 control-label">操作信息</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="content" placeholder="请输入操作信息...">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label">操作日期</label>
                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="minTime" placeholder="开始日期..." aria-describedby="basic-addon2">
                                    <span class="input-group-addon"><i class="glyphicon glyphicon-transfer"></i></span>
                                    <input type="text" class="form-control" id="maxTime" placeholder="结束日期..." aria-describedby="basic-addon2">
                                </div>
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

    <div class="row">
        <div class="col-md-12">
            <div class="panel panel-info" >
                <div class="panel-heading">日志列表</div>
                <table id="logTable" class="table table-striped table-hover table-striped table-bordered" >
                    <thead>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">用户名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">操作信息</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">操作信息</th>
                        <th style="text-align: center;">错误信息</th>
                        <th style="text-align: center;">操作时间</th>
                        <th style="text-align: center;">详情</th>
                    </tr>
                    </thead>
                    <tbody id="tbody" style="text-align: center;font-weight: bold;" ></tbody>
                    <tfoot>
                    <tr class="active">
                        <th style="text-align: center;">选择</th>
                        <th style="text-align: center;">用户名</th>
                        <th style="text-align: center;">真实姓名</th>
                        <th style="text-align: center;">操作信息</th>
                        <th style="text-align: center;">状态</th>
                        <th style="text-align: center;">执行信息</th>
                        <th style="text-align: center;">错误信息</th>
                        <th style="text-align: center;">操作时间</th>
                        <th style="text-align: center;">详情</th>
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
    })
    function initDate(){
        $("#minTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
        $("#maxTime").datetimepicker({
            format: 'YYYY-MM-DD HH:mm',
            locale: moment.locale('zh-CN'),
            showClear:true,
        });
    }

    //条件查询
    function search(){
        // 获取参数信息
        var v_userName = $("#userName").val();
        var v_realName = $("#realName").val();
        var v_minTime = $("#minTime").val();
        var v_maxTime = $("#maxTime").val();
        var v_status = $("#status").val();
        var content = $("#content").val();
        // 传递参数
        var v_param = {};
        v_param.userName = v_userName;
        v_param.realName = v_realName;
        v_param.maxTime = v_maxTime;
        v_param.minTime = v_minTime;
        v_param.status = v_status;
        v_param.content = content;
        logTable.settings()[0].ajax.data=v_param;
        logTable.ajax.reload();
    }
    var logTable;
    function findList(){
        /* 渲染datatables */
        logTable = $('#logTable').DataTable({
            "language": {
                "url": "/js/DataTables/Chinese.json"
            },
            "aLengthMenu": [5, 10, 15, 20], //更改显示记录数选项
            "bFilter": false, //是否启动过滤、搜索功能
            "processing": true,
            "serverSide": true,
            "sScrollX": "100%",
            "ajax": {
                "url": "/log/findList.jhtml",
                "type": "POST"
            },
            "columns": [
                {
                    "data": "id",
                    "render": function (data) {//这个data就是咱们查到的pageInfo中的数据集合里的对象
                        return '<input name="ids" type="checkbox" value="' + data + '"/>'
                    }
                },
                {"data": "userName"},
                {"data": "realName"},
                {"data": "content"},
                {
                    "data": "status",
                    "render": function (data, type, row, meta) {
                        return data == 1 ? "成功" : "失败"
                    }
                },
                {"data": "info"},
                {"data": "errorMsg"},
                {"data": "currDate"},
                {"data": "detail"},
            ]
        })
    }
</script>

</body>
</html>
