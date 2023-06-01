package Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.docmgmt.server.prop.PropertyInfo;

public class wordtopdf {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String WordToPDFFile;
		String WordToPDFFilePath;
		PropertyInfo propertyInfo = PropertyInfo.getPropInfo();
		
		WordToPDFFile = propertyInfo.getValue("WordToPDFFile");
		WordToPDFFilePath = propertyInfo.getValue("WordToPDFFilePath");
		
		String sourceFile = "//90.0.0.15/KnowledgeNET-CSV/User-Requirement-Specification.docx";
		String destFile;
		destFile = WordToPDFFilePath + "/"
				+ String.valueOf(System.currentTimeMillis()) + ".pdf";
		String command = WordToPDFFile + " " + sourceFile + " "
				+ destFile + " ";
		File inputFilePath = new File(sourceFile); // Existing file
		File outputFilePath = new File(destFile); // New file
		
		try {
			Runtime rt = Runtime.getRuntime();
			Process pCD = rt.exec(command);
			InputStream stderr = pCD.getErrorStream();
			InputStreamReader isr = new InputStreamReader(pCD.getInputStream());
			String line = null;

			BufferedReader input = new BufferedReader(new InputStreamReader(pCD
					.getInputStream()));
			while ((line = input.readLine()) != null)
				System.out.println(line);

			int exitVal = pCD.waitFor();
			int len;
			if ((len = pCD.getErrorStream().available()) > 0) {
				byte[] buf = new byte[len];
				pCD.getErrorStream().read(buf);
				System.err.println("Command error:\t\"" + new String(buf)
						+ "\"");
			}

			stderr.close();
			isr.close();
			System.gc();
			/*Path sourcePath = Paths.get(inputFilePath.getAbsolutePath());
		    Path destinationPath = Paths.get(outputFilePath.getAbsolutePath());
			 
	       	Files.copy(destinationPath,sourcePath, StandardCopyOption.REPLACE_EXISTING);*/
	       	
		   /* File outFile = null;
			outFile = new File(outputFilePath.getAbsolutePath());
    
			new File(inputFilePath.getAbsolutePath()).delete();
	
			outFile.renameTo(new File(inputFilePath.getAbsolutePath()));*/
		    
			System.out.println("Process exitValue: " + exitVal);
			
		} catch (Exception e) {
			System.out.println("Error...");
			e.printStackTrace();
			// TODO: handle exception
		}
	}

}
