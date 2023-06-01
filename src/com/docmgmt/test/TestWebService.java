package com.docmgmt.test;

import java.util.Scanner;


public class TestWebService {


	public static void main(String arg[]) {
		Scanner scan = new Scanner(System.in);	
		int inputValue=Integer.parseInt(scan.next());
		String arrNumberString[]={"one","two","three","four","five","six","seven","eight","nine"};
		System.out.println("==>"+arrNumberString[inputValue-1]);
			
	}
}
