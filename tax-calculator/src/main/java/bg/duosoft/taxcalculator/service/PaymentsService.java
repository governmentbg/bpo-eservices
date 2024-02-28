package bg.duosoft.taxcalculator.service;

import bg.duosoft.taxcalculator.rest.LiabilityRecord;
import bg.duosoft.taxcalculator.rest.PaymentLiabilityDetail;

import java.util.List;

public interface PaymentsService {

    String SELECT_LIABILITY_URL = "/liability?referenceNumber={referenceNumber}";
    String SELECT_LIABILITY_LIST_URL = "/liabilitydetails?referenceNumber={referenceNumber}";

    LiabilityRecord getLiabilityRecord(String referenceNumber);

    List<PaymentLiabilityDetail> getAllLiabilityDetails(String referenceNumber);

    List<PaymentLiabilityDetail> getUnpaidLiabilityDetails(String referenceNumber);
}
