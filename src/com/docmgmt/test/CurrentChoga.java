package com.docmgmt.test;

import java.util.ArrayList;
import java.util.Calendar;

public class CurrentChoga {
	public static void main(String s[]) {

		Calendar currentDateObj = Calendar.getInstance();

		CurrentChoga c = new CurrentChoga();
		c.getCh(currentDateObj);

	}

	public int getCh(Calendar currentDateObj) {

		String Sun[] = { "Udveg", "Chal", "Labh", "Amrut", "Kal", "shubh",
				"rog", "Udveg", "shubh", "amrut", "chal", "rog", "kal", "labh",
				"udveg", "shubh" };
		String Mon[] = { "Amrut", "Kal", "shubh", "rog", "Udveg", "Chal",
				"Labh", "Amrut", "chal", "rog", "kal", "labh", "Udveg",
				"shubh", "Amrut", "chal" };
		String Tue[] = { "rog", "udveg", "chal", "labh", "amrut", "kal",
				"shubh", "rog", "kal", "labh", "udveg", "shubh", "amrut",
				"chal", "rog", "kal" };
		String Wed[] = { "labh", "amrut", "kal", "shubh", "rog", "udveg",
				"chal", "labh", "udveg", "shubh", "amrut", "chal", "rog",
				"kal", "labh", "udveg" };
		String Thur[] = { "shubh", "rog", "udveg", "chal", "labh", "amrut",
				"kal", "shubh", "amrut", "chal", "rog", "kal", "labh", "udveg",
				"shubh", "amrut" };
		String Fri[] = { "chal", "labh", "amrut", "kal", "shubh", "rog",
				"udveg", "chal", "rog", "kal", "labh", "udveg", "shubh",
				"amrut", "chal", "rog" };
		String Sat[] = { "kal", "shubh", "rog", "Udveg", "chal", "labh",
				"amrut", "kal", "labh", "udveg", "shubh", "amrut", "chal",
				"rog", "kal", "labh" };

		String allChog[] = { "udveg", "chal", "labh", "amrut", "kal", "shubh",
				"rog" };

		ArrayList<String[]> daysVector = new ArrayList<String[]>();
		daysVector.add(Sun);
		daysVector.add(Mon);
		daysVector.add(Tue);
		daysVector.add(Wed);
		daysVector.add(Thur);
		daysVector.add(Fri);
		daysVector.add(Sat);

		Calendar tempDateObj = Calendar.getInstance();
		int dayOfWeek = tempDateObj.get(Calendar.DAY_OF_WEEK);
		// DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm:aa");

		tempDateObj.set(Calendar.DATE, currentDateObj.get(Calendar.DATE));
		tempDateObj.set(Calendar.MONTH, currentDateObj.get(Calendar.MONTH));
		tempDateObj.set(Calendar.YEAR, currentDateObj.get(Calendar.YEAR));

		tempDateObj.set(Calendar.HOUR, 6);
		tempDateObj.set(Calendar.MINUTE, 0);
		tempDateObj.set(Calendar.AM_PM, Calendar.AM);
		int i = 0;
		for (i = 1; i <= 16; i++) {
			if (i != 1)
				tempDateObj.add(Calendar.MINUTE, 90);
			tempDateObj.set(Calendar.DATE, currentDateObj.get(Calendar.DATE));
			tempDateObj.set(Calendar.MONTH, currentDateObj.get(Calendar.MONTH));
			tempDateObj.set(Calendar.YEAR, currentDateObj.get(Calendar.YEAR));
			double milisec = (tempDateObj.getTimeInMillis() - currentDateObj
					.getTimeInMillis());
			double minute = milisec / (60 * 1000);
			if (tempDateObj.before(currentDateObj) && Math.abs(minute) <= 89
					|| minute == 0)
				break;
		}
		String obj[] = daysVector.get(dayOfWeek - 1);
		System.out.println("Current->" + obj[i - 1]);
		int j;
		for (j = 0; j < allChog.length; j++) {
			if (allChog[j].toLowerCase()
					.equals(obj[i - 1].toLowerCase().trim()))
				break;
		}
		System.out.println("Current->" + allChog[j]);
		return j;
	}
}
