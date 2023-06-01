package com.docmgmt.test;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
	public static void main(String s[]) throws IOException {
		try {

			
			String s1="1099";
			int ic=Integer.parseInt(s1);
			ic++;
			
			String s2="100"+ic;
			
			s1=s2.substring(s2.length()-4);
			System.out.println(s1);
			
//			Robot robot = new Robot();
//			//		
//			robot.delay(5000);
//			long heapSize = Runtime.getRuntime().totalMemory();
//
//			long heapSize1 = Runtime.getRuntime().freeMemory();
//
//			// Print the jvm heap size.
//			System.out.println("Heap Size = " + heapSize1);

			// LoginAuto(robot);
			// robot.delay(10000);
			// robot.mousePress(InputEvent.BUTTON1_MASK);
			// robot.mouseRelease(InputEvent.BUTTON1_MASK);
			// robot.delay(10000);
			// openProject(robot);

			//allmenu(robot);
			// Capture(robot);

			// MoveMouse(robot);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void MoveMouse(Robot robot) {
		// TODO Auto-generated method stub

		int x, y;
		int i = 0;
		while (i++ < 30) {
			x = (int) (5 + (Math.random() * (1000 - 5)));
			y = (int) (5 + (Math.random() * (1000 - 5)));
			robot.delay(400);
			robot.mouseMove(x, y);
			System.out.println(x + "===" + y);
		}
	}

	private static void Capture(Robot robot) throws IOException {
		Dimension screenSize;
		Rectangle screenRectangle;
		BufferedImage image;
		String[] img = new String[10];
		int j = 10;
		for (int i = 0; i < j; i++) {
			try {
				img[i] = "D:\\captured\\" + (i + 1) + ".jpg"; // e.g 1.jpg saved
				// in C: drive
				// on 1st itr.
				screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				screenRectangle = new Rectangle(screenSize);
				image = robot.createScreenCapture(screenRectangle);

				ImageIO.write(image, "jpg", new File(img[i]));
			} catch (Exception e) {

				System.out.println(e.getMessage());
			}
			robot.delay(1000);
		}
	}

	private static void openProject(Robot robot) {
		robot.mouseMove(310, 200);
		robot.delay(1000);
		robot.mouseMove(310, 260);
		robot.delay(1000);
		robot.mouseMove(500, 260);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		robot.delay(7000);
		robot.mouseMove(310, 470);
		robot.delay(1000);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(5000);
		robot.mouseWheel(-100);

	}

	private static void allmenu(Robot robot) {
		robot.mouseMove(20, 20);

		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(50);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.delay(50);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

		/*
		 * robot.mouseMove(120, 200); robot.delay(3000); robot.mouseMove(220,
		 * 200); robot.delay(3000); robot.mouseMove(320, 200);
		 * robot.delay(3000); robot.mouseMove(420, 200); robot.delay(3000);
		 * robot.mouseMove(520, 200); robot.delay(3000); robot.mouseMove(620,
		 * 200); robot.delay(3000); robot.mouseMove(720, 200);
		 * robot.delay(3000);
		 */

	}

	private static void LoginAuto(Robot robot) {
		robot.keyPress(KeyEvent.VK_E);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyPress(KeyEvent.VK_T);
		robot.keyPress(KeyEvent.VK_D);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyPress(KeyEvent.VK_D);
		robot.keyPress(KeyEvent.VK_M);
		robot.keyPress(KeyEvent.VK_I);
		robot.keyPress(KeyEvent.VK_N);

		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyPress(KeyEvent.VK_1);
		robot.keyPress(KeyEvent.VK_2);
		robot.keyPress(KeyEvent.VK_3);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyPress(KeyEvent.VK_I);
		robot.keyPress(KeyEvent.VK_J);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyPress(KeyEvent.VK_Y);

		robot.mouseMove(335, 650);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);

	}

}
