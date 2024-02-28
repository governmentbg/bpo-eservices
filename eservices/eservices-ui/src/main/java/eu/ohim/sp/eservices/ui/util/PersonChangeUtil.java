package eu.ohim.sp.eservices.ui.util;

import eu.ohim.sp.common.ui.flow.FlowBean;
import eu.ohim.sp.common.ui.form.design.ESDesignDetailsForm;
import eu.ohim.sp.common.ui.form.person.ChangePersonType;
import eu.ohim.sp.common.ui.form.person.RepresentativeForm;
import eu.ohim.sp.common.ui.form.trademark.TMDetailsForm;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by velosma on 27/03/2017.
 */
public class PersonChangeUtil {

    /**
     * Get a representative from the imported dossiers
     * @param flowBean the flowBean
     * @param id the id of the representative
     * @return the representative or null
     */
    public static RepresentativeForm getImportedRepresentative(ESFlowBean flowBean, String id) {
        Map<String, List<RepresentativeForm>> representatives = getImportedRepresentatives(flowBean, id);
        if (MapUtils.isNotEmpty(representatives)) {
            return representatives.values().iterator().next().get(0);
        } else {
            return null;
        }
    }

    /**
     * Get all representatives from the imported dossiers
     * @param flowBean the flowBean
     * @return a map of representatives which key is the dossier id
     */
    public static Map<String, List<RepresentativeForm>> getImportedRepresentatives(ESFlowBean flowBean) {
        return getImportedRepresentatives(flowBean, null);
    }

    private static Map<String, List<RepresentativeForm>> getImportedRepresentatives(ESFlowBean flowBean, String id) {
        Map<String, List<RepresentativeForm>> representatives = new HashMap<>();
        if (CollectionUtils.isNotEmpty(flowBean.getTmsList())) {
            for (TMDetailsForm tmDetailsForm : flowBean.getTmsList()) {
                String tmId = tmDetailsForm.getRegistrationNumber();
                if (tmId == null) {
                    tmId = tmDetailsForm.getApplicationNumber();
                }
                getImportedRepresentative(tmDetailsForm.getRepresentatives(), id, tmId, representatives);
            }
        }
        if (CollectionUtils.isNotEmpty(flowBean.getDssList())) {
            for (ESDesignDetailsForm esDesignDetailsForm : flowBean.getDssList()) {
                String dsId = esDesignDetailsForm.getRegistrationNumber();
                if (dsId == null) {
                    dsId = esDesignDetailsForm.getDesignIdentifier();
                }
                getImportedRepresentative(esDesignDetailsForm.getRepresentatives(), id, dsId, representatives);
            }
        }
        return representatives;
    }

    private static void getImportedRepresentative(List<RepresentativeForm> representatives, String repId, String dossierId, Map<String, List<RepresentativeForm>> result) {
        if (CollectionUtils.isNotEmpty(representatives) && dossierId != null) {
            for (RepresentativeForm representative : representatives) {
                try {
                    if (repId == null || repId.equals(representative.getId())) {
                        List<RepresentativeForm> representativeForms = result.get(dossierId);
                        if (representativeForms == null) {
                            representativeForms = new ArrayList<>();
                            result.put(dossierId, representativeForms);
                        }
                        representativeForms.add(representative.clone());
                    }
                } catch (CloneNotSupportedException e) {
                    // Do nothing
                }
            }
        }
    }

    /**
     * Get the count of the remaining representatives of the dossier to which the representative belongs to
     * AFTER the changes take effect. The representatives could be in several dossiers so the minimum count is
     * choosen
     * @param flowBean the flowBean
     * @param representativeId the id of the representative
     * @return the number of representatives associated to the dossier AFTER applying changes
     */
    public static Integer countOfRemainRepresentatives(ESFlowBean flowBean, String representativeId) {
        Integer count = null;
        for (Map.Entry<String, List<RepresentativeForm>> entry : getImportedRepresentatives(flowBean, representativeId).entrySet()) {
            if (entry.getValue().stream().anyMatch(r -> r.getId().equals(representativeId))) {
                Integer dossierCount = countOfRemainRepresentativesOfDossier(flowBean, entry.getKey());
                if (dossierCount != null && (count == null || dossierCount < count)) {
                    count = dossierCount;
                }
            }
        }
        return count;
    }

    /**
     * Get the count of the remaining representatives of the dossier AFTER the changes take effect.
     * @param flowBean the flowBean
     * @param dossierId the id of the dossierId
     * @return the number of representatives associated to the dossier AFTER applying changes
     */
    private static Integer countOfRemainRepresentativesOfDossier(ESFlowBean flowBean, String dossierId) {
        // Number of representatives of the dossier
        List<RepresentativeForm> repDossier = getImportedRepresentatives(flowBean).get(dossierId);

        if (repDossier != null) {
            int toDelete = 0;
            int toAdd = 0;

            // Number of existing request for deletion for the specified dossier
            if (flowBean.getPersonChanges() != null) {
                for (RepresentativeForm repForm : flowBean.getPersonChanges()) {
                    if (ChangePersonType.REMOVE_REPRESENTATIVE.equals(repForm.getChangeType())
                            && repDossier.stream().anyMatch(r -> r.getId().equals(repForm.getPreviousPersonId()))) {
                        toDelete++;
                    }
                    if (ChangePersonType.ADD_NEW_REPRESENTATIVE.equals(repForm.getChangeType())
                            && repDossier.stream().noneMatch(r -> r.getId().equals(repForm.getId()))) {
                        toAdd++;
                    }
                }
            }

            return repDossier.size() - toDelete + toAdd;
        }

        return null;
    }

}
