/*
 *  FspDomain:: PhoneConverter 05/09/13 18:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.converter;

import eu.ohim.sp.core.domain.contact.Phone;
import eu.ohim.sp.core.domain.contact.PhoneKind;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.dozer.CustomConverter;
import org.dozer.Mapper;
import org.dozer.MapperAware;

import java.lang.reflect.InvocationTargetException;

/**
 * Custom Dozer Converter to handle phones conversions. It handles the case that on CoreDomain, we have a list of phones
 * and on XML schema we have a list of phones and one list of faxes. If it expects fax and receive phone, it will return
 * a null object
 */
public class PhoneConverter implements CustomConverter, MapperAware {

    private final Logger LOGGER = Logger.getLogger(PhoneConverter.class);

    /**
     * It gives access to the dozer mapper
     */
    private Mapper mapper;

    /**
     * It is used to set the mapper used on the context
     * @param mapper
     */
    @Override
    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    /**
     * It maps the following case on the mapper factory
     * <converter type="eu.ohim.sp.core.domain.converter.PhoneConverter">
     *      <class-a>eu.ohim.sp.filing.domain.tm.Phone</class-a>
     *      <class-b>eu.ohim.sp.core.domain.contact.Phone</class-b>
     * </converter>
     * <converter type="eu.ohim.sp.core.domain.converter.PhoneConverter">
     *      <class-a>eu.ohim.sp.core.domain.contact.Phone</class-a>
     *      <class-b>java.lang.String</class-b>
     * </converter>
     *
     * @param existingDestinationFieldValue
     * @param sourceFieldValue
     * @param destinationClass acceptable values {@see Phone}, {@see String},  {@see eu.ohim.sp.filing.domain.tm.Phone},
     *                                                                         {@see eu.ohim.sp.filing.domain.ds.Phone}
     * @param sourceClass acceptable values {@see Phone}, {@see String},  {@see eu.ohim.sp.filing.domain.tm.Phone},
     *                                                                         {@see eu.ohim.sp.filing.domain.ds.Phone}
     * @return the transformed value
     */
    @Override
    public Object convert(Object existingDestinationFieldValue, Object sourceFieldValue, Class<?> destinationClass, Class<?> sourceClass) {
        Object response = null;
        try {
            if (sourceFieldValue instanceof Phone) {
                if (((Phone) sourceFieldValue).getPhoneKind()==null
                        || (!((Phone) sourceFieldValue).getPhoneKind().equals(PhoneKind.FAX)
                        && (destinationClass.equals(eu.ohim.sp.filing.domain.tm.Phone.class)
                        || destinationClass.equals(eu.ohim.sp.filing.domain.ds.Phone.class)))) {

                    if (!destinationClass.equals(String.class)) {
                        response = destinationClass.newInstance();
                        BeanUtilsBean.getInstance().copyProperty(response, "value", ((Phone) sourceFieldValue).getNumber() );
                        if (((Phone) sourceFieldValue).getPhoneKind()!=null) {
                            if(destinationClass.equals(eu.ohim.sp.filing.domain.tm.Phone.class)) {
                                eu.ohim.sp.filing.domain.tm.PhoneKind kind = mapper.map(((Phone) sourceFieldValue).getPhoneKind(), eu.ohim.sp.filing.domain.tm.PhoneKind.class);
                                BeanUtilsBean.getInstance().copyProperty(response, "phoneKind", kind);
                            } else {
                                eu.ohim.sp.filing.domain.ds.PhoneKind kind = mapper.map(((Phone) sourceFieldValue).getPhoneKind(), eu.ohim.sp.filing.domain.ds.PhoneKind.class);
                                BeanUtilsBean.getInstance().copyProperty(response, "phoneKind", kind);
                            }
                        }
                    }
                } else if (((Phone) sourceFieldValue).getPhoneKind().equals(PhoneKind.FAX)
                        && destinationClass.equals(String.class)) {
                    response = ((Phone) sourceFieldValue).getNumber();
                }
            } else if (sourceFieldValue instanceof String) {
                if (destinationClass.equals(Phone.class)) {
                    response = new Phone();
                    ((Phone) response).setNumber(sourceFieldValue.toString());
                    ((Phone) response).setPhoneKind(PhoneKind.FAX);
                }
            } else if (sourceFieldValue instanceof eu.ohim.sp.filing.domain.tm.Phone
                    || sourceFieldValue instanceof eu.ohim.sp.filing.domain.ds.Phone) {
                if (destinationClass.equals(Phone.class)) {
                    response = new Phone();
                    ((Phone) response).setNumber(BeanUtilsBean.getInstance().getProperty(sourceFieldValue, "value"));
                    if (BeanUtilsBean.getInstance().getProperty(sourceFieldValue, "phoneKind") != null) {
                        ((Phone) response).setPhoneKind(mapper.map(
                                sourceFieldValue.getClass().getDeclaredMethod("getPhoneKind").invoke(sourceFieldValue), PhoneKind.class));
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
            LOGGER.warn(e);
        }

        return response;
    }
}
