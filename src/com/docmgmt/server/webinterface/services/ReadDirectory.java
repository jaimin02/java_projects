package com.docmgmt.server.webinterface.services; 


import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;


public class ReadDirectory {
    
    private int nodeId=0;
 
    private int currentParentId=0;
    private ArrayList<Object[]> allDirDtls = new ArrayList<Object[]>();
    
    //===================================================================== main
    public ArrayList<Object[]> dirRead(String dirPath){
        File root = new File(dirPath);
        if (root != null && root.isDirectory()) {
            listRecursively(root, 0,1);
        } else {
            System.out.println("Not a directory: " + root);
        }
        return allDirDtls;
    }
    
    //========================================================== listRecursively
    private void listRecursively(File fdir, int depth, int Nodeno) {
    	//Increasing the Unique NodeID
    	nodeId++;
    	
    	/****************************************************************/
		char [] indents = new char[depth];
		Arrays.fill(indents, '\t');
		String indenting = Arrays.toString(indents).replaceAll(", ", "");
		indenting = indenting.substring(1, indenting.length()-1);
		/****************************************************************/
		
		//System.out.println(indenting + fdir.getName()+ "\t N:"+(nodeId)+"\tP:"+currentParentId+"\tNN:"+Nodeno);  // Print name.
		 Object dirObj[] = new Object[2];		
		 Integer[] nodeIds = new Integer[3];
		 nodeIds[0] = nodeId;
		 nodeIds[1] = currentParentId;
		 nodeIds[2] = Nodeno;
		 
		 dirObj[0]= nodeIds;
		 dirObj[1] = fdir;
//		 System.out.println(((Integer[])dirObj[0])[0]);
		 allDirDtls.add(dirObj);

		 if (fdir.isDirectory()) {
        	int prev_parentId=currentParentId; 
        	currentParentId = nodeId;
        	
        	File[] children =fdir.listFiles();
        	for (int i = 0; i < children.length; i++) {
				listRecursively(children[i], depth+1, i+1);
			}
        	currentParentId =prev_parentId;
        }
        
    }
    
}


