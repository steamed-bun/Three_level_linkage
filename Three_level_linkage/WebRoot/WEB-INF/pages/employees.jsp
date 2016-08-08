<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <script type="text/javascript" src="script/jquery-1.7.2.js"></script>
    <!-- 
    	1、获取change事件,获取#city的值， 如果为 “请选择.."时是不发servlet请求的.
    	2、当city change后需要将 empolyee 除第一节点清除 
    	3、如果 #city的值为其它 准备Ajax的请求
    	3.1、url : employeeServlet?method=listDepartment
    		asgs : deptId
    	4、将返回的json数组值加到#department
    	4.1、如果返回的元素个数为 0    提示：“当前城市没有部门”
    	4.2、如果返回的元素个数不为 0  ： 遍历,添加到#department子节点 的 opsition
     -->
    <script type="text/javascript">
    	$(function(){
    		$("#city").change(function(){
				$("#department option:not(:first)").remove();
				var city = $(this).val();
				if(city != ""){
					var url = "employeeServlet?method=listDepartments";
					var args = {"cityId" : city};
					
					$.getJSON( url, args , function(data){
						if(data.length == 0){
							alert("当前城市没有部门");
						}else{
							for(var i = 0; i < data.length ; i++){
								var deptId = data[i].deptId;
								var deptName = data[i].deptName;
								$("#department").append("<option value='" + deptId  + "'>" + deptName +"</option>");
								
							}
						}
					});
				}
    		});
    		
    		$("#department").change(function(){
    			$("#employee option:not(:first)").remove();
    			var deptId = $(this).val();
    			if(deptId != ""){
    			var url = "employeeServlet?method=listEmployees";
    			var args = {"deptId":deptId};
    				$.getJSON(url,args,function(data) {
    					if(data.length == 0){
    						alert("该部门没有员工！");
    					}else{
    						for(var i = 0; i < data.length;i++){
    							var empId = data[i].empId;
    							var empName = data[i].empName;
    							$("#employee").append("<option value='" + empId  + "'>" + empName +"</option>");
    						}
    					}
    					
    				});
    			}
    		});
    		
    		$("#employee").change(function(){
    			var empId = $(this).val();
    			if(empId != ""){
    				var url = "employeeServlet?method=getEmployee";
    				var args = {"empId":empId};
    				$.getJSON(url,args,function(data) {
    					if(data != null){
    						alert(data.empId);
    						alert(data.empName);
    					}
    				});
    			}
    		});
    		
    	})
    	
    
    </script>
    <!--
    	 一旦employee的值改变  将 #empdetails 设置为不可见 再将其<td>节点的text值置为空
    	如果employee == "" 则不发任何请求
    	else 则发送Ajax请求
    	得到返回值后   为#empdetails 的<td>节点的text赋值
    	在设置为可见
    -->
  </head>
  
  <body>
	<center>
	City:
	<select id="city">
		<option value="">请选择..</option>
		<c:forEach items="${cities }" var="city">
			<option value="${city.cityId }">${city.cityName }</option>
		</c:forEach>
	</select>
	
	Department:
	<select id="department">
		<option value="">请选择..</option>
	</select>
	
	Employee:
	<select id="employee">
		<option value="">请选择..</option>
	</select>
	
	<table id="empdetails" style="display: none">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Salary</th>
		</tr>
		<tr>
			<td id="id"></td>
			<td id="name"></td>
			<td id="salary"></td>
		</tr>
		
	</table>
	</center>
  </body>
</html>
