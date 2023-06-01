package com.docmgmt.test;

import java.io.File;

public class ExcellReportDemo {
	public static void main(String str[]){
		  
		File file = new File("");
		String path = file.getAbsolutePath();
		System.out.println(path);
		
		//new Process1("Process1").start();
		//  new Process2("Process2").start();
		  
		  Thread.currentThread().setPriority(1);
		  for(int k=1;k<=100;k++){
			  System.out.println("Process2==>"+k);
		  }
		  
	}

}
class Process1 extends Thread {
    public Process1(String str) {
        super(str);
    }
    @Override
	public void run() {
        for (int i = 1; i <= 100; i++) {
            System.out.println(i + " " + getName());
//            try {
//                sleep(1);
//            } catch (InterruptedException e) {}
        }
        System.out.println("DONE! " + getName());
    }
}
class Process2 extends Thread {
    public Process2(String str) {
        super(str);
    }
    @Override
	public void run() {
        for (int i = 1; i < 10; i++) {
            System.out.println(i + " " + getName());
            try {
                sleep(1);
            } catch (InterruptedException e) {}
        }
        System.out.println("DONE! " + getName());
    }
}
