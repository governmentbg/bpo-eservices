/*******************************************************************************
 * * $Id:: LogConfigurator.java 14820 2012-11-05 10:06:51Z villama               $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.util;

import eu.ohim.sp.common.SPException;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class LogConfigurator {
	
	private static final String NON_VALIDATING_SCHEMA = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final Logger LOGGER = Logger.getLogger(LogConfigurator.class);

    private static DocumentBuilderFactory factory;
    private static DocumentBuilder builder;

    public static Element getDocument(String resource) {
        Document doc = null;
        InputStream inputStream = null;
        try {
            builder = getDomBuilder();
            inputStream = new FileInputStream(resource);
            
            doc = builder.parse(inputStream);
        } catch (FileNotFoundException e){
        	//No logs will be created but the application will proceed
            LOGGER.warn(e);
        } catch (IOException e) {
            throw new SPException("Error getting log4j properties", e, "");
        } catch (SAXException e) {
            throw new SPException("Error getting log4j properties", e, "");
        } finally {
        	if (inputStream!=null) {
        		try {
					inputStream.close();
				} catch (IOException e) {
		            LOGGER.debug("Error closing input stream", e);
				}
        	}
        }
        if (doc!=null) {
            return doc.getDocumentElement();
        } else {
        	return null;
        }
    }

    private static synchronized DocumentBuilder getDomBuilder() {
        try {
            if (builder == null) {
                if (factory == null) {
                    factory = DocumentBuilderFactory.newInstance();

                    factory.setAttribute(NON_VALIDATING_SCHEMA, Boolean.FALSE);
                }
                builder = factory.newDocumentBuilder();
            }
            return builder;
        } catch (ParserConfigurationException e) {
            throw new SPException(e);
        }

    }
}
