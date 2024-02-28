package bg.duosoft.taxcalculator.util;

import eu.ohim.sp.core.domain.payment.Fee;
import eu.ohim.sp.core.domain.payment.FeeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PcBuilder {
    static List<Object> buildChangeFee(Map<String, String> parameters) throws Exception {
        int holdersCount = Integer.parseInt(parameters.get("holdersCount"));
        Fee fee = new Fee();
        FeeType feeType = new FeeType();
        feeType.setNameKey("basicFee");
        ManualFee manualFee = new ManualFee();
        List<Fee> fees = new ArrayList<>();
        fee.setFeeType(feeType);
        fee.setUnitAmount(50.0);
        fee.setQuantity(holdersCount);
        fee.setAmount(fee.getUnitAmount() * fee.getQuantity());
        fees.add(fee);
        manualFee.setFees(fees);
        List<Object> objects = new ArrayList<>();
        objects.add(manualFee);
        return objects;
    }
}
