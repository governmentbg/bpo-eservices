package eu.ohim.sp.ui.tmefiling.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.service.AbstractFileService;

/**
 * @author karalch
 */
@Service
public class FileService extends AbstractFileService {

	@Autowired
	private FormUtil formUtil;
    
	@Override
	public String getModule() {
		return formUtil.getType();
	}
	
}
