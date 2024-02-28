package eu.ohim.sp.common.ui.form.opposition;



public class EarlierEntitlementRightInf implements java.io.Serializable, Cloneable  {
	
	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	private EarlierEntitlementRightDetails earlierEntitlementRightDetails;
	private EntitlementOpponent entitlementOpponent;
	private GroundsRelativeForOpposition groundsRelativeForOpposition;
	private boolean importHelper;
	private boolean importHelperManual;
	
	/**
	 * Method that returns the earlierEntitlementRightDetails
	 * 
	 * @return
	 */
	public EarlierEntitlementRightDetails getEarlierEntitlementRightDetails() {
		return earlierEntitlementRightDetails;
	}
	/**
     * Method that sets the earlierEntitlementRightDetails
     *
     * @param earlierEntitlementRightDetails the earlierEntitlementRightDetails to set
     */
	public void setEarlierEntitlementRightDetails(
			EarlierEntitlementRightDetails earlierEntitlementRightDetails) {
		this.earlierEntitlementRightDetails = earlierEntitlementRightDetails;
	}
	 /**
	 * @return the entitlementOpponent
	 */
	public EntitlementOpponent getEntitlementOpponent() {
		return entitlementOpponent;
	}
	/**
	 * @param entitlementOpponent the entitlementOpponent to set
	 */
	public void setEntitlementOpponent(EntitlementOpponent entitlementOpponent) {
		this.entitlementOpponent = entitlementOpponent;
	}
	/**
	 * @return the groundsRelativeForOpposition
	 */
	public GroundsRelativeForOpposition getGroundsRelativeForOpposition() {
		return groundsRelativeForOpposition;
	}
	/**
	 * @param groundsRelativeForOpposition the groundsRelativeForOpposition to set
	 */
	public void setGroundsRelativeForOpposition(
			GroundsRelativeForOpposition groundsRelativeForOpposition) {
		this.groundsRelativeForOpposition = groundsRelativeForOpposition;
	}
	
	
/**
	 * @return the importHelper
	 */
	public boolean isImportHelper() {
		return importHelper;
	}
	/**
	 * @param importHelper the importHelper to set
	 */
	public void setImportHelper(boolean importHelper) {
		this.importHelper = importHelper;
	}
	
	
/**
	 * @return the importHelperManual
	 */
	public boolean isImportHelperManual() {
		return importHelperManual;
	}
	/**
	 * @param importHelperManual the importHelperManual to set
	 */
	public void setImportHelperManual(boolean importHelperManual) {
		this.importHelperManual = importHelperManual;
	}
/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   EarlierEntitlementRightInf earlierEntitlementRightInfForm = new EarlierEntitlementRightInf();
	   earlierEntitlementRightInfForm.setEarlierEntitlementRightDetails(earlierEntitlementRightDetails);
	   earlierEntitlementRightInfForm.setEntitlementOpponent(entitlementOpponent);
	   earlierEntitlementRightInfForm.setGroundsRelativeForOpposition(groundsRelativeForOpposition);
	   earlierEntitlementRightInfForm.setImportHelper(importHelper);
	   earlierEntitlementRightInfForm.setImportHelperManual(importHelperManual);
       return earlierEntitlementRightInfForm;
   }
	
   public boolean isEmpty()
   {
       return (earlierEntitlementRightDetails.isEmpty() && entitlementOpponent.isEmpty() && groundsRelativeForOpposition.isEmpty());
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof EarlierEntitlementRightInf))
       {
           return false;
       }

       EarlierEntitlementRightInf that = (EarlierEntitlementRightInf) o;

       if (earlierEntitlementRightDetails != null ? !earlierEntitlementRightDetails.equals(that.earlierEntitlementRightDetails) : that.earlierEntitlementRightDetails != null)
       {
           return false;
       }
       if (entitlementOpponent != null ? !entitlementOpponent.equals(that.entitlementOpponent) : that.entitlementOpponent != null)
       {
           return false;
       }
       if (groundsRelativeForOpposition != null ? !groundsRelativeForOpposition.equals(that.groundsRelativeForOpposition) : that.groundsRelativeForOpposition != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = earlierEntitlementRightDetails != null ? earlierEntitlementRightDetails.hashCode() : 0;
       result = value31 * result + (entitlementOpponent != null ? entitlementOpponent.hashCode() : 0);
       result = value31 * result + (groundsRelativeForOpposition != null ? groundsRelativeForOpposition.hashCode() : 0);
       return result;
   }
	
	
}
