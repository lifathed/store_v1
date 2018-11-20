package cn.lizekang.store.dao;

import java.util.List;

import cn.lizekang.store.domain.PageModel;
import cn.lizekang.store.domain.Product;


public interface ProductDao {
	List<Product> findNew()throws Exception;
	List<Product> findHot()throws Exception;
	Product findProduct(String pid)throws Exception;
	int findTotalRecords(String cid)throws Exception;//统计当前分类商品的总个数
	List findProductsByCidWithPage(String cid, int startIndex, int pageSize)throws Exception;//统计当前类别当前页的商品信息
	int findTotalRecords()throws Exception;
	List<Product> findAllProductsWithPage(int startIndex, int pageSize)throws Exception;
	 void saveProduct(Product product)throws Exception ;
	
		
	
}
