package cn.lizekang.store.service;

import java.util.List;

import cn.lizekang.store.domain.Category;
import cn.lizekang.store.domain.PageModel;
import cn.lizekang.store.domain.Product;

public interface ProductService {
	List<Product> findNew()throws Exception;
	List<Product> findHot()throws Exception;
	Product findProduct(String pid)throws Exception;
	PageModel findProductsByCidWithPage(String cid,int curnum)throws Exception;
	PageModel findAllProductsWithPage(int curNum)throws Exception;
	void saveProduct(Product product)throws Exception;
}
