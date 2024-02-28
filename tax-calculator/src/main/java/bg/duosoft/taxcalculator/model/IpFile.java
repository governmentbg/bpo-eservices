package bg.duosoft.taxcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "ip_file", schema = "IPASPROD")
public class IpFile implements Serializable {
    @EmbeddedId
    private IpFileId ipFileId;

    @Column(name = "registration_nbr")
    private Long registrationNum;

    @Column(name = "title")
    private String title;

    @Column(name = "PROC_TYP")
    private String procTyp;

    @Column(name = "PROC_NBR")
    private Long procNbr;

    @ManyToOne
    @JoinColumn(name = "main_owner_person_nbr", referencedColumnName = "person_nbr")
    private IpPerson mainOwner;

    @ManyToOne
    @JoinColumn(name = "service_person_nbr", referencedColumnName = "person_nbr")
    private IpPerson servicePerson;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "PROC_TYP", referencedColumnName = "PROC_TYP", insertable = false, updatable = false),
            @JoinColumn(name = "PROC_NBR", referencedColumnName = "PROC_NBR", insertable = false, updatable = false)
    })
    private IpProc ipProc;

}

