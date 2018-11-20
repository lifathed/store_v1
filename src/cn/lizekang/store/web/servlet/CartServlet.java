package cn.lizekang.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.lizekang.store.domain.Cart;
import cn.lizekang.store.domain.CartItem;
import cn.lizekang.store.domain.Product;
import cn.lizekang.store.service.ProductService;
import cn.lizekang.store.service.serviceImp.ProductServiceImp;
import cn.lizekang.store.web.base.BaseServlet;

public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//从session获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(null==cart){
			// 如果获取不到,创建购物车对象,放在session中
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		//如果获取到,使用即可
		String pid=request.getParameter("pid"); //获取到商品id
		int num=Integer.parseInt(request.getParameter("quantity")); //获取到商品数量    
		//通过商品id查询商品对象
		ProductService ProductService=new ProductServiceImp();
		Product product=ProductService.findProduct(pid);	    
	    //获取到待购买的购物项  
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setNum(num);
		//调用购物车上的方法
		cart.addCartItemToCar(cartItem);
	     //重定向到/jsp/cart.jsp
		response.sendRedirect("/store_v1/jsp/cart.jsp");
		return null;
	}
	public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 //获取待删除商品pid
		String pid = request.getParameter("id");
        // 获取到购物车
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		cart.removeCartItem(pid);//调用购物车删除购物项方法
		response.sendRedirect("/store_v1/jsp/cart.jsp");
		return null;
	}
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取购物车
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		//调用购物车的清空方法
		cart.clearCart();
		//重定向到/jsp/cart.jsp页面
		response.sendRedirect("/store_v1/jsp/cart.jsp");
	return null;
	}
}