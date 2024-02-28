/*******************************************************************************
 * * $Id:: SpFlowRegistry.java 113489 2013-04-22 14:59:26Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.webflow;

import eu.ohim.sp.core.configuration.ConfigurationService;
import eu.ohim.sp.core.configuration.domain.xsd.Sections;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.definition.registry.FlowDefinitionHolder;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistryImpl;
import org.springframework.webflow.engine.builder.DefaultFlowHolder;
import org.springframework.webflow.engine.builder.FlowAssembler;
import org.springframework.webflow.engine.builder.FlowBuilder;
import org.springframework.webflow.engine.builder.FlowBuilderContext;
import org.springframework.webflow.engine.builder.model.FlowModelFlowBuilder;
import org.springframework.webflow.engine.builder.support.FlowBuilderContextImpl;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;
import org.springframework.webflow.engine.model.builder.DefaultFlowModelHolder;
import org.springframework.webflow.engine.model.builder.FlowModelBuilder;
import org.springframework.webflow.engine.model.builder.xml.XmlFlowModelBuilder;
import org.springframework.webflow.engine.model.registry.FlowModelHolder;
import org.springframework.webflow.engine.model.registry.FlowModelRegistry;
import org.springframework.webflow.engine.model.registry.FlowModelRegistryImpl;

import java.util.Collection;

/**
 * Custom Flow registry implementation for SP
 * Based on the spring webflow 2.3 class org.springframework.webflow.config.FlowRegistryFactoryBean
 * @author soriama
 */
public class SpFlowRegistry extends FlowDefinitionRegistryImpl {
    static final String WEBFLOW_DEFINITION_FILENAMES = "webflowDefinitionFileNames";

	private FlowBuilderServices flowBuilderServices;
	
	private ConfigurationService configurationService;

	private SectionViewConfiguration sectionViewConfiguration;

	private String module;
	
	/**
	 * A helper for creating abstract representation of externalized flow definition resources.
	 */
	private SpFlowDefinitionResourceFactory flowResourceFactory;

	private FlowModelRegistry flowModelRegistry = new FlowModelRegistryImpl();

	public FlowModelRegistry getFlowModelRegistry() {
		return flowModelRegistry;
	}

	public SpFlowRegistry(FlowBuilderServices flowBuilderServices,
                          ConfigurationService configurationService,
                          SectionViewConfiguration sectionViewConfiguration,
						  String module) {
		super();
		this.flowBuilderServices = flowBuilderServices;
		this.configurationService = configurationService;	
		this.sectionViewConfiguration = sectionViewConfiguration;
		this.module = module;
		init();
	}
	
	private void init() {
		flowResourceFactory = new SpFlowDefinitionResourceFactory(flowBuilderServices.getApplicationContext());
		registerFlowDefinitionFiles();
	}
	
	private void registerFlowDefinitionFiles() {		
		Collection<String> webflowDefinitionFileNames = configurationService.getValueList(WEBFLOW_DEFINITION_FILENAMES, module);

        if (webflowDefinitionFileNames == null || webflowDefinitionFileNames.isEmpty()) {
            throw new IllegalStateException("Flow definitions not found for module: " + module);
        }

		for(String webflowDefinitionFileName : webflowDefinitionFileNames) {
			//We get the xml file as a string
			String webflowDefinitionFile = configurationService.getXml(webflowDefinitionFileName, module);
			FlowDefinitionResource resource = flowResourceFactory.createResource(webflowDefinitionFile, null, webflowDefinitionFileName);
			this.registerFlowDefinition(createFlowDefinitionHolder(resource));			
			//Now read the related xml containing section and fields info whose name is: webflowDefinitionFileName + "-conf"
			Sections sections = configurationService.getObject(webflowDefinitionFileName + "-conf", module, Sections.class);
			this.sectionViewConfiguration.getViewConfiguration().put(webflowDefinitionFileName, sections);
		}		
	}
	
	private FlowDefinitionHolder createFlowDefinitionHolder(FlowDefinitionResource flowResource) {
		FlowBuilder builder = createFlowBuilder(flowResource);
		FlowBuilderContext builderContext = new FlowBuilderContextImpl(flowResource.getId(), flowResource
				.getAttributes(), this, flowBuilderServices);
		FlowAssembler assembler = new FlowAssembler(builder, builderContext);
		return new DefaultFlowHolder(assembler);
	}
	
	private FlowBuilder createFlowBuilder(FlowDefinitionResource resource) {
		return new FlowModelFlowBuilder(createFlowModelHolder(resource));
	}
	
	private FlowModelHolder createFlowModelHolder(FlowDefinitionResource resource) {
		FlowModelHolder modelHolder = new DefaultFlowModelHolder(createFlowModelBuilder(resource));
		// register the flow model holder with the backing flow model registry - this is needed to support flow model
		// merging during the flow build process
		this.getFlowModelRegistry().registerFlowModel(resource.getId(), modelHolder);
		return modelHolder;
	}
	
	private FlowModelBuilder createFlowModelBuilder(FlowDefinitionResource resource) {
		return new XmlFlowModelBuilder(resource.getPath(), this.getFlowModelRegistry());
	}

	public void setParent(FlowDefinitionRegistry parent) {
		super.setParent(parent);
		if (parent instanceof SpFlowRegistry) {
			SpFlowRegistry parentFlowRegistry = (SpFlowRegistry) parent;
			// link so a flow in the child registry that extends from a flow in the parent registry can find its parent
			flowModelRegistry.setParent(parentFlowRegistry.getFlowModelRegistry());
		}
	}
}
