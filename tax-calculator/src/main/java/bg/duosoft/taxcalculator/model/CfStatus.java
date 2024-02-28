package bg.duosoft.taxcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "CF_STATUS", schema = "IPASPROD")
public class CfStatus implements Serializable {

    @EmbeddedId
    private CfStatusId cfStatusId;

    @Column(name = "STATUS_NAME")
    private String statusName;

}

