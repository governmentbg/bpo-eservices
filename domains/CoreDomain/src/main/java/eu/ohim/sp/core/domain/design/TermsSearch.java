//@formatter:off
/**
 *  $Id$
 *       . * .
 *     * RRRR  *    Copyright (c) 2015 OHIM: Office for Harmonization
 *   .   RR  R   .  in the Internal Market (trade marks and designs)
 *   *   RRR     *
 *    .  RR RR  .   ALL RIGHTS RESERVED
 *     * . _ . *
 */
//@formatter:on
package eu.ohim.sp.core.domain.design;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class Terms Response.
 */
public class TermsSearch implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /** The number of Terms. */
    private Integer totalResults;

    /** The page size. */
    private Integer pageSize;

    /** The list of Terms. */
    private List<TermLocarno> results;

    /**
     * gets the number of results.
     * @return
     */
    public Integer getTotalResults() {
        return totalResults;
    }

    /**
     * sets the number of results.
     * @param totalResults
     */
    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * gets the results.
     * @return
     */
    public List<TermLocarno> getResults() {
        if(results==null){
            results=new ArrayList<>();
        }
        return results;
    }

    /**
     * sets the results.
     * @param results
     */
    public void setResults(List<TermLocarno> results) {
        this.results = results;
    }

    /**
     * gets the page size.
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * sets the page size.
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
