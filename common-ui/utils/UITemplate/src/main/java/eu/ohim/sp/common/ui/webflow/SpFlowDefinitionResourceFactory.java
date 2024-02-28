/*******************************************************************************
 * * $Id:: SpFlowDefinitionResourceFactory.java 113489 2013-04-22 14:59:26Z kar#$
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.AttributeMap;

/**
 * Custom Flow registry implementation for SP
 * @author soriama
 */
public class SpFlowDefinitionResourceFactory extends FlowDefinitionResourceFactory {
	public SpFlowDefinitionResourceFactory(ResourceLoader resourceLoader) {
		super();
	}
	
	@Override
	public FlowDefinitionResource createResource(String path,
			AttributeMap attributes, String flowId) {		
		Resource resource = new ByteArrayResource(path.getBytes());	
		if (flowId == null || flowId.length() == 0) {
			flowId = getFlowId(resource);
		}
		return new FlowDefinitionResource(flowId, resource, attributes);
	}
}
