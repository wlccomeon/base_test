package com.lc.test.baseknowlege.convertutil;

/**
 * @author wlc
 * @desc
 * @date 2019-09-18 17:47:05
 */
public class EnglishNumberFormatter {
	private static final String[] BITS = {"ONE", "TWO", "THREE", "FOUR", "FIVE",
			"SIX", "SEVEN", "EIGHT", "NINE", "TEN"};
	private static final String[] TEENS = {"ELEVEN", "TWELF", "THIRTEEN",
			"FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVETEEN", "EIGHTEEN", "NIGHTEEN"};
	private static final String[] TIES = {"TWENTY", "THRITY", "FORTY", "FIFTY",
			"SIXTY", "SEVENTY", "EIGHTY", "NINETY"};


	public static void main(String[] args) {
		for (int i = 0; i < 111; i++) {
			System.out.println(EnglishNumberFormatter.toEnglish(i).replace(" ", "").toLowerCase());
		}

	}

	public static String toEnglish(int num) {
		if (num == 0) {
			return "Zero";
		}
		StringBuffer buffer = new StringBuffer();
		if (num >= 100) {
			buffer.append(pickHunder(num));
			if (num % 100 != 0) {
				buffer.append(" AND ");
			}
			num -= (num / 100) * 100;
		}
		boolean largerThan20 = false;
		if (num >= 20) {
			largerThan20 = true;
			buffer.append(pickTies(num));
			num -= (num / 10) * 10;
		}
		if (!largerThan20 && num > 10) {
			buffer.append(pickTeens(num));
			num = 0;
		}
		if (num > 0) {
			String bit = pickBits(num);
			if (largerThan20) {
				buffer.append(" ");
			}
			buffer.append(bit);
		}
		return buffer.toString();
	}

	private static String pickHunder(int num) {
		int hunder = num / 100;
		return BITS[hunder - 1] + " HUNDER";
	}

	private static String pickTies(int num) {
		int ties = num / 10;
		return TIES[ties - 2];
	}

	private static String pickTeens(int num) {
		return TEENS[num - 11];
	}

	private static String pickBits(int num) {
		return BITS[num - 1];
	}
}
