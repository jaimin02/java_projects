package com.docmgmt.test;

import java.io.File;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeConnectionProtocol;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.artofsolving.jodconverter.util.PlatformUtils;

public class OpenOfficeLibTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		convertDOC();
	}

	public static File getOfficeExecutable(File officeHome) {
		if (PlatformUtils.isMac()) {
			return new File(officeHome, "MacOS/soffice.bin");
		} else if (PlatformUtils.isWindows()) {
			return new File(officeHome, "program/soffice.exe");
		} else {
			return new File(officeHome, "program/soffice.bin");
		}
	}

	public static void convertDOC()
	{
		OfficeManager om = null;
		try {
			//System.load("C://Program Files (x86)//OpenOffice.org 3//URE//bin//jpipe.dll");
			
			//System.setProperty( "java.library.path", "C://Program Files (x86)//OpenOffice.org 3//URE//bin//jpipe.dll" );
			 
				
			om = new DefaultOfficeManagerConfiguration().setOfficeHome(
					"C://Program Files (x86)//OpenOffice 4")
					.setConnectionProtocol(OfficeConnectionProtocol.PIPE)
					.setPipeNames("o1", "o2")
					.setTaskExecutionTimeout(200000L)
					.buildOfficeManager();
			
			
//			  ExternalOfficeManagerConfiguration extConf = (ExternalOfficeManagerConfiguration) new
//			  ExternalOfficeManagerConfiguration()
//			  .setConnectionProtocol(OfficeConnectionProtocol.SOCKET)
//			  .setPortNumber(80001)
//			  .buildOfficeManager();
			  
             
              System.out.println("Attached to existing OpenOffice process ... ");
	
			om.start();

			OfficeDocumentConverter converter = new OfficeDocumentConverter(om);
			long startTime = System.currentTimeMillis();

			converter.convert(new File("D://vijay//doctopdf//Test2010.docx"),
					new File("D://vijay//doctopdf//test2010.pdf"));
			long conversionTime = System.currentTimeMillis() - startTime;

			System.out.println(String.format("%dms", conversionTime));
				
		 
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			om.stop();
		}
	}
}

class ThreadConnection implements Runnable {

	public synchronized void run() {
		// TODO Auto-generated method stub
		System.out.println("Inside Thread....");

	}

}
