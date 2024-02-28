package eu.ohim.sp.ui.tmefiling.flow;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TermForm;

import java.util.List;
import java.util.Set;

public interface GoodsServicesFlowBean {

    /**
     * Method that returns the terms
     *
     * @return the terms
     */
    List<TermForm> getTerms();

    /**
     * Method that sets the terms
     *
     * @param terms the terms to set
     */
    void setTerms(List<TermForm> terms);

    /**
     * Method that returns the term
     *
     * @return the term
     */
    String getTerm();

    /**
     * Method that sets the term
     *
     * @param term the term to set
     */
    void setTerm(String term);

    /**
     * Retrieves stored goods and services
     *
     * @return List of GoodAndServiceForm
     */
    Set<GoodAndServiceForm> getGoodsAndServices();

    /**
     * Retrieves goods and services
     *
     * @param langId
     * @param classId
     * @return goods and services form object
     */
    GoodAndServiceForm getGoodsAndService(String langId, String classId);

    /**
     * Clear the Goods and Services
     */
    void clearGandS();

    /**
     * Help method with adds good and service in the map and refreshes main map.
     * 
     * @param gs The form to be added
     */
    void addGoodAndService(GoodAndServiceForm gs);

    /**
     * Replace edited term.
     *
     * @param gs the gs
     * @param termClass the term class
     * @param termDescription the term description
     */
    void replaceEditedTerm(List<GoodAndServiceForm> gs, String termClass, String termDescription);
    
    public void resetTempGoodsServices(String uuid, String classId, String langId);
    
    public void addTempGoodsServices(String uuid, GoodAndServiceForm current);
    
    public GoodAndServiceForm getTempGoodsServices(String uuid);
    
    public void removeTempGoodsServices(String uuid);

}
