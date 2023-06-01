package com.docmgmt.test;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class CreateOutlineTree {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		ArrayList<Integer> list = new ArrayList<Integer>(4);
        for (int i = 0; i < 17; i++) {
            list.add(i);
            System.out.format("Size: %2d, Capacity: %2f%n",
                              list.size(), getCapacity(list));
        }
    }

    static float getCapacity(ArrayList<?> l) throws Exception {
        Field dataField = ArrayList.class.getDeclaredField("elementData");
        dataField.setAccessible(true);
        
        return ((Object[]) dataField.get(l)).length;
     }

}
