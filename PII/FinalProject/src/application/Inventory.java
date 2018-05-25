package application;

import java.util.*;

/**
 * This class is intended to manage and maintain all instances Product, ProductCategory, User
 * DistributionCenter and is accessed by the Administrator
 * 
 * @author hagr, tatiana, seohee, susan, danny
 *
 */
public class Inventory{
	 private UserManager shopperManager;
	 private ProductManager productManager;
	 public DistributionManager distributionManager;
	 private ArrayList<ProductCategory> categoryList;
	 private ArrayList<Product> productList;
	 private HashMap<ProductCategory, ArrayList<Product>> categoryProductList;
	 
	 /**
	  * This method initializes a new Inventory and loads the products and ProductCategories 
	  * using the ProductManager from the file 
	  */
	public Inventory(){ 
		this.productManager = new ProductManager(this);
		this.shopperManager = new UserManager(this);
	
		
		this.productManager.loadFile();
		
		
		this.categoryList = productManager.getCategoryList();
		this.productList = productManager.getProductList();
		this.categoryProductList = productManager.getCategoryProductList();
	}
	
	/**
	 * This method returns the list of all Customers
	 * @return -> ArrayList of all the customers
	 */
	public ArrayList<Customer> getCustomerList(){
		return this.shopperManager.getCustomerList();
	}
	
	

	public void setDistributionManager(Inventory inventory){
	
		this.distributionManager = new DistributionManager(inventory);
		this.distributionManager.loadFile();
	
	}
	
	
	
	
	
	
	
	
	/**
	 * This method returns the list of all Administrators
	 * @return -> ArrayList of all Administrators 
	 */
	public ArrayList<Administrator> getAdminList() {
		return this.shopperManager.getAdminList();
	}
	
	/**
	 * This method adds the specified ProductCategory to the Product file and the list of all ProductCategory
	 * @param pc -> name of the ProductCategory to be added
	 */
	public void addProductCategory(ProductCategory pc){
		this.productManager.addProductCategory(pc);
	}
	
	/**
	 * This method returns the list of all Product Categories
	 * @return -> ArrayList of all product categories
	 */
	public ArrayList<ProductCategory> getCategoryList() {
		return categoryList;
	}

	/**
	 * This method sets the list of product categories to the one specified
	 * @param categoryList -> the name of the ProductCategory list to be set to
	 */
	public void setCategoryList(ArrayList<ProductCategory> categoryList) {
		this.categoryList = categoryList;
	}

	/**
	 * This method returns the list of all products
	 * @return -> ArrayList of all products 
	 */
	public ArrayList<Product> getProductList() {
		return productList;
	}

	/**
	 * This method returns the list of all Distribution Centers
	 * @return -> ArrayList of all distribution centers
	 */
	public ArrayList<DistributionCenter> getDistributionCenters() {
		return distributionManager.getDistributionCentres();
	}
	
	/**
	 * This method adds the product specified to all distribution centers and the DistributionCentres file
	 * @param p -> the name of the Product to be added
	 */
	public void addProductDistributionCenter(Product p){
		
		
		
	//	if (!this.distributionManager.getDistributionCentres().get(0).getQuantity().containsKey(p))
			this.distributionManager.addProductToFile(p);
	}

	/**
	 * Sets the product list to the productList
	 * @param productList -> the product List
	 */
	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}

	/**
	 * Returns the HashMap where the key is Category and the value is a list of products
	 * @return -> the HashMap of category: list of products in this category
	 */
	public HashMap<ProductCategory, ArrayList<Product>> getCategoryProductList() {
		return categoryProductList;
	}

	/**
	 * Sets the categoryProductList to the hashMap of category: list of products of this category
	 * @param categoryProductList -> hashMap categoryProductList
	 */
	public void setCategoryProductList(HashMap<ProductCategory, ArrayList<Product>> categoryProductList) {
		this.categoryProductList = categoryProductList;
	}
	
	/**
	 * Adds the shopper to the shopper list and shopper's file
	 * @param shopper -> shopper
	 */
	public void addShopper(Shopper shopper){
		this.shopperManager.addShopper(shopper);
	}
	  
  /**
	 * Adds customer to the customer list and to the file
	 * @param shopper -> shopper we want to add
	 */
	public void addCustomer(Shopper shopper){
		
		this.shopperManager.addCustomer(shopper);
		shopper.getCustomer().setCustomerID(shopper.getShopperID());
	}
	
	/**
	 * Adds administrator to the administrator list and to the file
	 * @param admin -> administrator we want to add
	 */
	public void addAdmin(Administrator admin) {
		this.shopperManager.addAdmin(admin);
	}
	
	/**
	 * Gets the information of shopper from file
	 * @param shopper -> shopper
	 */
	public void getFile(Shopper shopper){
		//get file information from the shopper's file
		this.shopperManager.loadFile(shopper.getUsername()); 
	}
	
	/**
	 * Checks if the user is already registered 
	 * @param username -> username
	 * @param pasword -> password 
	 * @param admin -> administrator
	 * @return -> true if the the user is registered, false otherwise.
	 */
	public boolean searchUser(String username, String password, boolean admin) {
		// check if user already registered
		// admin is true if User is admin
		if (!admin) {
			for (Shopper s: shopperManager.getShopperList()) {
				if (s.getUsername().equals(username) && s.getPassword().equals(password)) 
					return true;
			}
		}
		else {
			for (Administrator ad : shopperManager.getAdminList()) {
				if (ad.getUserID().equals(username) && ad.getPassword().equals(password))
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the product's quantity in the distribution center
	 * @param productID -> productID
	 * @param center -> Distribution Center
	 * @return -> the quantity in the distribution center if the product exists and false otherwise.
	 */
	public int getProductNumber(int productID, String center) {
		for (DistributionCenter d: this.distributionManager.getDistributionCentres()) {
			if (d.getCity().equals(center)) {
				for (Product p : d.getQuantity().keySet())
					if (p.getID() == productID) return d.getQuantity().get(p);
			}
		}
		return -1;
	}

//	//save a shopper's cart to a shopper file
//	public void saveCart(Shopper shopper){
//		this.shopperManager.saveCart(shopper.getShoppingCart());
//	}
//	
//	//empty a shopper's cart in the shopper's file
//	public void emptyCart(Shopper shopper){
//		this.shopperManager.emptyCart();
//	}
	
	//empty a shopper's cart in the shopper's file
/*	public void emptyCart(Shopper shopper){
		this.shopperManager.emptyCart();
	}*/
	
	//add invoice in the shopper's file
	/**
	 * Adds invoice to the shopper's file
	 * @param shopper -> shopper
	 * @param invoice -> Shipping invoice we want to add
	 */
	public void addInvoice(Shopper shopper, ShippingInvoice invoice){
		this.shopperManager.addInvoice(shopper, invoice);
	}
	
	//change quantity of a product in the product file
	/**
	 * Changes the quantity of the product in the product file
	 * @param product -> product
	 * @param quantity -> quantity
	 */
	public void changeQuantity(Product product, int quantity){
		int newQuantity = product.getQuantity() - quantity;
		product.setQuantity(newQuantity);
		product.setReservedQuantity(product.getReservedQuantity() - quantity);
		this.productManager.changeProductQuantity(product, newQuantity);
	}
	
	//change quantity of a product in the distribution center file
	/**
	 * Changes the quantity of product in the distribution center file
	 * @param product -> product
	 * @param quantity -> quantity
	 * @param DC -> Distribution Center
	 */
	public void changeQuantityDC(Product product, int quantity, DistributionCenter DC){
		int newQuantity = DC.getQuantity().get(product) - quantity;
		DC.getQuantity().put(product, newQuantity);
		this.distributionManager.changeProductQuantity(product, newQuantity, DC);
	}
	
	//sort by category
	/**
	 * Gets the list of product in the category
	 * @param productCategory -> product category
	 * @return -> Returns the list of products in given category
	 */
	public ArrayList<Product> sortByCategory(ProductCategory productCategory){
		return this.categoryProductList.get(productCategory);
	}
	
	//update availability
	protected void updateAvailability(double stock, double backord){ 
	}
	
	//returns the list of shoppers
	/**
	 * Gets the list of shoppers
	 * @return -> returns the list of shoppers
	 */
	public ArrayList<Shopper> getShopperList(){ 
		return this.shopperManager.getShopperList();  
	}

	//get the products list sorted by availability in increasing order
	/**
	 * Sorts the products by availability in increasing order
	 * @param products -> list of products
	 * @return -> Returns the sorted list of products by availability in increasing order
	 */
	public ArrayList<Product> getbyAvailabiltyUp(ArrayList<Product> products){
		Collections.sort(this.productList);
		return products;
	}
	
	//get the products list sorted by availability in decreasing order
	/**
	 * Sorts the products by availability in decreasing order
	 * @param products -> list of products
	 * @return -> Returns the sorted list of products by availability in decreasing order
	 */
	public ArrayList<Product> getbyAvailabiltyDown(ArrayList<Product> products){
		Collections.sort(this.productList);
		Collections.reverse(products);
		return products;
	}
	
	//get the products list sorted by price range
	/**
	 * Sorts the products by price range
	 * @param products -> list of products
	 * @param minValue -> minimum value
	 * @param maxValue -> maximum value
	 * @return -> Returns the sorted list of products by price range
	 */
	public ArrayList<Product> getByPriceRange(ArrayList<Product> products,double minValue,double maxValue){
		ArrayList<Product> result = new ArrayList<Product>();
		for (Product product: products){
			if (product.getPrice() <= maxValue && product.getPrice() >= minValue){
				result.add(product);
			}
		}
		return result;
	}
	
	//return product from id
	/**
	 * Returns the product by product ID
	 * @param id -> the product's id
	 * @return -> Returns the product by id
	 */
	public Product getProduct(int id){
		Product prod = null;
		if(this.productList != null){
			for(Product p: this.productList){
				if(p.getID() == id){
					prod = p;
				}
			}
		}
		return prod;
	}
	
	//return product from description
	/**
	 * Gets the product by description
	 * @param d -> description
	 * @return -> Returns the product found by description
	 */
		public Product getProductByDescription(String d){
			
			for(Product p: this.productList){
				if(p.getDescription().equals(d)){
					return p;
				}
			}
			return null;
		}
	
	// return product if given product name is in given list
	/**
	* Search product by name
	* @param cat -> product category
	* @param description -> description of product
	* @return -> Returns the product found by name
	*/
	protected Product searchProductByName (String cat, String description) {
		ArrayList<Product> products;
		for (ProductCategory pCat : categoryProductList.keySet()) {	// set of categories	
			if (pCat.getDescription().equals(cat)) {	// found given category
				products = sortByCategory(pCat);	// list of products by category description
				for (Product p : products) 
					if (p.getDescription().equals(description))
						return p;
			}
		}
		
		return null;
	}
	
	/**
	 * Adds product to the product file
	 * @return -> Adds product to the product file
	 */
	protected void addProduct(Product p) {
		
		this.productManager.addProduct(p, p.getDescription());
	}
	
	protected void addProductQuantity(Product p) {
		
		this.productManager.addProduct(p, p.getDescription());
	}
	
	
	
	
	
//	protected boolean findDistributionCenter(String city) {
//		return this.distributionManager.getDistributionCentres()
//	}
	/**
	 * Returns the string representation of inventory
	 * @return -> Returns the string representation of inventory
	 */
	@Override
	public String toString(){
		
		return this.productList.toString() + "\n" + this.categoryList.toString() + "\n";
	}



/**
	 * Adds distribution center to a file
	 * @param dc -> Distribution Center
	 */
	protected void addDistributionCenter(DistributionCenter d) {
		boolean exist = false;
		for (DistributionCenter dc : this.getDistributionCenters()){
			if (dc.getCity() == d.getCity()){
				exist = true;
			}
		}
		
		if(!exist){
			this.distributionManager.addDistributionCenter(d, d.getCity());//not sure
		}
	}
	
}