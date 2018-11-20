package cn.lizekang.store.dao;

import java.util.List;

import cn.lizekang.store.domain.Category;

public interface Categorydao {
	List<Category> getAllCats()throws Exception;

	void addCats(Category c)throws Exception;
}
