/*******************************************************************************
 * $Id:: MessagesTag.java 2020/06/30 18:02 jmunoze
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

package eu.ohim.sp.common.ui.servlet.tags;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.util.ExposedResourceMessageBundleSource;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.util.Properties;

/**
 * @author ionitdi
 */
public class MessagesTag extends FlowContextAwareTag {

    private static final long serialVersionUID = -2290152088503092762L;

    @Override
    protected int doStartTagInternal() {

        ExposedResourceMessageBundleSource messageSource = getRequestContext()
                .getWebApplicationContext().getBean(ExposedResourceMessageBundleSource.class);

        Properties properties = messageSource.getMessages(LocaleContextHolder.getLocale());

        String messages = null;
        try {
            messages = new ObjectMapper().writeValueAsString(properties);
        } catch (IOException e) {
            throw new SPException(e);
        }

        if (StringUtils.isNotBlank(messages)) {
            try {
                pageContext.getOut().print(messages);
            }
            catch (IOException e) {
                throw new SPException(e);
            }
        }

        return SKIP_BODY;
    }
}
