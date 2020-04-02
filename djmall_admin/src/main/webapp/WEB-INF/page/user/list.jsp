<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/4/1
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
    <table class="layui-table">
        <form id="fm">
            <tr align="center"><td colspan="11">
                <input type="text" name="userName" value="" placeholder="请输入用户名/手机号/邮箱">
            </td></tr>
            <tr align="center"><td colspan="11">
                级别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <c:forEach items="${roleList}" var="r" >
                    <input type="radio" name="roleId" value="${r.id}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${r.roleName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </c:forEach><br>
            </td></tr>
            <tr align="center"><td colspan="11">性别:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="radio" name="sex" value="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;女</td></tr>
            <tr align="center"><td colspan="11"><select name = "status">
                <option value="">请选择</option>
                <option value="-1">未激活</option>
                <option value="1">正常</option>
            </select></td></tr>
            <tr align="center"><td colspan="11">
                <input type="button" value="查询" onclick="search()"><br />
            </td></tr>
        </form>
        <tr align="center"><td colspan="11">
            <shiro:hasPermission name="USER_UPDATE"><input type="button" value="修改" onclick="updateById()"></shiro:hasPermission>
            <shiro:hasPermission name="USER_ACTIVE">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="激活" onclick="updateStatusById()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="USER_DEL"><input type="button" value="删除" onclick="delByIds()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="USER_WARRANT"><input type="button" value="授权" onclick="updateUserRoleById()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
            <shiro:hasPermission name="RESET_PWD"><input type="button" value="重置密码" onclick="resetPwd()"></shiro:hasPermission>
        </td></tr>
        <tr align="center">
            <td></td>
            <td>用户id</td>
            <td>用户名</td>
            <td>昵称</td>
            <td>手机号</td>
            <td>邮箱</td>
            <td>性别</td>
            <td>级别</td>
            <td>状态</td>
            <td>注册时间</td>
            <td>最后登录时间</td>
        </tr>
        <tbody id="tbd">

        </tbody>
    </table>
</body>
<script type="text/javascript">
    $(function(){
        search();
    })

    function search() {
        $.post(
            "<%=request.getContextPath()%>/auth/user/list",
            $("#fm").serialize(),
            function(data){
                var html = "";
                for (var i = 0; i < data.data.length; i++) {
                    var u = data.data[i];
                    html += "<tr align='center'>"
                    html += "<td><input type = 'checkbox' name = 'userId' value = '"+u.userId+"'>";
                    html += "<td>"+u.userId+"</td>"
                    html += "<td>"+u.userName+"</td>"
                    html += "<td>"+u.nickName+"</td>"
                    html += "<td>"+u.phone+"</td>"
                    html += "<td>"+u.email+"</td>"
                    html += u.sex == 1 ? "<td>男</td>" : "<td>女</td>"
                    html += u.roleShow != null ? "<td>"+u.roleShow+"</td>" : "<td>暂无角色</td>"
                    html += u.status == -1 ? "<td>未激活</td>" : "<td>正常</td>"
                    html += "<td>"+u.createTime+"</td>"
                    html += u.lastLoginTime != null ? "<td>"+u.lastLoginTime+"</td>" : "<td>未登录过</td>"
                    html += "</tr>"
                }
                $("#tbd").html(html);
            }
        )
    }
    //重置密码
    function resetPwd() {
        var length = $("input[name='userId']:checked").length;
        if(length <= 0){
            layer.msg('请至少选择一个!', {icon:0});
            return;
        }
        if(length > 1){
            layer.msg('只能选择一个!', {icon:0});
            return;
        }
        var id = $("input[name='userId']:checked").val();
        var index = layer.load(1,{shade:0.5});
        layer.confirm('确定重置密码吗?', {icon: 3, title:'提示'}, function(index){
            $.post("<%=request.getContextPath()%>/auth/user/resetPwd",
                {"id":id},
                function(data){
                    if(data.code == 200){
                        layer.msg(data.msg, {
                            icon: 6,
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                        });
                        return;
                    }
                    layer.msg(data.msg, {icon:5});
                    layer.close(index);
                });
        });
        layer.close(index);
    }
    
    //去修改
    function updateById(){
        var length = $("input[name='userId']:checked").length;
        if(length <= 0){
            layer.msg('请至少选择一个!', {icon:0});
            return;
        }
        if(length > 1){
            layer.msg('只能选择一个!', {icon:0});
            return;
        }
        var id = $("input[name='userId']:checked").val();
        layer.confirm('确定修改吗?', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.close(index);
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/auth/user/toUpdate/'+id
            });
        });
    }

    //激活
    function updateStatusById(){
        var length = $("input[name='userId']:checked").length;
        if(length <= 0){
            layer.msg('请至少选择一个!', {icon:0});
            return;
        }
        if(length > 1){
            layer.msg('只能选择一个!', {icon:0});
            return;
        }
        var id = $("input[name='userId']:checked").val();

        layer.confirm('确定激活吗?', {icon: 3, title:'提示'}, function(index){
            $.post(
                "<%=request.getContextPath()%>/auth/user/updateStatusById",
                {"id" : id, "status" : 1},
                function(data){
                    if (data.code != -1) {
                        layer.msg(data.msg, {icon: 6}, function(){
                            window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                        });
                        return;
                    }
                    layer.msg(data.msg, {icon: 5})
                    layer.close(index);
                }
            )
        });

    }

    //批量刪除
    function delByIds(){
        var length = $("input[name='userId']:checked").length;
        if(length <= 0){
            layer.msg('请至少选择一个!', {icon:0});
            return;
        }
        var str = "";
        $("input[name='userId']:checked").each(function (index, item) {
            if ($("input[name='userId']:checked").length-1==index) {
                str += $(this).val();
            } else {
                str += $(this).val() + ",";
            }
        });
        var id = $("input[name='userId']:checked").val();
        layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
            $.post("<%=request.getContextPath()%>/auth/user/delByIds",
                {"ids":str,"isDel": -1},
                function(data){
                    if(data.code == 200){
                        layer.msg(data.msg, {
                            icon: 6,
                            time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function(){
                            window.location.href = "<%=request.getContextPath()%>/auth/user/toList";
                        });
                        return;
                    }
                    layer.msg(data.msg, {icon:5});
                    layer.close(index);
                });
            layer.close(index);
        });
    }

    //授权
    function updateUserRoleById(){
        var length = $("input[name='userId']:checked").length;
        if(length <= 0){
            layer.msg('请至少选择一个!', {icon:0});
            return;
        }
        if(length > 1){
            layer.msg('只能选取一个!', {icon:0});
            return;
        }
        var id = $("input[name='userId']:checked").val();
        layer.open({
            type: 2,
            title: '修改页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: '<%=request.getContextPath()%>/auth/user/toUpdateRole/'+id
        });
    }
</script>
</html>
