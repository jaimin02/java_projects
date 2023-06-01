package com.docmgmt.test;

import java.util.ArrayList;
import java.util.Collections;

public class UniqueRandomNumbers {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[] myList = new int[6];
        for (int i=1; i<=30; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        int a=5,b=7;
        for (int i=0; i<4; i++) {
        	System.out.println(list.get(i));
        	if(list.get(i)==a || list.get(i)==b){
        		System.out.println("match");
        		Collections.shuffle(list);
        	}
        	
        }
    }
}