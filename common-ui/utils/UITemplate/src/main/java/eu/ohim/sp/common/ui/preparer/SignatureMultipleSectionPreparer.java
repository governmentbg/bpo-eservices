/*******************************************************************************
 * * $Id:: SignatureSectionPreparer.java 113489 2013-04-22 14:59:26Z karalch     $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.preparer;

import eu.ohim.sp.core.configuration.domain.xsd.AvailableSection;
import eu.ohim.sp.core.configuration.domain.xsd.Section;
import org.apache.tiles.Attribute;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ionitdi
 */
@Component
public class SignatureMultipleSectionPreparer extends BaseSectionPreparer
{
    @Override
    public void execute(TilesRequestContext tilesRequestContext, AttributeContext attributeContext)
    {
        //Find the sections to be rendered in this page and put them in the proper order
        List<Section> sections = viewConfiguration.getSortedSubsections(getFlowId(), getStateDefinition().getId(),
                                                                        AvailableSection.SIGNATURE);
        viewConfiguration.getSortedSubsections(getFlowId(), getStateDefinition().getId(),AvailableSection.SIGNATURE).get(0).setId(AvailableSection.SIGNATURE_DETAILS_SECTION);
        List<Attribute> attr = new ArrayList<Attribute>();
        for (Section s : sections)
        {
            attr.add(new Attribute(s.getPath()));
        }
        attributeContext.putAttribute("signature_content", new Attribute(attr));
    }
}
