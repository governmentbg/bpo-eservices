package eu.ohim.sp.common.ui.form.person;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 02.06.2021
 * Time: 15:03
 */
public enum NationalIdType {
    BG_NATIONAL_ID("Bulgarian National ID"), BG_FOREIGNER_ID("Bulgarian Foreigner ID");

    private String value;

    NationalIdType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static NationalIdType fromValue(String value){
        Optional<NationalIdType> opt = Arrays.stream(values()).filter(v -> v.getValue().equals(value)).findFirst();
        if(opt.isPresent()){
            return opt.get();
        } else {
            return null;
        }
    }
}
