/*
 * ApplicationService:: ApplicationService 14/11/13 15:48 karalch $
 * * . * .
 * * * RRRR * Copyright © 2013 OHIM: Office for Harmonization
 * * . RR R . in the Internal Market (trade marks and designs)
 * * * RRR *
 * * . RR RR . ALL RIGHTS RESERVED
 * * * . _ . *
 */

package eu.ohim.sp.core.application;

import java.util.List;
import java.util.Map;

import eu.ohim.sp.core.domain.application.DraftApplication;
import eu.ohim.sp.core.domain.application.FormatXML;
import eu.ohim.sp.core.domain.application.PrefixPdfKind;
import eu.ohim.sp.core.domain.application.wrapper.ApplicationNumber;
import eu.ohim.sp.core.domain.common.Result;
import eu.ohim.sp.core.domain.person.Applicant;
import eu.ohim.sp.core.domain.person.Representative;
import eu.ohim.sp.core.domain.resources.Document;
import eu.ohim.sp.core.domain.trademark.IPApplication;
import eu.ohim.sp.core.domain.validation.ErrorList;
import eu.ohim.sp.core.domain.validation.RulesInformation;

/**
 * The Application Management business interface ({@code ApplicationService}) provides
 * User Interface modules ({@code TM e-Filing}, {@code DS e-Filing} and {@code e-Services})
 * with methods to manage IP applications and draft IP applications.
 * <p>
 * The reference implementation {@code ApplicationServiceBean} interfaces with the following SP components:
 * <dl>
 * <dt><b>Business:</b>
 * </dl>
 * <ul>
 * <li>{@code ApplicationClient}: persistence of draft applications externally.</li>
 * <li>{@code ApplicantService}: persistence of applicants.</li>
 * <li>{@code RepresentativeService}: persistence of representatives.</li>
 * </ul>
 * <dl>
 * <dt><b>Support:</b>
 * </dl>
 * <ul>
 * <li>{@code ReportService}: generation of application receipts.</li>
 * <li>{@code RulesService}: validation of business rules.</li>
 * </ul>
 * <dl>
 * <dt><b>System:</b>
 * </dl>
 * <ul>
 * <li>{@code ConfigurationService}: retrieval of system configuration parameters.</li>
 * <li>{@code DocumentService}: persistence of files (applications and attachments).</li>
 * </ul>
 * 
 * @see eu.ohim.sp.external.application.ApplicationClient ApplicationClient
 * @see eu.ohim.sp.core.person.ApplicantService ApplicantService
 * @see eu.ohim.sp.core.person.RepresentativeService RepresentativeService
 * @see eu.ohim.sp.core.rules.RulesService RulesService
 * @see eu.ohim.sp.core.report.ReportService ReportService
 * @see eu.ohim.sp.core.configuration.ConfigurationService ConfigurationService
 * @see eu.ohim.sp.core.document.DocumentService DocumentService
 * 
 * @version 4.0.0
 * @since SP Core 1.0.0
 */
public interface ApplicationService {

    /**
     * Creates an entry in the draft application table and initialises the {@code DraftApplication} object, assigning a
     * new filing number ({@code provisionalId}).
     * <p>
     * Interfaces with {@code RulesService}.
     * 
     * @see eu.ohim.sp.core.rules.RulesService#calculate(String, String, List)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * 
     * @return Initialised {@code DraftApplication}.
     */
    DraftApplication initDraftApplication(final String office, final String module, String applicationType);

    /**
     * Updates the draft application table with the new values provided in the input {@code DraftApplication}.
     * <p>
     * The filing number ({@code provisionalId}) is mandatory; other values will be updated only if not null. Null
     * values and the history of statuses ({@code Set<DraftStatus>}) are always ignored.
     * 
     * @param draftApplication {@code DraftApplication} with values to be updated.
     * 
     * @return Updated {@code DraftApplication}.
     */
    DraftApplication updateDraftApplication(DraftApplication draftApplication);

    /**
     * Updates the status of a draft application table with the new values provided in the input
     * {@code DraftApplication}.
     * <p>
     * 
     * @param filingNumber the application to update
     * @param statusCode the status code of the status
     * @param statusDescription the description code of the status
     * 
     * @return Result the result of the update operation
     */

    Result updateDraftApplicationStatus(String filingNumber, String statusCode, String statusDescription);

    /**
     * Retrieves the {@code DraftApplication} corresponding to the filing Number ({@code provisionalId})
     * specified.
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * 
     * @return Persisted {@code DraftApplication}.
     */
    DraftApplication getDraftApplication(String office, String module, String filingNumber);

    /**
     * Retrieves the entire application in {@code ePUB} format, which includes the application data in {@code XML}
     * format and all application attachments.
     * 
     * @see <a href="http://idpf.org/epub" target="_blank">ePUB format</a>
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * 
     * @return {@code ePUB} file containing application and attachments (byte array).
     */
    byte[] getApplication(String office, String module, String applicationType, String filingNumber);

    /**
     * Searches for {@code DraftApplication}(s) that match the specified criteria and returns a list.
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param searchCriteria {@code Map<String, String>} containing search criteria (key - attribute name, value)
     * @param pageIndex Number of results to be skipped
     * @param pageSize Maximum number of results to be retrieved
     * @param sortDirection Sort direction. {@code true} sorts ascendingly (default), while {@code false} sorts
     *            descendingly
     * @param sortProperty Name of the attribute for which sorting is required (optional)
     * 
     * @return List of {@code DraftApplication}(s) (empty list if no matches are found).
     */
    List<DraftApplication> searchDraftApplication(String office, String module, Map<String, String> searchCriteria,
            Integer pageIndex, Integer pageSize, Boolean sortDirection, String sortProperty);

    long getApplicationsCount(String office, String module, Map<String, String> searchCriteria);

    public List<String> getTypeApplicationNames();

    /**
     * Retrieves a final application number ({@code ApplicationNumber}) to be assigned to the application
     * from an external system.
     * <p>
     * Interfaces with {@code ApplicationClient}.
     * 
     * @see eu.ohim.sp.external.application.ApplicationClient#getApplicationNumber(String, String, String)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * 
     * @return New {@code ApplicationNumber} consisting of final application number ({@code String})
     *         and a timestamp ({@code Date}).
     */
    ApplicationNumber getApplicationNumber(String office, String module, String filingNumber, String applicationType);

    /**
     * Publishes a notification that a new application has been filed to the filing queue, so that
     * the Back Office systems can retrieve it from SP Front Office. Interfaces with {@code ApplicationClient}.
     * <p>
     * Interfaces with {@code ApplicationClient}.
     * 
     * @see eu.ohim.sp.external.application.ApplicationClient#notifyApplicationFiling(String, String, DraftApplication)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param draftApplication {@code DraftApplication} filed.
     */
    void notifyApplicationFiling(String office, String module, DraftApplication draftApplication);

    /**
     * Triggers the saving of an application externally (i.e. Office web portal), so it can
     * be used to restore the draft application at a later stage.
     * <p>
     * Interfaces with {@code ApplicationClient}.
     * 
     * @see eu.ohim.sp.external.application.ApplicationClient#saveApplication(String, String, String, boolean)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * @param user Username of the person saving the {@code DraftApplication}.
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * @param finalDraft Flag that indicates whether changes to draft are blocked once it is restored.
     * 
     * @return Result of the operation {@code Result}. Either {@code SUCCESS} or {@code FAILURE} plus {@code errorCode}.
     */
    Result saveApplication(String office, String module, String applicationType, String user, String filingNumber,
            boolean finalDraft);

    /**
     * Triggers the saving of an application externally (i.e. Office web portal), so it can
     * be used to restore the draft application at a later stage.
     * <p>
     * Interfaces with {@code ApplicationClient}.
     * 
     * @see eu.ohim.sp.external.application.ApplicationClient#saveApplication(String, String, String)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * @param user Username of the person saving the {@code DraftApplication}.
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * 
     * @return Result of the operation {@code Result}. Either {@code SUCCESS} or {@code FAILURE} plus {@code errorCode}.
     */
    Result saveApplication(String office, String module, String applicationType, String user, String filingNumber);

    /**
     * Loads an IP Application ({@code TM}, {@code DS} or {@code e-Services}) persisted externally
     * (i.e. Office web portal), so filing can be resumed.
     * <p>
     * The application is retrieved in {@code ePUB} format. First it is unpacked, then attachments are persisted to the
     * document repository and {@code XML} format application is unmarshalled into a new {@code IPApplication} object.
     * <p>
     * Interfaces with {@code ApplicationClient} and {@code DocumentService}.
     * 
     * @see <a href="http://idpf.org/epub" target="_blank">ePUB format</a>
     * @see eu.ohim.sp.external.application.ApplicationClient#loadApplication(String, String, String)
     * @see eu.ohim.sp.core.document.DocumentService#unarchiveDocuments(Document, String, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#saveDocument(Document)
     * @see eu.ohim.sp.core.document.DocumentService#searchDocument(Map, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#deleteDocument(Document)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param user Username of the person restoring the {@code IPApplication}.
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * 
     * @return Loaded {@code IPApplication} (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     *         {@code DesignApplication} or {@code DSeServiceApplication}).
     */
    IPApplication loadApplication(String office, String module, String user, String applicationType, String filingNumber);

    /**
     * Retrieves an {@code IPApplication} object from the document repository.
     * <p>
     * If the retrieved application is in {@code ePUB} format, first it is unpacked, and only the {@code XML} format
     * application is unmarshalled into a new {@code IPApplication} object.
     * <p>
     * Interfaces with {@code DocumentService}.
     * 
     * @see <a href="http://idpf.org/epub" target="_blank">ePUB format</a>
     * @see eu.ohim.sp.core.document.DocumentService#searchDocument(Map, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#getContent(String)
     * @see eu.ohim.sp.core.document.DocumentService#unarchiveDocuments(Document, String, boolean)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param format Format in which the application was persisted in the document repository (i.e.
     *            <i>APPLICATION_XML</i>).
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * 
     * @return {@code IPApplication} (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     *         {@code DesignApplication} or {@code DSeServiceApplication}).
     */
    IPApplication retrieveApplication(String office, String module, FormatXML format, String filingNumber);

    /**
     * Generates the application ({@code IPApplication}) receipt in {@code PDF} format and persists the updated
     * application ({@code XML}+attachments) to the document repository.
     * <p>
     * Interfaces with {@code ReportService} and {@code DocumentService}.
     * 
     * @see eu.ohim.sp.core.report.ReportService#generateReport(String, String, String, Object...)
     * @see eu.ohim.sp.core.document.DocumentService#saveDocument(Document)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param application Application ({@code IPApplication}) to generate the report to.
     * 
     * @return {@code IPApplication} (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     *         {@code DesignApplication} or {@code DSeServiceApplication}), containing the generated
     *         application receipt.
     */
    IPApplication generateReceipts(String office, String module, IPApplication application);

    /**
     * Generates the application ({@code IPApplication}) receipt(s) in {@code PDF} format, adding a
     * suffix to the name of the report (whether saving to portal or submitting the application),
     * and persists the updated application ({@code XML}+attachments) to the document repository.
     * <p>
     * Interfaces with {@code ReportService} and {@code DocumentService}.
     * 
     * @see eu.ohim.sp.core.report.ReportService#generateReport(String, String, String, Object...)
     * @see eu.ohim.sp.core.document.DocumentService#saveDocument(Document)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param application Application ({@code IPApplication}) to generate the report to.
     * @param prefixPdfKind Prefix kind to be added to the {@code PDF} name.
     * 
     * @return {@code IPApplication} (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     *         {@code DesignApplication} or {@code DSeServiceApplication}), containing the generated
     *         application receipt.
     */
    IPApplication generateReceipts(String office, String module, IPApplication application, PrefixPdfKind prefixPdfKind);

    /**
     * Checks whether a form application ({@code e-Services}) already exists for a given IP
     * application/registration.
     * <p>
     * Interfaces with {@code ApplicationClient}.
     * 
     * @see eu.ohim.sp.external.application.ApplicationClient#checkExistingApplication(String, String, String, String)
     * 
     * @param applicationType Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * @param formName Type of form (i.e. <i>TM_RENEWAL</i>).
     * @param applicationNumber IP application number ({@code applicationNumber}) over which previous
     *            application should exist.
     * @param registrationNumber IP registration number over which previous application should exist.
     * 
     * @return {@code Boolean}. Either {@code TRUE} if application exists or {@code FALSE}.
     */
    Boolean checkExistingApplication(String applicationType, String formName, String applicationNumber,
            String registrationNumber);

    /**
     * Imports a provided IP Application ({@code TM}, {@code DS} or {@code e-Services}). It is unmarshalled
     * into a new {@code IPApplication} object with a new filing number ({@code provisionalId}).
     * <p>
     * If the application is provided in {@code ePUB} format, it is first unpacked and attachments are persisted to the
     * document repository.
     * <p>
     * Interfaces with {@code RulesService} and {@code DocumentService}.
     * 
     * @see <a href="http://idpf.org/epub" target="_blank">ePUB format</a>
     * @see #initDraftApplication(String, String, String)
     * @see #persistApplication(String, String, FormatXML, IPApplication)
     * @see eu.ohim.sp.core.document.DocumentService#unarchiveDocuments(Document, String, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#searchDocument(Map, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#deleteDocument(Document)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param data File (byte array) being imported (either {@code XML} or {@code ePUB} format).
     * @param typeApplication Type of application (i.e. <i>TRADEMARK_APPLICATION</i>).
     * @param format Format in which the application was persisted in the document repository (i.e.
     *            <i>APPLICATION_XML</i>).
     * 
     * @return Loaded {@code IPApplication} (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     *         {@code DesignApplication} or {@code DSeServiceApplication}).
     */
    IPApplication importApplication(String office, String module, byte[] data, String typeApplication, FormatXML format);

    /**
     * Exports an IP Application (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     * {@code DesignApplication} or {@code DSeServiceApplication}). It is marshalled into a {@code XML} file
     * of either TM or DS schema.
     * <p>
     * If the format requested is {@code ePUB}, application and its attachments are packed.
     * <p>
     * Interfaces with {@code DocumentService}.
     * 
     * @see <a href="http://idpf.org/epub" target="_blank">ePUB format</a>
     * @see eu.ohim.sp.core.document.DocumentService#searchDocument(Map, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#getContent(String)
     * @see eu.ohim.sp.core.document.DocumentService#archiveDocuments(Map, String, boolean)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param format Format in which the application was persisted in the document repository (i.e.
     *            <i>APPLICATION_XML</i>).
     * @param filingNumber Provisional filing number ({@code provisionalId}) assigned to application.
     * 
     * @return File (byte array) exported (either {@code XML} or {@code ePUB} format).
     */
    byte[] exportApplication(String office, String module, FormatXML format, String filingNumber);

    /**
     * Persists a provided {@code IPApplication} ({@code TM}, {@code DS} or {@code e-Services}) to the document
     * repository. First, it is marshalled into a {@code XML} file of either TM or DS schema.
     * <p>
     * Interfaces with {@code DocumentService}.
     * 
     * @see eu.ohim.sp.core.document.DocumentService#searchDocument(Map, boolean)
     * @see eu.ohim.sp.core.document.DocumentService#deleteDocument(Document)
     * @see eu.ohim.sp.core.document.DocumentService#getContent(String)
     * @see eu.ohim.sp.core.document.DocumentService#saveDocument(Document)
     * @see eu.ohim.sp.core.document.DocumentService#updateDocument(Document)
     *
	 * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
	 * @param module User interface module (i.e. <i>TM e-Filing</i>).
	 * @param format Format in which the application was persisted in the document repository (i.e.
*            <i>APPLICATION_XML</i>).
	 * @param application Application ({@code IPApplication}) to be persisted.
	 *
	 * @return persisted {@link Document}
	 * */
    Document persistApplication(String office, String module, FormatXML format, IPApplication application);

    /**
     * Validates a provided {@code IPApplication} ({@code TM}, {@code DS} or {@code e-Services})
     * according to a set of business rules.
     * <p>
     * Interfaces with {@code RulesService}.
     * 
     * @see eu.ohim.sp.core.rules.RulesService#validate(String, String, List)
     * 
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param application Application ({@code IPApplication}) to be validated.
     * @param rulesInformation Extra information needed to execute validation.
     * 
     * @return List ({@code ErrorList}) containing validation errors and/or warnings.
     */
    ErrorList validateApplication(String module, IPApplication application, RulesInformation rulesInformation);

    /**
     * Manages the process of filing an application ({@code TM}, {@code DS} or {@code e-Services}):
     * <p>
     * <ol>
     * <li>If enabled, saves the persons (applicants, representatives);</li>
     * <li>If enabled, retrieves the final application number externally;</li>
     * <li>Generates the PDF receipt(s);</li>
     * <li>Persists the final application in {@code ePUB} format;</li>
     * <li>Publishes a notification of a new application in the filing queue;</li>
     * <li>Updates entry in the draft application table;</li>
     * </ol>
     * <p>
     * Interfaces with {@code ApplicantService}, {@code RepresentativeService}, {@code ApplicationClient},
     * {@code ReportService} and {@code DocumentService}
     * 
     * @see <a href="http://idpf.org/epub" target="_blank">ePUB format</a>
     * @see eu.ohim.sp.core.person.ApplicantService#saveApplicant(String, String, String, Applicant)
     * @see eu.ohim.sp.core.person.RepresentativeService#saveRepresentative(String, String, String, Representative)
     * @see #getApplicationNumber(String, String, String, String)
     * @see #persistApplication(String, String, FormatXML, IPApplication)
     * @see #fillApplicationDocuments(IPApplication)
     * @see #generateReceipts(String, String, IPApplication, PrefixPdfKind)
     * @see #notifyApplicationFiling(String, String, DraftApplication)
     * 
     * @param office IP Office deploying SP (i.e. <i>OHIM</i>).
     * @param module User interface module (i.e. <i>TM e-Filing</i>).
     * @param tradeMarkApplication Application ({@code IPApplication}) to be filed.
     * 
     * @return Filed {@code IPApplication} (either {@code TradeMarkApplication}, {@code TMeServiceApplication},
     *         {@code DesignApplication} or {@code DSeServiceApplication}), including filing date.
     */
    IPApplication fileApplication(String office, String module, IPApplication tradeMarkApplication);

    /**
     * Fills {@code IPApplication} documents {@code data} attribute with byte array retrieved
     * from the document repository.
     * <p>
     * Interfaces with {@code DocumentService}.
     * 
     * @see eu.ohim.sp.core.document.DocumentService#getDocument(String)
     * 
     * @param iPApplication Application ({@code IPApplication}) to be validated.
     * 
     * @return {@code IPApplication} containing attachments contents (byte arrays).
     */
    IPApplication fillApplicationDocuments(IPApplication iPApplication);


    /**
     * Fills {@code IPApplication} documents {@code data} attribute with byte array retrieved
     * from the document repository or by fetching data from URI provided. If saveUnsavedDocuments == true
     * then save the documents that have only URI in the document repository.
     * <p>
     * Interfaces with {@code DocumentService}.
     *
     * @see eu.ohim.sp.core.document.DocumentService#getDocument(String)
     *
     * @param iPApplication Application ({@code IPApplication}) to be validated.
     * @param saveUnsavedDocuments whether to save or not the documents that are not saved in documentRepository
     *
     * @return {@code IPApplication} containing attachments contents (byte arrays).
     */
    IPApplication fillApplicationDocuments(IPApplication iPApplication, boolean saveUnsavedDocuments);

}
