package bg.duosoft.taxcalculator.util;

import eu.ohim.sp.core.domain.payment.Fee;

import java.util.List;

public class ManualFee {
    private List<Fee> fees;

    public List<Fee> getFees() {
        return fees;
    }

    public void setFees(List<Fee> fees) {
        this.fees = fees;
    }
}
