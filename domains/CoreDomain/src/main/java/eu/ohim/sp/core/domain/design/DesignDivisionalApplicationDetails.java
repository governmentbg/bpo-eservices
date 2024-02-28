/*
 *  CoreDomain:: DesignDivisionalApplicationDetails 19/08/13 10:57 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.core.domain.design;

import java.io.Serializable;
import java.util.List;

import eu.ohim.sp.core.domain.resources.AttachedDocument;
import eu.ohim.sp.core.domain.application.DivisionalApplicationDetails;

public class DesignDivisionalApplicationDetails extends DivisionalApplicationDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9161284570639199099L;
	
    /** The trade mark documents. */
    private List<AttachedDocument> attachment;
    
	private List<ProductIndication> productIndications;

	private List<Design> designs;
	
	
    public List<AttachedDocument> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<AttachedDocument> attachment) {
		this.attachment = attachment;
	}

	public List<ProductIndication> getProductIndications() {
		return productIndications;
	}

	public void setProductIndications(List<ProductIndication> productIndications) {
		this.productIndications = productIndications;
	}

	public List<Design> getDesigns() {
		return designs;
	}

	public void setDesigns(List<Design> designs) {
		this.designs = designs;
	}


}
