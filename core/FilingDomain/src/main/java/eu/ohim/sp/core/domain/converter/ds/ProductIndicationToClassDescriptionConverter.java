/*
 *  FspDomain:: ProductIndicationToClassDescriptionConverter 02/10/13 16:05 KARALCH $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.ProductIndicationClass;
import eu.ohim.sp.core.domain.design.ProductIndicationTerm;
import eu.ohim.sp.filing.domain.ds.*;
import org.dozer.DozerConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.util.ArrayList;

/**
 * @author ionitdi
 */
public class ProductIndicationToClassDescriptionConverter extends DozerConverter<ProductIndicationClass, ClassDescription>
        implements MapperAware {

    private Mapper mapper;

    @Override
    public void setMapper(Mapper mapper)
    {
        this.mapper = mapper;
    }

    public ProductIndicationToClassDescriptionConverter()
    {
        super(ProductIndicationClass.class, ClassDescription.class);
    }


    @Override
    public ClassDescription convertTo(ProductIndicationClass productIndicationClass, ClassDescription classDescription)
    {
    	ClassDescription result = new ClassDescription();
        result.setProductDescription(new ArrayList<Text>());

        // TODO: set language ?
        result.getProductDescription().add(new Text(productIndicationClass.getDescription(), null));

        if(productIndicationClass.getMainClass() != null && productIndicationClass.getSubClass() != null)
        {
            String classNumber = productIndicationClass.getMainClass() + "." + productIndicationClass.getSubClass();
            result.setClassNumber(new ClassNumber(new ArrayList<String>()));
            result.getClassNumber().getValue().add(classNumber);
        }


        result.setClassificationTermDetails(new ClassificationTermDetailsType(new ArrayList<ClassificationTermType>()));
        for (ProductIndicationTerm term : productIndicationClass.getTerms())
        {
            result.getClassificationTermDetails().getClassificationTerm().
                    add(mapper.map(term, ClassificationTermType.class));
        }

        return result;
    }

    @Override
    public ProductIndicationClass convertFrom(ClassDescription classDescription, ProductIndicationClass productIndicationClass)
    {
    	ProductIndicationClass result = new ProductIndicationClass();

        if (classDescription.getProductDescription() != null && !classDescription.getProductDescription().isEmpty())
        {
            result.setDescription(classDescription.getProductDescription().get(0).getValue());
        }

        if (classDescription.getClassNumber() != null && classDescription.getClassNumber().getValue() != null && !classDescription.getClassNumber().getValue().isEmpty())
        {
            String classNumber = classDescription.getClassNumber().getValue().get(0);

            int index = classNumber.indexOf('.');
            if (index == -1)
            {
                throw new IllegalStateException("The format of the string to transform into class number is not what is expected.");
            }

            String mainClass = classNumber.substring(0, index);
            String subClass = classNumber.substring(index + 1, classNumber.length());

            result.setMainClass(mainClass);
            result.setSubClass(subClass);
        }

        result.setTerms(new ArrayList<ProductIndicationTerm>());
        for (ClassificationTermType term : classDescription.getClassificationTermDetails().getClassificationTerm())
        {
            result.getTerms().
                    add(mapper.map(term, ProductIndicationTerm.class));
        }

        return result;
    }


}
