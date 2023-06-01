package com.docmgmt.test;


public class ThreadTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("ff");
		String strDateToSet="10-10-2012";
		String strTimeToSet="10:10:10";
		try {
			  
	   
  			Runtime.getRuntime().exec("cmd /C date " + strDateToSet);
			Runtime.getRuntime().exec("cmd /C time " + strTimeToSet); // hh:mm:ss
			
			//startActivity(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS));
	         //ProcessBuilder builder = new ProcessBuilder(new String[] { "cmd.exe", "/C notepad.exe" });
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
