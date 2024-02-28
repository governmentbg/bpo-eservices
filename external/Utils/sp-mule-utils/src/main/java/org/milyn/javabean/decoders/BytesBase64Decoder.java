package org.milyn.javabean.decoders;

import org.milyn.javabean.DataDecodeException;
import org.milyn.javabean.DataDecoder;
import org.milyn.javabean.DecodeType;

import java.util.Base64;

/**
   * Created by IntelliJ IDEA.
   * Use: Raya
   * Date: 2/3/2021
   * Time: 10:32 AM
*/
@DecodeType(BytesBase64Decoder.class)
public class BytesBase64Decoder implements DataDecoder {

    @Override
    public Object decode(String s) throws DataDecodeException {
        return Base64.getDecoder().decode(s);
    }
}
