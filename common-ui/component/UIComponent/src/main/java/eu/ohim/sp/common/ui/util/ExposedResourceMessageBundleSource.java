/*******************************************************************************
 * $Id:: ExposedResourceMessageBundleSource.java 2020/06/30 18:02 jmunoze
 *
 *        . * .
 *      * RRRR  *   Copyright (c) 2012-2020 EUIPO: European Intelectual
 *     .  RR  R  .  Property Organization (trademarks and designs).
 *     *  RRR    *
 *      . RR RR .   ALL RIGHTS RESERVED
 *       *. _ .*
 *
 *  The use and distribution of this software is under the restrictions exposed in 'license.txt'
 *
 ******************************************************************************/

package eu.ohim.sp.common.ui.util;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class ExposedResourceMessageBundleSource extends ReloadableResourceBundleMessageSource {

    @Override
    protected Properties loadProperties(Resource resource, String fileName) throws IOException {
        return super.loadProperties(resource, fileName);
    }

    /**
     * Gets all messages for presented Locale.
     * @param locale user request's locale
     * @return all messages
     */
    public Properties getMessages(Locale locale){
        return getMergedProperties(locale).getProperties();
    }
}
