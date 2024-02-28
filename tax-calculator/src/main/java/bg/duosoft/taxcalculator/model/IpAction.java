package bg.duosoft.taxcalculator.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "IP_ACTION", schema = "IPASPROD")
public class IpAction implements Serializable {

    @EmbeddedId
    private IpActionId ipActionId;

    @Column(name = "JOURNAL_CODE")
    private String journalCode;

    @ManyToOne
    @JoinColumn(name = "JOURNAL_CODE", referencedColumnName = "JOURNAL_CODE", insertable = false, updatable = false)
    private IpJournal ipJournal;

}

