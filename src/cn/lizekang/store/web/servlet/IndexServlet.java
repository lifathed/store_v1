package cn.lizekang.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lizekang.store.domain.Category;
import cn.lizekang.store.domain.Product;
import cn.lizekang.store.service.ProductService;
import cn.lizekang.store.service.serviceImp.CategoryServiceImp;
import cn.lizekang.store.service.serviceImp.ProductServiceImp;
import cn.lizekang.store.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	@Override
	//分类查询
	//在index.jsp跳转过来是page="/IndexServlet"，没有带什么方法，所以先去BaseServlet找service，没有service就调用excute（详情见BaseServlet代码）
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*//调用业务层功能：获取全部分类信息，返回集合
		CategoryServiceImp CategoryService = new CategoryServiceImp();
		List<Category> list=CategoryService.getAllCats();
		//将返回的集合放入request
		request.setAttribute("allCats", list);*/
		
		//调用业务层查询最新商品,查询最热商品,返回2个集合
		ProductService productService=new ProductServiceImp();
		List<Product> list01=productService.findNew();
		List<Product> list02=productService.findHot();
        //将2个集合放入到request
		request.setAttribute("news", list01);
		request.setAttribute("hot", list02);
		//转发到真实页面				
		return "/jsp/index.jsp";
	}
}
