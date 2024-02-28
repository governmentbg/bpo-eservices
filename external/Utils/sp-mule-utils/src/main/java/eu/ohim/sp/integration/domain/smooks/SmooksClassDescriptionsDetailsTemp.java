
package eu.ohim.sp.integration.domain.smooks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import eu.ohim.sp.external.domain.classification.ClassDescription;
import eu.ohim.sp.external.domain.classification.ClassificationTerm;

public class SmooksClassDescriptionsDetailsTemp implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List <SmooksGoodsServicesTermsTemp> classDescription;

    public List<SmooksGoodsServicesTermsTemp> getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(
            List<SmooksGoodsServicesTermsTemp> classDescription) {
        this.classDescription = classDescription;
    }

    public List<ClassDescription> getClassDescriptionsDecode(){
        List<ClassDescription> toReturn = new ArrayList<ClassDescription>();
        if (classDescription!=null && classDescription.size()>0){
            for (int i=0;i<classDescription.size();i++){

                String classNumber = classDescription.get(i).getClassNumber();
                if (classDescription.get(i).getGoodsServicesDescription()!=null && classDescription.get(i).getGoodsServicesDescription().size()>0){
                    for (SmooksGoodsServicesDescriptionTemp gSCDDetails : classDescription.get(i).getGoodsServicesDescription()){
                        ClassDescription classDescriptionReturn = new ClassDescription();
                        classDescriptionReturn.setClassNumber(classNumber);
                        classDescriptionReturn.setLanguage(gSCDDetails.getLanguageCode());
                        classDescriptionReturn.setClassificationTerms(decode(gSCDDetails.getValue()));
                        toReturn.add(classDescriptionReturn);
                    }
                }
            }
        }
        return toReturn;
    }

    private List<ClassificationTerm> decode(String data) {

        List<ClassificationTerm> toReturn = new ArrayList<ClassificationTerm>();
        if (data!=null && !(data.isEmpty())){
            String[] terms = data.split(";");
            for (int i = 0; i < terms.length; i++) {
                String term = terms[i];
                ClassificationTerm classificationTerm = new ClassificationTerm();
                classificationTerm.setTermText(term);
                toReturn.add(classificationTerm);
            }
        }
        return toReturn;
    }

}
