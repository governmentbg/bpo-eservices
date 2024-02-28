package eu.ohim.sp.common.ui.service.util;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 04.06.2021
 * Time: 15:23
 */
public class CompanyNumberUtils {

    public static boolean validateCompanyNumber(String companyNumber){
        if(companyNumber != null && companyNumber.matches("\\d+")){
            if(companyNumber.length() == 9){
                return validate9DigitCompanyNumber(companyNumber);
            } else if(companyNumber.length() == 13){
                return validate13DigitCompanyNumber(companyNumber);
            }
        }
        return false;
    }

    private static boolean validate9DigitCompanyNumber(String companyNumber){
        int[] coeffs = {1,2,3,4,5,6,7,8};
        int[] coeffsSecondCheck = {3,4,5,6,7,8,9,10};
        int[] digits = new int[9];
        for (int i = 0; i < companyNumber.length(); i++) {
            Integer digit = Integer.parseInt(companyNumber.charAt(i)+"");
            digits[i] = digit;
        }

        int multiplyResult = 0;
        for(int i =0; i< 8; i++){
            multiplyResult = multiplyResult + coeffs[i]*digits[i];
        }

        int checksum = multiplyResult%11;
        if(checksum != 10){
            return checksum == digits[8];
        } else {
            int multiplyResultSecond = 0;
            for(int i =0; i< 8; i++){
                multiplyResultSecond = multiplyResultSecond + coeffsSecondCheck[i]*digits[i];
            }
            int newchecksum = multiplyResultSecond%11;
            if(newchecksum != 10){
                return newchecksum == digits[8];
            } else {
                return 0 == digits[8];
            }
        }
    }

    private static boolean validate13DigitCompanyNumber(String companyNumber){
        int[] coeffs = {2,7,3,5};
        int[] coeffsSecondCheck = {4,9,5,7};
        int[] digits = new int[13];
        for (int i = 0; i < companyNumber.length(); i++) {
            Integer digit = Integer.parseInt(companyNumber.charAt(i)+"");
            digits[i] = digit;
        }
        int multiplyResult = coeffs[0]*digits[8]+coeffs[1]*digits[9]+coeffs[2]*digits[10]+coeffs[3]*digits[11];
        int checksum = multiplyResult%11;
        if(checksum != 10){
            return checksum == digits[12];
        } else {
            int multiplyResultSecond = coeffsSecondCheck[0]*digits[8]+coeffsSecondCheck[1]*digits[9]+
                    coeffsSecondCheck[2]*digits[10]+coeffsSecondCheck[3]*digits[11];
            int newchecksum = multiplyResultSecond%11;
            if(newchecksum != 10){
                return newchecksum == digits[12];
            } else {
                return 0 == digits[12];
            }
        }
    }

}
