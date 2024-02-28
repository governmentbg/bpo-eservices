package eu.ohim.sp.core.register;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 13.04.2022
 * Time: 14:36
 */
public interface IPOAutocompleteSearchService {

    String ipoAutocomplete(String word, int rows, String appType);
}
