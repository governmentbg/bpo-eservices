package eu.ohim.sp.common.ui.form.payment;

import java.util.Date;

public class FeeLineForm implements java.io.Serializable{

	private static final long serialVersionUID = 1L;

	private String nameKey;
	private Double amount = 0d;
	private String status;
    private String code;
	private String expirationExtentYearsFromEntitlement;
	private Date expirationExtentNewDate;
	
	public String getNameKey() {
		return nameKey;
	}
	public void setNameKey(String nameKey) {
		this.nameKey = nameKey;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

	public String getExpirationExtentYearsFromEntitlement() {
		return expirationExtentYearsFromEntitlement;
	}

	public void setExpirationExtentYearsFromEntitlement(String expirationExtentYearsFromEntitlement) {
		this.expirationExtentYearsFromEntitlement = expirationExtentYearsFromEntitlement;
	}

	public Date getExpirationExtentNewDate() {
		return expirationExtentNewDate;
	}

	public void setExpirationExtentNewDate(Date expirationExtentNewDate) {
		this.expirationExtentNewDate = expirationExtentNewDate;
	}
}
