<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/3/24
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/md5/md5-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
<body>
<form id="fm">
    <input type="hidden" name="salt" id="salt">
    <table class="layui-table">
        <tr align="center">
            <td>用户名:</td>
            <td><input type="text" name="userName" placeholder="请输入用户名/手机号/邮箱" onblur="getSalt(this)" /></td>
        </tr>
        <tr align="center">
            <td>密码:</td>
            <td><input type="password" name="userPwd" placeholder="请输入密码" id="pwd"/></td>
        </tr>
        <tr align="center">
            <td colspan="2"><a href="<%=request.getContextPath()%>/auth/user/toAdd" style="color: red">还没有账号?点我去注册!</a><br /></td>
        </tr>
        <tr align="center">
            <td colspan="2">
                <input type="button" value="登录" onclick="login()" /><br />
            </td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">

    //判断当前窗口路径与加载路径是否一致。
    if(window.top.document.URL != document.URL){
        //将窗口路径与加载路径同步
        window.top.location = document.URL;
    }

    //登录方法
    function login(){
        var index = layer.load(1,{shade:0.5});
        var pwd = md5($("#pwd").val());
        var salt = $("#salt").val();
        var md5pwd = md5(pwd + salt);
        $("#pwd").val(md5pwd);
        $.post(
            "<%=request.getContextPath()%>/auth/user/login",
            $("#fm").serialize(),
            function(data){
                if (data.code == 200) {
                    layer.msg(data.msg, {icon: 6}, function(){
                        window.location.href = "<%=request.getContextPath()%>/index/toIndex";
                    });
                    return;
                }
                layer.msg(data.msg, {icon: 5})
                layer.close(index);
            }
        )
        // });
    }

    function getSalt(obj) {
        $.post(
            "<%=request.getContextPath()%>/auth/user/getSalt",
            {"userName": obj.value},
            function(data){
                if (data.code == 200) {
                    $("#salt").val(data.msg);
                }
            }
        )
    }
</script>
</html>
