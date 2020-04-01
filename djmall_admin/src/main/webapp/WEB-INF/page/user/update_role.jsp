<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/4/1
  Time: 21:42
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
        <input type="hidden" name="userId" value="${userRole.userId}">
        <table class="layui-table">
            <tr align="center">
                <td>编号</td>
                <td>角色名</td>
            </tr>
            <c:forEach items="${roleList}" var="r">
                <tr align="center">
                    <td>
                        <input type="radio" name="roleId" value="${r.id}" <c:if test="${r.id == userRole.roleId}">checked</c:if>>
                            ${r.id}
                    </td>
                    <td>${r.roleName}</td>
                </tr>
            </c:forEach>
            <tr align="center">
                <td colspan="2"><input type="button" value="确定" onclick="update()"><br /></td>
            </tr>
        </table>
    </form>
</body>
<script type="text/javascript">

    function update (){
        var index = layer.load(1,{shade:0.5});
        $.post(
            "<%=request.getContextPath()%>/auth/user/updateUserRole",
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
