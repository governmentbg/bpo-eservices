package eu.ohim.sp.common.ui.form.opposition;

import org.apache.commons.lang.StringUtils;

public class EntitlementOpponent implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;	
	private static final int value31 = 31;
	
	private String entitlement;
	private String details;

	/**
	 * Method that returns the entitlement
	 *
	 * @return the entitlement
	 */
	public String getEntitlement() {
		return entitlement;
	}

	/**
     * Method that sets the entitlement
     *
     * @param entitlement the entitlement to set
     */
	public void setEntitlement(String entitlement) {
		this.entitlement = entitlement;
	}
	
	
	
	 /**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   EntitlementOpponent entitlementOpponentForm = new EntitlementOpponent();
	   entitlementOpponentForm.setEntitlement(entitlement);
	   entitlementOpponentForm.setDetails(details);
       return entitlementOpponentForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(entitlement)&&StringUtils.isBlank(details));
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof EntitlementOpponent))
       {
           return false;
       }

       EntitlementOpponent that = (EntitlementOpponent) o;

       if (entitlement != null ? !entitlement.equals(that.entitlement) : that.entitlement != null)
       {
           return false;
       }
       
       if (details != null ? !details.equals(that.details) : that.details != null)
       {
           return false;
       }
     

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = entitlement != null ? entitlement.hashCode() : 0;
       result = value31 * result + (details != null ? details.hashCode() : 0);
       return result;
   }
		
	
}
