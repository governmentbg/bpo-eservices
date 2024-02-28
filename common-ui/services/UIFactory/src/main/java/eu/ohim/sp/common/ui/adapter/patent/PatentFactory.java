package eu.ohim.sp.common.ui.adapter.patent;

import eu.ohim.sp.common.ui.adapter.ListAttachedDocumentFactory;
import eu.ohim.sp.common.ui.adapter.UIFactory;
import eu.ohim.sp.common.ui.form.patent.PatentForm;
import eu.ohim.sp.core.domain.patent.Patent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by Raya
 * 11.04.2019
 */
@Component
public class PatentFactory implements UIFactory<Patent, PatentForm> {

    @Autowired
    private ListAttachedDocumentFactory listAttachedDocumentFactory;

    @Autowired
    private PatentViewFactory patentViewFactory;

    @Override
    public Patent convertTo(PatentForm form) {
        Patent core = new Patent();
        if(form == null){
            return core;
        }

        core.setPatentAbstract(form.getPatentAbstract());
        core.setPatentAbstractSecondLang(form.getPatentAbstractSecondLang());
        core.setPatentTitle(form.getPatentTitle());
        core.setPatentTitleSecondLang(form.getPatentTitleSecondLang());
        if(form.getPatentViews() != null){
            core.setPatentViews(form.getPatentViews().stream().map(e -> patentViewFactory.convertTo(e)).collect(Collectors.toList()));
        }

        core.setPatentDescriptions(listAttachedDocumentFactory.convertTo(form.getPatentDescriptions()));
        core.setPatentDrawings(listAttachedDocumentFactory.convertTo(form.getPatentDrawings()));
        core.setPatentClaims(listAttachedDocumentFactory.convertTo(form.getPatentClaims()));
        core.setSequencesListing(listAttachedDocumentFactory.convertTo(form.getSequencesListing()));

        core.setClaimsCount(form.getClaimsCount());
        core.setIndependentClaimsCount(form.getIndependentClaimsCount());
        core.setPagesCount(form.getPagesCount());
        core.setDrawingsCount(form.getDrawingsCount());
        core.setDrawingNumber(form.getDrawingNumber());

        core.setApplicationNumber(form.getApplicationNumber());
        core.setRegistrationNumber(form.getRegistrationNumber());
        core.setApplicationDate(form.getApplicationDate());
        core.setRegistrationDate(form.getRegistrationDate());
        core.setRegistrationPublicationDate(form.getRegistrationPublicationDate());

        core.setPatentValidated(form.getPatentValidated());
        core.setExternalReferenceNumber(form.getExternalReferenceNumber());
        core.setPatentPublicationsInfo(form.getPatentPublicationsInfo());
        core.setSvKind(form.getSvKind());
        core.setTitleTransliteration(form.getTitleTransliteration());
        core.setTaxon(form.getTaxon());
        core.setLatinClassification(form.getLatinClassification());

        core.setRegKind(form.getRegKind());
        core.setFirstPermissionBGDate(form.getFirstPermissionBGDate());
        core.setFirstPermissionBGNotificationDate(form.getFirstPermissionBGNotificationDate());
        core.setFirstPermissionBGNumber(form.getFirstPermissionBGNumber());
        core.setFirstPermissionEUDate(form.getFirstPermissionEUDate());
        core.setFirstPermissionEUNumber(form.getFirstPermissionEUNumber());

        return core;
    }

    @Override
    public PatentForm convertFrom(Patent core) {
        PatentForm form = new PatentForm();
        if(core == null){
            return form;
        }
        form.setPatentAbstract(core.getPatentAbstract());
        form.setPatentAbstractSecondLang(core.getPatentAbstractSecondLang());
        form.setPatentTitle(core.getPatentTitle());
        form.setPatentTitleSecondLang(core.getPatentTitleSecondLang());
        form.setPatentDrawings(listAttachedDocumentFactory.convertFrom(core.getPatentDrawings()));
        form.setPatentDescriptions(listAttachedDocumentFactory.convertFrom(core.getPatentDescriptions()));
        form.setPatentClaims(listAttachedDocumentFactory.convertFrom(core.getPatentClaims()));
        form.setSequencesListing(listAttachedDocumentFactory.convertFrom(core.getSequencesListing()));

        form.setClaimsCount(core.getClaimsCount());
        form.setIndependentClaimsCount(core.getIndependentClaimsCount());
        form.setPagesCount(core.getPagesCount());
        form.setDrawingsCount(core.getDrawingsCount());
        form.setDrawingNumber(core.getDrawingNumber());

        form.setApplicationNumber(core.getApplicationNumber());
        if(core.getApplicationNumber() != null){
            form.setId(core.getApplicationNumber());
        }

        form.setRegistrationNumber(core.getRegistrationNumber());
        form.setApplicationDate(core.getApplicationDate());
        form.setRegistrationDate(core.getRegistrationDate());
        form.setRegistrationPublicationDate(core.getRegistrationPublicationDate());
        form.setPatentValidated(core.getPatentValidated());
        form.setExternalReferenceNumber(core.getExternalReferenceNumber());
        form.setPatentPublicationsInfo(core.getPatentPublicationsInfo());
        form.setSvKind(core.getSvKind());
        form.setTitleTransliteration(core.getTitleTransliteration());
        form.setTaxon(core.getTaxon());
        form.setLatinClassification(core.getLatinClassification());

        form.setRegKind(core.getRegKind());
        form.setFirstPermissionBGDate(core.getFirstPermissionBGDate());
        form.setFirstPermissionBGNotificationDate(core.getFirstPermissionBGNotificationDate());
        form.setFirstPermissionBGNumber(core.getFirstPermissionBGNumber());
        form.setFirstPermissionEUDate(core.getFirstPermissionEUDate());
        form.setFirstPermissionEUNumber(core.getFirstPermissionEUNumber());

        return form;
    }
}
