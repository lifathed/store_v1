package cn.lizekang.store.service.serviceImp;

import java.util.List;

import cn.lizekang.store.dao.ProductDao;
import cn.lizekang.store.dao.daoImp.ProductDaoImp;
import cn.lizekang.store.domain.PageModel;
import cn.lizekang.store.domain.Product;
import cn.lizekang.store.service.ProductService;


public class ProductServiceImp implements ProductService {
	ProductDao productdao=new ProductDaoImp();
	@Override
	public List<Product>findNew() throws Exception {		
		return productdao.findNew();
	}

	@Override
	public List<Product> findHot() throws Exception {
		return productdao.findHot();
	}

	public Product findProduct(String pid) throws Exception {
		return productdao.findProduct(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curnum) throws Exception {
		//1创建PageModel对象（目的是计算分页参数）
		int totalRecords=productdao.findTotalRecords(cid);//统计当前分类下商品的总数 select count(*) from product where cid=?
		PageModel pm=new PageModel(curnum, totalRecords, 12);
		//2关联集合 select * from product where cid =? limit ? ,?
		List list=productdao.findProductsByCidWithPage(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3关联url
		pm.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		//1_创建对象
		int totalRecords=productdao.findTotalRecords();
		PageModel pm=new PageModel(curNum, totalRecords, 5);
		//2_关联集合 select * from product limit ? , ?
		List<Product>list=productdao.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		productdao.saveProduct(product);
		
	}
	
}
