<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/3/26
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\js\md5-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>\static\layer-v3.1.1\layer\layer.js"></script>
<script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<body>
    <form id="fm">
        用户名:<input type="text" name="userName" id="userName"><br />
        昵称:<input type="text" name="nickName" id="userName2"><br />
        密码:<input type="password" name="userPwd" id="pwd"><br />
        确认密码:<input type="password" name="userPwd2"><br />
        手机号:<input type="text" name="phone" id="phone"><br />
        邮箱:<input type="text" name="email" id="email"><br />
        级别：
        商户<input type="radio" name="level" value="1" checked>
        管理员<input type="radio" name="level" value="2"><br />
        性别:
        男<input type="radio" name="sex" value="1" checked>
        女<input type="radio" name="sex" value="2"><br />
        <input type="hidden" name="status" value="-1">
        <input type="hidden" name="isDel" value="1">
        <input type="hidden" name="salt" value="${salt}" id="salt">
        <a href="<%=request.getContextPath()%>/auth/user/toLogin">已有账号?前去登录</a><br/>
        <input type="submit" value="注册">
    </form>
</body>
<script type="text/javascript">
    jQuery.validator.addMethod("phone",
        function(value, element) {
            var tel = /^1[3456789]\d{9}$/;
            return tel.test(value)
        }, "请正确填写您的手机号");

    // 判断用户名昵称不同
    jQuery.validator.addMethod("notEqu",
        function(value, element) {
            return value != $("#userName").val();
        }, "用户名昵称需不同!");

    $(function(){
        $("#fm").validate({
            //效验规则
            rules: {
                userName:{
                    required:true,
                    minlength:2,
                    remote: {
                        type: 'GET',
                        url: "<%=request.getContextPath()%>/auth/user/distinct",
                        data:{
                            userName:function() {
                                return $("#userName").val();
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
                nickname:{
                    required:true,
                    minlength:1,
                    notEqu:true
                },
                userPwd:{
                    required:true,
                    minlength:1,
                    digits:true
                },
                userPwd2:{
                    required:true,
                    minlength:1,
                    digits:true,
                    equalTo:"#pwd"
                },
                phone:{
                    required:true,
                    phone:true,
                    digits:true,
                    remote: {
                        type: 'GET',
                        url: "<%=request.getContextPath()%>/auth/user/distinct",
                        data:{
                            phone:function() {
                                return $("#phone").val();
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
                email:{
                    required:true,
                    email:5,
                    remote: {
                        type: 'GET',
                        url: "<%=request.getContextPath()%>/auth/user/distinct",
                        data:{
                            email:function() {
                                return $("#email").val();
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
                userName:{
                    required:"请填写名字",
                    minlength:"最少两个字"
                },
                userPwd:{
                    required:"请填写密码",
                    minlength:"最少1个字",
                    digits:"只能是数字"
                },
                userPwd2:{
                    required:"请确认密码",
                    minlength:"最少1个字",
                    digits:"只能是数字",
                    equalTo:"两次输入密码不同"
                },
                phone:{
                    required:"请填写手机号",
                    rangelength:"11位呀",
                    digits:"只能是数字"
                },
                email:{
                    required:"请填写你的邮箱",
                    email:"邮箱格式不对"
                },
            },
        })
    })

    $.validator.setDefaults({
        submitHandler: function() {
            var index = layer.load(1,{shade:0.5});
            var pwd = md5($("#pwd").val());
            var salt = $("#salt").val();
            var md5pwd = md5(pwd + salt);
            $("#pwd").val(md5pwd);
            $.post("<%=request.getContextPath()%>/auth/user/add",
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
                        parent.window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
                    });
                }
            )
        }
    });
</script>
</html>
