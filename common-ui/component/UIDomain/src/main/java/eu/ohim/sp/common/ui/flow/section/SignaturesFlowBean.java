package eu.ohim.sp.common.ui.flow.section;

import eu.ohim.sp.common.ui.form.application.SignatureForm;

import java.util.List;

public interface SignaturesFlowBean {

    List<SignatureForm> getSignatures();

    void setSignatures(List<SignatureForm> signatures);
}
