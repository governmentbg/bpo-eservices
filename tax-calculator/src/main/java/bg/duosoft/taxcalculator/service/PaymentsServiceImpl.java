package bg.duosoft.taxcalculator.service;

import bg.duosoft.taxcalculator.rest.LiabilityRecord;
import bg.duosoft.taxcalculator.rest.PaymentLiabilityDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PaymentsServiceImpl implements PaymentsService {
    private static Logger LOG = LoggerFactory.getLogger(PaymentsServiceImpl.class);

    @Autowired
    private RestTemplate paymentsRestTemplate;

    @Autowired
    private String paymentsRestServiceURL;


    @Override
    public LiabilityRecord getLiabilityRecord(String referenceNumber) {
        try {
            ResponseEntity<LiabilityRecord> result = paymentsRestTemplate.getForEntity(paymentsRestServiceURL + SELECT_LIABILITY_URL, LiabilityRecord.class, referenceNumber);
            return result.getBody();
        } catch (RestClientException e) {
            LOG.error("Error reading payment data...", e);
            return null;
        }
    }

    @Override
    public List<PaymentLiabilityDetail> getAllLiabilityDetails(String referenceNumber) {
        if (StringUtils.isEmpty(referenceNumber)) {
            return null;
        }

        try {
            ResponseEntity<PaymentLiabilityDetail[]> result = paymentsRestTemplate.getForEntity(paymentsRestServiceURL + SELECT_LIABILITY_LIST_URL, PaymentLiabilityDetail[].class, referenceNumber);
            List<PaymentLiabilityDetail> paymentLiabilityDetails = Objects.requireNonNull(result.getBody()).length == 0 ? null : Arrays.asList(result.getBody());
            return paymentLiabilityDetails;
        } catch (RestClientException e) {
            LOG.error("Error reading payment data...", e);
            return null;
        }
    }

    public List<PaymentLiabilityDetail> getUnpaidLiabilityDetails(String id) {
        List<PaymentLiabilityDetail> liabilityDetailsList = getAllLiabilityDetails(id);
        if (liabilityDetailsList != null) {
            liabilityDetailsList =
                    liabilityDetailsList.stream().filter(pd -> !pd.isPaid() && pd.getAmountOutstanding().compareTo(BigDecimal.ZERO) > 0).collect(Collectors.toList());

        } else {
            liabilityDetailsList = new ArrayList<>();
        }
        return liabilityDetailsList;
    }

}
