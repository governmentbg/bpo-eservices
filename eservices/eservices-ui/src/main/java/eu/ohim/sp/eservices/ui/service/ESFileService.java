package eu.ohim.sp.eservices.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.service.AbstractFileService;

@Service(value="fileService")
public class ESFileService extends AbstractFileService {

	@Autowired
	private FormUtil formUtil;
	
	@Override
	public String getModule() {
		return formUtil.getType();
	}
}
