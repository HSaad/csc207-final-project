package application;

import java.util.ArrayList;

public class ShopperTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Project p = new Project();
		
		String[] users = new String[] { "Shopper1", "Shopper2", "Admin1", "Admin2" };
		String[] passwords = new String[] { "pass1", "pass2", "pass3", "pass4" };
		boolean[] userTypes = new boolean[] { false, false, true, true };
		String[] cities = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J" };
		String[] warehouses = new String[] { "A", "E", "I" };
		String[][] edges = new String[][] { { "A", "B" }, { "A", "C" }, { "A", "D" },
				{ "B", "D" }, { "B", "E" }, { "C", "D" }, { "C", "F" },
				{ "D", "E" }, { "D", "G" }, { "E", "H" }, { "F", "G" },
				{ "F", "I" }, { "G", "H" }, { "G", "I" }, { "G", "J" },
				{ "H", "J" }, { "I", "J" } };
		int[] weights = new int[] { 20, 9, 12, 11, 13, 2, 10, 4, 17, 6, 7, 8, 16, 5,
				18, 2, 21 };
		String[] categories = new String[] { "TSHIRT", "BOOK" };
		String[] products = new String[] { "Blue T-Shirt L", "Grey T-Shirt S",
				"Red T-Shirt XL", "Introduction to Java" };
		int[][] prodQtys = new int[][] { { 100, 200, 300 }, { 50, 50, 50 },
				{ 300, 200, 100 }, { 1000, 2000, 3000 } };
		String[] customers = new String[] { "Customer B", "Customer J" };
		String[]streets = new String[] { "123 Bloor St", "123 Yonge St" };
		String[]custCities = new String[] { "B", "J" };
		
		int[] us = new int[4];
		int[] shopperSessions = new int [2];
		
		for (int i = 0; i<cities.length; i++) {
			
			p.del.addCity(cities[i]);
		}
		
		for (int i = 0; i < us.length; i++)
			p.addUser(users[i],passwords[i],userTypes[i]);
		int mySession = p.login(users[2], passwords[2]);
		//System.out.println(mySession);
		for (int i = 0; i<shopperSessions.length; i++)
			shopperSessions[i] = p.login(users[i], passwords[i]);
		int[] catID = new int[2];
		int[] prodIDs = new int[4];
		int[] custIDs = new int[2];
 		for (int i = 0; i<catID.length; i++) {
 			
			catID[i] = p.addCategory(categories[i], mySession);
 		}

		for (int i = 0; i<warehouses.length; i++){
			p.addDistributionCenter(warehouses[i], mySession);
			
		}
	
		for (int i = 0; i < customers.length; i++)
			custIDs[i] = p.addCustomer(customers[i], custCities[i], streets[i], shopperSessions[i]);
		
		for (int i = 0; i<products.length-1; i++){
			prodIDs[i] = p.addProduct(products[i], catID[0], (double)(i+10), mySession);
			
		}
		//System.out.println(p.inventory.getProductList());
		prodIDs[products.length-1] = p.addProduct(products[products.length-1], catID[1], 12, mySession);
		//System.out.println(p.inventory.getDistributionCenters());
		for (int i = 0; i<weights.length; i++) 
			p.addRoute(edges[i][0], edges[i][1], weights[i], mySession);

		for (int j = 0; j<prodQtys.length; j++) {
			for (int i = 0; i<warehouses.length; i++)
				p.updateQuantity(prodIDs[j], warehouses[i], prodQtys[j][i], mySession);
			
		}
		int[] orders = new int[4];
		
		for (int j = 0; j<products.length; j++) {
			//A : RedTshirts =300, BlueT= 100, Grey = 50, Book = 1000;  
			//E : RedTshirts =200, BlueT= 200, Grey = 50, Book = 2000;
			//I : RedTshirts =100, BlueT= 300, Grey = 50, Book = 3000;
			
			
			orders[j] =p.placeOrder(custIDs[0], prodIDs[j], (j+4), shopperSessions[0]);
		}
		
		
		
		for (int j = 0; j<4; j++) {
			//A : RedTshirts =300, BlueT= 100, Grey = 50, Book = 1000;  
			//E : RedTshirts =200, BlueT= 200, Grey = 50, Book = 2000;
			//I : RedTshirts =100, BlueT= 300, Grey = 50, Book = 3000;
			
			System.out.println(p.invoiceAmount(orders[j] , shopperSessions[0]));
			
			
		}

		
		
		
		
		
		
		
		
		
		
//		Project p = new Project();
//		boolean result = p.addUser("Ilir","pass",true);// the result must be true
//		boolean results = p.addUser("Ilir","pass",true);// the result must be false; user "Ilir" exists already
//		int mySession = p.login("Ilir","pass"); // expected result is a positive integer (session id)
//		int catID = p.addCategory("TSHIRT",mySession); // expected result is a positive integer, the category ID you have assigned to the new category "TSHIRT"
//		//System.out.println(catID);
//		int someID = p.addCategory("TSHIRT", mySession); // expected result is -1; "TSHIRT" exists
//		boolean resulte = p.addUser("Johnny","pass",false);// expected result true
//		int johnnySession = p.login("Johnny","pass");//expect a session ID, positive integer, not equal to mySesssion
//		p.addUser("danny", "pass", false);
//		int dannySession = p.login("danny","pass");
//		int someIDk = p.addCategory("TSHIRT",johnnySession);// expect -1 because Johnny is not administrator
//		
//		//System.out.println(someIDk);
//		//p.logout(johnnySession);// now user Johhny cannot do anything else because he logged out. All Johhny data must be saved in the files.
//		//p.logout(mySession);// user Ilir is also out
//		//int temp = p.addCategory("DRESS",mySession);//must fail (return -1) because user Ilir logged out
//		
////		p.addDistributionCenter("Toronto", mySession);
//		p.addDistributionCenter("TORONTO", mySession);
//		p.addDistributionCenter("Quebec", mySession);
//		int prodID = p.addProduct("T", catID, 6.00, mySession);
//		boolean f = p.updateQuantity(prodID, "TORONTO", 1000, mySession);
//		int custID = p.addCustomer("Johnny", "A", "st geroge", johnnySession);
//		//int custID2 = p.addCustomer("danny", "A", "st geroge", dannySession);
//		p.addRoute("A", "Quebec", 3, mySession);
//		p.addRoute("Quebec", "TORONTO", 5, mySession);
//		int ordID = p.placeOrder(custID, prodID, 10, johnnySession);	
//		try {
//			ArrayList<String> route = p.getDeliveryRoute(ordID, johnnySession);
//			System.out.println(route);
//		} catch (HeapEmptyException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (HeapFullException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	
		
	}

}
