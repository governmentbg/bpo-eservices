package eu.ohim.sp.external.application;

import eu.ohim.sp.core.domain.trademark.IPApplication;

/**
 * @author karalch
 */
public interface PostProcessor<O extends IPApplication, D>  {

    O processCore(O core);

    D processTransaction(D transaction);

}
