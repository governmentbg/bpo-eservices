package eu.ohim.sp.common.ui.flow.section;

import java.util.List;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.person.ApplicantForm;
import eu.ohim.sp.common.ui.form.person.DesignerForm;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.person.AssigneeForm;

public interface PersonFlowBean  extends FlowBean{

    /**
     * Clear the applicants
     */
    void clearApplicants();

    /**
     * Clear the representatives
     */
    void clearRepresentatives();
    
    /**
     * Clear the assignees
     */
    void clearAssignees();
    
    /**
     * Retrieves all the stored applicants
     * 
     * @return All the stored applicants
     */
    List<ApplicantForm> getApplicants();
    
    List<ApplicantForm> getAddedApplicants();
    
    List<ApplicantForm> getUserDataApplicants();

    List<RepresentativeForm> getAddedRepresentatives();

    List<RepresentativeForm> getUserDataRepresentatives();
        
    /**
     * Retrieves all the stored representatives
     * 
     * @return All the stored representatives
     */
    List<RepresentativeForm> getRepresentatives();
   
    List<AssigneeForm> getAddedAssignees();
    List<AssigneeForm> getUserDataAssignees();
    List<AssigneeForm> getAssignees();

    void clearDesigners();
    List<DesignerForm> getAddedDesigners();
    List<DesignerForm> getUserDataDesigners();
    List<DesignerForm> getDesigners();

}
