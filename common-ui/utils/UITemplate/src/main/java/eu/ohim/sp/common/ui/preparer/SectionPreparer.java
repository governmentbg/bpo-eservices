/*******************************************************************************
 * * $Id:: SectionPreparer.java 113489 2013-04-22 14:59:26Z karalch              $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.preparer;

import eu.ohim.sp.core.configuration.domain.xsd.Section;
import org.apache.log4j.Logger;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SectionPreparer extends BaseSectionPreparer
{
    private static final Logger logger = Logger.getLogger(SectionPreparer.class);

    public void execute(TilesRequestContext tilesContext, AttributeContext attributeContext) {
    	
        //Find the sections to be rendered in this page and put them in the proper order
        List<Section> sections = viewConfiguration.getSortedSections(getFlowId(), getStateDefinition().getId());
        List<Attribute> attr = new ArrayList<Attribute>();
        for (Section s : sections)
        {
            attr.add(new Attribute(s.getPath()));
        }

        attributeContext.putAttribute("content", new Attribute(attr));

        //Read the view states for the navigation path if some of the sections has the property navigationPath true (it applies to the page, not the section itself)
        attr = new ArrayList<Attribute>();
        if (viewConfiguration.displayNavigationPath(getFlowId()))
        {
            List<String> views = viewConfiguration.getSortedViewStates(getFlowId());
            for (String view : views)
            {
                attr.add(new Attribute(view));
            }
        }
        attributeContext.putAttribute("navigation_path", new Attribute(attr));
        attributeContext.putAttribute("currentState", new Attribute(getStateDefinition().getId()));
        
    }

}
