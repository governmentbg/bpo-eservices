package bg.duosoft.taxcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "ip_person", schema = "IPASPROD")
public class IpPerson {
    @Id
    @Column(name = "person_nbr")
    private Long personNumber;

    @Column(name = "person_name")
    private String personName;

}