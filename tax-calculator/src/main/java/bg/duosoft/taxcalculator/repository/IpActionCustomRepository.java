package bg.duosoft.taxcalculator.repository;

import bg.duosoft.taxcalculator.model.IpAction;
import bg.duosoft.taxcalculator.model.IpFile;
import bg.duosoft.taxcalculator.model.IpProc;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class IpActionCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public Boolean isValid(IpFile ipFile) {

        if(!ipFile.getIpFileId().getFileType().equals("P") && !ipFile.getIpFileId().getFileType().equals("U") && !ipFile.getIpFileId().getFileType().equals("Д"))
            return true;

        Query query = em.createNativeQuery("SELECT " +
                " a.ACTION_DATE, a.NOTES2, a.NOTES3, a.NOTES4, a.ACTION_TYP, j.PUBLICATION_DATE, j.JOURNAL_CODE " +
                " FROM [IP_ACTION] as a " +
                " JOIN [IP_PROC] as p on (p.PROC_NBR = a.PROC_NBR and a.PROC_TYP = p.PROC_TYP) " +
                " LEFT JOIN [IP_JOURNAL] as j on (j.JOURNAL_CODE = a.JOURNAL_CODE) " +
                " WHERE ((ACTION_TYP in (1834)) or (a.JOURNAL_CODE is not null AND j.PUBLICATION_DATE is not null)) " +
                " AND ((p.FILE_SEQ = :fileSeq and p.FILE_TYP = :fileTyp and p.FILE_SER = :fileSer and p.FILE_NBR = :fileNbr ) OR (p.USERDOC_FILE_SEQ = :fileSeq and p.USERDOC_FILE_TYP = :fileTyp and p.USERDOC_FILE_SER = :fileSer and p.USERDOC_FILE_NBR = :fileNbr )) ");
        query.setParameter("fileSeq", ipFile.getIpFileId().getFileSeq());
        query.setParameter("fileTyp", ipFile.getIpFileId().getFileType());
        query.setParameter("fileSer", ipFile.getIpFileId().getFileSer());
        query.setParameter("fileNbr", ipFile.getIpFileId().getFileNum());

//        THIS WORKS WITH OLD ENTRIES ONLY!!!
//        TypedQuery<IpAction> query = em.createQuery("select p from IpAction p where p.ipJournal is not null and " +
//                "p.ipActionId.procNbr = :procNbr and p.ipActionId.procTyp = :procTyp and p.ipJournal.journalCode is not null and " +
//                "p.ipJournal.publicationDate is not null and p.ipJournal.publicationDate < current_timestamp", IpAction.class);
//
//        query.setParameter("procNbr", ipFile.getIpProc().getIpProcId().getProcNbr());
//        query.setParameter("procTyp", ipFile.getIpProc().getIpProcId().getProcTyp());

//        Query query = em.createNativeQuery("select * from IP_PROC p join ip_action a on p.PROC_NBR = a.PROC_NBR and p.PROC_TYP = a.PROC_TYP join IP_JOURNAL j on a.JOURNAL_CODE = j.JOURNAL_CODE where file_typ= ?2 and file_ser= ?3 and file_nbr= ?1 and j.PUBLICATION_DATE is not null and PUBLICATION_DATE<current_timestamp", IpProc.class);
//        query.setParameter(1, ipFileId.getFileNum());
//        query.setParameter(2, ipFileId.getFileType());
//        query.setParameter(3, ipFileId.getFileSer());

        List resultList = query.getResultList();
        return !resultList.isEmpty();
    }

}
