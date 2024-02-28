package eu.ohim.sp.dsefiling.ui.util;

public final class DSEfilingConstants {

	private DSEfilingConstants() {}

    public static final String MODULE                       = "dsefiling";

    //signature
    public static final String SG_FM                        = "signatureForm";
    public static final String SG_SM                        = "signature/signature_multiple";
    public static final String SG_IW                        = "signature/signature_import_warning";
    public static final String SG_CL                        = "signature/signature_card_list";
    
    //headers
    public static final String HEADERS_ACC                  = "Accept=application/json";
    
    //produces
    public static final String PRODUCES_APPJSON             = "application/json; charset=utf-8";
    public static final String REQUEST_CONSUMES_JSON        = "application/json; charset=utf-8";
    
    //results
    public static final String SUCCESS                      = "success";
    
    //errors
    public static final String ERROR_GE                     = "error.genericError";
    public static final String ERROR_CODE                   = "errorCode";
    public static final String IMPORT_ERROR_VIEW_NAME       = "errors/importError";

    //Locarno
    public static final String DS_LOCARNO_ALL_CLASSES       = "design.form.classes.locarno.modal.selectAllClasses";
    public static final String DS_LOCARNO_ALL_SUBCLASSES    = "design.form.classes.locarno.modal.selectAllSubclasses";
    public static final String MODEL_LOCARNO_FORM           = "locarnoForm";
    public static final String MODEL_LOCARNO_SEARCH_FORM    = "locarnoSearchForm";
    public static final String MODEL_LOCARNO_COMPLEX_FORM   = "locarnoComplexForm";
    public static final String VIEW_LOCARNO_TABLE           = "locarno/dsclass/locarno_Table";
    //public static final String VIEW_LOCARNO_ADD_PI          = "locarno/dsclass/locarno_addPI";
    public static final String VIEW_LOCARNO_MODAL           = "locarno/dsclass/locarno_Modal";
    public static final String VIEW_LOCARNO_LIST            = "locarno/locarno_list";
    public static final String VIEW_LOCARNO_ADD_CLASS       = "locarno/locarno_addClass";
    public static final String VIEW_LOCARNO_LIST_CLASS      = "locarno/locarno_listClass";
    public static final String VIEW_LOCARNO_ADD_NEW_PRODUCT = "locarno/locarno_addNewProduct";
    public static final String VIEW_LOCARNO_ADD_NEW_COMPLEX_PRODUCT = "locarno/locarno_addNewComplexProduct";

    //others
    public static final String MIME_IMAGE_PNG               = "image/png";
    public static final String EarlierEM                    = "earlierEntitleMent";
    public static final String FORMEDIT                     = "formEdit";
    public static final String GROUND_CATEGORY              = "groundCategory";
    public static final String IMPORTACT                    = "importAction";
    public static final String LANGID                       = "langId";
    public static final String TERM                         = "term";
    public static final String CLASSID                      = "classId";
    public static final String SECT_NM                      = "sectionName";
    public static final String APP_EX_PT                    = "applicationExistInPortal";

    //logs
    public static final String CONST_LANG                   = "Language : ";
    public static final String CONST_CLAS                   = ", Class = ";
    public static final String CONST_TERM                   = ", Term = ";
    
}
