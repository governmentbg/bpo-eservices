package eu.ohim.sp.eservices.ui.utilTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import eu.ohim.sp.common.ui.form.classification.GoodAndServiceForm;
import eu.ohim.sp.common.ui.form.classification.TaxonomyConceptNodeTreeView;
import eu.ohim.sp.common.ui.form.classification.TermForm;
import eu.ohim.sp.common.ui.form.validation.ErrorType;
import eu.ohim.sp.core.domain.classification.MatchedTerm;
import eu.ohim.sp.core.domain.classification.wrapper.TaxonomyConceptNode;
import eu.ohim.sp.eservices.ui.domain.ESFlowBean;
import eu.ohim.sp.eservices.ui.util.TaxonomyUtil;


public class TaxonomyUtilTest {
	
	@Test
	public void generateTermJSONNull(){
		ESFlowBean flowBean=Mockito.mock(ESFlowBean.class); 
		String langId="EN"; 
		boolean containsAllNiceClassHeading=true; 
		boolean disabledRemoval=true;
		Assert.assertEquals(0,TaxonomyUtil.generateTermJSON(null, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
		
		GoodAndServiceForm good=Mockito.mock(GoodAndServiceForm.class); 
		Mockito.when(good.getLangId()).thenReturn(null);
		Assert.assertEquals(0,TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
		
		Mockito.when(good.getLangId()).thenReturn(langId);
		Mockito.when(flowBean.getSecLang()).thenReturn("");
		Assert.assertEquals(1,TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
	}
	
	
	@Test
	public void generateTermJSON1(){
		GoodAndServiceForm good=Mockito.mock(GoodAndServiceForm.class); 
		ESFlowBean flowBean=Mockito.mock(ESFlowBean.class); 
		String langId="EN"; 
		boolean containsAllNiceClassHeading=true; 
		boolean disabledRemoval=true;
		
		Mockito.when(good.getLangId()).thenReturn(langId);
		Mockito.when(flowBean.getSecLang()).thenReturn(langId);
		
		TermForm termForm=Mockito.mock(TermForm.class);
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(termForm);
		Mockito.when(good.getTermForms()).thenReturn(new HashSet<TermForm>());
		
		GoodAndServiceForm goodAux=Mockito.mock(GoodAndServiceForm.class);
		Mockito.when(goodAux.getTermForms()).thenReturn(setterms);
		Mockito.when(flowBean.getGoodsAndService(Mockito.anyString(), Mockito.anyString())).thenReturn(goodAux);
		
		Assert.assertEquals(1,TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
	}
	
	@Test
	public void generateTermJSON2(){
		GoodAndServiceForm good=Mockito.mock(GoodAndServiceForm.class); 
		ESFlowBean flowBean=Mockito.mock(ESFlowBean.class); 
		String langId="EN"; 
		boolean containsAllNiceClassHeading=true; 
		boolean disabledRemoval=true;
		
		Mockito.when(good.getLangId()).thenReturn(langId);
		Mockito.when(flowBean.getSecLang()).thenReturn(langId);
		
		TermForm termForm=Mockito.mock(TermForm.class);
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(termForm);
		Mockito.when(good.getTermForms()).thenReturn(setterms);
		Mockito.when(flowBean.getGoodsAndService(Mockito.anyString(), Mockito.anyString())).thenReturn(good);
		
		Assert.assertEquals(1,TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
	}
	
	@Test
	public void generateTermJSON3(){
		GoodAndServiceForm good=Mockito.mock(GoodAndServiceForm.class); 
		ESFlowBean flowBean=Mockito.mock(ESFlowBean.class); 
		String langId="EN"; 
		boolean containsAllNiceClassHeading=true; 
		boolean disabledRemoval=true;
		
		Mockito.when(good.getLangId()).thenReturn(langId);
		Mockito.when(flowBean.getSecLang()).thenReturn(langId);
		
		ErrorType errorType=Mockito.mock(ErrorType.class);
		Mockito.when(errorType.getVerificationAssessment()).thenReturn(ErrorType.NOT_FOUND);
		Mockito.when(errorType.getMatchedTerms()).thenReturn(new ArrayList<MatchedTerm>());
		TermForm termForm=Mockito.mock(TermForm.class);
		Mockito.when(termForm.getError()).thenReturn(errorType);
		Set<TermForm> setterms=new HashSet<TermForm>();
		setterms.add(termForm);
		Mockito.when(good.getTermForms()).thenReturn(setterms);
		Mockito.when(flowBean.getGoodsAndService(Mockito.anyString(), Mockito.anyString())).thenReturn(good);
		
		Assert.assertEquals(1,TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
		
		Mockito.when(errorType.getVerificationAssessment()).thenReturn(ErrorType.REJECTED_TERM);
		Mockito.when(errorType.getMatchedTerms()).thenReturn(null);
		Assert.assertEquals(1,TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval).size());
	}
	
	@Test
	public void getTaxonomyConceptNodes(){
		Collection<TaxonomyConceptNode> originalList = new ArrayList<TaxonomyConceptNode>();
		String taxoConceptNodeId="0";
		
		TaxonomyConceptNode taxonomyConceptNode0=Mockito.mock(TaxonomyConceptNode.class);
		Mockito.when(taxonomyConceptNode0.getParentId()).thenReturn(taxoConceptNodeId);
		Mockito.when(taxonomyConceptNode0.getLevel()).thenReturn(0);
		originalList.add(taxonomyConceptNode0);
		TaxonomyConceptNode taxonomyConceptNode1=Mockito.mock(TaxonomyConceptNode.class);
		Mockito.when(taxonomyConceptNode1.getParentId()).thenReturn(taxoConceptNodeId);
		Mockito.when(taxonomyConceptNode1.getLevel()).thenReturn(1);
		originalList.add(taxonomyConceptNode1);
		TaxonomyConceptNode taxonomyConceptNode2=Mockito.mock(TaxonomyConceptNode.class);
		Mockito.when(taxonomyConceptNode2.getParentId()).thenReturn(taxoConceptNodeId);
		Mockito.when(taxonomyConceptNode2.getLevel()).thenReturn(2);
		originalList.add(taxonomyConceptNode2);
		
		TaxonomyUtil.getTaxonomyConceptNodes(originalList, taxoConceptNodeId);
	}

	
	String taxoConceptNodeId;
	
	@Test
	public void getTaxonomyConceptNodes2()
	{
		 TaxonomyConceptNode originalList=new TaxonomyConceptNode();
		 originalList.setId("1");	
		 ArrayList<TaxonomyConceptNode> arrayList=new ArrayList<TaxonomyConceptNode>();
		 arrayList.add(originalList);
		 TaxonomyUtil.getTaxonomyConceptNodes(arrayList,taxoConceptNodeId);
		 TaxonomyUtil.getTaxonomyConceptNodes(null,taxoConceptNodeId);
	}
	  
	  
	@Test
	public void generateTermJSON(){
		
		GoodAndServiceForm good=new GoodAndServiceForm();  
		TermForm termForm=new TermForm();
		termForm.setError(new ErrorType());
		ArrayList<MatchedTerm> matchedTerms=new ArrayList<MatchedTerm>();
		MatchedTerm matchedTerm =new MatchedTerm();
		matchedTerm.setLegacyTermFrequency(3);
		matchedTerm.setId(2);
		matchedTerms.add(matchedTerm);
		matchedTerm.setMatchedClassNumber(3);
		  
		termForm.getError().setMatchedTerms(matchedTerms);
		termForm.getError().setVerificationAssessment("iubuhbuhb");
		good.addTermForm(termForm);
		good.setLangId("1");
		ESFlowBean flowBean=Mockito.mock(ESFlowBean.class);
		  
		  
		String langId="1";
		boolean containsAllNiceClassHeading=true;
		boolean disabledRemoval=true;
		TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval);
		  
		termForm.setError(null);
		TaxonomyUtil.generateTermJSON(good, flowBean, langId, containsAllNiceClassHeading, disabledRemoval);
	  }
	
	  @Test
	  public void limitMinifiedView(){
		  TaxonomyConceptNodeTreeView node = Mockito.mock(TaxonomyConceptNodeTreeView.class);
		  Mockito.when(node.getLevel()).thenReturn(1);
		  Collection<TaxonomyConceptNodeTreeView> taxonomyConcept=new ArrayList<TaxonomyConceptNodeTreeView>();
		  taxonomyConcept.add(node);
		  Integer levelLimit=-1;
		  Integer classScopeLevel=7;
		  TaxonomyUtil.limitMinifiedView(taxonomyConcept, levelLimit, classScopeLevel);
		  
		  levelLimit=7;
		  TaxonomyUtil.limitMinifiedView(taxonomyConcept, levelLimit, classScopeLevel);
	  }

}
