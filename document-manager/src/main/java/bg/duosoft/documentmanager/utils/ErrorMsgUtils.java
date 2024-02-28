package bg.duosoft.documentmanager.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Raya
 * 19.01.2021
 */
public class ErrorMsgUtils {

    public static List<String> createErrorMessages(Object... errors){
        List<String> errorList = new ArrayList<>();
        Arrays.stream(errors).forEach(err ->{
            if(err instanceof String){
                errorList.add((String)err);
            } else if(err instanceof Exception){
                errorList.add(((Exception)err).getMessage());
            }
        });
        return errorList;
    }
}
