package eu.ohim.sp.common.ui.service.util;

import eu.ohim.sp.common.ui.form.person.NationalIdType;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 02.06.2021
 * Time: 15:55
 */
public class NationalIdUtils {

    public static boolean validateNationalId(NationalIdType nationalIdType, String nationalIdValue){
        if(nationalIdType != null && nationalIdValue != null) {
            if (nationalIdType.equals(NationalIdType.BG_NATIONAL_ID)) {
                return validateBgNationalId(nationalIdValue);
            } else if (nationalIdType.equals(NationalIdType.BG_FOREIGNER_ID)) {
                return validateBgForeignerId(nationalIdValue);
            }
        }
        return false;
    }

    private static boolean validateBgNationalId(String input) {
        if (input.length() != 10) {
            return false;
        } else {

            int[] digits = new int[10];
            int[] coeffs = {2, 4, 8, 5, 10, 9, 7, 3, 6};
            int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};


            for (int i = 0; i < input.length(); i++) {
                Integer digit = parseInteger(input.charAt(i) + "", null);
                if (digit == null) {
                    break;
                }
                digits[i] = digit;
            }

            if (10 != digits.length) {
                return false;
            }

            int dd = digits[4] * 10 + digits[5];
            int mm = digits[2] * 10 + digits[3];
            int yy = digits[0] * 10 + digits[1];
            Integer yyyy = null;

            if (mm >= 1 && mm <= 12) {
                yyyy = 1900 + yy;
            }
            else if (mm >= 21 && mm <= 32) {
                mm -= 20; yyyy = 1800 + yy;
            }
            else if (mm >= 41 && mm <= 52) {
                mm -= 40; yyyy = 2000 + yy;
            } else {
                return false;
            }

            days[1] += isLeapYear(yyyy) ? 1 : 0;

            if (!(dd >= 1 && dd <= days[mm - 1])) {
                return false;
            }

            // Gregorian calendar adoption. 31 Mar 1916 was followed by 14 Apr 1916.
            if (yyyy == 1916 && mm == 4 && (dd >= 1 && dd < 14)) {
                return false;
            }

            int checksum = 0;

            for (int j = 0; j < coeffs.length; j++) {
                checksum = checksum + (digits[j] * coeffs[j]);
            }
            checksum %= 11;
            if (10 == checksum) {
                checksum = 0;
            }

            if (digits[9] != checksum) {
                return false;
            }
        }
        return true;
    }

    private static boolean validateBgForeignerId(String input) {
        if (input.length() != 10) {
            return false;
        } else {

            int[] digits = new int[10];
            int[] coeffs = {21, 19, 17, 13, 11, 9, 7, 3, 1};
            for (int i = 0; i < input.length(); i++) {
                Integer digit = parseInteger(input.charAt(i) + "", null);
                if (digit == null) {
                    break;
                }
                digits[i] = digit;
            }

            if (10 != digits.length) {
                return false;
            }

            int checksum = 0;

            for (int j = 0; j < coeffs.length; j++) {
                checksum = checksum + (digits[j] * coeffs[j]);
            }
            checksum %= 10;

            if (digits[9] != checksum) {
                return false;
            }
        }
        return true;
    }


    private static boolean isLeapYear(int yyyy) {
        if (yyyy % 400 == 0) {
            return true;
        }
        if (yyyy % 100 == 0) {
            return false;
        }
        if (yyyy % 4 == 0) {
            return true;
        }

        return false;
    }

    private static Integer parseInteger(String value, Integer defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
