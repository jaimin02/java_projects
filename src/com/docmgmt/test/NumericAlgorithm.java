package com.docmgmt.test;

public class NumericAlgorithm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(encryptNumber(50));
		
	}
	public static int encryptNumber(int number){
		
		int a=number*number;
		a=a+102;
		a=a*2;	
		a=a+number;
		System.out.println(descriptNumber(a));
		return a;
	}
	public static int descriptNumber(int number)
	{
		int a=number-2;
		a/=2;
		a=a-102;
	
		return (int) Math.sqrt(a);
	}

}
