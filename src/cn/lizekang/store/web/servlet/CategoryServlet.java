package cn.lizekang.store.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.lizekang.store.domain.Category;
import cn.lizekang.store.service.CategoryService;
import cn.lizekang.store.service.serviceImp.CategoryServiceImp;
import cn.lizekang.store.utils.JedisUtils;
import cn.lizekang.store.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

public class CategoryServlet extends BaseServlet {
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//在redis中获取全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		//第一次查询不到redis，在数据库中查询
		if(null==jsonStr||"".equals(jsonStr)){
			//调用业务层获取全部分类
			CategoryService CategoryService = new CategoryServiceImp();
			List<Category> list = CategoryService.getAllCats();
			//将全部分类转化成json格式(导包)(在网络上不能直接传Java对象，所以需要转成字符串)
			jsonStr=JSONArray.fromObject(list).toString();			
			//将在数据库中查询到的分类信息存进redis里
			jedis.set("allCats", jsonStr);			
			//将全部分类信息响应到客服端
			response.setContentType("application/json;charset=utf-8");//告诉浏览器本次响应的数据是json格式
			response.getWriter().print(jsonStr);
		}else{
			//将全部分类信息响应到客服端
			response.setContentType("application/json;charset=utf-8");//告诉浏览器本次响应的数据是json格式
			response.getWriter().print(jsonStr);  
		}
		
		
		//因为是Ajax请求，所以不用转发直接响应（所以是return null）
		return null;
	}
}