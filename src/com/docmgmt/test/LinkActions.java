package com.docmgmt.test;

import java.io.IOException;

public class LinkActions {

	static int a;
	static int b, c,totalCharacter=0;
	
	public static void main(String[] args) throws IOException {
		a=0;
		b=0;
		c=0;
		
		String a="";
		String b="";
		processA(a.trim().toLowerCase());
		ProcessB(b.trim().toLowerCase());
		
		processCalculation();
	}
	
	private static void processCalculation() {
		// TODO Auto-generated method stub
		
		int total=(a+b)/2;
		int per = 0;
		if(total%2==0 && total % 5==0){
				per=88;		
		}
		else if(total%3==0)
		{
			per=80;
		}
		else if(total%5==0){
			per=85;
		}
		else
			per=88;
				
		if(totalCharacter>=12){	
			per=per+(totalCharacter/2);
		}
		else{
			per=per+totalCharacter;
		}
		System.out.println("Percen->"+per);
		
	}

	public static String processA(String str1){		
		for(int i=0;i<str1.length();i++)
		{
			a=a+str1.charAt(i);
			totalCharacter++;		
		}
		System.out.println("Total A-"+a);
		return null;
	}
	private static String ProcessB(String str1) {
		for(int i=0;i<str1.length();i++)
		{
			b=b+str1.charAt(i);
			totalCharacter++;
			
		}
		System.out.println("Total B-"+b);	
		return null;
		
	}
	
	
	
}