package com.docmgmt.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateDiff {

	public static void main(String arg[]) {

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		Calendar cal = Calendar.getInstance();

		String currentDateString = df.format(cal.getTime()); // get current date
		String startDateString = "09/01/1988";
		Date startDate = null;
		Date currentDate = null;
		try {
			startDate = df.parse(startDateString);
			currentDate = df.parse(currentDateString);
			String newDateString = df.format(startDate);
			System.out.println(newDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int arr[] = getDateDifferenceInDDMMYYYY(startDate, currentDate);
		System.out.println("Day->" + arr[0] + "\nMonth->" + arr[1] + "\nYear->"
				+ arr[2]);

	}

	public static int[] getDateDifferenceInDDMMYYYY(Date date1, Date date2) {

		int[] monthDay = { 31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		Calendar fromDate;
		Calendar toDate;
		int increment = 0;
		int[] ageDiffArr = new int[3];

		int year;
		int month;
		int day;

		Calendar d1 = new GregorianCalendar().getInstance();
		d1.setTime(date1);

		Calendar d2 = new GregorianCalendar().getInstance();
		d2.setTime(date2);

		if (d1.getTime().getTime() > d2.getTime().getTime()) {
			fromDate = d2;
			toDate = d1;
		} else {
			fromDate = d1;
			toDate = d2;
		}

		if (fromDate.get(Calendar.DAY_OF_MONTH) > toDate
				.get(Calendar.DAY_OF_MONTH)) {
			increment = monthDay[fromDate.get(Calendar.MONTH)];
		}

		GregorianCalendar cal = new GregorianCalendar();
		boolean isLeapYear = cal.isLeapYear(fromDate.get(Calendar.YEAR));

		if (increment == -1) {
			if (isLeapYear) {
				increment = 29;
			} else {
				increment = 28;
			}
		}

		// DAY CALCULATION
		if (increment != 0) {
			day = (toDate.get(Calendar.DAY_OF_MONTH) + increment)
					- fromDate.get(Calendar.DAY_OF_MONTH);
			increment = 1;
		} else {
			day = toDate.get(Calendar.DAY_OF_MONTH)
					- fromDate.get(Calendar.DAY_OF_MONTH);
		}

		// MONTH CALCULATION
		if ((fromDate.get(Calendar.MONTH) + increment) > toDate
				.get(Calendar.MONTH)) {
			month = (toDate.get(Calendar.MONTH) + 12)
					- (fromDate.get(Calendar.MONTH) + increment);
			increment = 1;
		} else {
			month = (toDate.get(Calendar.MONTH))
					- (fromDate.get(Calendar.MONTH) + increment);
			increment = 0;
		}

		// YEAR CALCULATION
		year = toDate.get(Calendar.YEAR)
				- (fromDate.get(Calendar.YEAR) + increment);

		ageDiffArr[0] = day;
		ageDiffArr[1] = month;
		ageDiffArr[2] = year;

		return ageDiffArr; // RESULT AS DAY, MONTH AND YEAR in form of Array
	}

}
