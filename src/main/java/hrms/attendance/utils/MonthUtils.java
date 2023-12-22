package hrms.attendance.utils;

import hrms.attendance.enums.MonthName;

public class MonthUtils {

	public static MonthName getCurrentMonthName(int minusMonth) {
		return convertMonthName(java.time.LocalDate.now().getMonthValue() - minusMonth);
	}

	public static MonthName convertMonthName(int monthValue) {
		switch (monthValue) {
		case 1:
			return MonthName.JANUARY;
		case 2:
			return MonthName.FEBRUARY;
		case 3:
			return MonthName.MARCH;
		case 4:
			return MonthName.APRIL;
		case 5:
			return MonthName.MAY;
		case 6:
			return MonthName.JUNE;
		case 7:
			return MonthName.JULY;
		case 8:
			return MonthName.AUGUST;
		case 9:
			return MonthName.SEPTEMBER;
		case 10:
			return MonthName.OCTOBER;
		case 11:
			return MonthName.NOVEMBER;
		case 12:
			return MonthName.DECEMBER;
		default:
			throw new IllegalArgumentException("Invalid month value " + monthValue);
		}

	}
}
