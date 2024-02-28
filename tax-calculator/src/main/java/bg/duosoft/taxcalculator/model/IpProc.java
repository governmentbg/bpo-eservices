package bg.duosoft.taxcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "IP_PROC", schema = "IPASPROD")
public class IpProc implements Serializable {
    @EmbeddedId
    private IpProcId ipProcId;

    @Column(name = "file_typ")
    private String fileType;

    @Column(name = "file_ser")
    private Integer fileSer;

    @Column(name = "file_nbr")
    private Long fileNum;

    @Column(name = "STATUS_CODE")
    private String statusCode;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PROC_TYP", referencedColumnName = "PROC_TYP", insertable = false, updatable = false),
            @JoinColumn(name = "STATUS_CODE", referencedColumnName = "STATUS_CODE", insertable = false, updatable = false)
    })
    private CfStatus cfStatus;

}

