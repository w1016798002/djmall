<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/3/30
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath()%>\static\js\jquery.validate.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/layui.all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui-v2.5.5/layui/css/layui.css"  media="all">
<body>
    <form id="fm">
        <table class="layui-table">
            <tr align="center">
                <td>用户名:</td>
                <td>
                    <input type="text" name="roleName" id="roleName">
                    <input type="hidden" name="isDel" value="1">
                </td>
            </tr>
            <tr align="center">
                <td colspan="2"><input type="submit" value="添加"></td>
            </tr>
        </table>
    </form>
</body>
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
                        url: "<%=request.getContextPath()%>/role/findByRoleName",
                        data:{
                            userName:function() {
                                return $("#roleName").val();
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
            var index = layer.load(1,{shade:0.5});
            $.post("<%=request.getContextPath()%>/role/add",
                $("#fm").serialize(),
                function(data){
                    if(data.code == -1){
                        layer.msg(data.msg, {icon: 5});
                        layer.close(index);
                        return
                    }
                    layer.msg(data.msg, {
                        icon: 6,
                        time: 2000
                    }, function(){
                        layer.close(index);
                        parent.window.location.href = "<%=request.getContextPath()%>/role/toList";
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
</html>
