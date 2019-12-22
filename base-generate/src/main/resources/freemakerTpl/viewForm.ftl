<!DOCTYPE html>
<html lang="zh-CN" xmlns:th="http://www.thymeleaf.org">
<head th:replace="incloude/common::common_header(~{::meta},~{::link})"></head>
<head>
    <title>${functionName}编辑</title>
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
                                <li>
                                    <a th:href="${r"${adminPath}"} + '/${urlPrefix}/list'" >${functionName}列表</a>
                                </li>
                                <li class="layui-this">
                                    <a href="javascript:void(0);">${functionName}编辑</a>
                                </li>
                            </ul>
                            <div class="layui-tab-content">
                                <div class="layui-tab-item layui-show">

                                    <form class="layui-form layui-form-pane" th:action="${r"${adminPath}"} + '/${urlPrefix}/save'">
                                        <input name="id" th:value="${r"${"}${className}${r".id}"}" type="hidden"/>
                                        <div class="layui-form">
                                            <div class="layui-form-item">
                                                <div class="layui-inline">
                                                    <label class="layui-form-label"><em class="gg-star">* </em>id:</label>
                                                    <div class="layui-input-inline">
                                                        <input type="text" name="id" th:value="${r"${"}${className}${r".id}"}" lay-verify="required" class="layui-input" autocomplete="off" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>


                                        <div class="layui-form-item layui-layout-admin">
                                            <div class="layui-input-block">
                                                <div class="layui-footer" style="left: 0;">
                                                    <button class="layui-btn" lay-submit lay-filter="port-form-submit">立即提交</button>
                                                    <input type="button" class="layui-btn  layui-btn-primary" onclick="history.go(-1)" value="返回">
                                                </div>
                                            </div>
                                        </div>
                                    </form>
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
<script th:inline="javascript">
    layui.use(['layer','form'], function(){
        const layer = layui.layer;
        const form = layui.form;

        //表单验证
        form.verify();
        //监听提交
        form.on('submit(port-form-submit)', function(data){
            layer.confirm('确认提交数据？', function(index){
                layer.close(index);
                $('.layui-form').submit();
            });
            return false;
        });
    });

</script>

</body>
</html>