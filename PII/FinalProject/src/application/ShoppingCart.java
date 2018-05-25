package application;
import java.util.*;


public class ShoppingCart{

	private final static int LIMIT = 20;
	private static int orderSequence;
	private Shopper shopper;
//	private Customer customer;
	private double total;
	private HashMap<Product, Integer> cart;
	 
	/**
	 * Initialize the shopping cart given shopper
	 * @param shopper -> shopper which is connected to this shopping cart
	 */
	public ShoppingCart(Shopper shopper){
		this.shopper = shopper;
		this.cart = new HashMap<Product, Integer>();
		total = 0;
	}
	
	/**
	 * Returns the shopping cart
	 * @return -> HashMap where the key is a product and the value is the quantity that shopper added to the cart
	 */
	public HashMap<Product, Integer> getCart() {
		return this.cart;
	}

	/**
	 * Returns the shopper connected to this shopping cart
	 * @return -> shopper
	 */
	public Shopper getShopper() {
		return shopper;
	}
	
	/**
	 * Returns the customer that is connected to this shopping cart
	 * @return -> customer
	 */
	public Customer getCustomer() {
		
		return shopper.getCustomer();
	}
	
	/**
	 * Adds product with a given quantity to a shopping cart
	 * @param product -> product that we want to add to the shopping cart
	 * @param quantity -> the quantity of product that we want to add to the shopping cart
	 */
	public void add(Product product, int quantity) throws OutOfStockException {
		//product added to the cart
	
		
		if (product.getQuantity() - product.getReservedQuantity() >= quantity){
			this.cart.put(product, quantity);
			total+=product.getPrice()*quantity;
			this.persistQuantity(product, quantity);
		}
		else {
			throw new OutOfStockException("Oops this product is out of stock. Check back later.");
		}
	}
	
	/**
	 * Changes the quantity in the shopping cart of a given product to newQuantity
	 * @param product -> product that we want to modify
	 * @param qnewQuantity -> the new quantity that we want to set of a given product 
	 */
	public void changeQuantity(Product product, int newQuantity) {
		this.cart.put(product, newQuantity);
	}
	
	/**
	 * Remove product from a shopping cart
	 * @param product -> product that we want to remove from the shopping cart
	 */
	public void remove(Product product){
		//product removed from cart
		if (this.cart.containsKey(product)){
			total-=product.getPrice()*this.cart.get(product);
			product.setReservedQuantity(product.getReservedQuantity() - this.cart.get(product));
			this.cart.remove(product);
		}
	}
	
	/**
	 * Quantity of a product become available to other shoppers
	 * @param product -> product for which we modify reserved quantity
	 */
	public void publicQuantity(Product product){
		//when logs out quantity becomes available to others
		product.setReservedQuantity(product.getReservedQuantity() - this.cart.get(product));
	}
	
	/**
	 * Quantity of a product is reserved for this shopping cart
	 * @param product -> product for which we modify the reserved quantity
	 * @param quantity -> quantity we want to reserve
	 */
	public void persistQuantity(Product product, int quantity) {
		//during the session, a quantity is not available for others
		product.setReservedQuantity(product.getReservedQuantity() + quantity);
	}
	
	/**
	 * Review if the quantity is available in the inventory,
	 * if not then say that the item is out of stock
	 * @param product -> product for which we check the availability
	 */
	public void reviewQuantity(Product product) throws OutOfStockException{
		//when logs back in review the quantity in the cart
		//if the quantity is not available say that it is out of stock
		if (product.getQuantity() < cart.get(product)) {
			this.remove(product);
			throw new OutOfStockException("Oops the product was sold out");
			
			//remove product from file as well
		}
		//if the quantity is available
		else {
			this.persistQuantity(product, cart.get(product));
		}
	}
	
/*	@Override
	public void update(int available) {
		// TODO Auto-generated method stub
		//update inventory, then update file of products
		
	}*/
	
	/**
	 * The shopper checks out
	 * @param DC -> distribution center from where we ship the products
	 */
	public void checkout(DistributionCenter DC){
		ShippingInvoice ship = new ShippingInvoice(this);//generate a new invoice
		ship.setTrackNum(orderSequence);
		//ship.updateInvoices();
		total = 0;
		for (Product product : this.cart.keySet()){
			int quantity = this.cart.get(product);
			//product.setReservedQuantity(product.getReservedQuantity() - quantity);
			//product.setQuantity(product.getQuantity() - quantity);
			//product.getDCquantity().put(DC, product.getDCquantity().get(DC) - quantity);
			//need connection to dc
			//DC.getQuantity().put(product, DC.getQuantity().get(product) - quantity);
			//need to send this info to files
			//distribution center might be different for all the products
			this.shopper.inventory.changeQuantity(product, quantity);
			this.shopper.inventory.changeQuantityDC(product, quantity, DC);
		}
		//call addInvoice
	//	cart.clear();//empty the cart
		//this.shopper.inventory.emptyCart(this.shopper);
	}
	
	/**
	 * The customer with customerID places order for a single product with quantity with the sessionID which belonged to the authentically user
	  * @param customerID -> The customer ID
	 * @param product -> The product 
	 * @param quantity -> The desired quantity
	 * @param sessionID -> A valid sessionID that belongs to an authenticated shopper user.
	 * @return -> The orderSequence if successful.
	 */
	public int placeOrder(int customerID, Product product, int quantity, int sessionID){
		orderSequence++;
		ShippingInvoice ship = new ShippingInvoice(this);
		
		ship.setTrackNum(orderSequence);
		
		ship.updateInvoices(product, quantity);
		total = 0;

		return ship.getTrackNum();
	}
	
	/**
	 * Gives a string representation of the shopping cart
	 * @return -> the string representation of shopping cart
	 */
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		 Set set = cart.entrySet();
		 Iterator iterator = set.iterator();
	      while(iterator.hasNext()) {
	         Map.Entry mentry = (Map.Entry)iterator.next();
	         sb.append((mentry.getKey() +" "+"customer ordered "+ mentry.getValue()) +"\n            ");
	      }
		
		return sb.toString() + "total price is $" + String.format("%.2f", total) +"\n";
		
	}
		
}
