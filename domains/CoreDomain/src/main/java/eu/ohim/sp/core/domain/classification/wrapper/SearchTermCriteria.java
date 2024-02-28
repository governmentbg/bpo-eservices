/*
 *  CoreDomain:: SearchTermCriteria 08/08/13 18:42 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */
package eu.ohim.sp.core.domain.classification.wrapper;

import java.io.Serializable;

//Id has been removed has no sense for criteria
public class SearchTermCriteria extends GeneralTermCriteria implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5195700466485049617L;

    private boolean showMaster;
    private Integer size;
    private Integer page;
    private SortBy sortBy;
    private OrderBy orderBy;
    private String taxoConceptNodeId;
    private Boolean showNonTaxoTermsOnly;
    private String sources;

    public boolean isShowMaster() {
        return showMaster;
    }

    public void setShowMaster(boolean showMaster) {
        this.showMaster = showMaster;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getTaxoConceptNodeId() {
        return taxoConceptNodeId;
    }

    public void setTaxoConceptNodeId(String taxoConceptNodeId) {
        this.taxoConceptNodeId = taxoConceptNodeId;
    }

    /**
     * @return the showNonTaxoTermsOnly
     */
    public Boolean getShowNonTaxoTermsOnly() {
        return showNonTaxoTermsOnly;
    }

    /**
     * @param showNonTaxoTermsOnly the showNonTaxoTermsOnly to set
     */
    public void setShowNonTaxoTermsOnly(Boolean showNonTaxoTermsOnly) {
        this.showNonTaxoTermsOnly = showNonTaxoTermsOnly;
    }

    @Override
    public String toString() {
        return "SearchTermCriteria [showMaster="
                + showMaster + ", size=" + size + ", page=" + page
                + ", sortBy=" + sortBy + ", orderBy=" + orderBy
                + ", taxoConceptNodeId=" + taxoConceptNodeId + "]";
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }
}