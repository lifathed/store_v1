package cn.lizekang.store.dao;

import cn.lizekang.store.domain.User;

public interface Userdao {
	void userRegist(User user)throws Exception ;

	User userActive(String code) throws Exception;

	void updateUser(User user)throws Exception;

	User userLogin(User user)throws Exception;
}
