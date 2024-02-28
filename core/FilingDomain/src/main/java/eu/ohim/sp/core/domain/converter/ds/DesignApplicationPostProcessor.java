package eu.ohim.sp.core.domain.converter.ds;

import eu.ohim.sp.core.domain.design.*;
import eu.ohim.sp.filing.domain.ds.Transaction;
import eu.ohim.sp.external.application.PostProcessor;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ionitdi
 */
public class DesignApplicationPostProcessor implements PostProcessor<DesignApplication, Transaction> {
    
	private final Logger logger = Logger.getLogger(DesignApplicationPostProcessor.class);

    @Override
    public DesignApplication processCore(DesignApplication core)
    {
        if(core.getDesignDetails() == null)
        {
            return core;
        }
        for (Design design : core.getDesignDetails())
        {
            List<Priority> listPriorities = intersectPartialAndFull(design.getPriorities(), core.getPriorities());
            design.setPriorities(listPriorities);

            List<ExhibitionPriority> listExhibitionPriorities = intersectPartialAndFull(design.getExhibitionPriorities(), core.getExhibitionPriorities());
            design.setExhibitionPriorities(listExhibitionPriorities);

            List<Designer> listDesigners = intersectPartialAndFull(design.getDesigners(), core.getDesigners());
            design.setDesigners(listDesigners);
        }

        return core;
    }

    @Override
    public Transaction processTransaction(Transaction transaction)
    {
        return transaction;
    }

    private <T extends Sequenceable> List<T> intersectPartialAndFull(List<T> partialItems, List<T> fullItems)
    {
        if (partialItems == null || partialItems.isEmpty())
        {
            return null;
        }

        List<T> toReturn = new ArrayList<T>();
        for (int i = 0; i < partialItems.size(); i++)
        {
            int sequenceNumber = partialItems.get(i).getSequenceNumber();
            toReturn.add(searchForElement(sequenceNumber, fullItems));
        }
        return toReturn;
    }

    private <T extends Sequenceable> T searchForElement(int sequenceNumber, List<T> list)
    {
        if (list == null || list.isEmpty())
        {
            logger.warn("List of items null or empty when expected to contain at least one element.");
            return null;
        }
        for (T item : list)
        {
            if (item.getSequenceNumber() == sequenceNumber)
            {
                return item;
            }
        }
        return null;
    }
}
