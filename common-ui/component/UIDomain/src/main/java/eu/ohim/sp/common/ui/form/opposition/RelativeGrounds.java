package eu.ohim.sp.common.ui.form.opposition;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class RelativeGrounds implements java.io.Serializable, Cloneable {


	private static final long serialVersionUID = 1L;
	private static final int value31 = 31;
	
	protected String earlierEntitlementRightType;
	protected String earlierEntitlementRightTypeCode;
	protected EarlierEntitlementRightInf earlierEntitlementRightInf;
	protected List <RelativeGroundLawArticle> relativeGroundLawArticle;
	/**
	 * @return the earlierEntitlementRightType
	 */
	public String getEarlierEntitlementRightType() {
		return earlierEntitlementRightType;
	}
	/**
	 * @param earlierEntitlementRightType the earlierEntitlementRightType to set
	 */
	public void setEarlierEntitlementRightType(String earlierEntitlementRightType) {
		this.earlierEntitlementRightType = earlierEntitlementRightType;
	}
	/**
	 * @return the earlierEntitlementRightInf
	 */
	public EarlierEntitlementRightInf getEarlierEntitlementRightInf() {
		return earlierEntitlementRightInf;
	}
	/**
	 * @param earlierEntitlementRightInf the earlierEntitlementRightInf to set
	 */
	public void setEarlierEntitlementRightInf(
			EarlierEntitlementRightInf earlierEntitlementRightInf) {
		this.earlierEntitlementRightInf = earlierEntitlementRightInf;
	}
	/**
	 * @return the relativeGroundLawArticle
	 */
	public List<RelativeGroundLawArticle> getRelativeGroundLawArticle() {
		return relativeGroundLawArticle;
	}
	/**
	 * @param relativeGroundLawArticle the relativeGroundLawArticle to set
	 */
	public void setRelativeGroundLawArticle(
			List<RelativeGroundLawArticle> relativeGroundLawArticle) {
		this.relativeGroundLawArticle = relativeGroundLawArticle;
	}
	
	
	
	 /**
	 * @return the earlierEntitlementRightTypeCode
	 */
	public String getEarlierEntitlementRightTypeCode() {
		return earlierEntitlementRightTypeCode;
	}
	/**
	 * @param earlierEntitlementRightTypeCode the earlierEntitlementRightTypeCode to set
	 */
	public void setEarlierEntitlementRightTypeCode(
			String earlierEntitlementRightTypeCode) {
		this.earlierEntitlementRightTypeCode = earlierEntitlementRightTypeCode;
	}
/*
     * (non-Javadoc)
     *
     * @see java.lang.Object#clone()
     */
   @Override
   public Object clone() 
   {
	   RelativeGrounds relativeGroundsForm = new RelativeGrounds();
	   relativeGroundsForm.setEarlierEntitlementRightInf(earlierEntitlementRightInf);
	   relativeGroundsForm.setEarlierEntitlementRightType(earlierEntitlementRightType);
	   relativeGroundsForm.setEarlierEntitlementRightTypeCode(earlierEntitlementRightTypeCode);
	   relativeGroundsForm.setRelativeGroundLawArticle(relativeGroundLawArticle);
       return relativeGroundsForm;
   }
	
   public boolean isEmpty()
   {
       return (StringUtils.isBlank(earlierEntitlementRightType) && earlierEntitlementRightInf.isEmpty() && relativeGroundLawArticle.isEmpty());
   }

   @Override
   public boolean equals(Object o)
   {
       if (this == o)
       {
           return true;
       }
       if (!(o instanceof RelativeGrounds))
       {
           return false;
       }

       RelativeGrounds that = (RelativeGrounds) o;

       if (earlierEntitlementRightType != null ? !earlierEntitlementRightType.equals(that.earlierEntitlementRightType) : that.earlierEntitlementRightType != null)
       {
           return false;
       }
       if (earlierEntitlementRightTypeCode != null ? !earlierEntitlementRightTypeCode.equals(that.earlierEntitlementRightTypeCode) : that.earlierEntitlementRightTypeCode != null)
       {
           return false;
       }
       if (earlierEntitlementRightInf != null ? !earlierEntitlementRightInf.equals(that.earlierEntitlementRightInf) : that.earlierEntitlementRightInf != null)
       {
           return false;
       }
       if (relativeGroundLawArticle != null ? !relativeGroundLawArticle.equals(that.relativeGroundLawArticle) : that.relativeGroundLawArticle != null)
       {
           return false;
       }

       return true;
   }

   @Override
   public int hashCode()
   {
       int result = earlierEntitlementRightType != null ? earlierEntitlementRightType.hashCode() : 0;
       result = value31 * result + (earlierEntitlementRightInf != null ? earlierEntitlementRightInf.hashCode() : 0);
       result = value31 * result + (relativeGroundLawArticle != null ? relativeGroundLawArticle.hashCode() : 0);
       result = value31 * result + (earlierEntitlementRightTypeCode != null ? earlierEntitlementRightTypeCode.hashCode() : 0);
       return result;
   }
	
	
	
}
