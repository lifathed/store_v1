package cn.lizekang.store.web.servlet;


import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lizekang.store.domain.Category;
import cn.lizekang.store.service.CategoryService;
import cn.lizekang.store.service.serviceImp.CategoryServiceImp;
import cn.lizekang.store.utils.UUIDUtils;
import cn.lizekang.store.web.base.BaseServlet;


public class AdminCategoryServlet extends BaseServlet {
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取全部分类信息
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> allCats = categoryService.getAllCats();
		//全部分类信息放入request
		request.setAttribute("allCats", allCats);
		
		//转发到/admin/category/list.jsp		
		return "/admin/category/list.jsp";
	}
	//addCategoryUI  
	
//	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		 return "/admin/category/add.jsp";
//	}
	
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取分类名称
		//Object cname = request.getAttribute("cname");
		String cname=request.getParameter("cname");
		//创建分类ID
		String id=UUIDUtils.getCode();
		Category c=new Category();
		c.setCid(id);
		c.setCname(cname);
		//调用业务层添加分类功能
		CategoryService categoryService = new CategoryServiceImp();
		categoryService.addCats(c);
		//重定向到查询全部分类信息
		response.sendRedirect("/store_v1/AdminCategoryServlet?method=findAllCats");
		
		return null;
	
	}
	
	
}