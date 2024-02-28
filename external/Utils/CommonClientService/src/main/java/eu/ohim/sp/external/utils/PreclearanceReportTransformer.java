package eu.ohim.sp.external.utils;

import eu.ohim.sp.external.domain.classification.ClassDescription;
import eu.ohim.sp.external.domain.trademark.TradeMark;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;

public  class PreclearanceReportTransformer {

	/** The Constant TRADEMARK_NAME. */
	public static final String TRADEMARK_NAME ="trademarkName";
	
	/** The Constant NICE_CLASSES. */
	public static final String NICE_CLASSES ="niceClasses";
	
	/** The Constant APPLICANT_NAME. */
	public static final String APPLICANT_NAME ="applicantName";
	
	/** The Constant FORMAT. */
	public static final String FORMAT ="format";
	
	  /** The Constant logger. */
	private static final Logger logger = Logger
				.getLogger(PreclearanceReportTransformer.class);
	

	public static Object doTransform(Object src, String encoding) {
		
		TradeMark trademark= (TradeMark) src;
		
		
		Map<String, Object> attributesURL = new HashMap<String, Object>();
		
		if (trademark != null && trademark.getWordSpecifications()!=null
				&& trademark.getWordSpecifications().size()>0) {
			
			String trademarkName = trademark.getWordSpecifications().get(0).getWordElements();
			
			//Get by default the first one, TBC if we need to do different handling
			attributesURL.put(TRADEMARK_NAME, trademarkName);

			StringBuffer niceClasses = new StringBuffer("");
			String niceClassesStr = "";
			if (trademark.getClassDescriptions() != null && trademark.getClassDescriptions().size() > 0) {
				int i = 0;
				int size = trademark.getClassDescriptions().size();
				for (ClassDescription goodsServices : trademark.getClassDescriptions()) {
					if (StringUtils.isNotEmpty(goodsServices.getClassNumber())) {
						niceClasses.append(goodsServices.getClassNumber());
						if (i < size - 1) {
							niceClasses.append(",");
						}
					}
					i++;
				}
				if (StringUtils.isNotEmpty(niceClasses.toString())) {
					niceClassesStr = niceClasses.toString();
					if (niceClassesStr.endsWith(",")) {
						niceClassesStr = niceClassesStr.substring(0, niceClassesStr.length() - 1);
					}
				}
			}

			attributesURL.put(NICE_CLASSES, niceClassesStr);
	        attributesURL.put(FORMAT, "xml-index");
		}
		return attributesURL;	
	}
}