<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
    <script type="text/javascript">

        $(function(){
            $("#fm").validate({
                //效验规则
                rules: {
                    roleName:{
                        required:true,
                        minlength:2,
                        remote: {
                            type: 'POST',
                            url: "<%=request.getContextPath()%>/role/findByResourceName",
                            data:{
                                userName:function() {
                                    return $("#resourceName").val();
                                },
                                dataType:"json",
                                dataFilter:function(data,type){
                                    if (data == 'true'){
                                        return true;
                                    }else {
                                        return false	;
                                    }
                                }
                            }
                        }
                    },
                },
                messages:{
                    roleName:{
                        required:"名字必填",
                        minlength:"最少两个字儿"
                    },
                },
            })
        })

        $.validator.setDefaults({
            submitHandler: function() {
                if ($("#pId").val() != ${pId}) {
                    layer.msg("上级节点不得修改", {icon: 5});
                    return;
                }
                var index = layer.load(1,{shade:0.5});
                $.post("<%=request.getContextPath()%>/resource/add",
                    $("#fm").serialize(),
                    function(data){
                        if(data.code == -1){
                            layer.close(index);
                            layer.msg(data.msg, {icon: 5});
                            return
                        }
                        layer.msg(data.msg, {
                            icon: 6,
                            time: 2000
                        }, function(){
                            layer.close(index);
                            parent.window.location.href = "<%=request.getContextPath()%>/resource/toList";
                        });
                    }
                )

            }
        });

    </script>
    <!-- 错误时提示颜色 -->
    <style>
        .error{
            color:red;
        }
    </style>
</head>
<body>
<form id="fm">
    <table class="layui-table">
        <tr align="center"><td><input type="text" name="pId" value="${pId}" placeholder="上级节点" id="pId"></td></tr>
        <tr align="center"><td><input type="text" name="resourceName" id="resourceName" placeholder="资源名称"></td></tr>
        <tr align="center"><td><input type="text" name="url" placeholder="URL"></td></tr>
        <tr align="center"><td><input type="text" name="resourceCode" placeholder="资源编码"></td></tr>
        <tr align="center"><td><input type="radio" name="resourceType" value="1" checked>菜单
            <input type="radio" name="resourceType" value="2">按钮</td></tr>
            <input type="hidden" name="isDel" value="1"></td></tr>
        <tr align="center"><td><input type="submit" value="添加"></td></tr>
    </table>
</form>
</body>
</html>