package com.docmgmt.test;

import java.util.Random;

public class MainMethodTest {

	/**
	 * @param args
	 */
	public static int a;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(randomString(-229985452) + " " + randomString(-147909649));
		System.out.println(randomString(-229985452) + " " + randomString(-147909649));

		
	}
	static
    {
        
        a=10;
        //System.exit(0);
    }
	
	public static String randomString(int i)
	{
	    Random ran = new Random(i);
	    StringBuilder sb = new StringBuilder();
	    while (true)
	    {
	        int k = ran.nextInt(27);
	        if (k == 0)
	            break;

	        sb.append((char)('`' + k));
	    }

	    return sb.toString();
	}


}
