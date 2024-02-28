/*
 *  FspDomain:: MapperFactory 15/11/13 17:04 karalch $
 *  * . * .
 *  * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 *  * . RR R . in the Internal Market (trade marks and designs)
 *  * * RRR *
 *  * . RR RR . ALL RIGHTS RESERVED
 *  * * . _ . *
 */

package eu.ohim.sp.filing.domain.utils;

import eu.ohim.sp.core.domain.design.DSeServiceApplication;
import eu.ohim.sp.core.domain.design.DesignApplication;
import eu.ohim.sp.core.domain.other.OLeServiceApplication;
import eu.ohim.sp.core.domain.patent.PTeServiceApplication;
import eu.ohim.sp.core.domain.patent.PatentApplication;
import eu.ohim.sp.core.domain.trademark.TMeServiceApplication;
import eu.ohim.sp.external.application.TransactionUtil;
import eu.ohim.sp.external.application.qualifier.Design;
import eu.ohim.sp.external.application.qualifier.Eservice;
import eu.ohim.sp.external.application.qualifier.Patent;
import eu.ohim.sp.external.application.qualifier.TradeMark;
import eu.ohim.sp.core.domain.trademark.TradeMarkApplication;
import eu.ohim.sp.filing.domain.tm.Document;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import javax.enterprise.inject.Produces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: karalch
 * Date: 16/08/13
 * Time: 09:53
 * To change this template use File | Settings | File Templates.
 */
public class MapperFactory {

    public static final String GLOBAL_TM_CONFIGURATION = "dozer/tm/globalConfigurationMapping.xml";
    public static final String GLOBAL_TM_CONFIGURATION_NO_ATTACH = "dozer/tm/globalConfigurationWithoutAttachmentsMapping.xml";
    public static final String GLOBAL_DS_CONFIGURATION = "dozer/ds/globalConfigurationMapping.xml";
    public static final String GLOBAL_DS_CONFIGURATION_NO_ATTACH = "dozer/ds/globalConfigurationWithoutAttachmentsMapping.xml";
    static final String GLOBAL_PT_CONFIGURATION = "dozer/pt/globalConfigurationMapping.xml";
    static final String GLOBAL_OL_CONFIGURATION = "dozer/ol/globalConfigurationMapping.xml";
    public static final String GLOBAL_PT_CONFIGURATION_NO_ATTACH = "dozer/pt/globalConfigurationWithoutAttachmentsMapping.xml";
    public static final String GLOBAL_OL_CONFIGURATION_NO_ATTACH = "dozer/ol/globalConfigurationWithoutAttachmentsMapping.xml";
    public static final String MAPPING_TM = "dozer/tm/dozerMapping.xml";
    public static final String MAPPING_DS =  "dozer/ds/dozerMapping.xml";
    public static final String MAPPING_DS_ES =  "dozer/ds/dozerMapping-eServices.xml";
    public static final String MAPPING_PT =  "dozer/pt/dozerMapping.xml";
    public static final String MAPPING_OL =  "dozer/ol/dozerMapping.xml";

    /**
     * Injects the mapper depending on the qualifier
     * @return a transaction util
     */
    @Produces
    @TradeMark
    public TransactionUtil generateTradeMarkTU() {
        return getTradeMark();
    }

    /**
     * Injects the mapper depending on the qualifier
     * @return a transaction util
     */
    @Produces
    @Eservice
    public TransactionUtil generateEservicesTU() {
        return new EserviceTransactionUtil(getTMEservice(), getDSEservice(), getPTEservice(), getOLEservice());
    }

    /**
     * Injects the mapper depending on the qualifier
     * @return a transaction util
     */
    @Produces
    @Design
    public TransactionUtil generateDesignTU() {
        return getDesign();
    }

    /**
     * Injects the mapper depending on the qualifier
     * @return a transaction util
     */
    @Produces
    @Patent
    public TransactionUtil generatePatentTU() {
        return getPatent();
    }



    /**
     * Generates a transaction util for {@see TradeMarkApplication}
     * with the provided mapping on the classpath
     * @return {@see TradeMarkApplication}
     */
    public static FOTransactionUtil<TradeMarkApplication, eu.ohim.sp.filing.domain.tm.Transaction> getTradeMark() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_TM_CONFIGURATION);
        myMappingFiles.add(MAPPING_TM);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_TM_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_TM);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.tm.Transaction, Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.tm.Transaction, Document>(Document.class);

        return new FOTransactionUtil<TradeMarkApplication, eu.ohim.sp.filing.domain.tm.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.tm.Transaction.class, TradeMarkApplication.class, null, mappers);
    }


    /**
     * Generates a transaction util for {@see DesignApplication}
     * with the provided mapping on the classpath
     * @return {@see DesignApplication}
     */
    public static FOTransactionUtil<DesignApplication, eu.ohim.sp.filing.domain.ds.Transaction> getDesign() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_DS_CONFIGURATION);
        myMappingFiles.add(MAPPING_DS);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_DS_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_DS);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.ds.Transaction, eu.ohim.sp.filing.domain.ds.Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.ds.Transaction, eu.ohim.sp.filing.domain.ds.Document>(eu.ohim.sp.filing.domain.ds.Document.class);

        return new FOTransactionUtil<DesignApplication, eu.ohim.sp.filing.domain.ds.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.ds.Transaction.class, DesignApplication.class, null, mappers);
    }

    /**
     * Generates a transaction util for {@see TMeServiceApplication}
     * with the provided mapping on the classpath
     * @return {@see TMeServiceApplication}
     */
    public static FOTransactionUtil<TMeServiceApplication, eu.ohim.sp.filing.domain.tm.Transaction> getTMEservice() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_TM_CONFIGURATION);
        myMappingFiles.add(MAPPING_TM);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_TM_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_TM);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.tm.Transaction, Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.tm.Transaction, Document>(Document.class);

        return new FOTransactionUtil<TMeServiceApplication, eu.ohim.sp.filing.domain.tm.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.tm.Transaction.class, TMeServiceApplication.class, null, mappers);
    }

    /**
     * Generates a transaction util for {@see DSeServiceApplication}
     * with the provided mapping on the classpath
     * @return {@see TMeServiceApplication}
     */
    public static FOTransactionUtil<DSeServiceApplication, eu.ohim.sp.filing.domain.ds.Transaction> getDSEservice() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_DS_CONFIGURATION);
        myMappingFiles.add(MAPPING_DS);
        myMappingFiles.add(MAPPING_DS_ES);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_DS_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_DS);
        myMappingFiles.add(MAPPING_DS_ES);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.ds.Transaction, eu.ohim.sp.filing.domain.ds.Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.ds.Transaction, eu.ohim.sp.filing.domain.ds.Document>(eu.ohim.sp.filing.domain.ds.Document.class);

        return new FOTransactionUtil<DSeServiceApplication, eu.ohim.sp.filing.domain.ds.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.ds.Transaction.class, DSeServiceApplication.class, null, mappers);
    }

    /**
     * Generates a transaction util for {@see PTeServiceApplication}
     * with the provided mapping on the classpath
     * @return {@see PTeServiceApplication}
     */
    public static FOTransactionUtil<PTeServiceApplication, eu.ohim.sp.filing.domain.pt.Transaction> getPTEservice() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_PT_CONFIGURATION);
        myMappingFiles.add(MAPPING_PT);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_PT_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_PT);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.pt.Transaction, eu.ohim.sp.filing.domain.pt.Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.pt.Transaction, eu.ohim.sp.filing.domain.pt.Document>(eu.ohim.sp.filing.domain.pt.Document.class);

        return new FOTransactionUtil<PTeServiceApplication, eu.ohim.sp.filing.domain.pt.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.pt.Transaction.class, PTeServiceApplication.class, null, mappers);
    }

    /**
     * Generates a transaction util for {@see OLeServiceApplication}
     * with the provided mapping on the classpath
     * @return {@see OLeServiceApplication}
     */
    public static FOTransactionUtil<OLeServiceApplication, eu.ohim.sp.filing.domain.ol.Transaction> getOLEservice() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_OL_CONFIGURATION);
        myMappingFiles.add(MAPPING_OL);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_OL_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_OL);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.ol.Transaction, eu.ohim.sp.filing.domain.ol.Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.ol.Transaction, eu.ohim.sp.filing.domain.ol.Document>(eu.ohim.sp.filing.domain.ol.Document.class);

        return new FOTransactionUtil<OLeServiceApplication, eu.ohim.sp.filing.domain.ol.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.ol.Transaction.class, OLeServiceApplication.class, null, mappers);
    }

    /**
     * Generates a transaction util for {@see PatentApplication}
     * with the provided mapping on the classpath
     * @return {@see PatentApplication}
     */
    public static FOTransactionUtil<PatentApplication, eu.ohim.sp.filing.domain.pt.Transaction> getPatent() {
        Map<String, Mapper> mappers = new HashMap<String, Mapper>();

        List<String> myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_PT_CONFIGURATION);
        myMappingFiles.add(MAPPING_PT);

        DozerBeanMapper dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.MAPPED_DOCUMENTS, dozerBeanMapper);

        myMappingFiles = new ArrayList<String>();
        myMappingFiles.add(GLOBAL_PT_CONFIGURATION_NO_ATTACH);
        myMappingFiles.add(MAPPING_PT);

        dozerBeanMapper = new DozerBeanMapper();
        dozerBeanMapper.setMappingFiles(myMappingFiles);

        mappers.put(FOTransactionUtil.NOT_MAPPED_DOCUMENTS, dozerBeanMapper);

        ReferenceUtil<eu.ohim.sp.filing.domain.pt.Transaction, eu.ohim.sp.filing.domain.pt.Document> referenceUtil = new ReferenceUtil<eu.ohim.sp.filing.domain.pt.Transaction, eu.ohim.sp.filing.domain.pt.Document>(eu.ohim.sp.filing.domain.pt.Document.class);

        return new FOTransactionUtil<PatentApplication, eu.ohim.sp.filing.domain.pt.Transaction>(referenceUtil, eu.ohim.sp.filing.domain.pt.Transaction.class, PatentApplication.class, null, mappers);
    }


}
