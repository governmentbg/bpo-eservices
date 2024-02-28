package eu.ohim.sp.eservices.ui.util;

import eu.ohim.sp.common.ui.form.AbstractImportableForm;
import eu.ohim.sp.common.ui.form.opposition.GroundCategoryKindLegalAct;
import eu.ohim.sp.common.ui.form.opposition.LegalActVersion;
import eu.ohim.sp.common.ui.form.opposition.OppositionBasisForm;
import eu.ohim.sp.common.ui.service.interfaces.ConfigurationServiceDelegatorInterface;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.AbsoluteGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RelativeGrounds;
import eu.ohim.sp.core.configuration.domain.groundsOpposition.xsd.RevocationGrounds;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.service.interfaces.ESTrademarkServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("groundsUtil")
public class GroundsUtil {

    @Autowired
    private ConfigurationServiceDelegatorInterface configurationService;

    @Autowired
    private ESTrademarkServiceInterface trademarkServiceInterface;

    /**
     * Refresh the list of flow bean Opposition basis
     * @param flowBean the flow bean
     * @return the list of removed basis
     */
    public List<OppositionBasisForm> refreshOppositionBasisList(ESFlowBean flowBean) {
        List<OppositionBasisForm> removedOBList = new ArrayList<>();

        if (flowBean.getObsList() != null && flowBean.getObsList().size() > 0) {
            removedOBList = getNotApplicableOppositionBasis(flowBean.getObsList(), flowBean.getAvaibleLegalActVersions());
            flowBean.getObsList().removeAll(removedOBList);
        }

        return removedOBList;
    }

    /**
     * Calculate and return the list of avaliable legal act versions
     * @param form the form
     * @param applicationType type of eservice
     * @return a list of avaliable legal act versions
     */
    public List<LegalActVersion> getAvaibleLegalActVersions(AbstractImportableForm form,
                                                                String applicationType) {

		boolean hasAbsoluteGrounds = false;
		boolean hasRelativeGrounds = false;
		boolean hasRevocationGrounds = false;

		List<LegalActVersion> filtered = new ArrayList<>();
		List<String> codesFiltered = new ArrayList<>();

		if (!applicationType.equals("tm-revocation")) {

			List<RelativeGrounds.RelativeGround> relativeGrounds = configurationService
					.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.RELATIVE_GROUNDS_PARAM,
							RelativeGrounds.class)
					.getRelativeGround();

			List<AbsoluteGrounds.AbsoluteGround> absoluteGrounds = configurationService
					.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.ABSOLUTE_GROUNDS_PARAM,
							AbsoluteGrounds.class)
					.getAbsoluteGround();

			for (AbsoluteGrounds.AbsoluteGround itemAb : absoluteGrounds) {
				if (itemAb.getApplicationType().equals(applicationType)) {
					if (itemAb.isEnabled()) {
						hasAbsoluteGrounds = true;
						if (!codesFiltered.contains(itemAb.getLegalActVersion())) {
							codesFiltered.add(itemAb.getLegalActVersion());
						}
					}
				}
			}

			for (RelativeGrounds.RelativeGround itemRe : relativeGrounds) {
				if (itemRe.getApplicationType().equals(applicationType)) {
					if (itemRe.isEnabled()) {
						hasRelativeGrounds = true;
						if (!codesFiltered.contains(itemRe.getLegalActVersion())) {
							codesFiltered.add(itemRe.getLegalActVersion());
						}
					}
				}
			}
		} else {

			List<RevocationGrounds.RevocationGround> revocationGrounds = configurationService
					.getObjectFromGeneralComponent(ConfigurationServiceDelegatorInterface.REVOCATION_GROUNDS_PARAM,
							RevocationGrounds.class)
					.getRevocationGround();
			for (RevocationGrounds.RevocationGround itemRev : revocationGrounds) {
				if (itemRev.isEnabled()) {
					hasRevocationGrounds = true;
					if (!codesFiltered.contains(itemRev.getLegalActVersion())) {
						codesFiltered.add(itemRev.getLegalActVersion());
					}
				}
			}
		}

		if (hasAbsoluteGrounds) {
			List<LegalActVersion> resultAbsoluteFilteredCore = trademarkServiceInterface.filter(applicationType, form,
					GroundCategoryKindLegalAct.ABSOLUTE_GROUNDS, codesFiltered);
			filtered.addAll(resultAbsoluteFilteredCore);
		}
		if (hasRelativeGrounds) {
			List<LegalActVersion> resultRelativeFilteredCore = trademarkServiceInterface.filter(applicationType, form,
					GroundCategoryKindLegalAct.RELATIVE_GROUNDS, codesFiltered);
			filtered.addAll(resultRelativeFilteredCore);
		}
		if (hasRevocationGrounds) {
			List<LegalActVersion> resultRevocationFilteredCore = trademarkServiceInterface.filter(applicationType, form,
					GroundCategoryKindLegalAct.REVOCATION_GROUNDS, codesFiltered);
			filtered.addAll(resultRevocationFilteredCore);
		}

		/*
		 * Drl for legalActVersions. Parameters= applicationType, TradeMark
		 * (core object), groundCategory, filteredCore (coreobject). Result =
		 * List <LegalActVersion> (coreobject)
		 *
		 * Two calls, 1 - with groundCategory ABSOLUTE_GROUNDS -> List
		 * <LegalActVersion> (convert) (ui
		 * object).groundCategory=ABSOLUTE_GROUNDS 2 - with groundCategory
		 * RELATIVE_GROUNDS -> List <LegalActVersion> (convert) (ui
		 * object).groundCategory=RELATIVE_GROUNDS
		 *
		 * filteredResult = two lists results merged ->
		 * FlowBean.avaibleLegalActVersions
		 */

        return filtered;

	}

    /**
     * Calculate and returns the list of Opposition basis that are already added but are not applicable to the provided
     * list of legal acts
     * @param obsList list of opposition basis
     * @param avaibleLegalActVersions list of available legal act versions
     * @return a list of opposition basis that cannot be applied because of the legal acts available
     */
    public List<OppositionBasisForm> getNotApplicableOppositionBasis(List<OppositionBasisForm> obsList,
                                                                     List<LegalActVersion> avaibleLegalActVersions) {
        List<OppositionBasisForm> removedOBList = new ArrayList<OppositionBasisForm>();
        if (obsList != null && obsList.size() > 0) {
            for (OppositionBasisForm oBForm : obsList) {
                LegalActVersion lAOBForm = oBForm.getLegalActVersion();
                boolean match = false;
                if (avaibleLegalActVersions != null) {
                    for (LegalActVersion lAALAV : avaibleLegalActVersions) {
                        if (lAALAV.getCode().equalsIgnoreCase(lAOBForm.getCode()) && lAALAV.getGroundCategory()
                                .toString().equalsIgnoreCase(oBForm.getGroundCategory().toString())) {
                            match = true;
                        }
                    }
                }
                if (!match) {
                    removedOBList.add(oBForm);
                }
            }
        }
        return removedOBList;
    }
}
