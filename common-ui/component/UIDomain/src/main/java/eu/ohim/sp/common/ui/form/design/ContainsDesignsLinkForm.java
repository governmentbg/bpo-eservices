package eu.ohim.sp.common.ui.form.design;

import java.util.List;

/**
 * Interface for those forms that contain designs.
 * @author serrajo
 */
public interface ContainsDesignsLinkForm {
	
	List<DesignForm> getDesignsLinked();
	void setDesignsLinked(List<DesignForm> designsLinked);
	List<DesignForm> getDesignsNotLinked();
	void setDesignsNotLinked(List<DesignForm> designsNotLinked);

}
