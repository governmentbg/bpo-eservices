package eu.ohim.sp.external.register.outside.ws.client;

import eu.ohim.sp.external.domain.design.Design;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.ws.exception.DesignFaultException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;
import javax.xml.ws.soap.SOAPBinding;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 *
 */
@WebService(name = DesignSearchClientWSService.SERVICE_NAME, targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE)
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public interface DesignSearchClientWS {


	/**
	 *
	 * @param office
	 * @param designId
	 * @return
	 *     returns eu.ohim.sp.external.services.design.Design
	 * @throws DesignFaultException
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "getDesign", targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, className = "GetDesign")
	@ResponseWrapper(localName = "getDesignResponse", targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, className = "GetDesignResponse")
    Design getDesign(
			@WebParam(name = "office", targetNamespace = "")
			String office,
			@WebParam(name = "designId", targetNamespace = "")
			String designId)
			throws DesignFaultException
	;

	/**
	 *
	 * @param office
	 * @param extraImport
	 * @param designId
	 * @return
	 *     returns eu.ohim.sp.external.services.design.DesignApplication
	 * @throws DesignFaultException
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "getDesignApplication", targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, className = "GetDesignApplication")
	@ResponseWrapper(localName = "getDesignApplicationResponse", targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, className = "GetDesignApplicationResponse")
    DesignApplication getDesignApplication(
			@WebParam(name = "office", targetNamespace = "")
			String office,
			@WebParam(name = "designId", targetNamespace = "")
			String designId,
			@WebParam(name = "extraImport", targetNamespace = "")
			Boolean extraImport)
			throws DesignFaultException
	;

	/**
	 *
	 * @param office
	 * @param text
	 * @param numberOfResults
	 * @return
	 *     returns java.lang.String
	 * @throws DesignFaultException
	 */
	@WebMethod
	@WebResult(targetNamespace = "")
	@RequestWrapper(localName = "getDesignAutocomplete", targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, className = "GetDesignAutocomplete")
	@ResponseWrapper(localName = "getDesignAutocompleteResponse", targetNamespace = DesignSearchClientWSService.SERVICE_NAMESPACE, className = "GetDesignAutocompleteResponse")
	String getDesignAutocomplete(
			@WebParam(name = "office", targetNamespace = "http://ohim.eu/sp/domain/common/v3")
			String office,
			@WebParam(name = "text", targetNamespace = "http://ohim.eu/sp/domain/common/v3")
			String text,
			@WebParam(name = "numberOfResults", targetNamespace = "http://ohim.eu/sp/domain/common/v3")
			Integer numberOfResults)
			throws DesignFaultException
	;

}
