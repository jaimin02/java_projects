package com.docmgmt.test;

import java.util.UUID;

public class GenerateUUID {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 UUID uuid = UUID.randomUUID();
	        String randomUUIDString = uuid.toString();

	        System.out.println("Random UUID String = " + randomUUIDString);
	        System.out.println("UUID version       = " + uuid.version());
	        System.out.println("UUID variant       = " + uuid.variant());
	    }
	

}
