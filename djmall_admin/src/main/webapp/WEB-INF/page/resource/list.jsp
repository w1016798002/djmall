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
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/layer-v3.1.1/layer/layer.js"></script>
<body>
    <shiro:hasPermission name="RESOURCE_ADD">
        <input type="button" value="新增资源" onclick="saveTwo()">
    </shiro:hasPermission>
    <shiro:hasPermission name="RESOURCE_UPDATE">
        <input type="button" value="编辑" onclick="updateRes()">
    </shiro:hasPermission>
    <shiro:hasPermission name="RESOURCE_DEL">
        <input type="button" value="删除" onclick="deleRes()">
    </shiro:hasPermission>
    <div id="tree" class="ztree"></div>
</body>
<script type="text/javascript">
    var treeObj;
    var setting = {
        data: {
            simpleData: {
                enable: true,
                pIdKey: "pid",
            },
            key: {
                name: "resourceName",
                url: "xUrl",

            }
        },

    };

    $(function () {
        $.post(
            "<%=request.getContextPath()%>/resource/show",
            function (data) {
                treeObj = $.fn.zTree.init($("#tree"), setting, data);
            }
        )
    })

    //增二级节点，现在已改，一级二级都可以增
    function saveTwo() {
        var treeOb = $.fn.zTree.getZTreeObj("tree");
        var selectNodes = treeOb.getSelectedNodes();
        // if (selectNodes.length <= 0) {
        //     alert("请选择父节点");
        //     return false;
        // }
        // var pId = selectNodes[0].id;
        if (selectNodes.length > 0) {
            var pId = selectNodes[0].id;
        } else {
            var pId = 0;
        }
        layer.open({
            type: 2,
            titel: "新增",
            area: ["400px", "500px"],
            content: "<%=request.getContextPath()%>/resource/toAdd/" + pId,
            end: function () {
                location.reload();
            }
        });
    }

    function updateRes(value) {
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var selectNode = treeObj.getSelectedNodes();
        if (selectNode.length <= 0) {
            layer.msg("至少选择一个节点操作");
            return false;
        }
        if (selectNode.length > 1) {
            layer.msg("只能选择一个节点操作");
            return false;
        }
        var value = selectNode[0].id;
        layer.open({
            type: 2,
            titel: "修改",
            area: ["400px", "500px"],
            content: "<%=request.getContextPath()%>/resource/toUpdate?id=" + value,
        });
    }

    function deleRes() {
        // 获取选中的节点
        var treeObj = $.fn.zTree.getZTreeObj("tree");
        var selectNode = treeObj.getSelectedNodes()[0];
        if (selectNode == null) {
            layer.msg("至少选择一个节点操作");
            return false;
        }
        // 如果当前选中的节点是 父节点 要获取全部的后代节点信息
        var ids = "";
        if (selectNode.isParent) {
            ids = getChildNode(selectNode);
        }
        ids += selectNode.id;
        layer.msg('确定删除?', {
            time: 0 //不自动关闭
            , btn: ['确定', '取消']
            , yes: function () {
                $.post(
                    "<%=request.getContextPath()%>/resource/delById",
                    {"ids": ids},
                    function (data) {
                        if (data.code == 200) {
                            layer.msg(data.msg, {
                                icon: 6,
                            }, function () {
                                window.location.href = "<%=request.getContextPath()%>/resource/toList";
                            });
                        } else {
                            layer.msg(data.msg, {icon: 5});
                        }
                    })
            }
        })
    }

    //递归自我调用
    function getChildNode(parentNode) {
        var ids = "";
        // 获取子节点
        var childs = parentNode.children;
        for (var i = 0; i < childs.length; i++) {
            ids += childs[i].id + ",";
            if (childs[i].isParent) {
                ids += getChildNode(childs[i]);
            }
        }
        return ids;
    }


</script>
</html>
