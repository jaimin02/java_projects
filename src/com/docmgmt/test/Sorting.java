package com.docmgmt.test;

import java.util.Scanner;

public class Sorting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner reader = new Scanner(System.in);

		System.out.print("Enter the strings > ");
        String s1 = new String(reader.nextLine());

        String[] str = s1.split(" ");

        for(int i=1;i<str.length;i++){
        	
        	System.out.println(Integer.parseInt(str[i]));
        }
	}

}
