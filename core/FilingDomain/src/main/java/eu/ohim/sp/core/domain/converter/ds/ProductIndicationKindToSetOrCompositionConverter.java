package eu.ohim.sp.core.domain.converter.ds;


import eu.ohim.sp.core.domain.design.ProductIndicationKind;
import org.dozer.DozerConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Maria
 * Date: 17/12/13
 * Time: 11:26
 * To change this template use File | Settings | File Templates.
 */
public class ProductIndicationKindToSetOrCompositionConverter extends DozerConverter<ProductIndicationKind, Boolean>
{
    public ProductIndicationKindToSetOrCompositionConverter()
    {
        super(ProductIndicationKind.class, Boolean.class);
    }


    @Override
    public Boolean convertTo(ProductIndicationKind s, Boolean aBoolean) {
        if(ProductIndicationKind.SET_COMPOSITION.equals(s)){
            return true;
        }
        return false;
    }

    @Override
    public ProductIndicationKind convertFrom(Boolean aBoolean, ProductIndicationKind s) {
        if(aBoolean){
            return ProductIndicationKind.SET_COMPOSITION;

        }
        return ProductIndicationKind.SINGLE_PRODUCT;
    }
}
