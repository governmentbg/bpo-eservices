package eu.ohim.sp.ptefiling.ui.service.interfaces;

import eu.ohim.sp.common.ui.form.patent.PatentApplicationKind;
import eu.ohim.sp.ptefiling.ui.domain.PTFlowBean;

/**
 * Created by Raya
 * 18.06.2019
 */
public interface PTImportServiceInterface {

    PTFlowBean getPatentDivisional(PTFlowBean originalFlowBean, String patentId);

    PTFlowBean getUMDivisional(PTFlowBean originalFlowBean, String modelId);

    PTFlowBean getSVDivisional(PTFlowBean originalFlowBean, String modelId);

    PTFlowBean getPatentTransformation(PTFlowBean originalFlowBean, String patentId);

    PTFlowBean getPatentConversion(PTFlowBean originalFlowBean, String euPatentId);

    PTFlowBean getPatentNationalParallel(PTFlowBean originalFlowBean, String patentId);

    PTFlowBean getPatentEUParallel(PTFlowBean originalFlowBean, String euPatentId);

    PTFlowBean getPatentTemplateImport(PTFlowBean originalFlowBean, String patentId, String patentIdType, Boolean forceReimport);

    PTFlowBean clearPatentTemplateImportedApplicants(PTFlowBean originalFlowBean);
}
