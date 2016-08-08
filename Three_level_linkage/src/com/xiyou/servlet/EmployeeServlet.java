package com.xiyou.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.xiyou.bean.City;
import com.xiyou.bean.Department;
import com.xiyou.bean.Employee;
import com.xiyou.dao.BaseDao;

public class EmployeeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this,request , response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private BaseDao baseDao = new BaseDao();
	
	public void list(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "select cityId, cityName from city";
		List<City> cities = baseDao.getForList(sql, City.class);
		request.setAttribute("cities", cities);
		request.getRequestDispatcher("/WEB-INF/pages/employees.jsp").forward(request, response);
	}

	public void listDepartments(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cityId = request.getParameter("cityId");
		String sql = "select deptId, deptName from department where cityid = ?";
		List<Department> depts = baseDao.getForList(sql, Department.class, Integer.parseInt(cityId));
		Gson gson = new Gson();
		String json = gson.toJson(depts);
		System.out.println(json);
		
//		另一种将list转为json 字符串的方法
//		ObjectMapper
		
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
	}
	
	public void listEmployees(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String deptId = request.getParameter("deptId");
		System.out.println(deptId);
		//由于数据库中字段和实体类不匹配 所以必须这样写
		String sql = "select empId, empName,salary from employee where deptid  = ?";
		List<Employee> emps = baseDao.getForList(sql, Employee.class, Integer.parseInt(deptId));
		System.out.println(emps);
		Gson gson = new Gson();
		String json = gson.toJson(emps);
		System.out.println(json);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
	}
	
	public void getEmployee(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String empId = request.getParameter("empId");
		String sql = "select empId , empName, salary from employee where empId = ?";
		Employee emp = baseDao.get(sql, Employee.class, Integer.parseInt(empId));
		Gson gson = new Gson();
		String json = gson.toJson(emp);
		System.out.println(json);
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().print(json);
	}
	
}
