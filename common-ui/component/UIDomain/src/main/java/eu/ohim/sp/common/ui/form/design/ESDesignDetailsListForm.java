package eu.ohim.sp.common.ui.form.design;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: Raya
 * Date: 13.05.2021
 * Time: 13:50
 */
public class ESDesignDetailsListForm implements Serializable {

    public ESDesignDetailsListForm(List<ESDesignDetailsForm> designDetailsFormList) {
        this.designDetailsFormList = designDetailsFormList;

        if(designDetailsFormList != null && designDetailsFormList.size()>0){
            this.listApplications = designDetailsFormList.stream().map(ds -> ds.geteSDesignApplicationData().getApplicationNumber()).collect(Collectors.toSet());
        }
    }

    private Set<String> listApplications;

    private List<ESDesignDetailsForm> designDetailsFormList;

    public List<ESDesignDetailsForm> getDesignDetailsFormList() {
        return designDetailsFormList;
    }

    public Set<String> getListApplications() {
        return listApplications;
    }

    public boolean hasMultipleApplications(){
        return listApplications != null && listApplications.size()>1;
    }

    public boolean containsApplications(){
        return listApplications != null && listApplications.size()>0;
    }
}
