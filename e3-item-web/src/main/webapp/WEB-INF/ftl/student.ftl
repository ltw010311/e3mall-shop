<html>
<head>
	<title>学生信息</title>
</head>
<body>
学生信息
	<p>
		学号：${student.id}<br>
		姓名：${student.name}<br>
		年龄：${student.age}<br>
		地址：${student.address}<br>
	</p>
	<p>
	学生列表：<br>
	<table border="1">
		<tr>
			<th>序号</th>
			<th>学号</th>
			<th>姓名</th>
			<th>年龄</th>
			<th>家庭住址</th>
		</tr>
		<#list stuList as stu>
		<#if stu_index%2 ==0>
			<tr bgcolor="red">
		<#else>
			<tr bgcolor="blue">
		</#if>
				<td>${stu_index}</td>
				<td>${stu.id}</td>
				<td>${stu.name}</td>
				<td>${stu.age}</td>
				<td>${stu.address}</td>
			</tr>
		</#list>
	</table>
	</p>
	日期:${data?date}<br>
	时间:${data?time}<br>
	日期+ 时间:${data?datetime}<br>
	日期+ 时间:${data?string("yyyy/MM/dd HH:mm:ss")}<br>
	<p>
		null的空值：${var!"22222"}<br>
		<#if var??>
			val的值不为空
		<#else>
			val的值为空
		</#if>
	</p>
	<p>
		包含外部的模板
		<#include "hello.ftl">
	</p>
</body>
</html>
