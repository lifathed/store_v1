package cn.lizekang.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import cn.lizekang.store.domain.Category;
import cn.lizekang.store.domain.User;
import cn.lizekang.store.service.CategoryService;
import cn.lizekang.store.service.serviceImp.CategoryServiceImp;
import cn.lizekang.store.service.serviceImp.UserServiceImp;
import cn.lizekang.store.utils.MailUtils;
import cn.lizekang.store.utils.MyBeanUtils;
import cn.lizekang.store.utils.UUIDUtils;
import cn.lizekang.store.web.base.BaseServlet;

public class UserServlet extends BaseServlet {
	
	

	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//regist UI是注册页面
		//跳转到这个servlet没什么实际意义（相比直接跳转到jsp），但是方便项目管理
		return "/jsp/register.jsp";
	}
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//logint UI是登录页面
		//调用业务层功能：获取全部分类信息，返回集合
		CategoryService CategoryService = new CategoryServiceImp();
		List<Category> list=CategoryService.getAllCats();		
		//将返回的集合放入request
		request.setAttribute("allCats", list);	
		//跳转到这个servlet没什么实际意义（相比直接跳转到jsp），但是方便项目管理
		return "/jsp/login.jsp";
	}

	
	//用户注册
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {	
		//接收表单数据
		Map<String, String[]> map = request.getParameterMap();
        User user = new User();
       
        
		/*// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
		// 1_创建时间类型的转换器
		DateConverter dt = new DateConverter();
		// 2_设置转换的格式
		dt.setPattern("yyyy-MM-dd");
		// 3_注册转换器
		ConvertUtils.register(dt, java.util.Date.class);	
		BeanUtils.populate(user, map);*/
		MyBeanUtils.populate(user, map);
		
		//为用户的其他属性赋值（uid，state，code）	
		user.setUid(UUIDUtils.getId());//生成随机字符串
		user.setState(0);
		user.setCode(UUIDUtils.getCode());//生成激活码
		
		
		//2 调用业务层注册功能
		UserServiceImp userServiceImp = new UserServiceImp();
		try {
			userServiceImp.userRegist(user);
			//3 注册成功，向用户邮箱发送信息，跳转到提示页面
			
			//发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());
			
			request.setAttribute("msg", "用户注册成功，请到邮箱激活！");
			} catch (Exception e) {
			//4 注册失败，跳转到提示页面
			request.setAttribute("msg", "用户注册失败，请重新注册！");
			}	
/*		User user = new User();
		Class clazz = user.getClass();
		BeanUtils.populate(user, map);
		System.out.println(user);
		//遍历map中的数据
		Set<String> keySet = map.keySet();//获取Map对象所有的key值
		Iterator<String> iterator = keySet.iterator();//返回Map里面的全部key的集合，然后去除重复的元素
		while(iterator.hasNext()){ //判断当前元素是否存在，并没有指向的移动
			String str = iterator.next(); //返回当前元素， 并指向下一个元素
			System.out.println(str);
			String[] strs = map.get(str);//获取map中的value值			
			for (String string : strs) {   //foreach (元素类型 元素变量:循环对象)因为str是一个数组，所以需要用循环
				System.out.println(string);
			} 
		
	}*/		
		return "/jsp/info.jsp";
 }
	
	//实现用户激活功能
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取激活码		
		String code=request.getParameter("code");		
		//调用业务层激活功能
		UserServiceImp userService = new UserServiceImp();
		boolean flag=userService.userActive(code);	
		//进行激活信息提示
		if(flag==true){
			//用户激活成功，向request加入提示信息，并跳转到登录页面
			request.setAttribute("msg", "激活成功，请登录！");
			return "/jsp/login.jsp";
		}else{
			//用户激活失败，向request加入提示信息，转发到提示页面
			request.setAttribute("msg", "激活失败，请重新注册！");
			return "/jsp/info.jsp";
		}
	}
	
	
	//登录的实现
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户数据
		User user=new User();
		
		//获得只有username和password的user
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用业务层登录功能
		UserServiceImp userService = new UserServiceImp();
		User user02=null;
		try {
			//用user02不用user的原因：user02是通过select * from user where username=？and password=？查询得来的，具有所有的值而user只有username和password
			user02=userService.userLogin(user);
			//用户登录成功，将数据存入sessio
			//存进session的原因：session的数据是存储在服务器的，而cookie是存在客服端的，接下来登录成功可能在其他页面也要显示user的信息，所以要存在session
			request.getSession().setAttribute("loginUser", user02);
			
			
			
			
			//若将下面两句换成return “/jsp/index.jsp”  则出现显示问题，为什么？？？？？？？？
			
			response.sendRedirect("/store_v1/index.jsp");
			return null;
			
			
			
		} catch (Exception e) {
			//用户登录失败
			String msg = e.getMessage();			
			//向request加入失败的详细信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}	
	}
	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//退出功能
		//清楚session
		request.getSession().invalidate();
		//重定向到首页
		response.sendRedirect("/store_v1/index.jsp");
		return null;
	}
}	

