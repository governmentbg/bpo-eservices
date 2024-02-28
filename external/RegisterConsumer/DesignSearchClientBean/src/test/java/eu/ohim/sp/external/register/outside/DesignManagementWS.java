package eu.ohim.sp.external.register.outside;

import eu.ohim.sp.external.domain.common.Fault;
import eu.ohim.sp.external.domain.design.Design;
import eu.ohim.sp.external.domain.design.DesignApplication;
import eu.ohim.sp.external.services.design.DesignFault;
import eu.ohim.sp.external.ws.exception.DesignFaultException;
import org.apache.commons.lang.StringUtils;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;

@WebService(serviceName = "DesignSearchService", targetNamespace = "http://ohim.eu/sp/services/design-search/v3", portName = "DesignSearchServicePort", wsdlLocation = "wsdl/DesignSearchService.wsdl")
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
public class DesignManagementWS implements DesignManagementWSInterface {

	@Override
	public String getDesignAutocomplete(@WebParam(name = "office", targetNamespace = "http://ohim.eu/sp/domain/common/v3") String office, @WebParam(name = "text", targetNamespace = "http://ohim.eu/sp/domain/common/v3") String text, @WebParam(name = "numberOfRows", targetNamespace = "http://ohim.eu/sp/domain/common/v3") int numberOfRows) throws DesignFaultException {
		if (StringUtils.isBlank(office)) {
			DesignFault designFault = new DesignFault();
			designFault.setFault(new Fault());
			designFault.getFault().setCode("error.code");
			designFault.getFault().setMessage("office should not be empty");
			throw  new DesignFaultException("office should not be empty", designFault);
		}

		return "getDesignAutocomplete"+office+text;
	}

    @Override
    public DesignApplication getDesignApplication(@WebParam(name = "office", targetNamespace = "") String office, @WebParam(name = "designId", targetNamespace = "") String designId, @WebParam(name = "extraImport", targetNamespace = "") Boolean extraImport) throws DesignFaultException {
        if (StringUtils.isBlank(office)) {
            try {
                StringUtils.defaultIfBlank(office, null).equals("error");
            } catch (NullPointerException e) {
                DesignFault designFault = new DesignFault();
                designFault.setFault(new Fault());
                designFault.getFault().setCode("error.code");
                designFault.getFault().setMessage("system error");
                throw new DesignFaultException("system error", designFault);
            }
        }

        DesignApplication designApplication = new DesignApplication();
        designApplication.setApplicationNumber(designId);
        designApplication.setReceivingOffice(office);
        return designApplication;  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
    public Design getDesign(@WebParam(name = "office", targetNamespace = "") String office, @WebParam(name = "designId", targetNamespace = "") String designId) throws DesignFaultException {
        if (StringUtils.isBlank(office)) {
            try {
                StringUtils.defaultIfBlank(office, null).equals("error");
            } catch (NullPointerException e) {
                DesignFault designFault = new DesignFault();
                designFault.setFault(new Fault());
                designFault.getFault().setCode("error.code");
                designFault.getFault().setMessage("system error");
                throw new DesignFaultException("system error", designFault, e);
            }
        }

        Design design = new Design();
        design.setRegistrationNumber(designId);
        return design;
    }
}
