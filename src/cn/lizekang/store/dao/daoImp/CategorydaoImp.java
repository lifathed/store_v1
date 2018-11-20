package cn.lizekang.store.dao.daoImp;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.lizekang.store.dao.Categorydao;
import cn.lizekang.store.domain.Category;
import cn.lizekang.store.utils.JDBCUtils;

public class CategorydaoImp implements Categorydao {

	@Override
	public List<Category> getAllCats() throws Exception{
		String sql="select * from Category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
		 
	}

	@Override
	public void addCats(Category c) throws Exception {
		String sql="insert into category values (? ,?)";
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql,c.getCid(),c.getCname());
	}

}
