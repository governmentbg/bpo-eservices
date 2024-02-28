package bg.duosoft.taxcalculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class IpProcId implements Serializable {
    @Column(name = "PROC_TYP")
    private String procTyp;
    @Column(name = "PROC_NBR")
    private Long procNbr;

}
