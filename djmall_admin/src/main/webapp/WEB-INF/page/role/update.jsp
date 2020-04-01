<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/3/30
  Time: 12:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
<body>
<form id = "fm">
    <input type="hidden" name="id" value="${role.id}">
    <table class="layui-table">
        <tr align="center">
            <td>角色名:</td>
            <td><input type="text" name="roleName" value="${role.roleName}"></td>
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
            "<%=request.getContextPath()%>/role/update",
            $("#fm").serialize(),
            function(data){
                if (data.code != -1) {
                    layer.msg(data.msg, {icon: 6}, function(){
                        parent.window.location.href = "<%=request.getContextPath()%>/role/toList";
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
