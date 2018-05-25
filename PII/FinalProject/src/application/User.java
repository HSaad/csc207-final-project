package application;

import java.io.File;
import java.util.ArrayList;

/**
 * The User class represents all users on the application
 * @author group_0549
 *
 */
public abstract class User {
	private Inventory inventory;
	
	/**
	 * Searches for a product in the inventory
	 * @param p -> The product to be searched
	 * @return -> true if the product exists, false otherwise
	 */
	public boolean searchProducts(Product p){
		//searches for product in product list
		// return true if product exists in product list
		ArrayList<Product> temp = getProductsCat(p.getCategory());
		if (temp.contains(p))
			return true;
		return false;	
	}
	
	/**
	 * This method returns inventory
	 * @return -> inventory
	 */
	protected Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * Gets the list of available products in the specified product category,
	 * sorted by availability in increasing order
	 * @param category -> The desired product category
	 * @return -> The list of products available, sorted by availability in increasing order
	 */
	public ArrayList<Product> getProductsAvailUp(ProductCategory category){
		//returns list of products sorted by availability
		ArrayList<Product> products = getProductsCat(category);
		return inventory.getbyAvailabiltyUp(products);
	}
	
	/**
	 * Gets the list of available products in the specified product category,
	 * sorted by availability in decreasing order
	 * @param category -> The desired product category
	 * @return -> The list of products available, sorted by availability in decreasing order
	 */
	public ArrayList<Product> getProductsAvailDown(ProductCategory category){
		//returns list of products sorted by availability
		ArrayList<Product> products = getProductsCat(category);
		return inventory.getbyAvailabiltyDown(products);
	}
	
	/**
	 * Gets the list of available products in the specified product category
	 * @param category -> The desired product category
	 * @return -> The list of products available
	 */
	public ArrayList<Product> getProductsCat(ProductCategory category){
		//returns list of products sorted by given category
		return inventory.sortByCategory(category);
	}
	
	/**
	 * Gets the list of products available in the specified product category and price range
	 * @param category -> The desired product category
	 * @param min -> The minimum price for the results
	 * @param max -> The maximum price for the results
	 * @return -> The list of products in the specified price range and product category
	 */
	public ArrayList<Product> getProductsPrice(ProductCategory category, int min, int max){
		//returns list of products in the price range
		// any products in the given range or certain products in certain category?
		ArrayList<Product> products = getProductsCat(category);
		return inventory.getByPriceRange(products, min, max);
	}
	
	/**
	 * Gets the list of all products available in the specified price range
	 * @param min -> The minimum price for the results
	 * @param max -> The maximum price for the results
	 * @return -> The list of products in the specified price range
	 */
	public ArrayList<Product> getAllProductsPrice(int min, int max){
		return inventory.getByPriceRange(inventory.getProductList(), min, max);
	}
	
	/**
	 * Gets the list of all products available, sorted by availability in increasing order
	 * @return -> The list of all products, sorted by availability in increasing order
	 */
	public ArrayList<Product> getAllProductsAvailUp(){
		//returns list of products sorted by availability
		return inventory.getbyAvailabiltyUp(inventory.getProductList());
	}
	
	/**
	 * Gets the list of all products available, sorted by availability in decreasing order
	 * @return -> The list of all products, sorted by availability in decreasing order
	 */
	public ArrayList<Product> getAllProductsAvailDown(){
		//returns list of products sorted by availability
		return inventory.getbyAvailabiltyDown(inventory.getProductList());
	}
	

	/**
	 * Logs the user in; creates a session ID
	 * @param username -> The user's username
	 * @param password -> The user's password
	 * @return -> The session ID if login successful, -1 otherwise
	 */
	public abstract int logIn(String username, String password);
	//check username file path then check password in file
	//it should load the product file every time you log in
	/**
	 * Logs the user out; resets the session ID
	 */
	public abstract void logOut();
	/**
	 * Gets the user's session ID
	 * @return -> The user's session ID
	 */
	public abstract int getSessionID();
	
//	public void register(String username, String password, boolean admin) throws UsernameExistsException{
//		File f = new File("src/DataFiles/" + username);
//		if(f.exists() && !f.isDirectory()) { 
//		    throw new UsernameExistsException("This username already exists, please pick another one.");
//		} else{
//			//Shopper shop = new Shopper()
//			this.inventory.getShopperManager().add();
//		}
//		//adds new information and creates new files 
//	}
	//calls loadfile method (if the shopper/admin registers, it creates a new file, if a customer registers it adds information to their existing fil

}
