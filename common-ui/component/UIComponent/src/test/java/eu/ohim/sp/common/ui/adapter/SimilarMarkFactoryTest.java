/*******************************************************************************
 * * $Id:: SimilarMarkFactoryTest.java 113496 2013-04-22 15:03:04Z karalch       $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.adapter;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eu.ohim.sp.common.ui.form.trademark.SimilarMarkForm;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.PersonName;
import eu.ohim.sp.core.domain.trademark.ClassDescription;
import eu.ohim.sp.core.domain.trademark.ClassificationTerm;
import eu.ohim.sp.core.domain.trademark.MarkFeature;
import eu.ohim.sp.core.domain.trademark.TradeMark;
import eu.ohim.sp.core.domain.trademark.WordSpecification;

/**
 * @author ionitdi
 */
public class SimilarMarkFactoryTest
{
	private SimilarMarkFactory similarMarkFactory;

    @Before
    public void setUp()
    {
        similarMarkFactory = new SimilarMarkFactory();
    }

    @Test
    public void coreTradeMarkApplicationToSimilarMarkForm_nullCore_returnsEmptyForm()
    {
        /// Arrange
        TradeMark core = null;

        /// Act
        SimilarMarkForm result = similarMarkFactory.coreTradeMarkApplicationToSimilarMarkForm(core);

        /// Assert
        assertEquals(null, result.getOwnerName());
        assertEquals(null, result.getApplicationNumber());
        assertEquals(null, result.getInputTerms());
        assertEquals(null, result.getName());
        assertEquals(null, result.getOffice());
        assertEquals(null, result.getType());
    }

    @Test
    public void coreTradeMarkApplicationToSimilarMarkForm_filledCore_returnsForm()
    {
        similarMarkFactory.setOffice("office");
        /// Arrange
        TradeMark core = new TradeMark();

        List<Applicant> applicants = new ArrayList<Applicant>();
        Applicant applicant = new Applicant();
        PersonName personName = new PersonName();
        personName.setFirstName("first name");
        personName.setLastName("last name");
        applicant.setName(personName);
        applicants.add(applicant);
        core.setApplicants(applicants);
        
        core.setMarkKind(MarkFeature.SOUND);

        core.setApplicationNumber("application number");

        List<WordSpecification> wordSpecifications = new ArrayList<WordSpecification>();
        WordSpecification wordSpecification = new WordSpecification();
        wordSpecification.setWordElements("word elements");
        wordSpecifications.add(wordSpecification);
        core.setWordSpecifications(wordSpecifications);
        
        core.setRegistrationOffice("office");

        List<ClassDescription> classDescriptions = new ArrayList<ClassDescription>();
        ClassDescription classDescription = new ClassDescription();
        classDescription.setClassificationTerms(new ArrayList<ClassificationTerm>());
        classDescription.setClassNumber("class no");
        classDescriptions.add(classDescription);
        core.setClassDescriptions(classDescriptions);
        
        /// Act
        SimilarMarkForm result = similarMarkFactory.coreTradeMarkApplicationToSimilarMarkForm(core);

        /// Assert
        assertEquals("first name last name", result.getOwnerName());
        assertEquals("application number", result.getApplicationNumber());
        assertEquals("class no", result.getInputTerms().get(0));
        assertEquals("word elements", result.getName());
        assertEquals("office", result.getOffice());
        assertEquals("Sound", result.getType());
    }

    @Test
    public void convertFrom_nullCore_returnsEmptyList()
    {
        /// Arrange
        List<TradeMark> core = null;

        /// Act
        List<SimilarMarkForm> result = similarMarkFactory.convertFrom(core);

        /// Assert
        assertEquals(true, result.isEmpty());
    }

    @Test
    public void convertFrom_filledCore_returnsList()
    {
        /// Arrange
        List<TradeMark> core = new ArrayList<TradeMark>();
        core.add(new TradeMark());

        /// Act
        List<SimilarMarkForm> result = similarMarkFactory.convertFrom(core);

        /// Assert
        assertEquals(false, result.isEmpty());
    }
}
