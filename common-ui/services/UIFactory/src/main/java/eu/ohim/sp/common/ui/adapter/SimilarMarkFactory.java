/*******************************************************************************
 * * $Id:: SimilarMarkFactory.java 113496 2013-04-22 15:03:04Z karalch           $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;
import eu.ohim.sp.core.domain.contact.Address;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.MarkDescription;
import eu.ohim.sp.core.domain.trademark.MarkDisclaimer;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import eu.ohim.sp.core.domain.trademark.TradeMark;

@Component
public class SimilarMarkFactory implements UIFactory<List<TradeMark>, List<SimilarMarkForm>>
{
	@Value("${sp.office}")
    private String office;

    /**
     * Gets the office
     * @return String office
     */
    public String getOffice()
    {
        return office;
    }

    /**
     * Returns the office
     * @param office
     */
    public void setOffice(String office)
    {
        this.office = office;
    }

    @Override
    public List<TradeMark> convertTo(List<SimilarMarkForm> form)
    {
    	if (form == null)
        {
            return new ArrayList<TradeMark>();
        }
        List<TradeMark> list = new ArrayList<TradeMark>();

        for (SimilarMarkForm tm : form)
        {
            list.add(similarMarkFormToCoreTradeMarkApplication(tm));
        }
        return list;
    }

    protected TradeMark similarMarkFormToCoreTradeMarkApplication(SimilarMarkForm form) {
    	if (form == null) {
    		return new TradeMark();
    	}
    	TradeMark tradeMark = new TradeMark();
    	
    	//Set owner name
    	tradeMark.setApplicants(new ArrayList<Applicant>());
    	Applicant applicant = new Applicant();
    	List<Address> addresses = new ArrayList<Address>();
    	applicant.setAddresses(addresses);
    	applicant.setName(new PersonName());
    	applicant.getName().setFirstName(form.getOwnerName());
    	tradeMark.getApplicants().add(applicant);
    	
    	//Set mark details
    	List<MarkDescription> markDescriptions = new ArrayList<MarkDescription>();
		tradeMark.setMarkDescriptions(markDescriptions );
		
		List<MarkDisclaimer> markDisclaimers = new ArrayList<MarkDisclaimer>();
		tradeMark.setMarkDisclaimers(markDisclaimers );
    	
    	for(MarkFeature mf : MarkFeature.values()) {
    		if(mf.toString().equals(form.getType())) {
    			tradeMark.setMarkKind(mf);
    			break;
    		}
    	}    	

    	tradeMark.setApplicationNumber(form.getApplicationNumber());
    	if (StringUtils.isNotEmpty(form.getName())) {
    		List<MarkDescription> markDescriptionList = new ArrayList<MarkDescription>();
    		MarkDescription md = new MarkDescription();
    		md.setValue(form.getName());
    		markDescriptionList.add(md);
    		tradeMark.setMarkDescriptions(markDescriptionList);
    	}
    	tradeMark.setRegistrationOffice(form.getOffice());
    	
    	//Set goods and services
    	List<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
    	for(String classNumber : form.getInputTerms()) {
    		ClassDescription cd = new ClassDescription();
    		cd.setClassNumber(classNumber);
    		classDescriptions.add(cd);
    	}
    	
    	tradeMark.setClassDescriptions(classDescriptions);
    	
    	return tradeMark;
    }

    protected SimilarMarkForm coreTradeMarkApplicationToSimilarMarkForm(TradeMark core)
    {
        if (core == null)
        {
            return new SimilarMarkForm();
        }
        SimilarMarkForm form = new SimilarMarkForm();
        if (core.getApplicants() != null && !core.getApplicants().isEmpty())
        {
        	
        	Applicant applicant = core.getApplicants().get(0);
        	if (applicant.getName() != null) {
        		String firstName = StringUtils.isNotBlank(applicant.getName().getFirstName()) ? applicant.getName().getFirstName() : "";
        		String lastName = StringUtils.isNotBlank(applicant.getName().getLastName()) ? applicant.getName().getLastName() : "";
        		form.setOwnerName((firstName + " " + lastName).trim());
        	}
        }
        if (core.getMarkKind() != null)
        {
            form.setType(core.getMarkKind().name());
        }
        if (core.getApplicationNumber() != null)
        {
            form.setApplicationNumber(core.getApplicationNumber());
        }
        if (core.getWordSpecifications() != null && !core.getWordSpecifications().isEmpty())
        {
            form.setName(core.getWordSpecifications().get(0).getWordElements());
        }
        if (core.getClassDescriptions() != null && !core.getClassDescriptions().isEmpty())
        {
        	List<String> gsTerms = new ArrayList<String>();
        	for (ClassDescription classDescription : core.getClassDescriptions()) {
				gsTerms.add(classDescription.getClassNumber());
			}
        	form.setInputTerms(gsTerms);
        }
            
        form.setOffice(core.getRegistrationOffice());
        form.setOfficeSpecificId(core.getOfficeSpecificId());
        return form;
    }

    @Override
    public List<SimilarMarkForm> convertFrom(List<TradeMark> core)
    {
        if (core == null)
        {
            return new ArrayList<SimilarMarkForm>();
        }
        List<SimilarMarkForm> list = new ArrayList<SimilarMarkForm>();

        for (TradeMark tm : core)
        {
            list.add(coreTradeMarkApplicationToSimilarMarkForm(tm));
        }
        return list;
    }

}
