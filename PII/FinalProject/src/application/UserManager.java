package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class UserManager{
	private ArrayList<Shopper> shopperList; 	//list of all shopper
	private ArrayList<Administrator> adminList; 	// list of administrators
	private ArrayList<Customer> customerList;
	private HashMap<Shopper, ShoppingCart> shopperCart; //hashmap of shoppers and their cart
	private HashMap<Shopper, ShippingInvoice> invoices;
	private String pathname;	// no need in here
	private Inventory inventory;
	private Shopper shopper;
	private Customer customer;
	
	// need HashMap<username, password> and need to read file for usernames and passwords
	
	public UserManager(Inventory inventory){
		this.shopperList = new ArrayList<Shopper>();
		this.customerList = new ArrayList<Customer>();
		this.adminList = new ArrayList<Administrator>();
		this.shopperCart = new HashMap<Shopper, ShoppingCart>();
		this.invoices = new HashMap<Shopper, ShippingInvoice>();
		this.inventory = inventory;
		this.pathname = "src/DataFiles/";
	}
	//??change the shopping cart??
	//??change the quantity and product in the shopping cart??
	//??remove the product form the shopping cart??
	//add invoice
	//save cart
	//delete cart
	
//	public void emptyCart(Shopper shopper){
//		Path filename = FileSystems.getDefault().getPath(this.pathname + shopper.getUsername() + ".csv");
//		List<String> fileContent;
//		
//		try {
//			fileContent = new ArrayList<>(Files.readAllLines(filename, StandardCharsets.UTF_8));
//			//number of products in the shopping cart
//			int numberProducts = shopper.getShoppingCart().getCart().keySet().size();
//			for (int i = 10; i < numberProducts; i++) {
//			    //fileContent.set(i, ",,");
//			    fileContent.remove(i);
//			}
//			Files.write(filename, fileContent, StandardCharsets.UTF_8);
//			
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}	
//	}
	//review method to get info about the shopping cart from file
	//use load file
	
	
	public void saveCart(Shopper shopper){
		Path filename = FileSystems.getDefault().getPath(this.pathname + shopper.getUsername() + ".csv");
		List<String> fileContent;
		int invoice = 0;
		int size = 0;
		ArrayList<String> invoices = new ArrayList<String>();;
		try {
			fileContent = new ArrayList<>(Files.readAllLines(filename, StandardCharsets.UTF_8));
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals("INVOICES")) {
			    	
			    	invoice = i;
			    	invoices.add(fileContent.get(i));
			    }
			    else if (i > invoice){
			    	invoices.add(fileContent.get(i));
			    }
			}   
			for (int i = 0; i < fileContent.size(); i++) {
			    if (fileContent.get(i).equals("SHOPPINGCART")) {
			    	
			    	if (shopper.getShoppingCart().getCart().size() > 0) {	// if stuff in shopping cart
						ShoppingCart sc = shopper.getShoppingCart();
						
						size = i;
						for (Product key : sc.getCart().keySet()) {
							size++;
							String line = key.getCategory().getDescription() + "," + key.getDescription() + "," + sc.getCart().get(key);
							fileContent.set(size, line);
						}
					}
			        
			    }
			    else if (i > size){
			    	//need to add all the things from the invoices arraylist
			    	//after it adds all the cart items
			    	
			    }
			}

			Files.write(filename, fileContent, StandardCharsets.UTF_8);
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
		
	
	/**
	 * This method adds given shopper's invoice to list of invoices 
	 * @param shopper
	 * @param invoice
	 */
	public void addInvoice(Shopper shopper, ShippingInvoice invoice){
		invoices.put(shopper, invoice);
		//put in file
		FileWriter fw = null;
		
		try{
			fw = new FileWriter(this.pathname + shopper.getUsername() + ".csv", true);
			fw.append("\n");
			fw.append(String.valueOf(invoice.getTrackNum()) + ",");	// record tracking number
			
			for (Product key : invoice.getProductList().keySet()) {
				fw.append(key.getDescription()); // record each product name
				fw.append(": ");
				fw.append(String.valueOf(invoice.getProductList().get(key))); // record the amount that the user bought accordingly
				fw.append(",");
			}
			fw.append(String.valueOf(invoice.getTotalPrice()));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				fw.flush();
				fw.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method returns list of shoppers
	 * @return -> shopper list
	 */
	public ArrayList<Shopper> getShopperList() {
		//returns list of shoppers
		return shopperList;
	}
	
	/**
	 * This method returns list of customers
	 * @return -> customerList
	 */
	public ArrayList<Customer> getCustomerList() {
		//returns list of shoppers
		return customerList;
	}

	/**
	 * This method returns list of administrators
	 * @return -> adminList
	 */
	public ArrayList<Administrator> getAdminList() {
		return adminList;
	}
	
	/**
	 * This method sets the adminList to another adminList
	 */
	public void setAdminList(ArrayList<Administrator> adminList) {
		this.adminList = adminList;
	}
	
	/**
	 * This method sets the list of customers to the customerList
	 */
	public void setCustomerList(ArrayList<Customer> customerList) {
		this.customerList = customerList;
	}
	
	/**
	 * This method sets the list of shoppers to the shopperList
	 */
	public void setShopperList(ArrayList<Shopper> shopperList) {
		//set list of shoppers
		this.shopperList = shopperList;
		this.shopperCart();
	}

	/**
	 * This method returns a hashMap where the key is shopper and the value is their shopping cart
	 * @return -> shopperCart
	 */
	public HashMap<Shopper, ShoppingCart> getShopperCart() {
		//return HashMap of shoppers and their carts
		this.shopperCart();
		return shopperCart;
	}

	/**
	 * This method sets the shopperCart hashMap to the given shopperCart
	 */
	public void setShopperCart(HashMap<Shopper, ShoppingCart> shopperCart) {
		//set HashMap of shoppers and their carts
		this.shopperCart = shopperCart;
	}

	/**
	 * This method sets the shopping cart for every shopper in the shopper list
	 */
	public void shopperCart(){
		//makes the shopper and cart HashMap
		for(Shopper shopper: this.shopperList){
			if(!this.shopperCart.containsKey(shopper)){
				this.shopperCart.put(shopper, shopper.getShoppingCart());
			}
		}
	}
	
	/**
	 * This method add new shopper to list and save userID and password to file
	 * @param s
	 */
	public void addShopper(Shopper s) {
		if(!shopperList.contains(s)){
			shopperList.add(s);
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(pathname + s.getUsername() +".csv"));
				bw.append(s.getUsername());
				bw.newLine();
				bw.append(s.getPassword());
				bw.newLine();
				
/*		//		bw.append("SHOPPINGCART");
				bw.newLine();
				for(Product p: s.getShoppingCart().getCart().keySet()){
					bw.append(p.getCategory().getDescription() + ",");
					bw.append(p.getID() + ",");
					bw.append(s.getShoppingCart().getCart().get(p) + ",");
					bw.newLine();
				
				}
		//		bw.append("INVOICES");	
*/			}
			catch (FileNotFoundException e){
				e.printStackTrace();
			
		    }catch(IOException e){
		    	e.printStackTrace();
		    }finally {
		    	//close file
		    	if(bw != null) {
		    		try {
		    			bw.close();
		    		}catch (IOException e){
		    			e.printStackTrace();
		    		}
		    	}
		    }	
		}
	}
	
	/**
	 * This method adds administrator object to list
	 * @param admin
	 */
	public void addAdmin(Administrator admin) {
		if(!adminList.contains(admin)){
			adminList.add(admin);
			//save to file
			BufferedWriter bw = null;
			try {
				bw = new BufferedWriter(new FileWriter(pathname + admin.getUserID() +".csv"));
				bw.write(admin.getUserID());
				bw.newLine();
				bw.write(admin.getPassword());
				bw.newLine();
			}
			catch (FileNotFoundException e){
				e.printStackTrace();
			
		    }catch(IOException e){
		    	e.printStackTrace();
		    }finally {
		    	//close file
		    	if(bw != null) {
		    		try {
		    			bw.close();
		    		}catch (IOException e){
		    			e.printStackTrace();
		    		}
		    	}
		    }	
		}
	}
	

		
	
	/**
	 * This method add customer to list 
	 * @param c ->  customer object

	 */

	public void addCustomer(Shopper s) {	
		// create a new Customer that is not in list
		this.customerList.add(s.getCustomer());
		this.customer = s.getCustomer();
		this.saveFile(s);
	}
	
	/**
	 * This method records the customer information to a file that this method creates
	 */
	public void saveFile(Shopper s){  
		// customer (record info to file)
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(pathname + s.getUsername()+".csv", true));
			// transfer user info to file
			bw.write(customer.getFirstName());
			bw.newLine();
			bw.write(customer.getCity());
			bw.newLine();
			bw.write(customer.getStreet());
			bw.newLine();
			// end of user info
			
			// record shipping cart
			bw.write("SHOPPINGCART");
			bw.newLine();
			
			if (customer.getShoppingCart().getCart().size() > 0) {	// if stuffs in shopping cart
				ShoppingCart sc = customer.getShoppingCart();
				for (Product key : sc.getCart().keySet()) {
					bw.write(key.getCategory().getDescription());
					bw.write(",");
					bw.write(key.getDescription());
					bw.write(",");
					bw.write(sc.getCart().get(key));
					bw.newLine();
				}
			}
			
			// record shipping invoice
			bw.write("INVOICES");
			bw.newLine();
			
			if (customer.getInvoices().size() > 0) {	// there is invoice
				for (ShippingInvoice invoice: customer.getInvoices()) {
					bw.write(invoice.getTrackNum());	// record tracking number
					bw.write(",");
					for (Product key : invoice.getProductList().keySet()) {
						bw.write(key.getDescription()); // record each product name
						bw.write(": ");
						bw.write(invoice.getProductList().get(key)); // record the amount that the user bought accordingly
						bw.write(",");
					}
					bw.write(String.valueOf(invoice.getTotalPrice()));
				}
			}			
		}
		catch (FileNotFoundException e){
			e.printStackTrace();
		
	    }catch(IOException e){
	    	e.printStackTrace();
	    }finally {
	    	//close file
	    	if(bw != null) {
	    		try {
	    			bw.close();
	    		}catch (IOException e){
	    			e.printStackTrace();
	    		}
	    	}
	    }	
	}
	
	//public void updateShoppingcart(){	
	//}
	
	// in file, shopping cart has a list of products with demand
	/**
	 * This method loads the information of a shopper and sets it to the shopper and adds this shopper to the list of shoppers
	 * @param userID -> the userID of shopper
	 */
	public void loadFile(String userID) {
		BufferedReader br = null;
		String line = "";
	
		String username, pw, firstName, lastName, email, street, phoneNum, creditNum, creditDate, city;
		try{
			br = new BufferedReader(new FileReader(this.pathname+userID+".csv"));
			// initialize Shopper object here
			username = br.readLine();
			pw = br.readLine();
			if(shopper != null && !shopperList.contains(shopper))
				shopperList.add(shopper);	// add to shopper list
			
			if ((line = br.readLine()) != null) {
				if (line.equals("SHOPPINGCART")) {	// shopper
					shopper = new Shopper(username, pw, this.inventory);
					ShoppingCart cart = shopper.getShoppingCart();
					
					while ((line = br.readLine()) != null && !line.equals("INVOICES")) {
						String[] productInfo = line.split(",");
						String cat = productInfo[0].trim();
						String description = productInfo[1].trim();
						int quantity = Integer.parseInt(productInfo[2].trim()); 
						Product p = inventory.searchProductByName(cat, description);
						try {
							cart.add(p, Integer.parseInt(productInfo[2]));
						} catch (OutOfStockException e) {
							System.out.println("Product is out of stock!");
						}
					}
				}
				else {	// customer
					firstName = br.readLine();
					lastName = br.readLine();
					city = br.readLine();
					street = br.readLine();
					email = br.readLine();
					phoneNum = br.readLine();
					creditNum = br.readLine();
					creditDate = br.readLine();
					customer = new Customer(username, pw, firstName, street, city); 
					ShoppingCart cart = customer.getShoppingCart();
					
					if ((line = br.readLine()) != null && line.equals("SHOPPINGCART")) {	
						// initialize shopping cart
						while ((line = br.readLine()) != null && !line.equals("INVOICES")) {
							String[] productInfo = line.split(",");
							String cat = productInfo[0].trim();
							String description = productInfo[1].trim();
							int quantity = Integer.parseInt(productInfo[2].trim()); 
							Product p = inventory.searchProductByName(cat, description);
							try {
								cart.add(p, quantity);
							} catch (OutOfStockException e) {
								System.out.println("Product is out of stock!");
							}
						}
					}
					
					if ((line = br.readLine()) != null && line.equals("INVOICES")) {	
						// initialize invoice
						while ((line = br.readLine()) != null) {
							String[] info = line.split(",");
							String[] productInfo;
							Product product;
							ProductCategory pc = null;
							String category = "";
							int trackNum = Integer.parseInt(info[0]); // tracking number of invoice
							for (int i = 1; i < info.length - 1; i++) {
								productInfo = info[i].split(":");
								String pName = productInfo[1].trim();	// product description
								String pCat = productInfo[0].trim();	// product Category
								product = inventory.searchProductByName(pCat, pName);
								int quantity = Integer.parseInt(productInfo[2]);
								if (product != null)
									try {
										cart.add(product, quantity);
									} catch (OutOfStockException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							} // for loop
							// initialize invoice
							ShippingInvoice invoice = new ShippingInvoice(cart);
							//set tracknum here
							invoice.setTotalPrice(Double.parseDouble(info[info.length-1]));
							customer.addInvoice(invoice); 	// add invoice to customer list of invoices
						}
						
					}
				}
			}
		}	// try
		catch (FileNotFoundException e){
			e.printStackTrace();
		
	    }catch(IOException e){
	    	e.printStackTrace();
	    }finally {
	    	//close file
	    	if(br != null) {
	    		try {
	    			br.close();
	    		}catch (IOException e){
	    			e.printStackTrace();
	    		}
	    	}
	    }
	}	
}
