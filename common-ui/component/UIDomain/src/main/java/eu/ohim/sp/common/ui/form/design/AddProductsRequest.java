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
package eu.ohim.sp.common.ui.form.design;

import java.io.Serializable;
import java.util.List;

/**
 * Created by estevca on 13/04/2015.
 */
public class AddProductsRequest implements Serializable{
    
    /** The serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The design identifier. */
    private String designIdentifier;
    
    /** The product indications. */
    private List<IndicationProductForm> productIndications;

    /**
     * Gets the design identifier.
     *
     * @return the design identifier
     */
    public String getDesignIdentifier() {
        return designIdentifier;
    }

    /**
     * Sets the design identifier.
     *
     * @param designIdentifier the new design identifier
     */
    public void setDesignIdentifier(String designIdentifier) {
        this.designIdentifier = designIdentifier;
    }

    /**
     * Gets the product indications.
     *
     * @return the product indications
     */
    public List<IndicationProductForm> getProductIndications() {
        return productIndications;
    }

    /**
     * Sets the product indications.
     *
     * @param productIndications the new product indications
     */
    public void setProductIndications(List<IndicationProductForm> productIndications) {
        this.productIndications = productIndications;
    }
}
