<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/4/2
  Time: 0:45
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <input type="hidden" name="userId" value="${user.userId}">
        <table class="layui-table">
            <tr align="center">
                <td>用户名:</td>
                <td><input type="text" name="userName" value="${user.userName}"></td>
            </tr>
            <tr align="center">
                <td>手机号:</td>
                <td><input type="text" name="phone" value="${user.phone}"></td>
            </tr>
            <tr align="center">
                <td>邮箱:</td>
                <td><input type="text" name="email" value="${user.email}"></td>
            </tr>
            <tr align="center">
                <td> 性别:</td>
                <td><input type="radio" name="sex" value="1" <c:if test="${user.sex == 1}">checked</c:if>>&nbsp;&nbsp; 男
                    &nbsp;<input type="radio" name="sex" value="2" <c:if test="${user.sex == 2}">checked</c:if>> &nbsp;&nbsp;女</td>
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
            "<%=request.getContextPath()%>/auth/user/update",
            $("#fm").serialize(),
            function(data){
                if (data.code != -1) {
                    layer.msg(data.msg, {icon: 6}, function(){
                        parent.window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
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
