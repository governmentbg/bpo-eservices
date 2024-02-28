package bg.duosoft.taxcalculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class IpFileId implements Serializable {
    @Column(name = "file_seq")
    private String fileSeq;
    @Column(name = "file_typ")
    private String fileType;
    @Column(name = "file_ser")
    private Integer fileSer;
    @Column(name = "file_nbr")
    private Long fileNum;

    @Override
    public String toString() {
        return fileSeq + '/' + fileType + '/' + fileSer + "/" + fileNum;
    }
}
