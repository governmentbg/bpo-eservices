package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.filing.domain.ds.UnitType;
import eu.ohim.sp.filing.domain.ds.ViewRepresentationSizeType;
import org.dozer.DozerConverter;

import java.math.BigInteger;

/**
 * @author ionitdi
 */
public class StringToViewRepresentationSizeConverter extends DozerConverter<String, ViewRepresentationSizeType>
{

    public StringToViewRepresentationSizeConverter()
    {
        super(String.class, ViewRepresentationSizeType.class);
    }

    @Override
    public ViewRepresentationSizeType convertTo(String s, ViewRepresentationSizeType viewRepresentationSizeType)
    {
        String value = s.toLowerCase();
        if(!value.contains("x"))
        {
            throw new IllegalStateException("The format of the string to transform into ViewRepresentationSizeType " +
                                                    "is not what is expected.");
        }
        String height = value.substring(0, value.indexOf('x'));
        BigInteger bigHeight = new BigInteger(height);

        String width = value.substring(value.indexOf('x') + 1, value.length());
        BigInteger bigWidth = new BigInteger(width);

        // TODO : define actual unit of measure
        ViewRepresentationSizeType result = new ViewRepresentationSizeType(bigHeight, bigWidth, UnitType.CENTIMETRE);

        return result;
    }

    @Override
    public String convertFrom(ViewRepresentationSizeType viewRepresentationSizeType, String s)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(viewRepresentationSizeType.getHeight());
        sb.append("x");
        sb.append(viewRepresentationSizeType.getWidth());

        String result = sb.toString();

        return result;
    }

}
