<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Insert title here</title>
</head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/zTree_v3/js/jquery.ztree.core.js"></script>
<body>
<div id="treeDemo" class="ztree">

</div>
</body>
<script type="text/javascript">
	var setting = {
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "pid",
			},
			key: {
				name: "resourceName",
				url: "xUrl",
		}
		},
		callback: {
			onClick: function (event, treeId, treeNode) {
				if (!treeNode.isParent) {
					parent.right.location.href = "<%=request.getContextPath()%>" + treeNode.url;
				}
			}
		}
	};

	$(document).ready(function(){
		$.post("<%=request.getContextPath()%>/resource/list", function (data) {
			$.fn.zTree.init($("#treeDemo"), setting, data);
		})
	});

</script>
</html>