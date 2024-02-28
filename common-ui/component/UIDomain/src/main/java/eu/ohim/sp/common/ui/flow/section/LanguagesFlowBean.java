package eu.ohim.sp.common.ui.flow.section;

import eu.ohim.sp.common.ui.flow.FlowBean;


public interface LanguagesFlowBean  extends FlowBean{
    /**
     * Getter method for firstLang
     * 
     * @return the firstLang
     */
    String getFirstLang();

    /**
     * Setter method for first lang
     * 
     * @param firstLang the firstLang to set
     */
    void setFirstLang(String firstLang);

    /**
     * Getter method for second lang
     * 
     * @return the secLang
     */
    String getSecLang();

    /**
     * Setter method for second lang
     * 
     * @param secLang the secLang to set
     */
    void setSecLang(String secLang);
}
