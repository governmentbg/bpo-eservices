/*
 *  CoreDomain:: ProductIndicationTerm 17/09/13 12:22 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.design;

import eu.ohim.sp.core.domain.id.Id;

import java.io.Serializable;

public class ProductIndicationTerm extends Id implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 7373620823799806928L;

    private String indprodID;
    private String text;

    public String getIndprodID() {
        return indprodID;
    }

    public void setIndprodID(String indprodID) {
        this.indprodID = indprodID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
