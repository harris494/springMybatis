<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>json交互测试</title>
<script type="text/javascript" src="
${pageContext.request.contextPath }/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	//请求的是json 输出的也是json
	function requestJson() {
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath }/responseJson.action',
			//contentType:'application/json;charset=UTF-8',
			//数据格式是json串,商品信息
			data:{"id":3,"name":"手机","price":999},
			success:function(data){//return json 
				alert(data.id);
				document.write(JSON.stringify(data));
				},

			});
	}
	//请求的是key/value 输出的也是json
	function responseJson() {
		$.ajax({
			type:'post',
			url:'${pageContext.request.contextPath }/responseJson.action',
			//请求是key/value 这里不需要指定contentType,因为默认就是key/value类型
			//contentType:'application/json;charset=utf-8',
			data:'name=手机&price=999',
			success:function(data){//return json 
				alert(data.name);
				document.write(JSON.stringify(data));
				}

			});
	}
</script>
</head>
<body>
	<input type="button" onclick="requestJson()" value="请求的是json 输出的也是json" />
	<input type="button" onclick="responseJson()" value="请求的是key/value 输出的也是json" />
</body>
</html>