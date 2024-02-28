/*******************************************************************************
 * * $Id:: BaseSectionPreparer.java 113489 2013-04-22 14:59:26Z karalch          $
 * *       . * .
 * *     * RRRR  *    Copyright © 2012 OHIM: Office for Harmonization
 * *   .   RR  R   .  in the Internal Market (trade marks and designs)
 * *   *   RRR     *
 * *    .  RR RR  .   ALL RIGHTS RESERVED
 * *     * . _ . *
 ******************************************************************************/
package eu.ohim.sp.common.ui.preparer;

import eu.ohim.sp.common.ui.webflow.SectionViewConfiguration;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.ViewPreparer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.webflow.definition.StateDefinition;
import org.springframework.webflow.execution.RequestContextHolder;

/**
 * @author ionitdi
 */
public abstract class BaseSectionPreparer implements ViewPreparer
{
    @Autowired
    SectionViewConfiguration viewConfiguration;

    public SectionViewConfiguration getViewConfiguration()
    {
        return viewConfiguration;
    }

    public void setViewConfiguration(SectionViewConfiguration viewConfiguration)
    {
        this.viewConfiguration = viewConfiguration;
    }

    protected String getFlowId()
    {
        return RequestContextHolder.getRequestContext().getActiveFlow().getId();

    }

    protected StateDefinition getStateDefinition()
    {
        return RequestContextHolder.getRequestContext().getCurrentState();
    }

    @Override
    public abstract void execute(TilesRequestContext tilesRequestContext, AttributeContext attributeContext);
}
