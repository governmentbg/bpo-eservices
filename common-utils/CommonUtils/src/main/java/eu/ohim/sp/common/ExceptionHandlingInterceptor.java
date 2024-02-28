/*******************************************************************************
 * * $Id:: ExceptionHandlingInterceptor.java 51515 2012-11-22 16:28:52Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common;

import javax.ejb.EJBTransactionRolledbackException;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

public class ExceptionHandlingInterceptor {

    Logger logger = Logger.getLogger(ExceptionHandlingInterceptor.class);

    public ExceptionHandlingInterceptor() {

    }

    @AroundInvoke
    public Object handleException(InvocationContext context) throws SPException {
        try {
            return context.proceed();
        } catch (EJBTransactionRolledbackException e) {
            logger.debug(e.getMessage());
            logger.error(e);

            Throwable throwable = e;
            SPException initialError = null;
            while (throwable != null) {
                throwable = throwable.getCause();
                logger.error(throwable);
                if (throwable instanceof SPException) {
                    initialError = (SPException) throwable;
                }
            }
            logger.error(e.getMessage());
            throw initialError;

        } catch (javax.xml.ws.soap.SOAPFaultException exc) {
            logger.debug(exc.getMessage());
            logger.error(exc);
            throw new SPException(exc.getMessage());

        } catch (SPException e) {
            logger.debug(e.getMessage());
            logger.error(e);
            throw e;
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw (RuntimeException) e;
            }

            logger.debug(e.getMessage());
            logger.error(e);
            throw new SPException("An exception was thrown", e, "error.generic.code");
        }
    }
}
