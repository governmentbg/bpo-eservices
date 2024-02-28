package bg.duosoft.taxcalculator.service;

import bg.duosoft.taxcalculator.model.IpFile;

public interface IpFileService {

    IpFile getFile(Long fileNbr, Long regNbr, String fileTyp);

}
