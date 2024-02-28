package eu.ohim.sp.core.esignature;

import javax.ejb.Local;

/**
 * Local decoration of the {@link ESignatureService} support interface.
 * 
 * @see ESignatureService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
@Local
public interface ESignatureServiceLocal extends ESignatureService {
}
