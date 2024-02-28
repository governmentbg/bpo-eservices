package bg.duosoft.taxcalculator.service;

import bg.duosoft.taxcalculator.model.IpFile;
import bg.duosoft.taxcalculator.repository.IpActionCustomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IpActionServiceImpl implements IpActionService {

    @Autowired
    private IpActionCustomRepository ipActionCustomRepository;

    @Override
    public Boolean isValid(IpFile ipFile) {
        return ipActionCustomRepository.isValid(ipFile);
    }
}
