package bg.duosoft.taxcalculator.service;

import bg.duosoft.taxcalculator.model.IpFile;
import bg.duosoft.taxcalculator.repository.IpFileCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpFileServiceImpl implements IpFileService {

    @Autowired
    private IpFileCustomRepository ipFileCustomRepository;

    @Override
    public IpFile getFile(Long fileNbr, Long regNbr, String fileTyp) {
        return ipFileCustomRepository.getFile(fileNbr, regNbr, fileTyp);
    }
}
