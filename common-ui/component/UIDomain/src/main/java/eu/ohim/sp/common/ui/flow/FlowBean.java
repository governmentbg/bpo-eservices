/*******************************************************************************
 * * $Id:: FlowBean.java 58358 2013-03-05 11:18:23Z soriama $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.flow;

import java.util.List;

import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.MainForm;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.validator.Validatable;

/**
 * Bean that will hold all the information
 * about an application. It's scope would be
 * session so to keep all the information during
 * the session.
 * 
 * @author ckara
 */
public interface FlowBean extends Validatable {

    /**
     * Clear the bean
     */
    void clear();

    /**
     * Returns the collection that contains the specified class
     * 
     * @param commandClass the given class
     * @return the collection that contains the specified class
     */
    <T extends AbstractForm> List<T> getCollection(Class<T> commandClass);

    /**
     * Returns the object with the given id and is contained in the collection with the specified class
     * 
     * @param commandClass the given class
     * @param id the given id
     * @return the object with the specific id
     */
    <T extends AbstractForm> T getObject(Class<T> commandClass, String id);

    /**
     * Returns the object with the given id and is contained in the collection
     * with the specified class
     * 
     * @param newValue the new value
     * @param id the given id
     */
    <T extends AbstractForm> void replaceObject(T newValue, String id);

    /**
     * Adds a list of objects to the collection that contains the class of the objects
     * 
     * @param objects the list of objects that will be added
     */
    void addAll(List<? extends AbstractForm> objects);

    /**
     * Adds an object to the collection that contains the class of this object
     * 
     * @param obj the object that will be added
     */
    <T extends AbstractForm> void addObject(T obj);

    /**
     * Removes an object with the given id from the collection containing the given class
     * 
     * @param commandClass the classes that is contained by the collection
     * @param id the given id
     */
    <T extends AbstractForm> void removeObject(Class<T> commandClass, String id);

    /**
     * Checks if an object with the given id exists in the collection containing the given class
     * 
     * @param commandClass the classes that is contained by the collection
     * @param id the given id
     */
    <T extends AbstractForm> boolean existsObject(Class<T> commandClass, String id);


    /**
     * Error code that is created on start of the flow bean in case of error
     * 
     * @param errorCode
     */
    void setInitializationErrorCode(String errorCode);

    /**
     * Error code that is set on start of the flow bean in case of error
     * 
     * @param errorCode
     */
    String getInitializationErrorCode();
    
     <T extends AbstractForm> Integer getIndex(Class<T> commandClass, String id);

     MainForm getMainForm();
     
     String getFilingNumber();
     
     String getFirstLang();

    Boolean getEsignDocDeclaration();

    void setEsignDocDeclaration(Boolean esignDocDeclaration);

    String getCurrentUserName();
    void setCurrentUserName(String currentUserName);

    String getCurrentUserEmail();

    void setCurrentUserEmail(String currentUserEmail);

    List<ApplicantForm> getUserApplicantsForm();

    void setUserApplicantsForm(List<ApplicantForm> userApplicantsForm);

    List<RepresentativeForm> getUserRepresentativesForm();

    void setUserRepresentativesForm(List<RepresentativeForm> userRepresentativesForm);
}
