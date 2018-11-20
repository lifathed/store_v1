package cn.lizekang.store.service;

import java.util.List;

import cn.lizekang.store.domain.Order;
import cn.lizekang.store.domain.PageModel;
import cn.lizekang.store.domain.User;

public interface OrderService {

	void saveOrder(Order order)throws Exception;

	PageModel findMyOrdersWithPage(User user, int curnum)throws Exception;

	Order findOrderByOid(String oid)throws Exception;

	void updateOrder(Order order)throws Exception;

	List<Order> findAllOrders()throws Exception;

	List<Order> findAllOrders(String st)throws Exception;

	

}
