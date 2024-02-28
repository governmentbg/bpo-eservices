package eu.ohim.sp.external.person;

import eu.ohim.sp.core.domain.person.Inventor;

import java.util.List;

/**
 * Created by Raya
 * 05.06.2019
 */
public interface InventorClient {

    String getInventorAutocomplete(String module, String office, String text, int numberOfRows);
    Inventor getInventor(String module, String office, String inventorId);
    List<Inventor> matchInventor(String module, String office, Inventor inventor, int numberOfResults);
}
