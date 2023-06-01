package com.docmgmt.test;

import java.io.File;
import java.rmi.server.UID;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Test1 {

	public static void main(String args[]) {

		try {
			
			 UID userId = new UID();
			 
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

			String randomNum = new Integer(prng.nextInt()).toString();
			
			System.out.println("Random number: " +  userId);

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// processFramesBatch();

	}

	private static void processFramesBatch() {
		String frameRootPath = "D:/temp";
		int i = 1;
		for (int j = 5;; j--) {
			if (j < 1) {
				return;
			}
			File source = new File(frameRootPath + "/" + j + ".txt");
			File dest = new File(frameRootPath + "/test/" + i + ".txt");
			source.renameTo(dest);
			i++;
		}
	}
}