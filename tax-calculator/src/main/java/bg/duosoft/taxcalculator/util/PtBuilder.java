package bg.duosoft.taxcalculator.util;

import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.application.AppealKind;
import eu.ohim.sp.core.domain.patent.*;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeType;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.trademark.LimitedTradeMark;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.text.SimpleDateFormat;
import java.util.*;

public class PtBuilder {

    @Autowired
    private static MessageSource messageSource;

    static List<Object> buildPtefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PatentApplication patentApplication = new PatentApplication();

        boolean isSmallCompany = Boolean.parseBoolean(parameters.get("smallCompany"));
        boolean isLicenceAvailability = Boolean.parseBoolean(parameters.get("licenceAvailability"));
        boolean feesArePayedInPCT = Boolean.parseBoolean(parameters.get("feesArePayedInPCT"));
        String claimsCount = parameters.get("claimsCount");
        int prioritiesCount = Integer.parseInt(parameters.get("prioritiesCount"));
        String independentClaimsCount = parameters.get("independentClaimsCount");
        boolean examinationRequired = Boolean.parseBoolean(parameters.get("examinationRequired"));
        boolean anticipationOfPublication = Boolean.parseBoolean(parameters.get("anticipationOfPublication"));

        patentApplication.setSmallCompany(isSmallCompany);
        patentApplication.setLicenceAvailability(isLicenceAvailability);

        Patent patent = new Patent();
        List<PCT> pcts = new ArrayList<>();
        PCT pct = new PCT();
        pct.setPayedFees(feesArePayedInPCT);
        pcts.add(pct);
        patent.setPcts(pcts);

        patent.setClaimsCount(claimsCount);

        List<PatentPriority> patentPriorities = new ArrayList<>();
        for (int i = 0; i < prioritiesCount; i++) {
            patentPriorities.add(new PatentPriority());
        }
        patent.setPriorities(patentPriorities);

        patent.setIndependentClaimsCount(independentClaimsCount);

        patentApplication.setPatent(patent);

        patentApplication.setExaminationRequested(examinationRequired);

        patentApplication.setAnticipationOfPublication(anticipationOfPublication);

        list.add(patentApplication);
        return list;
    }

    static List<Object> buildUmefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PatentApplication patentApplication = new PatentApplication();

        boolean isSmallCompany = Boolean.parseBoolean(parameters.get("smallCompany"));
        boolean feesArePayedInPCT = Boolean.parseBoolean(parameters.get("feesArePayedInPCT"));
        boolean feesArePayedInTransformation = Boolean.parseBoolean(parameters.get("feesArePayedInTransformation"));
        int prioritiesCount = Integer.parseInt(parameters.get("prioritiesCount"));
        boolean examinationRequired = Boolean.parseBoolean(parameters.get("examinationRequired"));

        patentApplication.setSmallCompany(isSmallCompany);

        Patent patent = new Patent();
        List<PCT> pcts = new ArrayList<>();
        PCT pct = new PCT();
        pct.setPayedFees(feesArePayedInPCT);
        pcts.add(pct);
        patent.setPcts(pcts);

        List<PatentTransformation> patentTransformations = new ArrayList<>();
        PatentTransformation patentTransformation = new PatentTransformation();
        patentTransformation.setPayedFees(feesArePayedInTransformation);
        patentTransformations.add(patentTransformation);
        patent.setTransformationPriorities(patentTransformations);

        List<PatentPriority> patentPriorities = new ArrayList<>();
        for (int i = 0; i < prioritiesCount; i++) {
            patentPriorities.add(new PatentPriority());
        }
        patent.setPriorities(patentPriorities);

        patentApplication.setPatent(patent);

        patentApplication.setExaminationRequested(examinationRequired);

        list.add(patentApplication);
        return list;
    }

    static List<Object> buildEpefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PatentApplication patentApplication = new PatentApplication();

        String epApplicationKind = parameters.get("epApplicationKind");
        String pagesCount = parameters.get("pagesCount");

        Patent patent = new Patent();
        patent.setPagesCount(pagesCount);
        patentApplication.setPatent(patent);
        patentApplication.setApplicationKind(epApplicationKind);
        list.add(patentApplication);
        return list;
    }

    static List<Object> buildSvefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PatentApplication patentApplication = new PatentApplication();
        int prioritiesCount = Integer.parseInt(parameters.get("prioritiesCount"));
        Patent patent = new Patent();
        List<PatentPriority> patentPriorities = new ArrayList<>();
        for (int i = 0; i < prioritiesCount; i++) {
            patentPriorities.add(new PatentPriority());
        }
        patent.setPriorities(patentPriorities);
        patentApplication.setPatent(patent);
        list.add(patentApplication);
        return list;
    }

    static List<Object> buildSpcefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PatentApplication patentApplication = new PatentApplication();
        Patent patent = new Patent();
        patentApplication.setPatent(patent);
        list.add(patentApplication);
        return list;
    }

    static List<Object> buildPtChangeList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();
        List<Holder> holders = CalcPatramListBuilder.fillHolders(parameters);
        pTeServiceApplication.setHolders(holders);
        list.add(pTeServiceApplication);
        return list;
    }

    static List<Object> buildPtLicenceList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();
        List<Assignee> assignees = CalcPatramListBuilder.getAssignees(parameters);
        pTeServiceApplication.setAssignees(assignees);
        list.add(pTeServiceApplication);
        return list;
    }

    static List<Object> buildPtAppealList(Map<String, String> parameters) throws Exception {

        String smallCompanyStr = parameters.get("smallCompany");
        String licenceAvailabilityStr = parameters.get("licenceAvailability");
        String singleOrGroupInventionStr = parameters.get("singleOrGroupInvention");
        String appealKindStr = parameters.get("appealKind");

        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();

        List<Patent> patentList = new ArrayList<>();
        List<Appeal> appeals = new ArrayList<>();

        int patentCount;
        if (singleOrGroupInventionStr.equals("singleInvention")) {
            patentCount = 1;
        } else {
            patentCount = 2;
        }

        for(int i = 0; i < patentCount; i++){
            patentList.add(new Patent());
        }

        Appeal appeal = new Appeal();
        if (appealKindStr.equalsIgnoreCase("appealAgainstRefusal"))
            appeal.setAppealKind(AppealKind.APPEAL_AGAINST_REFUSAL);
        else if (appealKindStr.equalsIgnoreCase("appealAgainstTermination"))
            appeal.setAppealKind(AppealKind.APPEAL_AGAINST_TERMINATION);
        appeals.add(appeal);

        pTeServiceApplication.setSmallCompany(smallCompanyStr.equalsIgnoreCase("true"));
        pTeServiceApplication.setLicenceAvailability(licenceAvailabilityStr.equalsIgnoreCase("true"));
        pTeServiceApplication.setPatentList(patentList);
        pTeServiceApplication.setAppeals(appeals);

        list.add(pTeServiceApplication);
        return list;
    }

    static List<Object> buildUmAppealList(Map<String, String> parameters) throws Exception {
        String appealKindStr = parameters.get("appealKind");

        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();

        List<Patent> patentList = new ArrayList<>();
        patentList.add(new Patent());
        List<Appeal> appeals = new ArrayList<>();


        Appeal appeal = new Appeal();
        if (appealKindStr.equalsIgnoreCase("appealAgainstRefusal"))
            appeal.setAppealKind(AppealKind.APPEAL_AGAINST_REFUSAL);
        else if (appealKindStr.equalsIgnoreCase("appealAgainstTermination"))
            appeal.setAppealKind(AppealKind.APPEAL_AGAINST_TERMINATION);
        appeals.add(appeal);

        pTeServiceApplication.setPatentList(patentList);
        pTeServiceApplication.setAppeals(appeals);

        list.add(pTeServiceApplication);
        return list;
    }

    static List<Object> buildTranslationFee(Map<String, String> parameters, Locale locale) throws Exception {
        String translationType = parameters.get("translationType");
        int pagesCount = Integer.parseInt(parameters.get("pagesCount"));

        Fee fee = new Fee();
        FeeType feeType = new FeeType();
        ManualFee manualFee = new ManualFee();
        List<Fee> fees = new ArrayList<>();

        if (translationType.equals("message")) {
            fee.setUnitAmount(45.0);
            fee.setQuantity(1);
            fee.setAmount(fee.getUnitAmount() * fee.getQuantity());
            feeType.setNameKey("epMessageFee");
            fee.setFeeType(feeType);
            fees.add(fee);
        } else if (translationType.equals("publication")) {
            fee.setUnitAmount(80.0);
            fee.setQuantity(1);
            fee.setAmount(fee.getUnitAmount() * fee.getQuantity());
            feeType.setNameKey("epPublicationFee");
            fee.setFeeType(feeType);
            fees.add(fee);
            if (pagesCount > 10) {
                Fee extraFee = new Fee();
                extraFee.setUnitAmount(10.0);
                extraFee.setQuantity(pagesCount - 10);
                extraFee.setAmount(extraFee.getUnitAmount() * extraFee.getQuantity());
                FeeType extraFeeType = new FeeType();
                extraFeeType.setNameKey("extraPagesPublicationFee");
                extraFee.setFeeType(extraFeeType);
                fees.add(extraFee);
            }
        } else {
            throw new Exception(messageSource.getMessage("error.convert_params", null, locale));
        }
        manualFee.setFees(fees);
        List<Object> objects = new ArrayList<>();
        objects.add(manualFee);
        return objects;
    }

    static List<Object> buildPtGenericList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();
        pTeServiceApplication.setChangeType(parameters.get("changeType"));
        if (parameters.get("changeType").equals("translationCorrection")){
            pTeServiceApplication.setPagesCountAttachment(11);
        }
        list.add(pTeServiceApplication);
        return list;
    }

    static List<Object> buildPtInvalidityList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();
        boolean partialInvalidity = Boolean.parseBoolean(parameters.get("partialInvalidity"));
        pTeServiceApplication.setPartialInvalidity(partialInvalidity);
        list.add(pTeServiceApplication);
        return list;
    }

    static List<Object> buildUmRenewalList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        PTeServiceApplication pTeServiceApplication = new PTeServiceApplication();
        List<Patent> patents = new ArrayList<>();
        Patent patent = new Patent();
        Date entitlementDate = new SimpleDateFormat("dd.MM.yyyy").parse(parameters.get("entitlementDate"));
        Date expDate = new SimpleDateFormat("dd.MM.yyyy").parse(parameters.get("expirationDate"));
        patent.setExpirationDate(expDate);
        patent.setEntitlementDate(entitlementDate);
        patents.add(patent);
        pTeServiceApplication.setPatentList(patents);
        list.add(pTeServiceApplication);
        return list;
    }

}
