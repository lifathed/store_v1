package cn.lizekang.store.service.serviceImp;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.List;

import cn.lizekang.store.dao.OrderDao;
import cn.lizekang.store.dao.daoImp.OrderDaoImp;
import cn.lizekang.store.domain.Order;
import cn.lizekang.store.domain.OrderItem;
import cn.lizekang.store.domain.PageModel;
import cn.lizekang.store.domain.User;
import cn.lizekang.store.service.OrderService;
import cn.lizekang.store.utils.JDBCUtils;

public class OrderServiceImp implements OrderService {
	
	OrderDao orderDao = new OrderDaoImp();
	@Override
	public void saveOrder(Order order) throws Exception {
		Connection conn=null;
		try {
			//获取连接
			conn=JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);//土方法理解：设置不自动提交，就是要让两个一起提交，就相当于开启事务
			//保存订单			
			orderDao.saveOrder(conn,order);//事务必须将conn传进去，才能确保同时执行完
			//保存订单项
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn,item);
			}
			//提交
			conn.commit();
		} catch (Exception e) {
			//回滚
			conn.rollback();
		}
	}

	
	/*
	 * 将orderdaoimp查询到的订单list装进pagemodel对象的集合里面
	 * @see cn.lizekang.store.service.OrderService#findMyOrdersWithPage(cn.lizekang.store.domain.User, int)
	 */
	@Override
	public PageModel findMyOrdersWithPage(User user, int curnum) throws Exception {
		//1_创建PageModel对象,目的:计算并且携带分页参数
		int totalRecords=orderDao.getTotalRecords(user);
		PageModel pm = new PageModel(curnum, totalRecords, 3);
		//2_关联集合(将查询到的订单装进pagemodel对象的集合里面)
		List list=orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		OrderDao orderDao = new OrderDaoImp();
		Order order=orderDao.findOrderByOid(oid);
		return order;
	}


	@Override
	public void updateOrder(Order order) throws Exception {
		
		orderDao.updateOrder(order);
	}


	@Override
	public List<Order> findAllOrders() throws Exception {
		return orderDao.findAllOrders();
	}


	@Override
	public List<Order> findAllOrders(String st) throws Exception {
		return orderDao.findAllOrders(st);
	}




}
