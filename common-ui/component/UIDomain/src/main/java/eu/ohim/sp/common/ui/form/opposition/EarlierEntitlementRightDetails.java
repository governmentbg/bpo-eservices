package eu.ohim.sp.common.ui.form.opposition;

import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.patent.ESPatentDetailsForm;
import org.apache.commons.lang.StringUtils;

import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarlierEntitlementRightDetails implements java.io.Serializable, Cloneable {

	private static final long serialVersionUID = 1L;	
	private static final int value31 = 31;
	

	private String categoryTradeMark;
	private String relatedApplicationsNumbers;
	private Date dateOfFame;
	private TMDetailsForm earlierTradeMarkDetails;
	private ApplicantForm applicantHolder;
	private List<ESDesignDetailsForm> earlierDesigns = new ArrayList<>();
	private String country;
	private String typeRight;
	private String typeRightDetails;
	private String areaActivity;
	private Boolean filterCountriesEarlierWellKnow;
	private Boolean filterCountriesNonRegisteredTM;
	private Boolean filterCountriesAll;
	private Boolean filterCountriesParisConvention;
	private Boolean filterCountriesEarlierTradeMark;
	private Boolean filterCountriesForeignTMView;

	private String earlierRightDescription;

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}


	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}



	/**
	 * Method that returns the categoryTradeMark
	 * 
	 * @return
	 */
	public String getCategoryTradeMark() {
		return categoryTradeMark;
	}

	/**
     * Method that sets the categoryTradeMark
     *
     * @param categoryTradeMark the categoryTradeMark to set
     */
	public void setCategoryTradeMark(String categoryTradeMark) {
		this.categoryTradeMark = categoryTradeMark;
	}

	/**
	 * Method that returns the typeRight
	 * 
	 * @return
	 */
	public String getTypeRight() {
		return typeRight;
	}

	/**
     * Method that sets the typeRight
     *
     * @param typeRight the typeRight to set
     */
	public void setTypeRight(String typeRight) {
		this.typeRight = typeRight;
	}
	/**
	 * Method that returns the typeRightDetails
	 * 
	 * @return
	 */
	public String getTypeRightDetails() {
		return typeRightDetails;
	}

	/**
     * Method that sets the typeRightDetails
     *
     * @param typeRightDetails the typeRightDetails to set
     */
	public void setTypeRightDetails(String typeRightDetails) {
		this.typeRightDetails = typeRightDetails;
	}
	


	/**
	 * Method that returns the areaActivity
	 * 
	 * @return
	 */
	public String getAreaActivity() {
		return areaActivity;
	}

	/**
     * Method that sets the areaActivity
     *
     * @param areaActivity the areaActivity to set
     */
	public void setAreaActivity(String areaActivity) {
		this.areaActivity = areaActivity;
	}
	
	
	
	 /**
	 * @return the earlierTradeMarkDetails
	 */
	public TMDetailsForm getEarlierTradeMarkDetails() {
		return earlierTradeMarkDetails;
	}


	/**
	 * @param earlierTradeMarkDetails the earlierTradeMarkDetails to set
	 */
	public void setEarlierTradeMarkDetails(TMDetailsForm earlierTradeMarkDetails) {
		this.earlierTradeMarkDetails = earlierTradeMarkDetails;
	}

	/**
	 * @return the applicantHolder
	 */
	public ApplicantForm getApplicantHolder() {
		return applicantHolder;
	}


	/**
	 * @param applicantHolder the applicantHolder to set
	 */
	public void setApplicantHolder(ApplicantForm applicantHolder) {
		this.applicantHolder = applicantHolder;
	}

	

	/**
	 * @return the filterCountriesEarlierWellKnow
	 */
	public Boolean getFilterCountriesEarlierWellKnow() {
		return filterCountriesEarlierWellKnow;
	}


	/**
	 * @param filterCountriesEarlierWellKnow the filterCountriesEarlierWellKnow to set
	 */
	public void setFilterCountriesEarlierWellKnow(
			Boolean filterCountriesEarlierWellKnow) {
		this.filterCountriesEarlierWellKnow = filterCountriesEarlierWellKnow;
	}


	/**
	 * @return the filterCountriesNonRegisteredTM
	 */
	public Boolean getFilterCountriesNonRegisteredTM() {
		return filterCountriesNonRegisteredTM;
	}


	/**
	 * @param filterCountriesNonRegisteredTM the filterCountriesNonRegisteredTM to set
	 */
	public void setFilterCountriesNonRegisteredTM(
			Boolean filterCountriesNonRegisteredTM) {
		this.filterCountriesNonRegisteredTM = filterCountriesNonRegisteredTM;
	}


	/**
	 * @return the filterCountriesAll
	 */
	public Boolean getFilterCountriesAll() {
		return filterCountriesAll;
	}


	/**
	 * @param filterCountriesAll the filterCountriesAll to set
	 */
	public void setFilterCountriesAll(Boolean filterCountriesAll) {
		this.filterCountriesAll = filterCountriesAll;
	}


	/**
	 * @return the filterCountriesParisConvention
	 */
	public Boolean getFilterCountriesParisConvention() {
		return filterCountriesParisConvention;
	}


	/**
	 * @param filterCountriesParisConvention the filterCountriesParisConvention to set
	 */
	public void setFilterCountriesParisConvention(
			Boolean filterCountriesParisConvention) {
		this.filterCountriesParisConvention = filterCountriesParisConvention;
	}


	/**
	 * @return the filterCountriesEarlierTradeMark
	 */
	public Boolean getFilterCountriesEarlierTradeMark() {
		return filterCountriesEarlierTradeMark;
	}


	/**
	 * @param filterCountriesEarlierTradeMark the filterCountriesEarlierTradeMark to set
	 */
	public void setFilterCountriesEarlierTradeMark(
			Boolean filterCountriesEarlierTradeMark) {
		this.filterCountriesEarlierTradeMark = filterCountriesEarlierTradeMark;
	}

	

	/**
	 * @return the filterCountriesForeignTMView
	 */
	public Boolean getFilterCountriesForeignTMView() {
		return filterCountriesForeignTMView;
	}


	/**
	 * @param filterCountriesForeignTMView the filterCountriesForeignTMView to set
	 */
	public void setFilterCountriesForeignTMView(Boolean filterCountriesForeignTMView) {
		this.filterCountriesForeignTMView = filterCountriesForeignTMView;
	}



	public List<ESDesignDetailsForm> getEarlierDesigns() {
		return earlierDesigns;
	}

	public void setEarlierDesigns(List<ESDesignDetailsForm> earlierDesigns) {
		this.earlierDesigns = earlierDesigns;
	}

    public Date getDateOfFame() {
        return dateOfFame;
    }

    public void setDateOfFame(Date dateOfFame) {
        this.dateOfFame = dateOfFame;
    }

	public String getRelatedApplicationsNumbers() {
		return relatedApplicationsNumbers;
	}

	public void setRelatedApplicationsNumbers(String relatedApplicationsNumbers) {
		this.relatedApplicationsNumbers = relatedApplicationsNumbers;
	}

	public String getEarlierRightDescription() {
		return earlierRightDescription;
	}

	public void setEarlierRightDescription(String earlierRightDescription) {
		this.earlierRightDescription = earlierRightDescription;
	}

	@Override
	   public Object clone() 
	   {
		 EarlierEntitlementRightDetails earlierEntitlementRightDetailsForm = new EarlierEntitlementRightDetails();
		 earlierEntitlementRightDetailsForm.setAreaActivity(areaActivity);
		 earlierEntitlementRightDetailsForm.setCategoryTradeMark(categoryTradeMark);
		 earlierEntitlementRightDetailsForm.setCountry(country);
		 earlierEntitlementRightDetailsForm.setTypeRight(typeRight);
		 earlierEntitlementRightDetailsForm.setTypeRightDetails(typeRightDetails);
		 earlierEntitlementRightDetailsForm.setEarlierTradeMarkDetails(earlierTradeMarkDetails);
		 earlierEntitlementRightDetailsForm.setApplicantHolder(applicantHolder);
		 List<ESDesignDetailsForm> dsEarl = new ArrayList<>();
		 for(ESDesignDetailsForm dsForm: earlierDesigns){
		     dsEarl.add(dsForm.clone());
		 }
		 earlierEntitlementRightDetailsForm.setEarlierDesigns(dsEarl);
		 earlierEntitlementRightDetailsForm.setDateOfFame(this.dateOfFame);
		 earlierEntitlementRightDetailsForm.setRelatedApplicationsNumbers(this.relatedApplicationsNumbers);
		 earlierEntitlementRightDetailsForm.setEarlierRightDescription(this.earlierRightDescription);
	     return earlierEntitlementRightDetailsForm;
	   }
	 
	 public boolean isEmpty()
	   {
	       return (StringUtils.isEmpty(areaActivity) && StringUtils.isEmpty(categoryTradeMark) &&
	    		  StringUtils.isBlank(country)  &&
	    		  StringUtils.isEmpty(typeRight) && 
	    		  StringUtils.isEmpty(typeRightDetails) && earlierTradeMarkDetails==null && applicantHolder==null
				   && (earlierDesigns == null || earlierDesigns.size() == 0) && StringUtils.isEmpty(relatedApplicationsNumbers) && dateOfFame == null
		   && StringUtils.isEmpty(earlierRightDescription));
	   }

	   @Override
	   public boolean equals(Object o)
	   {
	       if (this == o)
	       {
	           return true;
	       }
	       if (!(o instanceof EarlierEntitlementRightDetails))
	       {
	           return false;
	       }

	       EarlierEntitlementRightDetails that = (EarlierEntitlementRightDetails) o;

	       if (areaActivity != null ? !areaActivity.equals(that.areaActivity) : that.areaActivity != null)
	       {
	           return false;
	       }
	       if (categoryTradeMark != null ? !categoryTradeMark.equals(that.categoryTradeMark) : that.categoryTradeMark != null)
	       {
	           return false;
	       }
	       if (country != null ? !country.equals(that.country) : that.country != null)
	       {
	           return false;
	       }

	       if (typeRight != null ? !typeRight.equals(that.typeRight) : that.typeRight != null)
	       {
	           return false;
	       }
	       if (typeRightDetails != null ? !typeRightDetails.equals(that.typeRightDetails) : that.typeRightDetails != null)
	       {
	           return false;
	       }
	       if (typeRightDetails != null ? !typeRightDetails.equals(that.typeRightDetails) : that.typeRightDetails != null)
	       {
	           return false;
	       }
	       if (earlierTradeMarkDetails != null ? !earlierTradeMarkDetails.equals(that.earlierTradeMarkDetails) : that.earlierTradeMarkDetails != null)
	       {
	           return false;
	       }
	       
	       if (applicantHolder != null ? !applicantHolder.equals(that.applicantHolder) : that.applicantHolder != null)
	       {
	           return false;
	       }
		   if(earlierDesigns != null && earlierDesigns.size() != 0){
			   if(that.earlierDesigns == null || that.earlierDesigns.size() == 0){
				   return false;
			   }
			   if(!that.earlierDesigns.containsAll(earlierDesigns) || !earlierDesigns.containsAll(that.earlierDesigns)){
				   return false;
			   }
		   } else {
			   if(that.earlierDesigns != null && that.earlierDesigns.size() != 0){
				   return false;
			   }
		   }
		   if(dateOfFame != null ? !dateOfFame.equals(that.dateOfFame) : that.dateOfFame != null){
	       		return false;
		   }
		   if(relatedApplicationsNumbers != null ? !relatedApplicationsNumbers.equals(that.relatedApplicationsNumbers) : that.relatedApplicationsNumbers != null){
			   return false;
		   }
		   if(earlierRightDescription != null ? !earlierRightDescription.equals(that.earlierRightDescription) : that.earlierRightDescription != null){
			   return false;
		   }


	       return true;
	   }

	   @Override
	   public int hashCode()
	   {
	       int result =  (areaActivity != null ? areaActivity.hashCode() : 0);
	       result = value31 * result + (areaActivity != null ? areaActivity.hashCode() : 0);
	       result = value31 * result + (categoryTradeMark != null ? categoryTradeMark.hashCode() : 0);
	       result = value31 * result + (country != null ? country.hashCode() : 0);
	       result = value31 * result + (typeRight != null ? typeRight.hashCode() : 0);
	       result = value31 * result + (typeRightDetails != null ? typeRightDetails.hashCode() : 0);
	       result = value31 * result + (earlierTradeMarkDetails != null ? earlierTradeMarkDetails.hashCode() : 0);
	       result = value31 * result + (applicantHolder != null ? applicantHolder.hashCode() : 0);
		   result = value31 * result + (earlierDesigns != null ? earlierDesigns.hashCode() : 0);
		   result = value31 * result + (dateOfFame != null ? dateOfFame.hashCode() : 0);
		   result = value31 * result + (relatedApplicationsNumbers != null ? relatedApplicationsNumbers.hashCode() : 0);
		   result = value31 * result + (earlierRightDescription != null ? earlierRightDescription.hashCode() : 0);
	       return result;
	   }
	
	
	
}
