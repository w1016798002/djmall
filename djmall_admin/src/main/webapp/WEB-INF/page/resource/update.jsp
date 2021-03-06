<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
<body>
    <form id = "fm">
        <input type="hidden" name="id" value="${resource.id}"><br />
        <table class="layui-table">
            <tr align="center">
                <td>资源名:</td>
                <td><input type="text" name="resourceName" value="${resource.resourceName}"></td>
            </tr>
            <tr align="center">
                <td>资源路径:</td>
                <td><input type="text" name="url" value="${resource.url}"></td>
            </tr>
            <tr align="center">
                <td colspan="2"><input type="button" value="修改提交" onclick="update()"></td>
            </tr>
        </table>
    </form>
</body>
<script type="text/javascript">
    function update (){
        var index = layer.load(1,{shade:0.5});
        $.post(
            "<%=request.getContextPath()%>/resource/update",
            $("#fm").serialize(),
            function(data){

                if (data.code != -1) {
                    layer.msg(data.msg, {icon: 6}, function(){
                        parent.window.location.href = "<%=request.getContextPath()%>/resource/toList";
                    });
                    return;
                }
                layer.msg(data.msg, {icon: 5})
                layer.close(index);

            }
        )
    }
</script>
</html>