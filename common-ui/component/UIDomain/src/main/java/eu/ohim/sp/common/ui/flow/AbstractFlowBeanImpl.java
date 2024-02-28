/*******************************************************************************
 * * $Id:: FlowBeanImpl.java 58440 2013-03-05 21:30:12Z karalch $
 * * . * .
 * * * RRRR * Copyright © 2012 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.flow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.ohim.sp.common.SPException;
import eu.ohim.sp.common.ui.form.AbstractForm;
import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.application.ApplicationCAForm;
import eu.ohim.sp.core.configuration.domain.payments.xsd.PayerTypes.PayerType;
import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;

/**
 * Bean that will hold all the information about an application. It's scope
 * would be session so to keep all the information during the session.
 * 
 * @author ckara
 */
public abstract class AbstractFlowBeanImpl extends AbstractImportableForm implements FlowBean {

    private static final long serialVersionUID = 1L;

    private static transient final Logger logger = Logger.getLogger(AbstractFlowBeanImpl.class);

	public abstract List<ApplicationCAForm> getCorrespondanceAddresses();

 
   

    /**
     * Flag indicating if there is a warning for validation
     */
    private Boolean warningValidated;

  



    /**
     * Internal mapping of classes and collections
     */
    protected Map<Class<? extends AbstractForm>, List<? extends AbstractForm>> map;

    /**
     * Private counter that helps to create new identifiers for the various
     * elements
     */
    private int i = 0;
   


    /**
     * Error code that can be set when the application is created.
     * It is used because on-start needs one created flowbean, even empty
     * but we pass the error.
     */
    private String initializationErrorCode = null;

    /**
     * Default constructor for FlowBeanImpl Initializes the collections
     */
    public AbstractFlowBeanImpl() {
        map = new HashMap<Class<? extends AbstractForm>, List<? extends AbstractForm>>();
        id = null;
    }

    

    /**
     * Clear the bean
     */
    @Override
    public void clear() {
    	map = new HashMap<Class<? extends AbstractForm>, List<? extends AbstractForm>>();
        id = null;
    }

   



    /**
     * Returns the collection that contains the specified class
     * 
     * @param commandClass the given class
     * @return the collection that contains the specified class
     */
    @Override
    public <T extends AbstractForm> List<T> getCollection(Class<T> commandClass) {
        return (List<T>) map.get(commandClass);
    }

    /**
     * Returns the object with the given id and is contained in the collection
     * with the specified class
     * 
     * @param commandClass the given class
     * @param id the given id
     * @return the object with the specific id
     * @throws java.lang.Exception
     */
    @Override
    public <T extends AbstractForm> T getObject(Class<T> commandClass, String id) {
        List<T> myCol = (List<T>) map.get(commandClass);
        Iterator<T> iter = myCol.iterator();
        while (iter.hasNext()) {
            T obj = iter.next();
            String iteratedid = obj.getId();
            if (iteratedid.equals(id))
                return obj;
        }
        return null;
    }

    @Override
    public <T extends AbstractForm> void replaceObject(T newValue, String id) {
        List<T> list = (List<T>) map.get(newValue.getClass());
        ListIterator<T> iter = list.listIterator();
        while (iter.hasNext()) {
            T obj = iter.next();
            if (id.equals(obj.getId())) {
                iter.set(newValue);
            }

        }
    }

    /**
     * Adds a list of objects to the collection that contains the class of the
     * objects
     * 
     * @param objects the list of objects that will be added
     */
    @Override
    public void addAll(List<? extends AbstractForm> objects) {
        for (AbstractForm o : objects) {
            addObject(o);
        }
    }

    /**
     * Adds an object to the collection that contains the class of this object
     * 
     * @param obj the object that will be added
     */
    @Override
    public <T extends AbstractForm> void addObject(T obj) {
        List<T> myCol = (List<T>) map.get(obj.getClass());

        String id = obj.getId();
        if (id == null || id.trim().equals("")) {
            obj.setId("Efiling" + i++);
        }

        myCol.add(obj);

        if (logger.isDebugEnabled()) {
            logger.debug(myCol.size());
        }
    }

    /**
     * Removes an object with the given id from the collection containing the
     * given class
     * 
     * @param commandClass the classes that is contained by the collection
     * @param id the given id
     * @throws java.lang.Exception
     */
    @Override
    public <T extends AbstractForm> void removeObject(Class<T> commandClass, String id) {
        List<T> myCol = (List<T>) map.get(commandClass);
        Iterator<T> iter = myCol.iterator();
        while (iter.hasNext()) {
            T obj = iter.next();
            if (id.equals(obj.getId())) {
                iter.remove();
            }
        }
    }

    /**
     * Checks if an object with the given id exists in the collection containing the given class
     * 
     * @param commandClass the classes that is contained by the collection
     * @param id the given id
     * @param <T>
     * @return a boolean indicating if the object exists in the collection
     */
    @Override
    public <T extends AbstractForm> boolean existsObject(Class<T> commandClass, String id) {
        List<T> listToCheck = (List<T>) map.get(commandClass);
        for (T item : listToCheck) {
            if (item.getId().equals(id))
                return true;
        }
        return false;
    }

  

  

   
   
  
    public Boolean getWarningValidated() {
        return warningValidated;
    }

    public void setWarningValidated(Boolean warningValidated) {
        this.warningValidated = warningValidated;
    }

  
   

    @Override
    public AvailableSection getAvailableSectionName() {
        return null;
    }

   
    @Override
    public String getInitializationErrorCode() {
        return initializationErrorCode;
    }

    @Override
    public void setInitializationErrorCode(String initializationErrorCode) {
        this.initializationErrorCode = initializationErrorCode;
    }

    @Override
    public AbstractFlowBeanImpl clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends AbstractForm> Integer getIndex(Class<T> commandClass, String id) {
        List<T> myCol = (List<T>) map.get(commandClass);
        Iterator<T> iter = myCol.iterator();
        for (int i = 0; iter.hasNext(); i++) {
            T obj = iter.next();
            String iteratedid = obj.getId();
            if (iteratedid.equals(id))
                return i;
        }
        return null;
    }

  
}
