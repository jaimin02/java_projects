package com.docmgmt.test;

import java.util.Random;

public class ShuffleArray {
    
  public static void main(String[] args) {
    int[] a = new int[] { 1, 2, 3, 4, 5, 6, 7 };
   
    int sp[]={0,4,6};
    
    char find='B';
    int found=0;
    AllCharacter ac=new AllCharacter();
    for(int i=0;i<a.length;i++)
    {
    	if(ac.l[i].charAt(0)==find){
    		System.out.println(ac.l[i]);
    		found=1;
    	}
    	if(found==1 && ac.l[i].charAt(0)!=find){
    		break;
    	}  	
    	
    }
    System.out.println(new Random(System.nanoTime()).nextInt());
    /*
    ArrayList<String> list=new ArrayList<String>();
    list.add("1..Hello");
    list.add("2..Hello");
    list.add("3...Hello");
    list.add("4...Hello");
    
    Collections.shuffle(list);
    System.out.println(list.toString());
    */
    /*
    shuffleArray(a);
    for (int i : a) {
      System.out.println(i);
    }*/
  }
} 
class AllCharacter{
	 String l[]={"Abcd","Adfgbd","Adf","Arre","Bee","Bere","Cgtr"};
	    
}
