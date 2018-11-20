package cn.lizekang.store.domain;

import java.util.HashMap;
import java.util.Map;



import java.util.Collection;


/*
 * 购物车：包含不确定个数的购物项
 */
public class Cart {
	private double total=0;
	Map<String,CartItem> map=new HashMap<String,CartItem>();


	// 方法:添加商品到购物车
	public void addCartItemToCar(CartItem cartItem){
		//获取到正在想购物车中添加的商品pid
		String pid = cartItem.getProduct().getPid();
		//将当前的购物项加入购物车之前,判断之前是否买过这类商品
		if(map.containsKey(pid)){
			//如果买过: 获取到原先的数量,获取到本次的数量,相加之后设置到原先购物项上
			CartItem oldItem = map.get(pid);//获取原来的购物项
			oldItem.setNum(oldItem.getNum()+cartItem.getNum());			
		}else{
			//如果没有买过    list.add(cartItem);
			map.put(pid, cartItem);
		}		
	}
	//返回map的值
	public Collection<CartItem> getCartItem(){
		return map.values();
	}
	
	//方法:清空购物车
	public void clearCart(){
		map.clear();
	}
	
	//方法:移除购物车上的购物项
	public void removeCartItem(String pid){
		map.remove(pid);
	}
	
	//总计是可以经过计算获取到
		public double getTotal() {
			//向让总计请0
			total=0;
			//获取到Map中所有的购物项
			Collection<CartItem> values = map.values();
			
			//遍历所有的购物项,将购物项上的小计相加
			for (CartItem cartItem : values) {
				total+=cartItem.getSubTotal();
			}
			
			return total;
		}
}
