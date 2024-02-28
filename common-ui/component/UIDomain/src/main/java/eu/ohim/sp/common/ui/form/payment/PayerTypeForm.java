/*******************************************************************************
 * * $Id:: PriorityForm.java 14333 2012-10-29 13:23:34Z soriama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.form.payment;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

public class PayerTypeForm extends AbstractForm {

    private static final long serialVersionUID = 1L;
    private String code;
    private String description;
    private String payerTypeCode;

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the payerTypeCode
     */
    public String getPayerTypeCode() {
        return payerTypeCode;
    }

    /**
     * @param payerTypeCode the payerTypeCode to set
     */
    public void setPayerTypeCode(String payerTypeCode) {
        this.payerTypeCode = payerTypeCode;
    }

    @Override
    public AvailableSection getAvailableSectionName() {
        return AvailableSection.PAYMENT_SECTION;
    }

    @Override
    public AbstractForm clone() throws CloneNotSupportedException {
        PayerTypeForm payerTypeForm = new PayerTypeForm();
        payerTypeForm.setCode(code);
        payerTypeForm.setPayerTypeCode(payerTypeCode);
        payerTypeForm.setDescription(description);
        return payerTypeForm;
    }

}
