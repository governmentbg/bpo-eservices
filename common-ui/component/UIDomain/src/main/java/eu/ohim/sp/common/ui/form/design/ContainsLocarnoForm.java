package eu.ohim.sp.common.ui.form.design;

import java.util.List;

/**
 * Interface for those forms that contain Locarno classifications.
 * @author serrajo
 */
public interface ContainsLocarnoForm {
    
	/**
     * Get the Locarno classes
     * @return the Locarno classes
     */
    public List<LocarnoAbstractForm> getLocarno();

    /**
     * Set the Locarno classes
     * @param locarno the Locarno classes
     */
    public void setLocarno(List<LocarnoAbstractForm> locarno);
	
}
