package application;


/**
 * The City class represents the locations of distribution centers and customers
 * @author group_0549
 *
 */
class City implements HeapItem
{
    private String city;
    private int cost;
    protected int heapIndex;
    protected City parent;      // points to previous node in the calculated path
    protected boolean inPath;
 
    /**
     * Constructs a city instance with the specified city name and cost.
     * @param city -> The name of the city
     * @param cost -> The cost of reaching this city
     */
    public City(String city, int cost)
    {
        this.city = city;
        this.cost = cost;
    }
    
	/**
	 * Sets the cost of the city.
	 * @param cost -> The cost that will be set for this city
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * Gets the cost of the city
	 * @return -> The cost of the city
	 */
	public int getCost() {
		return this.cost;
	}	
	
	
	
	/**
	 * Sets the cost of the city.
	 * @param cost -> The cost that will be set for this city
	 */
	public void setPath() {
		this.inPath=false;
	}

	
	
	
	
	
	/**
	 * Gets the name of the city
	 * @return -> Name of the city
	 */
	public String getCity() {
		return city;
	}

	public int compareTo(HeapItem other)
    {
      return this.getCost()- ((City) other).getCost();
    }

	public void setHeapIndex(int index) {
		// TODO Auto-generated method stub
		heapIndex = index;
	}

	public int getHeapIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

/*	@Override
	public boolean equals(Object other) {
		
		if(this.city.equals(((City) other).getCity())) return true;
		else return false;
	
	}*/
	
	/**
	 * This method returns the string representation of the City
	 * @return-> the name of the city
	 */
	@Override
	public String toString() {
		return this.city;
	}
}