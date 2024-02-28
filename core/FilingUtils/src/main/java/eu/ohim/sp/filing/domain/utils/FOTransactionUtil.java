/*
 *  FspDomain:: TransactionUtil 09/10/13 15:03 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.filing.domain.utils;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.external.application.PostProcessor;
import eu.ohim.sp.external.application.TransactionUtil;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.dozer.Mapper;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

/**
 * Keeps the mapping and parses xml or transforms object to xml
 * @param <O> the original generated object e.g. TrademarkApplication that should extend IPApplication
 * @param <D> the destination object e.g. Transaction that should be JAXB generated class
 */
public class FOTransactionUtil<O extends IPApplication, D> implements TransactionUtil {

    public static final String MAPPED_DOCUMENTS = "MAPPED_DOCUMENTS";
    public static final String NOT_MAPPED_DOCUMENTS = "WITHOUT_ATTACHMENTS";


    /**
     * Logger of {@see ReferenceUtil}
     */
    private static final Logger LOGGER = Logger.getLogger(TransactionUtil.class);

    /**
     * It contains the marshaller and the unmarshaller
     */
	private JAXBInitiator<D> jaxbInitiator;

    /**
     * Holder of the destination class
     */
    private Class<D> destination;
    /**
     * Holder of the original class that should be transformed to xml
     */
    private Class<O> original;

    /**
     * Utility class to modify URI dynamically
     */
    private ReferenceUtil referenceUtil;

    private PostProcessor<O, D> postProcessor;

    /**
     * Holder of the mapper from original to destination
     */
    private Map<String, Mapper> mappers;

    public PostProcessor<O, D> getPostProcessor()
    {
        return postProcessor;
    }

    public void setPostProcessor(PostProcessor<O, D> postProcessor)
    {
        this.postProcessor = postProcessor;
    }

    /**
     * Constructor that should contain the following information
     * @param referenceUtil util that is used to post process the resulted destination object
     * @param destination required of the destination class
     * @param original required of the original class
     * @param schema schema against which will be done validation. if null then no validation is done
     * @param mappers mappers that are provided to map the classes
     */
    public FOTransactionUtil(ReferenceUtil referenceUtil,
                           Class<D> destination,
                           Class<O> original,
                           String schema,
                           Map<String, Mapper> mappers) {
        try {
            this.referenceUtil = referenceUtil;
            this.original = original;
            this.destination = destination;
            this.mappers = mappers;

            jaxbInitiator = new JAXBInitiator<D>(destination, schema);
        } catch (JAXBException e) {
            LOGGER.error(e);
        }
    }

    public O postProcess(O core) {
        if (postProcessor != null) {
            return postProcessor.processCore(core);
        }
        return core;
    }

    public D postProcess(D transaction) {
        if (postProcessor != null) {
            return postProcessor.processTransaction(transaction);
        }
        return transaction;
    }

    /**
     * Trasform from transaction D to IPApplication
     * @param transaction
     * @param mapAttachments
     * @return
     */
    public IPApplication retrieveIPApplication(D transaction, boolean mapAttachments)  {
        IPApplication ipApplication = null;

        if (mapAttachments) {
            ipApplication = mappers.get(MAPPED_DOCUMENTS).map(transaction, original);
        } else {
            ipApplication = mappers.get(NOT_MAPPED_DOCUMENTS).map(transaction, original);
        }

        ipApplication = postProcess((O) ipApplication);

        return ipApplication;
    }


    /**
     * Parses the input type xml on form of {@see byte[]} and get an IPApplication
     * @param byteArray contains the expected xml
     * @param typeApplication the type of the application
     * @param mapAttachments
     * @return the IPApplication
     */
    @SuppressWarnings("rawtypes")
    @Override
    public IPApplication generateIPApplication(byte[] byteArray, String typeApplication, boolean mapAttachments) {
        IPApplication ipApplication = null;
		LOGGER.debug("	>>> GenerateTradeMarkApplication START");
		ByteArrayInputStream is = new ByteArrayInputStream(byteArray);
		try {
			Unmarshaller unmarshaller = jaxbInitiator.getUnmarshaller();
            Object unmarshalled = unmarshaller.unmarshal(is);

            D destinationObject = null;
            if (unmarshalled instanceof  JAXBElement) {
                destinationObject = (D) ((JAXBElement) unmarshalled).getValue();
            } else {
                destinationObject = (D) unmarshalled;
            }

            ipApplication = retrieveIPApplication(destinationObject, mapAttachments);

            LOGGER.debug("	>>> GenerateTradeMarkApplication END");
		} catch (JAXBException e) {
            LOGGER.error(e);
			throw new SPException("JAXBException", e, "error.repository.error");
		}
        return ipApplication;
	}

	/**
     * Generates {@see byte[]} that contain the resulting xml that depends on {@see D}
     * @param application The IPApplication used to generate the byte[]
	 * @param typeApplication The typeApplication of the application
	 * @param mapAttachments If it's true the documents will be converted
	 * @return {@see byte[]} that contain the resulting xml
	 */
    @Override
	public byte[] generateByte(IPApplication application, String typeApplication, boolean mapAttachments) {
		try {
			LOGGER.debug("	>>> GenerateByte START");

			Marshaller marshaller = jaxbInitiator.getMarshaller();

			D transaction = createTransactionInformation(application, typeApplication, mapAttachments);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

            transaction = postProcess(transaction);
            marshaller.marshal(transaction, baos);

            LOGGER.debug("	>>> GenerateByte END");
			return baos.toByteArray();
		} catch (JAXBException e) {
            throw new SPException("Failed to read file", e, "error.io.error");
		}
	}

	/**
     * Creates a Transaction Type JAXB Object by providing the core TradeMarkApplication object
     * @param application the core TradeMarkApplication object
	 * @param typeApplication
     * @param mapAttachments If it's true convert the attachments
     * @return a Transaction Type JAXB Object
     */
    public D createTransactionInformation(IPApplication application, String typeApplication, boolean mapAttachments) {
        try {
            D destinationObject;

            if (mapAttachments) {
                destinationObject = mappers.get(MAPPED_DOCUMENTS).map(application, destination);
            } else {
                destinationObject = mappers.get(NOT_MAPPED_DOCUMENTS).map(application, destination);
            }

            BeanUtilsBean.getInstance().copyProperty(destinationObject, "tradeMarkTransactionBody[0].transactionContentDetails.transactionCode", "Trade Mark E-Filing");
            if (referenceUtil!=null) {
                referenceUtil.setUrlsDocumentsByTransaction(destinationObject);
            }

	        //Check if the transaction has a TradeMarkApplication
            return destinationObject;
        } catch (Exception e) {
        	throw new SPException("Exception in createTransactionInformation " + e.getMessage(), e);
        }
    }

}