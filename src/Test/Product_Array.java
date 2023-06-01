package Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Product_Array {
		
	public static void main(String[] args)
	{
		ArrayList<Integer> alcol = new ArrayList<Integer>();
		ArrayList<Integer> alrow = new ArrayList<Integer>();
 		String[][] main = new String[][]{
			{"1","A","","25"},
			{"2","B","","50"},
			{"3","c","","75"},
			{"4","d","","100"},
			{"","","",""},
			{"6","","","150"},
			{"7","g","","175"},
		};
		
		
		int rows = main.length;
		int cols = main[0].length;
		
		 List<List<String>> table = new ArrayList<List<String>>();
	       
	        for (String[] row : main) {
	            table.add(Arrays.asList(row));
	        }
	        System.out.println(table); 
	       
	        table = returnTranspose(table);
	        
	        System.out.println(table); 
	        
	        table = returnTranspose(table);
	        
	        System.out.println(table); 
	        
	       
	    }
	    static  List<List<String>> returnTranspose(List<List<String>> table) {
	       
	        List<List<String>> transposedList = new ArrayList<List<String>>();
	       
	        final int firstListSize = table.get(0).size();
	        for (int i = 0; i < firstListSize; i++) {
	            List<String> tempList = new ArrayList<String>();
	            List<String> temprmv2 = new ArrayList<String>(); 
	            
	            for (List<String> row : table) { 
	                tempList.add(row.get(i));
	                temprmv2.add(row.get(i));           
	                }
	           // System.out.println(tempList.size());	  
	            temprmv2.removeAll(Arrays.asList("",null));     
	           if(temprmv2.size()!=0)
	           {
	           transposedList.add(tempList);
	           }
	        }
	       
	        return transposedList;
	        
	}
}
	
