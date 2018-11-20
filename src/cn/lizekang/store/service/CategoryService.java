package cn.lizekang.store.service;

import java.util.List;

import cn.lizekang.store.domain.Category;

public interface CategoryService {
	List<Category> getAllCats()throws Exception;

	void addCats(Category c)throws Exception;
}
