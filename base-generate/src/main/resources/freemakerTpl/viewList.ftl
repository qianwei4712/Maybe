<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<head th:replace="incloude/common::common_header(~{::meta},~{::link})"></head>
<head>
    <title>${functionName}列表</title>
</head>

<body>

<div>
    <div class="layui-fluid">
        <div class="layui-row layui-col-space15">
            <div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-body">
                        <div class="layui-tab layui-tab-brief" lay-filter="component-tabs-brief">
                            <ul class="layui-tab-title">
                                <li class="layui-this">
                                    <a th:href="${r"${adminPath}"} + '/${urlPrefix}/list'">${functionName}列表</a>
                                </li>
                                <li>
                                    <a th:href="${r"${adminPath}"} + '/${urlPrefix}/form'">${functionName}添加</a>
                                </li>
                            </ul>
                            <div class="layui-tab-content">
                                <div class="layui-tab-item layui-show">
                                    <div class="layui-card-body">
                                        <div class="table-reload-btn" style="margin-bottom: 10px;">
                                            <form class="layui-form">
                                                <div class="layui-form-item">
                                                    <div class="layui-inline">
                                                        <label class="layui-form-label">名称：</label>
                                                        <div class="layui-input-inline">
                                                            <input type="text" name="name" id="name" class="layui-input" autocomplete="off">
                                                        </div>
                                                    </div>
                                                    <div class="layui-inline">
                                                        <button type="button" class="layui-btn" data-type="reload"><i class="layui-icon layui-icon-search layuiadmin-button-btn"></i></button>
                                                        <button type="button" class="layui-btn" data-type="reset">重置</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>

                                        <table class="layui-hide" id="table" lay-filter="table"></table>

                                        <script type="text/html" id="table-operate-bar">
                                            <a class="layui-btn layui-btn-xs" lay-event="edit"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i class="layui-icon layui-icon-delete"></i>删除</a>
                                        </script>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div th:replace="incloude/common::common_js"></div>
<div th:replace="incloude/common::layui_config"></div>
<script type="text/javascript" th:inline="none">
    layui.use(['layer','table','form'], function(){
        const table = layui.table;
        const layer = layui.layer;
        const form = layui.form;
        const ${ClassName} = { TableId:"table" };

        //删除方法
        ${ClassName}.Delete = function(data){
            layer.confirm('确定删除数据吗？', function(index){
                $.ajax({
                    url: adminPath + "/${urlPrefix}/delete",
                    data: { "id": data.id },
                    success: function(res){
                        if(res.code === 0){
                            table.reload(${ClassName}.TableId);
                        }else{ layer.msg('删除失败'); }
                    }
                });
                layer.close(index);
            });
        };

        //重载表格
        ${ClassName}.Search = function (){
            var queryData = {};
            queryData['name'] = $("#name").val();
            table.reload(${ClassName}.TableId, {page: { curr: 1 },where: queryData});
        };

        //初始化表格列
        ${ClassName}.InitColumn = function(){
            return [[
                {type: 'numbers', title: '序号'}
                ,{align:'center', title: '操作', fixed: 'right', width:'20%' , toolbar: '#table-operate-bar'}
            ]];
        };

        table.render({
            elem: '#' + ${ClassName}.TableId,
            url: adminPath + '/${urlPrefix}/findByPage',
            cols: ${ClassName}.InitColumn(),
            cellMinWidth: 100,
            request: {
                pageName: 'pageNo',
                limitName: 'pageSize'
            },
            page: true
        });

        var $ = layui.$, active = {
            reload: function(){
                ${ClassName}.Search();
            },
            reset: function(){
                $('#name').val('');
                ${ClassName}.Search();
            }
        };

        //监听行工具事件
        table.on('tool('+${ClassName}.TableId+')', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                ${ClassName}.Delete(data);
            } else if(obj.event === 'edit'){
                window.location.href= adminPath + '/${urlPrefix}/form?id='+data.id;
            }
        });

        $('.table-reload-btn .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
</script>

</body>

</html>