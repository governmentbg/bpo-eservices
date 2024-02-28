package bg.duosoft.taxcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "IP_JOURNAL", schema = "IPASPROD")
public class IpJournal implements Serializable {

    @Id
    @Column(name = "JOURNAL_CODE")
    private String journalCode;

    @Column(name = "PUBLICATION_DATE")
    private Date publicationDate;

}

