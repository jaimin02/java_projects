package Test;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.docmgmt.server.db.DocMgmtImpl;

public class CreateDirectory {
	public static DocMgmtImpl docMgmtImpl = new DocMgmtImpl();
	public static File dir;
	
	public static void main(String[] args){

	String source = "//90.0.0.15/docmgmtandpub/workspace/0278/181";
	
	File srcDir = new File(source);

	String destination = "C:/Users/SSPL712/Desktop/0854/25";
	File destDir = new File(destination);
	
	createDirectory(srcDir,destDir);
}
	
	public static void createDirectory(File srcDir,File destDir){
		
		
		String filepath;
		File file = new File( srcDir.toString() );
	    File[] list = file.listFiles();
		
		for (File files : list ) {
	    	
	        if ( files.isDirectory() ) {
	        	
	        	System.out.println( "Dir:" + files.getName());
	        	
	        	filepath=destDir.toString();
	        	
	        	int TranNo = docMgmtImpl.getNewTranNo("0278");
	        	
	        	dir = new File(filepath+"/"+files.getName());
	        	        	
	        	if(!dir.isDirectory()){
	        		dir.mkdir();
	        		createDirectory(files.getAbsoluteFile(),dir);
	        	}
	        	else{
	        		try {
	        			FileUtils.copyDirectory(srcDir, dir);
	        		} catch (IOException e) {
	        		    e.printStackTrace();
	        		}
	        	}
	        }else{
	        	try {
	        		FileUtils.copyDirectory(srcDir, dir);
	        	} catch (IOException e) {
			    e.printStackTrace();
	        	}
	        }
		}
	}
}

