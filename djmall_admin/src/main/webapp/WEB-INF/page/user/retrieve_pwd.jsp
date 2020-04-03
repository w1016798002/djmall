<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/4/3
  Time: 12:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script src="<%=request.getContextPath()%>/static/slideVerify/js/jq-slideVerify.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery.validate.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/md5/md5-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
<body>
    <form id = "fm">
        <table class="layui-table">
            <tr align="center"><td><input type="text" name="phone" id="userPhone" placeholder="请输入已绑定的手机号"></td></tr>
            <tr align="center"><td>图形验证码
            <div class="verify-wrap" id="verify-wrap"></div></td></tr>
            <tr align="center"><td><input type = "text" name = "code" id = "verify" placeholder="短信验证码"/>
            <input type="button"  value="获取验证码" onclick="getNum(this)" /></td></tr>
            <tr align="center"><td><input type="text" name="userPwd" id="pwd" placeholder="新密码"/></td></tr>
            <tr align="center"><td><input type="text" name="userPwd1" placeholder="确认新密码"/></td></tr>
            <tr align="center"><td><input type ="submit" value="确定" >
            <input type="button" onclick="back()" value="取消"></td></tr>
            <tr><td><input type="hidden" name="salt" id="salt" value="${salt}"></td></tr>
        </table>
    </form>
</body>
<script>
    function back() {
        window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
    }
    //验证码时间
    var countdown = 5;
    //图形状态
    var slideFinishState = "";

    //图形
    $(function(){
        console.log(parseFloat('1px'))

        var SlideVerifyPlug = window.slideVerifyPlug;
        var slideVerify = new SlideVerifyPlug('#verify-wrap',{
            wrapWidth:'250',//设置 容器的宽度 ,不设置的话，会设置成100%，需要自己在外层包层div,设置宽度，这是为了适应方便点；
            initText:'请按住滑块，123',  //设置  初始的 显示文字
            sucessText:'验证通过最右边',//设置 验证通过 显示的文字
            getSuccessState:function(res){
                //当验证完成的时候 会 返回 res 值 true，只留了这个应该够用了
                console.log(res);
                slideFinishState = slideVerify.slideFinishState;
            }
        });
        $("#resetBtn").on('click',function(){
            slideVerify.resetVerify();//可以重置 插件 回到初始状态
        })
    })


    //验证码
    function settime(val) {
        if (countdown == 0) {
            val.removeAttribute("disabled");
            val.value="免费获取验证码";
            countdown = 5;
            return;
        } else {
            val.setAttribute("disabled", true);
            val.value="重新发送(" + countdown + ")";
            countdown--;
        }
        setTimeout(function() {
            settime(val)
        },1000)
    }

    //手机验证码
    function getNum(val){
        if(slideFinishState == false){
            layer.msg("请先完成图形验证码!", {icon: 5});
            return;
        }
        settime(val);
        $.post(
            "<%=request.getContextPath()%>/auth/user/sendMessage",
            $("#fm").serialize(),
            function(data) {
                if (data.code == -1) {
                    layer.msg(data.msg, {icon: 5});
                    return;
                }
                layer.msg(data.msg);
                $("#verify").val(data.data);
            }
        )
    }
    //验证
    $(function(){
        $("#fm").validate({
            rules:{
                userPwd:{
                    required:true,
                    rangelength:[3,18],
                    digits:true
                },
                userPwd1:{
                    required:true,
                    rangelength:[3,18],
                    digits:true,
                    equalTo:"#pwd"
                },
                phone:{
                    required:true,
                    isPhone:true
                },
                verify:{
                    required:true
                }
            },
            messages:{
                userPwd:{
                    required:"请填写密码",
                    rangelength:"长度不符合规则",
                    digits:"必须是整数"
                },
                userPwd1:{
                    required:"请填写密码",
                    rangelength:"长度不符合规则",
                    equalTo:"两次密码不统一",
                    digits:"必须是整数"
                },
                phone:{
                    required:"请填写手机号码",
                    isPhone:"手机号有误"
                },
                verify:{
                    required:"请填写验证码"
                }
            }
        })

    })

    // 手机验证
    jQuery.validator.addMethod("isPhone",
        function(value, element) {
            var userPhone = new RegExp(/^1[3578]\d{9}$/);
            return userPhone.test(value)
        }, "请正确填写您的手机号码");

    $.validator.setDefaults({
        submitHandler: function() {
            var index = layer.load(1,{shade:0.5});
            var pwd = md5($("#pwd").val());
            var salt = $("#salt").val();
            var md5pwd = md5(pwd + salt);
            $("#pwd").val(md5pwd);
            $.post("<%=request.getContextPath()%>/auth/user/retrievePwd",
                $("#fm").serialize(),
                function(data){
                    if(data.code == -1){
                        layer.close(index);
                        layer.msg(data.msg, {
                            icon: 5,
                            time: 2000
                        }, function () {
                            window.location.href = "<%=request.getContextPath()%>/auth/user/toRetrievePwd";
                        });
                        return;
                    }
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 2000
                    }, function(){
                        window.location.href = "<%=request.getContextPath()%>/auth/user/toLogin";
                    });
                }
            )
        }
    });
</script>
<style>
    .error{
        color: red;
    }
</style>
</html>

