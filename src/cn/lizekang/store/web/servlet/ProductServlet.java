package cn.lizekang.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lizekang.store.domain.PageModel;
import cn.lizekang.store.domain.Product;
import cn.lizekang.store.service.ProductService;
import cn.lizekang.store.service.serviceImp.ProductServiceImp;
import cn.lizekang.store.web.base.BaseServlet;

public class ProductServlet extends BaseServlet {
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取商品pid
		String pid = request.getParameter("pid");
		//根据商品pid查询商品信息
		ProductService productService=new ProductServiceImp();
		Product product=productService.findProduct(pid);
		//将获取到的商品放入request
		request.setAttribute("product", product);
		//转发到/jsp/product_info.jsp
		return "/jsp/product_info.jsp";
	}

	public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		//获取cid,num
		String cid = request.getParameter("cid");
		int curnum = Integer.parseInt(request.getParameter("num"));		
		//调用业务层功能:以分页形式查询当前类别下商品信息
		ProductService ProductService = new ProductServiceImp();
		PageModel pm=ProductService.findProductsByCidWithPage(cid,curnum);//返回PageModel对象(1_当前页商品信息2_分页3_url)		
		//将PageModel对象放入request
		request.setAttribute("page", pm);
		//转发到/jsp/product_list.jsp
		return "/jsp/product_list.jsp";
	}
}