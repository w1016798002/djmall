<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/4/2
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>小页面</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/md5/md5-min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
</head>
<body>
<form id = "fm"  >
    <input type = "hidden" name = "salt" id="salt" value = "${salt}"/>
    <input type = "hidden" name = "userName"  value = "${userName}"/>
    <table class="layui-table">
        <tr align="center">
            <td>新密码:</td>
            <td><input type="text" name="userPwd"  id = "password"></td>
        </tr>
        <tr align="center">
            <td>请确认密码:</td>
            <td><input type="text" name="userPwd2"  id = "password1"></td>
        </tr>
        <tr align="center">
            <td colspan="2"><input type = "submit" value="确认修改"/></td>
        </tr>
    </table>
</form>
</body>
<script type="text/javascript">
    $(function(){
        $("#fm").validate({
            rules:{
                userPwd:{
                    required:true,
                    minlength:3
                },
                userPwd2:{
                    required:true,
                    equalTo:"#password"
                },
            },
            messages:{
                password:{
                    required:"请填写密码!",
                    minlength:"密码最少3位"
                },
                password1:{
                    required:"请确认密码!",
                    equalTo:"两次输入点密码不一致,请检查后重新输入"
                }
            }
        })
    })
    $.validator.setDefaults({
        submitHandler: function() {
            var index = layer.load(1); //换了种风格
            var pwd = md5($("#password").val());
            var salt = $("#salt").val();
            $("#password").val(md5(pwd + salt));
            $.post(
                "<%=request.getContextPath()%>/auth/user/updatePwd",
                $("#fm").serialize(),
                function(data){
                    if (data.code != 200) {
                        layer.msg(data.msg, {icon: 5, time: 1000}, function(){
                            layer.close(index);
                        });
                        return;
                    }
                    layer.msg(data.msg, {icon: 6, time: 1000}, function(){
                        layer.close(index);
                        parent.window.location.href="<%=request.getContextPath()%>/auth/user/toLogin";
                    })
                })
        }
    });

</script>
<style type="text/css">
    .error{
        color:red;
    }
</style>
</html>
