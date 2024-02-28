package bg.duosoft.taxcalculator.util;

import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.application.AppealKind;
import eu.ohim.sp.core.domain.claim.trademark.Priority;
import eu.ohim.sp.core.domain.opposition.EarlierEntitlementRightKind;
import eu.ohim.sp.core.domain.opposition.OppositionRelativeGrounds;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.trademark.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class TmBuilder {

    static List<Object> buildTmefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TradeMark tm = new TradeMark();
        fillMarkKind(parameters, tm);
        fillClassesCount(parameters, tm);

        int prioritiesCount = Integer.parseInt(parameters.get("prioritiesCount"));
        if (prioritiesCount > 0) {
            List<Priority> priorities = new ArrayList<>();
            for (int i = 0; i < prioritiesCount; i++)
                priorities.add(new Priority());
            tm.setPriorities(priorities);
        }

        list.add(tm);

        if (parameters.get("registrationCertificate").equalsIgnoreCase("yes")) {
            list.add(new AbstractMap.SimpleEntry<>("certificateRequestedIndicator", true));
        }

        return list;
    }

    static List<Object> buildTmOppositionList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        LimitedTradeMark tm = new LimitedTradeMark();
        fillMarkKind(parameters, tm);

        List<LimitedTradeMark> tradeMarkList = new ArrayList<>();
        tradeMarkList.add(tm);
        tMeServiceApplication.setTradeMarks(tradeMarkList);

        fillOppositionRelativeGrounds(parameters, tMeServiceApplication);

        list.add(tMeServiceApplication);
        list.add(tm);

        return list;
    }

    static List<Object> buildTmAppealList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        List<Appeal> appeals = new ArrayList<>();
        Appeal appeal = new Appeal();

        switch (parameters.get("appealKind")) {
            case "appealAgainstOppositionDecision":
                appeal.setAppealKind(AppealKind.APPEAL_AGAINST_OPPOSITION_DECISION);
                break;
            case "appealAgainstRefusal":
                appeal.setAppealKind(AppealKind.APPEAL_AGAINST_REFUSAL);
                break;
            case "appealAgainstTermination":
                appeal.setAppealKind(AppealKind.APPEAL_AGAINST_TERMINATION);
                break;
        }
        appeals.add(appeal);
        tMeServiceApplication.setAppeals(appeals);
        list.add(tMeServiceApplication);

        return list;
    }

    static List<Object> buildTmInvalidityList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        fillOppositionRelativeGrounds(parameters, tMeServiceApplication);
        list.add(tMeServiceApplication);

        return list;
    }

    static List<Object> buildTmSurrenderList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        List<GSHelperDetails> gsHelperDetails = new ArrayList<>();
        GSHelperDetails gsHelper = new GSHelperDetails();

        switch (parameters.get("applicationExtent")) {
            case "partialGoodsAndServices":
                gsHelper.setApplicationExtent(ApplicationExtent.PARTIAL_GOODS_AND_SERVICES);
                break;
            case "allGoodsAndServices":
                gsHelper.setApplicationExtent(ApplicationExtent.ALL_GOODS_AND_SERVICES);
                break;
        }
        gsHelperDetails.add(gsHelper);
        tMeServiceApplication.setGsHelpers(gsHelperDetails);
        list.add(tMeServiceApplication);

        return list;
    }

    static List<Object> buildTmLicenceList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        List<Assignee> assignees = CalcPatramListBuilder.getAssignees(parameters);
        tMeServiceApplication.setAssignees(assignees);
        list.add(tMeServiceApplication);
        return list;
    }

    static List<Object> buildTmRenewalList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        LimitedTradeMark tm = new LimitedTradeMark();

        fillMarkKind(parameters, tm);

        List<ClassDescription> classes = fillClassesCount(parameters, tm);

        Date expDate = new SimpleDateFormat("dd.MM.yyyy").parse(parameters.get("expirationDate"));
        tm.setExpirationDate(expDate);

        tm.setApplicationNumber("1");

        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        List<LimitedTradeMark> tmlist = new ArrayList<>();
        tmlist.add(tm);
        tMeServiceApplication.setTradeMarks(tmlist);

        List<GSHelperDetails> gsHelperDetails = new ArrayList<>();
        GSHelperDetails gsHelper = new GSHelperDetails();
        gsHelper.setClassDescriptions(classes);
        gsHelper.setTmApplicationNumber("1");
        gsHelperDetails.add(gsHelper);
        tMeServiceApplication.setGsHelpers(gsHelperDetails);

        list.add(tMeServiceApplication);
        list.add(tm);

        return list;
    }

    static List<Object> buildTmChangeList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        List<Holder> holders = CalcPatramListBuilder.fillHolders(parameters);

        tMeServiceApplication.setHolders(holders);
        list.add(tMeServiceApplication);

        return list;
    }

    static List<Object> buildTmGenericList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        TMeServiceApplication tMeServiceApplication = new TMeServiceApplication();
        tMeServiceApplication.setChangeType(parameters.get("changeType"));
        list.add(new LimitedTradeMark());
        list.add(tMeServiceApplication);

        return list;
    }

    static void fillMarkKind(Map<String, String> parameters, TradeMark tm) {
        switch (parameters.get("markKind")) {
            case "individual":
                tm.setMarkRightKind(MarkKind.INDIVIDUAL);
                break;
            case "certificate":
                tm.setMarkRightKind(MarkKind.CERTIFICATE);
                break;
            case "collective":
                tm.setMarkRightKind(MarkKind.COLLECTIVE);
                break;
        }
    }

    static List<ClassDescription> fillClassesCount(Map<String, String> parameters, TradeMark tm) {
        int classesCount = Integer.parseInt(parameters.get("classesCount"));
        List<ClassDescription> classes = new ArrayList<>();
        for (int i = 0; i < classesCount; i++)
            classes.add(new ClassDescription());
        tm.setClassDescriptions(classes);
        return classes;
    }

    private static void fillOppositionRelativeGrounds(Map<String, String> parameters, TMeServiceApplication tMeServiceApplication) {
        List<OppositionRelativeGrounds> oppositionGrounds = new ArrayList<>();
        OppositionRelativeGrounds oppositionGround = new OppositionRelativeGrounds();

        switch (parameters.get("invalidityGroundKind")) {
            case "badFaithOnly":
                oppositionGround.setEarlierEntitlementRightType(EarlierEntitlementRightKind.BAD_FAITH);
                break;
            case "others":
                oppositionGround.setEarlierEntitlementRightType(EarlierEntitlementRightKind.EARLIER_TRADE_MARK);
                break;
        }
        oppositionGrounds.add(oppositionGround);
        tMeServiceApplication.setOppositionGrounds(Collections.unmodifiableList(oppositionGrounds));
    }

}
