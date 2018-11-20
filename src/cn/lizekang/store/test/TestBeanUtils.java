package cn.lizekang.store.test;



import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.junit.Test;

import cn.lizekang.store.domain.User;



public class TestBeanUtils {
	@Test
	public void test() throws Exception{
		
		Map<String,String[]> map=new HashMap<String,String[]>();		
		map.put("username", new String[]{"tom"});
		map.put("password", new String[]{"1234"});
		map.put("birthday", new String[]{"1996-04-17"});
		User user = new User();
		
		// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
		// 1_创建时间类型的转换器
		DateConverter dt = new DateConverter();
		// 2_设置转换的格式
		dt.setPattern("yyyy-MM-dd");
		// 3_注册转换器
		ConvertUtils.register(dt, java.util.Date.class);
		
		BeanUtils.populate(user, map);
		System.out.println(user);
	}
}
