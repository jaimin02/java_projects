package com.docmgmt.test;



//overloading and overriding

 class java_test extends Vehicle {
	 
	public static void main(String args[])
	{
		
		String first_name="abc";
		//testmethod();
		System.out.println("\n"+first_name);
		
		System.out.println(Overloading.add(10, 20));
		System.out.println(Overloading.add(10, 20));
		System.out.println(Overloading.add(20, 30));
		Overloading obj=new Overloading();
		int a=obj.add(10,20);
		System.out.println(a);
		
		Vehicle obj1 = new java_test();
		obj1.run();
	}
	
	void run(){
		System.out.println("Test the process in inner part");
	}
	
}
 class Overloading{
	static int add(int a,int b){
		return a+b;
		
	}
	static int add(int a,int b,int c)
	{
		return a+b;
	}
 }
class Vehicle {
	void run(){
		System.out.println("Test the process");
	}
	
}

	

