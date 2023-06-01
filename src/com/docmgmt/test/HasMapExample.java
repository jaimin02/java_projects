package com.docmgmt.test;

import java.util.HashMap;

public class HasMapExample {

	/**
	 * @param args
	 */
	static HashMap<String, Restaurant> resturantByKey;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Restaurant resturant = new Restaurant(1, "Woodlawn super club",
				"Fort Atkinson");
		Restaurant resturant2 = new Restaurant(2, "Sammy's", "Fort Atkinson");
		Restaurant resturant3 = new Restaurant(3, "ColdSpring Inn",
				"Cold Spring");

		resturantByKey =new HashMap<String, Restaurant>();
		resturantByKey.put(""+1, resturant);
		resturantByKey.put(""+2, resturant2);
		resturantByKey.put(""+3, resturant3);

	}

}

class Restaurant {

	public Restaurant(int i, String string, String string2) {
		// TODO Auto-generated constructor stub
		
		
	}
	private Integer id;
	private String name;
	private String address;

	// ...
}