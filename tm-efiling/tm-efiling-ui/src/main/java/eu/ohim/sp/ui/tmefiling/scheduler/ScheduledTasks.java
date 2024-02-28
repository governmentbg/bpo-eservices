package eu.ohim.sp.ui.tmefiling.scheduler;

import org.springframework.beans.factory.annotation.Autowired;

import eu.ohim.sp.ui.tmefiling.service.interfaces.GoodsServicesServiceInterface;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class ScheduledTasks {
    private static final Logger logger = Logger.getLogger(ScheduledTasks.class);

	@Autowired
	private GoodsServicesServiceInterface goodsServicesService;

	private String numberOfClasses;

    private String preloadLanguages;

    public void setGoodsServicesService(GoodsServicesServiceInterface service) {
    	this.goodsServicesService = service;
    }
    
    public String getNumberOfClasses() {
		return numberOfClasses;
	}

	public void setNumberOfClasses(String numberOfClasses) {
		this.numberOfClasses = numberOfClasses;
	}

	public String getPreloadLanguages() {
		return preloadLanguages;
	}

	public void setPreloadLanguages(String preloadLanguages) {
		this.preloadLanguages = preloadLanguages;
	}

	public void preloadNiceClassHeadings() {
		if (logger.isDebugEnabled()) {
			logger.debug("START preloadNiceClassHeadings :" + preloadLanguages);
		}
		
		if (StringUtils.isNotBlank(preloadLanguages) 
				&& StringUtils.isNotBlank(numberOfClasses)) {
			for (int i=1;i<=Integer.valueOf(numberOfClasses);i++) {
				
				for (String language:preloadLanguages.split(",")) {
					goodsServicesService.importNiceClassHeading(Integer.toString(i), language);

				}
			}
		}
		logger.debug("END preloadNiceClassHeadings");
	}

}
