package Test;

import java.io.File;
import java.io.IOException;

import org.pdfbox.pdmodel.PDDocument;

public class checkencryptedfile {

    public void checkfile( String path ) {

        File file = new File( path );
        File[] list = file.listFiles();

        if (list == null) return;

        for ( File files : list ) {
            if ( files.isDirectory() ) {
            	checkfile( files.getAbsolutePath() );
                System.out.println( "Dir:" + files.getAbsoluteFile() );
            }
            else {
                PDDocument document;
				try {
					document = PDDocument.load(files.getAbsoluteFile());
            	
			    if(document.isEncrypted())
			    {
			    	 System.out.println( "File:" + files.getName());
			    	 
			    }
			    document.close();
			    
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        }        
    }

    public static void main(String[] args) {
    	checkencryptedfile encryptedfile = new checkencryptedfile();
    	encryptedfile.checkfile("//10.1.10.90/FileServer/Live/workspace/0109" );
    	System.out.println("******Completed*****");
    }

}