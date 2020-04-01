<%--
  Created by IntelliJ IDEA.
  User: 86150
  Date: 2020/3/30
  Time: 10:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
        <tr align="center">
            <td colspan="3">
                <shiro:hasPermission name="ROLE_ADD">
                    <input type="button" value="新增" onclick="add()"><br />
                </shiro:hasPermission>
            </td>
        </tr>
        <tr align="center">
            <td>用户id</td>
            <td>角色名</td>
            <td>操作</td>
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
            "<%=request.getContextPath()%>/role/show",
            {},
            function(data){
                var html = "";
                for (var i = 0; i < data.length; i++) {
                    var r = data[i];
                    html += "<tr align='center'>"
                    html += "<td>"+r.id+"</td>"
                    html += "<td>"+r.roleName+"</td>"
                    html += "<td><shiro:hasPermission name='ROLE_UPDATE'><input type='button' value='修改' onclick='updateById("+r.id+")'></shiro:hasPermission>"
                    html += "<shiro:hasPermission name='ROLE_DEL'><input type='button' value='删除' onclick='delById("+r.id+")'></shiro:hasPermission>"
                    html += "<shiro:hasPermission name='ROLE_RESOURCE'><input type='button' value='关联资源' onclick='addRoleResources("+r.id+")'></shiro:hasPermission></td>"
                }
                $("#tbd").html(html);
            }
        )
    }
    function delById(id){
        var index = layer.load(1,{shade:0.5});
        layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index) {
            $.post(
                "<%=request.getContextPath()%>/role/delById",
                {"id": id},
                function (data) {
                    if (data.code != -1) {
                        layer.msg(data.msg, {icon: 6}, function () {
                            window.location.href = "<%=request.getContextPath()%>/role/toList";
                        });
                        return;
                    }
                    layer.msg(data.msg, {icon: 5})
                    layer.close(index);
                }
            )
        })
        layer.close(index);
    }

    function addRoleResources(roleId){
        layer.open({
            type: 2,
            title: '关联资源页面',
            shadeClose: true,
            shade: 0.8,
            area: ['380px', '90%'],
            content: '<%=request.getContextPath()%>/role/toRoleResList?roleId='+roleId
        });
    }

    function updateById(id){
        layer.confirm('确定修改吗?', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/role/toUpdate?id='+id
            });
        });
    }


    function add(){
        layer.confirm('确定新增吗?', {icon: 3, title:'提示'}, function(index){
            //do something
            layer.open({
                type: 2,
                title: '修改页面',
                shadeClose: true,
                shade: 0.8,
                area: ['380px', '90%'],
                content: '<%=request.getContextPath()%>/role/toAdd'
            });
        });
    }
</script>
</html>
