package bg.duosoft.taxcalculator.util;

import eu.ohim.sp.core.domain.application.Appeal;
import eu.ohim.sp.core.domain.application.AppealKind;
import eu.ohim.sp.core.domain.design.*;
import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeType;
import eu.ohim.sp.core.domain.person.Assignee;
import eu.ohim.sp.core.domain.person.Holder;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;

import java.util.*;

public class DsBuilder {

    static List<Object> buildDsefilingList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();

        String hasSetComposition = parameters.get("hasSetComposition");
        int deferredDesignsCount = Integer.parseInt(parameters.get("deferredDesignsCount"));
        int totalDesignsCount = Integer.parseInt(parameters.get("totalDesignsCount"));
        int prioritiesCount = Integer.parseInt(parameters.get("prioritiesCount"));

        DesignApplication designApplication = new DesignApplication();
        List<Design> designDetails = new ArrayList<>();
        int composition = 0;

        if (hasSetComposition.equals("yes")) {
            composition = 1;
            Design design = new Design();
            design.setPublicationDefermentIndicator(false);
            List<ProductIndication> productIndications = new ArrayList<>();
            ProductIndication productIndication = new ProductIndication();
            productIndication.setKind(ProductIndicationKind.SET_COMPOSITION);
            productIndications.add(productIndication);
            design.setProductIndications(productIndications);
            designDetails.add(design);
        }
        for (int i = 0; i < deferredDesignsCount; i++) {
            Design tempDesign = new Design();
            tempDesign.setPublicationDefermentIndicator(true);
            List<ProductIndication> productIndications = new ArrayList<>();
            ProductIndication productIndication = new ProductIndication();
            productIndication.setKind(ProductIndicationKind.SINGLE_PRODUCT);
            productIndications.add(productIndication);
            tempDesign.setProductIndications(productIndications);
            designDetails.add(tempDesign);
        }
        for (int i = 0; i < (totalDesignsCount - deferredDesignsCount - composition); i++) {
            Design design = new Design();
            design.setPublicationDefermentIndicator(false);
            List<ProductIndication> productIndications = new ArrayList<>();
            ProductIndication productIndication = new ProductIndication();
            productIndication.setKind(ProductIndicationKind.SINGLE_PRODUCT);
            productIndications.add(productIndication);
            design.setProductIndications(productIndications);
            designDetails.add(design);
        }
        List<eu.ohim.sp.core.domain.design.Priority> priorities = new ArrayList<>();
        if (prioritiesCount > 0) {
            for (int i = 0; i < prioritiesCount; i++)
                priorities.add(new eu.ohim.sp.core.domain.design.Priority());
        }
        designApplication.setPriorities(priorities);

        designApplication.setDesignDetails(designDetails);
        list.add(designApplication);
        return list;
    }

    static List<Object> buildDsAppealList(Map<String, String> parameters) throws Exception {

        int totalDesignsCount = Integer.parseInt(parameters.get("totalDesignsCount"));
        String hasSetComposition = parameters.get("hasSetComposition");

        List<Fee> fees = new ArrayList<>();
        ManualFee manualFee = new ManualFee();

        Fee fee = new Fee();
        FeeType feeType = new FeeType();
        feeType.setNameKey("basicFee");
        fee.setFeeType(feeType);
        fee.setQuantity(1);
        switch (parameters.get("appealKind")) {
            case "appealAgainstRefusal":
                if (hasSetComposition.equals("yes")){
                    fee.setUnitAmount(230.0);
                } else {
                    fee.setUnitAmount(180.0);
                }
                if (totalDesignsCount > 1){
                    Fee fee2 = new Fee();
                    FeeType feeType2 = new FeeType();
                    feeType2.setNameKey("dsEveryOtherRefusalFee");
                    fee2.setFeeType(feeType2);
                    fee2.setUnitAmount(70.0);
                    fee2.setQuantity(totalDesignsCount-1);
                    fee2.setAmount(fee2.getUnitAmount() * fee2.getQuantity());
                    fees.add(fee2);
                }
                break;
            case "appealAgainstTermination":
                fee.setUnitAmount(90.0);
                break;
        }
        fee.setAmount(fee.getUnitAmount() * fee.getQuantity());

        fees.add(fee);
        manualFee.setFees(fees);
        List<Object> objects = new ArrayList<>();
        objects.add(manualFee);
        return objects;
    }

    static List<Object> buildDsChangeList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        DSeServiceApplication dSeServiceApplication = new DSeServiceApplication();
        List<Holder> holders = CalcPatramListBuilder.fillHolders(parameters);
        dSeServiceApplication.setHolders(holders);
        list.add(dSeServiceApplication);
        return list;
    }

    static List<Object> buildDsGenericList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        DSeServiceApplication dSeServiceApplication = new DSeServiceApplication();
        dSeServiceApplication.setChangeType(parameters.get("changeType"));
        list.add(new DesignApplication());
        list.add(dSeServiceApplication);

        return list;
    }

    static List<Object> buildDsInvalidityList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        DSeServiceApplication dSeServiceApplication = new DSeServiceApplication();
        int designsCount = Integer.parseInt(parameters.get("designsCount"));
        List<DesignApplication> designApplications = new ArrayList<>();
        for (int i = 0; i < designsCount; i++) {
            DesignApplication designApplication = new DesignApplication();
            List<Design> designDetails = new ArrayList<>();
            Design design = new Design();
            design.setSelected(true);
            designDetails.add(design);
            designApplication.setDesignDetails(designDetails);
            designApplications.add(designApplication);
        }
        dSeServiceApplication.setDesignDetails(designApplications);
        list.add(dSeServiceApplication);
        return list;
    }

    static List<Object> buildDsLicenceList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        DSeServiceApplication dSeServiceApplication = new DSeServiceApplication();
        List<Assignee> assignees = CalcPatramListBuilder.getAssignees(parameters);
        dSeServiceApplication.setAssignees(assignees);
        list.add(dSeServiceApplication);
        return list;
    }

    static List<Object> buildDsRenewalList(Map<String, String> parameters) throws Exception {
        List<Object> list = new ArrayList<>();
        DSeServiceApplication dSeServiceApplication = new DSeServiceApplication();
        String firstSixMonths = parameters.get("renewalPeriod");
        String serialRenewal = parameters.get("serialRenewal");
        int years;

        switch (serialRenewal) {
            case "first_renewal":
                years = 10;
                break;
            case "second_renewal":
                years = 15;
                break;
            case "third_renewal":
                years = 20;
                break;
            default:
                years = 0;
        }

        Date applicationDate;
        Date expiryDate;
        Calendar calendar = Calendar.getInstance();
        if (firstSixMonths.equalsIgnoreCase("yes")) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
        } else {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        expiryDate = calendar.getTime();
        calendar.add(Calendar.YEAR, years * (-1));
        applicationDate = calendar.getTime();
        List<DesignApplication> designApplications = new ArrayList<>();
        DesignApplication designApplication = new DesignApplication();
        designApplication.setApplicationDate(applicationDate);
        List<Design> designDetails = new ArrayList<>();
        Design design = new Design();
        design.setSelected(true);
        design.setExpiryDate(expiryDate);
        designDetails.add(design);
        designApplication.setDesignDetails(designDetails);
        designApplications.add(designApplication);
        dSeServiceApplication.setDesignDetails(designApplications);
        list.add(dSeServiceApplication);
        return list;
    }
}
