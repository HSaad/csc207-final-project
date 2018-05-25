package application;

import java.util.*;

/**
 * The Shipping Invoice class represents shoppers' shipping invoices
 * @author group_0549
 *
 */
public class ShippingInvoice {
	private int orderSequence;
	private ShoppingCart shoppingCart;
	private double totalPrice;
	private HashMap<Product, Integer> productList;  
	
	/**
	 * Initialize the shipping invoice with a given shopping cart
	 * @param shoppingCart -> the shopping cart which is connected to this invoice
	 */
	public ShippingInvoice(ShoppingCart shoppingCart){
		// need to generate tracking number when we actually generate an invoice
		//this.trackNum = trackNum;
		
		this.shoppingCart = shoppingCart;
		this.productList = new HashMap<Product, Integer>(); 
		this.totalPrice = this.getTotalPrice();
		//when you call addinvoice this oesnot work
		//this.updateInvoices();
	}
	
	/**
	 * Gets the shipping invoice's tracking number
	 * @return -> The order's tracking number
	 */
	public int getTrackNum() {
		return orderSequence;
	}
	
	/**
	 * Sets the tracking number to the orderSequence
	 * @param orderSequence -> orderSequence which keeps track of the invoices
	 */
	public void setTrackNum(int orderSequence) {
		this.orderSequence = orderSequence;
	}
	
	/**
	 * Returns the Product list
	 * @return -> the product list
	 */
	public HashMap<Product, Integer> getProductList() {
		// TODO Auto-generated method stub
		return productList;
	}
	
	/**
	 * Gets the total price of the order
	 * @return -> The total price as shown on the shipping invoice
	 */
	public double getTotalPrice() {
		this.calculateTotalPrice();
		this.calculateDeliveryCost();
		return totalPrice;
	}
	
	/**
	 * Sets the total price of the order
	 * @param price -> The price to be set for the shipping invoice
	 */
	public void setTotalPrice(double price) {
		this.totalPrice = price;
	}

	/**
	 * Calculates total price of the order before shipping charges
	 */
	public void calculateTotalPrice(){
		double result = 0;
		for (Product p : this.productList.keySet()){
			result += p.getPrice() * this.productList.get(p);
		}
		this.totalPrice = result;
	}
	
	/**
	 * Updates the lists of shipping invoices for both shopper and admin
	 */
	public void updateInvoices(Product p, int q){
		// add to customer's invoice list
		

		//System.out.println(this.shoppingCart.getCustomer() == null);
		this.productList.put(p, q);
		this.shoppingCart.getCustomer().addInvoice(this);
		
		//update the invoices in shopper's list of invoices
		//update the admin's list of invoices. I think it's enough to do just previous task
		//update a file of shopper 
	}
	
	/**
	 * Calculates the cost of delivery for the order
	 */
	public void calculateDeliveryCost(){
		double result = 0;
		for (Product p : this.productList.keySet()){
			//need to find the distribution centre closest to the shopper
			//calculate distance between shopper and this distribution centre
			//result += 
		}
		this.totalPrice += result;
	}
	
	/**
	 * Returns the string representation of the shipping invoice
	 * @return -> string representation of the shipping invoice
	 */
	@Override
	public String toString(){
		return "Tracking Number: " + orderSequence + ", $" + String.format("%.2f", totalPrice);
	}
}
