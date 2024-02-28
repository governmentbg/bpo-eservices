/*
 * ReportService:: ReportService 16/10/13 20:16 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */
package eu.ohim.sp.core.report;

import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.TradeMark;

import java.util.List;

/**
 * It has the methods to make the invocations to the ReportService
 * 
 * @author soriama
 * 
 */
public interface ReportService {

    String RECEIPT_REPORT = "receipt";
    String SIMILAR_TRADE_MARK_REPORT = "similarTM";

    byte[] generateReport(String module, String template, String localeCode, Object... args);

    void sendMail(String mail, String subject, String content, List<Document> documentList);

    byte[] generateSimilaritySpreadSheet(String module, List<TradeMark> tmList);
}
