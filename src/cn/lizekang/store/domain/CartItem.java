package cn.lizekang.store.domain;
/*
 * 购物项，需要包括(图片路径,商品名称,商品价格,这类商品购买的数量,这类商品总价小计)
 */
public class CartItem {
	private Product product;//携带图片路径,商品名称,商品价格
	private int num;//当前类别商品的数量
	private double subTotal;//小计,当前这类商品总共价格  经过计算获取到
	
	
	public double getSubTotal() {
		return product.getShop_price()*num;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
}
