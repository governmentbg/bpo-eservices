/*
 *  CoreDomain:: GeneralTermCriteria 16/10/13 20:16 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.classification.wrapper;

import java.util.List;
import java.io.Serializable;

/**
 * Generic Criteria to retrieve terms from the nice classification service
 * User: karalch
 * Date: 08/08/13
 */
public class GeneralTermCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    /** the office under which criteria exist */
    private String office;
    /** the language of the terms that are expected */
    private String language;
    /** the term should match the expected terms */
    private String term;
    /** the mode that term should be considered e.g. exact match*/
    private SearchMode searchMode;
    /** the list of nice class that we expect the terms */
    private List<Integer> niceClassList;

    public String getOffice() {
        return office;
    }
    public void setOffice(String office) {
        this.office = office;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getTerm() {
        return term;
    }
    public void setTerm(String term) {
        this.term = term;
    }
    public SearchMode getSearchMode() {
        return searchMode;
    }
    public void setSearchMode(SearchMode searchMode) {
        this.searchMode = searchMode;
    }
    public List<Integer> getNiceClassList() {
        return niceClassList;
    }
    public void setNiceClassList(List<Integer> niceClassList) {
        this.niceClassList = niceClassList;
    }
}
