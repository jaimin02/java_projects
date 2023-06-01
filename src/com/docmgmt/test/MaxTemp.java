package com.docmgmt.test;

public class MaxTemp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a, b, c;
		a = 20;
		b = 10;
		c = 20;
		int max = (a > b ? (a > c ? a : (b > c ? b : c)) : b > c ? b : c);
		int same = a == b ? 1 : (a == c ? 2 : (b == c ? 3 : 4));
		if (same == 4)
			System.out.println("Max=" + max);
		else if (same == 1 && b == c)
			System.out.println("All Same");
		else {
			if (same == 1 && a >= max)
				System.out.println("A & B are Same and MAX");
			else if (same == 2 && a >= max)
				System.out.println("A & C are Same and Max");
			else if (same == 3 && b >= max)
				System.out.println("B & C are Same and max");
			else
				System.out.println("Max->" + max);

		}

	}

}
