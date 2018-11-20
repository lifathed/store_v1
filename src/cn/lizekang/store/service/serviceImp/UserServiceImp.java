package cn.lizekang.store.service.serviceImp;

import java.sql.SQLException;

import cn.lizekang.store.dao.Userdao;
import cn.lizekang.store.dao.daoImp.UserdaoImp;
import cn.lizekang.store.domain.User;
import cn.lizekang.store.service.UserService;

public class UserServiceImp implements UserService {

	@Override
	public void userRegist(User user)throws Exception {
		Userdao userdao = new UserdaoImp();
		userdao.userRegist(user);
	}

	@Override
	//实现用户激活功能
	public boolean userActive(String code) throws Exception{
		Userdao userdao = new UserdaoImp();
		//向数据库发送一条查询语句判断是否存在这个激活码
		User user=userdao.userActive(code);
		//存在激活码(即可以查询到一个user用户)
		if(null!=user){
			//设置用户的状态和激活码(这两句在内存中执行，数据库还没有更新)
			user.setState(1);
			user.setCode(null);
			//对数据库执行一次更新操作
			userdao.updateUser(user);
			return true;
		}else{
			return false;
		}
	
	}

	@Override
	public User userLogin(User user) throws Exception {
		//利用异常在模块中传递数据
		Userdao userdao = new UserdaoImp();
		User uu=userdao.userLogin(user);
		if(null==uu){
			//用户名不存在的情况在用户填写完用户名后就用Ajax判断了，所以这里只需判断密码错误和用户没激活的情况
			throw new RuntimeException("密码有误");
		}else if(uu.getState()==0){
			throw new RuntimeException("用户未激活");
		}else{
			return uu;
		}
		
	}

	
}
