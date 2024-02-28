package eu.ohim.sp.core.fee.provided;

import eu.ohim.sp.external.domain.epayment.Fee;

public interface FeeCalculation {

    public java.util.List<Fee> calculateApplicationFees(String applicationType, byte[] applicationXML)
            throws FeeFault_Exception;

}
