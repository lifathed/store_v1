package cn.lizekang.store.service.serviceImp;

import java.util.List;

import cn.lizekang.store.dao.Categorydao;
import cn.lizekang.store.dao.daoImp.CategorydaoImp;
import cn.lizekang.store.domain.Category;
import cn.lizekang.store.service.CategoryService;
import cn.lizekang.store.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService {

	@Override
	public List<Category> getAllCats() throws Exception {
		Categorydao Categorydao = new CategorydaoImp();
		return Categorydao.getAllCats();
	}

	@Override
	public void addCats(Category c) throws Exception {
		Categorydao Categorydao = new CategorydaoImp();
		//本质是向MYSQL插入一条数据
		Categorydao.addCats(c);
		//更新redis的数据，否则向MYSQL插入一条数据只会改变MySQL数据库的数据，但是不会改变redis的数据
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		jedis.close();
	}

}
