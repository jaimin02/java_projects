package com.docmgmt.test;

import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class BeepExample {

	public interface Kernel32 extends Library {
		// FREQUENCY is expressed in hertz and ranges from 37 to 32767
		// DURATION is expressed in milliseconds
		public boolean Beep(int FREQUENCY, int DURATION);

		public void Sleep(int DURATION);
	}

	public interface simpleDLL extends Library {
		

		byte giveVoidPtrGetChar(Pointer param);

		int giveVoidPtrGetInt(Pointer param);

		int giveIntGetInt(int a);

		void simpleCall();
	}

	public static void main(String[] args) {
		
		
	System.out.println(	System.getProperty("sun.arch.data.model") );
		String myLibraryPath = System.getProperty("user.dir");
		System.setProperty("java.library.path", myLibraryPath);
		
		
		Kernel32 lib = (Kernel32) Native
				.loadLibrary("kernel32", Kernel32.class);
		lib.Beep(98, 00);
		lib.Sleep(500);
		lib.Beep(98, 500);
	
		simpleDLL INSTANCE1 = (simpleDLL) Native.loadLibrary("simpleDLL.dll",simpleDLL.class);
		
	//	 simpleDLL sdll = simpleDLL.INSTANCE;
		 
	      //  INSTANCE.simpleCall();  // call of void function
		Memory intMem = new Memory(4);
		
		  int int2 = INSTANCE1.giveVoidPtrGetInt(intMem);
		
		System.out.println(myLibraryPath);

	}

}
