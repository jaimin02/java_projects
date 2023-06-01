package com.docmgmt.test;
import java.io.File;
class prog21 {
	  static int file_count = 0;
	  static int folder_count=0;
	  float dirlen=0L;
	  String unit="byte";
	  public static void main(String[] args)throws Exception{

		File f = new File("D:/FileServer/");

		float rootdir=0F;
		
		for (File files : f.listFiles()) {
			rootdir+=files.length();
		}

		float kbsize=(rootdir/1024);
		float mbsize=(kbsize/1024);
	
		
		System.out.println("root directory length = " + mbsize +"mb" );
       prog21 p=new prog21();
	  p.countFilesInDirectory(f);
	System.out.println("total file is= " + file_count);
	System.out.println("total folder is= " + folder_count);	
	  
  }
  public void countFilesInDirectory(File directory) {
	 
	  for (File file : directory.listFiles()) {
		if (file.isFile()) {
			  file_count++;
		}
		if (file.isDirectory()) {
			
			folder_count++;
		
			String[] str=file.list();

			for(int i=0;i<str.length;i++){
				
				file_count++;

			}
		
		  }
	  }
		
	}
}


