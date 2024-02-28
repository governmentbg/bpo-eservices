package bg.duosoft.taxcalculator.repository;

import bg.duosoft.taxcalculator.model.IpFile;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;

@Repository
public class IpFileCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public IpFile getFile(Long fileNbr, Long regNbr, String fileTyp) {
        String queryStr;
        if (Objects.nonNull(fileNbr))
            queryStr = "select o from IpFile o where o.ipFileId.fileNum = :nbr and o.ipFileId.fileType = :fileTyp";
        else if (Objects.nonNull(regNbr))
            queryStr = "select o from IpFile o where o.registrationNum = :nbr and o.ipFileId.fileType = :fileTyp";
        else
            return null;
        TypedQuery<IpFile> query = em.createQuery(queryStr, IpFile.class);
        if (Objects.nonNull(fileNbr))
            query.setParameter("nbr", fileNbr);
        else
            query.setParameter("nbr", regNbr);
        query.setParameter("fileTyp", fileTyp);
        List<IpFile> resultList = query.getResultList();
        if (resultList.size() == 1)
            return query.getResultList().get(0);
        else
            return null;
    }

}
