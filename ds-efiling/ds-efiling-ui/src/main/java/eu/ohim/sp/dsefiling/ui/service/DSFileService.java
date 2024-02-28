package eu.ohim.sp.dsefiling.ui.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import eu.ohim.sp.common.ui.service.AbstractFileService;

@Service
public class DSFileService extends AbstractFileService{

    @Value("${sp.application}")
    private String application;
    
	@Override
	public String getModule() {
		return application;
	}

}
