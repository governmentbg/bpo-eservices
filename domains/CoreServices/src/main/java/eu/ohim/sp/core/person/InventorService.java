package eu.ohim.sp.core.person;

import eu.ohim.sp.core.domain.person.Inventor;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

import java.util.List;

/**
 * Created by Raya
 * 05.06.2019
 */
public interface InventorService {

    String getInventorAutocomplete(String module, String office, String text, int numberOfRows);
    Inventor getInventor(String module, String office, String inventorId);
    ErrorList validateInventor(String module, Inventor inventor, RulesInformation rulesInformation);
    List<Inventor> matchInventor(String module, String office,
                                 Inventor designer, int numberOfResults);
}
