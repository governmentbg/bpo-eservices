/*
 *  FspDomain:: LogTimeInterceptor 02/09/13 12:02 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.filing.domain.utils;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: KARALCH
 * Date: 28/08/13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public class LogTimeInterceptor {

    @javax.interceptor.AroundInvoke
    public java.lang.Object handleException(javax.interceptor.InvocationContext ctx) throws Exception {
        String clazz = ctx.getMethod().getDeclaringClass().getName();
        String method = ctx.getMethod().getName();

        Logger logger = Logger.getLogger(clazz);

        long startTime = 0;
        if (logger.isDebugEnabled()) {
            startTime = new Date().getTime();
            logger.debug("START : " + clazz + "." + method);
        }
        Object obj = null;
        try {
            obj = ctx.proceed();
        } catch (Exception e) {
            logger.error("clazz + \".\" + method", e);
            throw e;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("END : " + clazz + "." + method);
            long endTime = new Date().getTime();
            logger.debug(endTime - startTime);
        }

        return obj;
    }

}
