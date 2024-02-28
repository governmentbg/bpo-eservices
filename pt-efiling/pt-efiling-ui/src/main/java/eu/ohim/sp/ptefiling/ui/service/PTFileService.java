package eu.ohim.sp.ptefiling.ui.service;

import eu.ohim.sp.common.ui.service.AbstractFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Raya
 * 21.11.2018
 */
@Service(value="fileService")
public class PTFileService extends AbstractFileService {

    @Autowired
    private FormUtil formUtil;

    @Override
    public String getModule() {
        return formUtil.getType();
    }
}
