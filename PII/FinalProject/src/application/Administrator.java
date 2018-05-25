package application;

import java.util.ArrayList;

/**
 * The Administrator class represents the admin of the application
 * @author group_0549
 *
 */
public class Administrator extends User{
	private static int session;
	private int sessionID;
	private String userID;
	private String password;
	private ProductManager productMan;
	private UserManager shopMan;
	private DistributionManager distributionMan;
	private Inventory inventory;
	private ArrayList<City> city;
	
	/**
	 * Constructs an Administrator instance with the specified user ID and password
	 * @param userID -> The admin's userID
	 * @param pw -> The admin's password
	 */
	public Administrator(String userID, String pw, Inventory inventory) {
		this.userID = userID;
		this.password = pw;
		this.inventory = inventory;
		this.city = new ArrayList<City>();
		//this.productMan = new ProductManager(this.inventory);
		//this.shopMan = new UserManager(this.inventory);
		//this.distributionMan = new DistributionManager(this.inventory);
		session+=2;
		this.sessionID=session;
	}
	
//	public Administrator(String username, String pw, ProductManager pm, ShopperManager sm) {
//		super(username, pw);
//		productMan = pm;
//		shopMan = sm;
//	
//		
//	}
	
	/**
	 * Gets the admin's user ID
	 * @return -> The admin's user ID
	 */
	public String getUserID() {
		return this.userID;
	}
	
	/**
	 * Sets the admin's user ID
	 * @param userID -> The user ID to be set to the admin
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	/**
	 * Gets the admin's password
	 * @return -> The admin's password
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Sets the admin's password
	 * @param password -> The password to be set to the admin
	 */
	public void setUserPassword(String password) {
		this.password = password;
	}
	
	@Override
	public int logIn(String username, String pw) {
		//call log in from user
		session+=2;
		sessionID = session;
		return session;	
	}

	@Override
	public void logOut() {
		sessionID = -2;
	}
	
	/**
	 * Adds a product to the inventory
	 * @param p -> The product to be added to the inventory
	 */
	public void addProduct(Product p){
		productMan.addProduct(p, p.getDescription());
	}
	
	/**
	 * Adds a shopper to the list of shoppers
	 * @param s -> The shopper to be added to the list of shoppers
	 */
	public void addShopper(Shopper s){
		shopMan.addShopper(s);
	}
	
	//we need methods:
	//change product quantity
	/**
	 * Changes the quantity of the specified product
	 * @param product -> The desired product to be modified
	 * @param quantity -> The quantity to be set for the product
	 */
	public void changeProductQuantity(Product product, int quantity){
		this.productMan.changeProductQuantity(product, quantity);
	}
	
	//change description
	/**
	 * Changes the description of the specified product
	 * @param product -> The desired product to be modified
	 * @param description -> The description to be set for the product
	 */
	public void changeProductDescription(Product product, String description){
		this.productMan.changeProductDescription(product, description);
	}
	
	//change price
	/**
	 * Changes the price of the specified product.
	 * @param product -> The desired product to be modified
	 * @param price -> The price to be set for the product
	 */
	public void changeProductPrice(Product product, int price){
		this.productMan.changeProductPrice(product, price);
	}
	
	//change product image
	/**
	 * Changes the image of the specified product
	 * @param product -> The desired product to be modified
	 * @param image -> The image to be set for the product
	 */
	public void changeProductImage(Product product, String image){
		this.productMan.changeProductPicture(product, image);
	}
	
	//add distribution center
	/**
	 * Adds a distribution center to the list of distribution centers.
	 * @param dc -> The distribution center to be added
	 */
	public void addDistributionCenter(DistributionCenter dc){
		//System.out.println(dc.getCity());
		this.inventory.addDistributionCenter(dc);
		
	}
	
	//add product category
	/**
	 * Adds a new product category to the list of product categories.
	 * @param pc -> The product category to be added
	 */
	public void addProductCategory(ProductCategory pc){
		this.inventory.addProductCategory(pc);
		//should save to product category to file
	}
	
	//register method
	/**
	 * 
	 */
	public void register(){
		
	}
	
	/**
	 * Gets the admin's distribution manager
	 * @return -> The admin's distribution manager
	 */
	public DistributionManager getDistributionManager() {
		return distributionMan;
	}
	

	/**
	 * Gets the store's inventory
	 * @return -> The store's inventory
	 */
	public Inventory getInvenory() {
		return this.inventory;
	}
	
	/**
	 * Gets the admin's shopper manager
	 * @return -> The admin's shopper manager
	 */
	public UserManager getShopperManager() {
		return this.shopMan;
	}
	
	/**
	 * Gets the admin's product manager
	 * @return -> The admin's product manager
	 */
	public ProductManager getProductManager(){
		return this.productMan;
	}
	
	
	/**
	 * Gets the list of cities in which distribution centers and shoppers reside
	 * @return -> The list of cities
	 */
	public ArrayList<City> getCity() {
		return city;
	}

	/**
	 * Adds a city to the list of cities
	 * @param city -> The city to be added to the list
	 */
	public void AddCity(City city) {
		this.city.add(city);
	}

	public int getSessionID() {
		return sessionID;
	}
	@Override
	public String toString(){
		
		return this.userID;
	}
	
}
