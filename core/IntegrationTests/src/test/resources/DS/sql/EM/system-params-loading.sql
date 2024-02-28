insert into CCOMPONENT (IdComponent, NmComponent) values (1, 'eu.ohim.sp.tmefiling.ui.form');
insert into CCOMPONENT (IdComponent, NmComponent) values (2, 'eu.ohim.sp.core.person');
insert into CCOMPONENT (IdComponent, NmComponent) values (3, 'eu.ohim.sp.core.rules');
insert into CCOMPONENT (IdComponent, NmComponent) values (4, 'general');

-- Num of chars before autocomplete service is called for searching a mark
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (7, 'The number of characters to be entered before autocomplete service for searching a mark is invoked.', 0, 'minimum.characters.autocomplete.trademark', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (7, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'minimum.characters.autocomplete.trademark'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (7, '3',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'minimum.characters.autocomplete.trademark'), ${valid.from});

-- Num of chars before autocomplete service is called for searching an applicant
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (8, 'The number of characters to be entered before autocomplete service for searching an applicant is invoked', 0, 'service.applicant.autocomplete.minchars', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (8, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.applicant.autocomplete.minchars'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (8, '3',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.applicant.autocomplete.minchars'), ${valid.from});

-- Applicant autocomplete max number of results
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (9, 'Applicant autocomplete max number of results', 0, 'service.applicant.autocomplete.maxResults', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (9, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.autocomplete.maxResults'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (9, '10',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.autocomplete.maxResults'), ${valid.from});

-- Num of chars before autocomplete service is called for searching a representative
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (10, 'The number of characters to be entered before autocomplete service for searching a representative is invoked', 0, 'service.representative.autocomplete.minchars', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (10, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.autocomplete.minchars'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (10, '3',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.autocomplete.minchars'), ${valid.from});

-- Representative autocomplete max number of results
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (11, 'Applicant autocomplete max number of results', 0, 'service.representative.autocomplete.maxResults', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (11, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.autocomplete.maxResults'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (11, '10',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.autocomplete.maxResults'), ${valid.from});

-- Confirmation for importing all the information related to the imported priority
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (12, 'Ask for confirmation for importing all the information related to the imported priority', 0, 'service.import.priority.extraImport', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (12, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.priority.extraImport'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (12, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.priority.extraImport'), ${valid.from});

-- Confirmation for importing all the information related to the imported seniority
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (13, 'Ask for confirmation for importing all the information related to the imported seniority', 0, 'service.import.seniority.extraImport', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (13, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.seniority.extraImport'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (13, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.seniority.extraImport'), ${valid.from});

-- Confirmation for importing all the information related to the imported transformation
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (14, 'Ask for confirmation for importing all the information related to the imported transformation', 0, 'service.import.transformation.extraImport', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (14, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.transformation.extraImport'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (14, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.transformation.extraImport'), ${valid.from});

-- Confirmation for importing all the information related to the imported priority in the wizard mode
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (15, 'Ask for confirmation for importing all the information related to the imported priority in the wizard mode', 0, 'service.import.priority.extraImportWizard', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (15, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.priority.extraImportWizard'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (15, 'false',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.priority.extraImportWizard'), ${valid.from});

-- Confirmation for importing all the information related to the imported seniority in the wizard mode
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (16, 'Ask for confirmation for importing all the information related to the imported seniority in the wizard mode', 0, 'service.import.seniority.extraImportWizard', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (16, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.seniority.extraImportWizard'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (16, 'false',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.seniority.extraImportWizard'), ${valid.from});

-- Confirmation for importing all the information related to the imported transformation in the wizard mode
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (17, 'Ask for confirmation for importing all the information related to the imported transformation in the wizard mode', 0, 'service.import.transformation.extraImportWizard', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (17, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.transformation.extraImportWizard'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (17, 'false',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.import.transformation.extraImportWizard'), ${valid.from});

-- Max num of applicant correspondence addresses
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (18, 'Maximum number of applicant correspondence addresses', 0, 'person.applicant.correspondenceAddresses.max', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (18, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'person.applicant.correspondenceAddresses.max'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (18, '1',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'person.applicant.correspondenceAddresses.max'), ${valid.from});

-- Max num of representative correspondence addresses
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (19, 'Maximum number of representative correspondence addresses', 0, 'person.representative.correspondenceAddresses.max', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (19, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'person.representative.correspondenceAddresses.max'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (19, '1',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'person.representative.correspondenceAddresses.max'), ${valid.from});

-- Is applicant search autocomplete enabled?
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (20, 'Is applicant search autocomplete enabled?', 0, 'service.applicant.autocomplete.enabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (20, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.applicant.autocomplete.enabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (20, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.applicant.autocomplete.enabled'), ${valid.from});

-- URI for applicant search autocomplete
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (21, 'URI for applicant search autocomplete', 0, 'service.applicant.autocomplete.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (21, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.applicant.autocomplete.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (21, 'autocompleteApplicant.htm',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.applicant.autocomplete.url'), ${valid.from});

-- Is trademark search autocomplete enabled?
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (22, 'Is trademark search autocomplete enabled?.', 0, 'service.autocomplete.trademark.exists', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (22, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.autocomplete.trademark.exists'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (22, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.autocomplete.trademark.exists'), ${valid.from});

-- Is representative search autocomplete enabled?
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (23, 'Is representative search autocomplete enabled', 0, 'service.representative.autocomplete.enabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (23, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.autocomplete.enabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (23, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.autocomplete.enabled'), ${valid.from});

-- URI for representative search autocomplete
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (24, 'URI for representative search autocomplete', 0, 'service.representative.autocomplete.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (24, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.autocomplete.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (24, 'autocompleteRepresentative.htm',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.autocomplete.url'), ${valid.from});

-- Checks whether to display Similar Trade mark details button
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (25, 'Checks whether to display Similar Trade mark details button', 0, 'service.similarTm.details.display', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (25, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.similarTm.details.display'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (25, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.similarTm.details.display'), ${valid.from});
	
-- Similar Trade mark details URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (26, 'Similar Trade mark details URI', 0, 'service.similarTm.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (26, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.similarTm.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (26, '${url.display.similar.mark.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.similarTm.details.url'), ${valid.from});

-- Previous CTM search service URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (27, 'Previous CTM search service URI', 0, 'service.previousCtm.search.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (27, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.previousCtm.search.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (27, '${url.search.previous.marks.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.previousCtm.search.url'), ${valid.from});

-- Trade marks search link for Priorities
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (28, 'Trade marks search URI for Priorities', 0, 'service.priorityTM.search.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (28, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.priorityTM.search.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (28, '${url.search.priority.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.priorityTM.search.url'), ${valid.from});

-- Trade marks search link for Seniorities
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (29, 'Trade marks search URI for Seniorities', 0, 'service.seniorityTM.search.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (29, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.seniorityTM.search.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (29, '${url.search.seniority.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.seniorityTM.search.url'), ${valid.from});

-- Trade marks search link for International Transformations
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (30, 'Trade marks search URI for International Transformations', 0, 'service.transformationTM.search.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (30, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.transformationTM.search.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (30, '${url.search.transformation.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.transformationTM.search.url'), ${valid.from});

-- Search link for Applicants
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (31, 'Search URI for Applicants', 0, 'service.applicant.search.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (31, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.search.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (31, '${url.search.applicant.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.search.url'), ${valid.from});

-- Search link for Representatives
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (32, 'Search URI for Representatives', 0, 'service.representative.search.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (32, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.search.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (32, '${url.search.representative.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.search.url'), ${valid.from});

-- Number of nodes that are loaded on the tree the first time that renders in the Goods  Services section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (33, 'Number of nodes that are loaded on the tree (related to Goods and Services) the first time that renders', 0, 'service.goods.limit.taxonomy', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (33, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.goods.limit.taxonomy'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (33, '5',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.goods.limit.taxonomy'), ${valid.from});

-- Number of results of search in terms (related to Goods and Services)
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (34, 'Number of results of search in terms (related to Goods and Services)', 0, 'service.goods.search.limit', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (34, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.goods.search.limit'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (34, '15',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.goods.search.limit'), ${valid.from});

-- Number of classes (related to Goods and Services)
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (35, 'Number of classes (related to Goods and Services)', 0, 'service.goods.classes.number', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (35, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.goods.classes.number'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (35, '45',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.goods.classes.number'), ${valid.from});

-- Classification Service URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (36, 'Classification Service URI', 0, 'euroclass.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (36, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'euroclass.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (36, '${url.classification.external.service}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'euroclass.url'), ${valid.from});

-- Path where the drl files containing the rules for Applicants are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (37, 'The rules for Applicant', 0, 'applicant.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (37, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'applicant.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (37, '${fsp.services.config.dir}/drl/applicant.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'applicant.drl'), ${valid.from});

-- Path where the drl files containing the rules for Trademark are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (38, 'The rules for Trademark', 0, 'trademark.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (38, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'trademark.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (38, '${fsp.services.config.dir}/drl/trademark.drl', (select IdParam from PCONFIGPARAM where NmParam = 'trademark.drl'), ${valid.from});

-- Path where the drl files containing the rules for Claims are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (39, 'The rules for Claim', 0, 'claim.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (39, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'claim.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (39, '${fsp.services.config.dir}/drl/claim.drl', (select IdParam from PCONFIGPARAM where NmParam = 'claim.drl'), ${valid.from});

-- Path where the drl files containing the rules for Representatives are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (40, 'The rules for Representatives', 0, 'representative.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (40, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'representative.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (40, '${fsp.services.config.dir}/drl/representative.drl', (select IdParam from PCONFIGPARAM where NmParam = 'representative.drl'), ${valid.from});

-- Path where the drl files containing the rules for Fees are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (41, 'The rules for Fees', 0, 'fees.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (41, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'fees.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (41, '${fsp.services.config.dir}/drl/fees.drl', (select IdParam from PCONFIGPARAM where NmParam = 'fees.drl'), ${valid.from});

-- Path where the drl files containing the rules for Sections are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (42, 'The rules for Sections', 0, 'sections.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (42, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'sections.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (42, '${fsp.services.config.dir}/drl/sections.drl', (select IdParam from PCONFIGPARAM where NmParam = 'sections.drl'), ${valid.from});

-- Path where the drl files containing the rules for Languages are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (43, 'The rules for Languages', 0, 'languages.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (43, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'languages.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (43, '${fsp.services.config.dir}/drl/languages.drl', (select IdParam from PCONFIGPARAM where NmParam = 'languages.drl'), ${valid.from});

-- Path where the drl files containing the rules for Registration Number are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (44, 'The registration Number rules', 0, 'registrationNumber.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (44, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'registrationNumber.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (44, '${fsp.services.config.dir}/drl/registrationNumber.drl', (select IdParam from PCONFIGPARAM where NmParam = 'registrationNumber.drl'), ${valid.from});

-- Path where the drl files containing the rules for Goods and Services are held
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (45, 'The rules for Goods and Services', 0, 'goodsandservices.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (45, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'goodsandservices.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (45, '${fsp.services.config.dir}/drl/goodsandservices.drl', (select IdParam from PCONFIGPARAM where NmParam = 'goodsandservices.drl'), ${valid.from});

-- Path where the drl files containing the functions for the rules
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (46, 'The functions common for the rules', 0, 'functions.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (46, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'functions.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (46, '${fsp.services.config.dir}/drl/functions.drl', (select IdParam from PCONFIGPARAM where NmParam = 'functions.drl'), ${valid.from});

-- Path where the drl files containing the rules for identifier creator
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (47, 'The rules for identifier creator', 0, 'identifierCreator.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (47, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'identifierCreator.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (47, '${fsp.services.config.dir}/drl/identifierCreator.drl', (select IdParam from PCONFIGPARAM where NmParam = 'identifierCreator.drl'), ${valid.from});

-- Path where the drl files containing the rules for payment
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (48, 'The rules for payment', 0, 'payment.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (48, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'payment.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (48, '${fsp.services.config.dir}/drl/payment.drl', (select IdParam from PCONFIGPARAM where NmParam = 'payment.drl'), ${valid.from});

-- Path where the drl files containing the rules for other Attachments
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (49, 'The rules for other attachments', 0, 'otherAttachments.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (49, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'otherAttachments.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (49, '${fsp.services.config.dir}/drl/otherAttachments.drl', (select IdParam from PCONFIGPARAM where NmParam = 'otherAttachments.drl'), ${valid.from});

-- Path where the drl files containing the rules for signature
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (50, 'The rules for other signature', 0, 'signature.drl', 1, 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (50, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'signature.drl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (50, '${fsp.services.config.dir}/drl/signature.drl', (select IdParam from PCONFIGPARAM where NmParam = 'signature.drl'), ${valid.from});

-- List of drl files pertaining to the rule list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (51, 'The rule list', 0, 'rule_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (51, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'rule_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (51, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (52, 'fees.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (53, 'trademark.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (54, 'applicant.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (55, 'claim.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (56, 'representative.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (57, 'sections.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (58, 'languages.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (59, 'payment.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (60, 'signature.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'rule_list'), ${valid.from});

-- List of drl files pertaining to the registration number list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (52, 'The registration number list', 0, 'registration_number_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (52, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'registration_number_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (61, 'registrationNumber.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'registration_number_list'), ${valid.from});

-- List of drl files pertaining to the fee list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (53, 'The fee list', 0, 'fee_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (53, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'fee_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (62, 'fees.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'fee_list'), ${valid.from});

-- List of drl files pertaining to the goods and services list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (54, 'The goods and services list', 0, 'goodsandservices_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (54, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'goodsandservices_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (63, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'goodsandservices_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (64, 'goodsandservices.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'goodsandservices_list'), ${valid.from});

-- List of drl files pertaining to the applicant list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (55, 'The applicant list', 0, 'applicant_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (55, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'applicant_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (65, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'applicant_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (66, 'applicant.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'applicant_list'), ${valid.from});

-- List of drl files pertaining to the trademark list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (56, 'The trademark list', 0, 'trademark_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (56, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'trademark_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (67, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'trademark_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (68, 'trademark.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'trademark_list'), ${valid.from});

-- List of drl files pertaining to the claim list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (57, 'The claim list', 0, 'claim_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (57, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'claim_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (69, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'claim_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (70, 'claim.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'claim_list'), ${valid.from});

-- List of drl files pertaining to the representative list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (58, 'The representative list', 0, 'representative_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (58, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'representative_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (71, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'representative_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (72, 'representative.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'representative_list'), ${valid.from});

-- List of drl files pertaining to the identifier creator
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (59, 'The identifier creator list', 0, 'application_identifier_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (59, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'application_identifier_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (73, 'identifierCreator.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'application_identifier_list'), ${valid.from});

-- List of drl files pertaining to the other attachments list parameter
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, PrmSource, TyParam, IdComponent) values (60, 'The other attachments list', 0, 'other_attachments_list', 0, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.rules'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (60, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'other_attachments_list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (74, 'functions.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'other_attachments_list'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (75, 'otherAttachments.drl',  (select IdParam from PCONFIGPARAM where NmParam = 'other_attachments_list'), ${valid.from});

-- Configuration for the oneform sections and fields
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (61, 'Configuration for the oneform sections and fields', 0, 'oneform-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (61, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'oneform-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (76, '${fsp.services.config.dir}/conf/oneform-conf.xml', (select IdParam from PCONFIGPARAM where NmParam = 'oneform-conf'), ${valid.from});

-- Configuration for the oneform flow
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (62, 'Configuration for the oneform flow', 0, 'oneform', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (62, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'oneform'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (77, '${fsp.services.config.dir}/conf/oneform-flow.xml', (select IdParam from PCONFIGPARAM where NmParam = 'oneform'), ${valid.from});

-- Configuration for the wizard sections and fields
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (63, 'Configuration for the wizard sections and fields', 0, 'wizard-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (63, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'wizard-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (78, '${fsp.services.config.dir}/conf/wizard-conf.xml', (select IdParam from PCONFIGPARAM where NmParam = 'wizard-conf'), ${valid.from});

-- Configuration for the wizard flow
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (64, 'Configuration for the wizard flow', 0, 'wizard', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (64, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'wizard'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (79, '${fsp.services.config.dir}/conf/wizard-flow.xml', (select IdParam from PCONFIGPARAM where NmParam = 'wizard'), ${valid.from});

-- Configuration for the tm-efiling abstract sections and fields
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (65, 'Configuration for the wizard sections and fields', 0, 'tm-efiling-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (65, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'tm-efiling-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (80, '${fsp.services.config.dir}/conf/tm-efiling-conf.xml', (select IdParam from PCONFIGPARAM where NmParam = 'tm-efiling-conf'), ${valid.from});

-- Configuration for the tm-efiling abstract flow
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (66, 'Configuration for the tm-efiling flow', 0, 'tm-efiling', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (66, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'tm-efiling'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (81, '${fsp.services.config.dir}/conf/tm-efiling-flow.xml', (select IdParam from PCONFIGPARAM where NmParam = 'tm-efiling'), ${valid.from});

-- Configuration of the modes of flows list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (67, 'The kinds of flow', 0, 'webflowDefinitionFileNames', 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (67, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'webflowDefinitionFileNames'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (82, 'oneform', (select IdParam from PCONFIGPARAM where NmParam = 'webflowDefinitionFileNames'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (83, 'wizard', (select IdParam from PCONFIGPARAM where NmParam = 'webflowDefinitionFileNames'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (84, 'tm-efiling', (select IdParam from PCONFIGPARAM where NmParam = 'webflowDefinitionFileNames'), ${valid.from});

-- Configuration of the JCR repository
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (68, 'Configuration of the JCR repository', 0, 'repository', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (68, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'repository'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (85, '${fsp.services.config.dir}/conf/repository.xml', (select IdParam from PCONFIGPARAM where NmParam = 'repository'), ${valid.from});

-- Configuration of the file upload
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (69, 'Configuration of the file upload', 0, 'fileUploadConfiguration', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (69, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'fileUploadConfiguration'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (86, '${fsp.services.config.dir}/conf/fileUploadConfiguration.xml', (select IdParam from PCONFIGPARAM where NmParam = 'fileUploadConfiguration'), ${valid.from});

-- Configuration of the countries list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (70, 'Configuration of Country list', 0, 'country-config', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (70, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'country-config'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (87, '${fsp.services.config.dir}/conf/countries.xml', (select IdParam from PCONFIGPARAM where NmParam = 'country-config'), ${valid.from});

-- Configuration of the nationalities list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (71, 'Configuration of Nationality list', 0, 'nationality-config', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (71, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'nationality-config'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (88, '${fsp.services.config.dir}/conf/nationalities.xml', (select IdParam from PCONFIGPARAM where NmParam = 'nationality-config'), ${valid.from});

-- Configuration of the list of states
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (72, 'Configuration of the list States', 0, 'states-config', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (72, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'states-config'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (89, '${fsp.services.config.dir}/conf/states.xml', (select IdParam from PCONFIGPARAM where NmParam = 'states-config'), ${valid.from});

-- Configuration of the First Language list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (73, 'Configuration of the First Language list', 0, 'first-lang-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (73, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'first-lang-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (90, '${fsp.services.config.dir}/conf/first-lang.xml', (select IdParam from PCONFIGPARAM where NmParam = 'first-lang-conf'), ${valid.from});

-- Configuration of the econd Language list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (74, 'Configuration of the econd Language list', 0, 'second-lang-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (74, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'second-lang-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (91, '${fsp.services.config.dir}/conf/second-lang.xml', (select IdParam from PCONFIGPARAM where NmParam = 'second-lang-conf'), ${valid.from});

-- Configuration of the Roles Permissions mapping
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (75, 'Configuration of the Roles Permissions mapping', 0, 'mapping-roles', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (75, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'mapping-roles'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (92, '${fsp.services.config.dir}/conf/permissions.xml', (select IdParam from PCONFIGPARAM where NmParam = 'mapping-roles'), ${valid.from});

-- Configuration of Services enabling/disabling
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (76, 'Configuration of Services enabling/disabling', 0, 'enabled-services', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (76, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'enabled-services'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (93, '${fsp.services.config.dir}/conf/service-enabled.xml', (select IdParam from PCONFIGPARAM where NmParam = 'enabled-services'), ${valid.from});

-- Configuration of Colour list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (77, 'Configuration of Colour list', 0, 'colour-configuration', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (77, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'colour-configuration'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (94, '${fsp.services.config.dir}/conf/colour-configuration.xml', (select IdParam from PCONFIGPARAM where NmParam = 'colour-configuration'), ${valid.from});

-- Configuration of regular Expressions
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (78, 'Configuration of Regular Expressions', 0, 'regex-configuration', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (78, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'regex-configuration'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (95, '${fsp.services.config.dir}/conf/regex-configuration.xml', (select IdParam from PCONFIGPARAM where NmParam = 'regex-configuration'), ${valid.from});

-- Configuration for Seniority Natures list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (79, 'Configuration for Seniority Natures list', 0, 'seniority-natures', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (79, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'seniority-natures'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (96, '${fsp.services.config.dir}/conf/seniorityNature.xml', (select IdParam from PCONFIGPARAM where NmParam = 'seniority-natures'), ${valid.from});

-- Configuration for Exhibitions list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (80, 'Configuration for Exhibitions list', 0, 'exhibitions', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (80, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'exhibitions'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (97, '${fsp.services.config.dir}/conf/exhibition.xml', (select IdParam from PCONFIGPARAM where NmParam = 'exhibitions'), ${valid.from});

-- Configuration for Payment Methods list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (81, 'Configuration for PaymentMethods list', 0, 'payment-methods', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (81, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'payment-methods'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (98, '${fsp.services.config.dir}/conf/paymentMethod.xml', (select IdParam from PCONFIGPARAM where NmParam = 'payment-methods'), ${valid.from});

-- Configuration for Payer Types list
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (82, 'Configuration for Payer Types list', 0, 'payer-types', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (82, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'payer-types'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (99, '${fsp.services.config.dir}/conf/payerType.xml', (select IdParam from PCONFIGPARAM where NmParam = 'payer-types'), ${valid.from});

-- Birt Report RPTDESIGN file for Receipt
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (83, 'Birt Report RPTDESIGN file for Receipt', 0, 'receipt', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (83, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'receipt'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (100, '${fsp.services.config.dir}/conf/receipt.rptdesign', (select IdParam from PCONFIGPARAM where NmParam = 'receipt'), ${valid.from});

-- Birt Report RPTDESIGN file for similar trade marks report
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (84, 'Birt Report RPTDESIGN file for similar trade marks report', 0, 'similarTM', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (84, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'similarTM'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (101, '${fsp.services.config.dir}/conf/similarTM.rptdesign', (select IdParam from PCONFIGPARAM where NmParam = 'similarTM'), ${valid.from});

-- Mimetype file information
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (85, 'Information needed for storing the application. Mimetype file', 0, 'mimetype', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (85, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'mimetype'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (102, '${fsp.services.config.dir}/conf/mimetype', (select IdParam from PCONFIGPARAM where NmParam = 'mimetype'), ${valid.from});

-- Container.xml file information
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (86, 'Information needed for storing the application. Container.xml file', 0, 'container.xml', 2, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (86, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'container.xml'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (103, '${fsp.services.config.dir}/conf/container.xml', (select IdParam from PCONFIGPARAM where NmParam = 'container.xml'), ${valid.from});

-- Indicator specifying whether to display the Applicant Autocomplete Search Link
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (87, 'Indicator for whether to enable the display of the Applicant details link', 0, 'service.applicant.details.urlEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (87, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.details.urlEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (104, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.details.urlEnabled'), ${valid.from});

-- Applicant Search URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (88, 'Applicant details URI', 0, 'service.applicant.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (88, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (105, '${url.display.applicant.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.details.url'), ${valid.from});

-- Applicant matches max number of results
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (89, 'Applicant matches max number of results', 0, 'service.applicant.match.maxResults', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (89, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.match.maxResults'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (106, '5',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.applicant.match.maxResults'), ${valid.from});

-- Indicator specifying whether to display the Representative Autocomplete Search Link
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (90, 'Indicator for whether to enable the display of the Representative details link', 0, 'service.representative.details.urlEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (90, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.details.urlEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (107, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.details.urlEnabled'), ${valid.from});

-- Representative Search URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (91, 'Representative details URI', 0, 'service.representative.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (91, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (108, '${url.display.representative.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.details.url'), ${valid.from});

-- Representative matches max number of results
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (92, 'Representative matches max number of results', 0, 'service.representative.match.maxResults', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (92, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.match.maxResults'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (109, '5',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.representative.match.maxResults'), ${valid.from});

-- Indicator specifying whether to display the Previous CTM Autocomplete Search Link
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (93, 'Indicator for whether to enable the display of the Previous Ctm details link', 0, 'service.trademark.details.urlEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (93, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.details.urlEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (110, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.details.urlEnabled'), ${valid.from});

-- Trade Mark Search URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (94, 'Trade Mark details URI', 0, 'service.trademark.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (94, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (111, '${url.display.previous.trademark.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.details.url'), ${valid.from});

-- Indicator specifying whether to display the TradeMark Autocomplete Search Link in Priority Section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (95, 'Indicator specifying whether to display the TradeMark Autocomplete Search Link in Priority Section', 0, 'service.trademark.priority.details.urlEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (95, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.priority.details.urlEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (112, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.priority.details.urlEnabled'), ${valid.from});

-- Trade Mark Search URI in Priority Section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (96, 'Trade Mark Search URI in Priority Section', 0, 'service.trademark.priority.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (96, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.priority.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (113, '${url.display.priority.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.priority.details.url'), ${valid.from});

-- Indicator specifying whether to display the TradeMark Autocomplete Search Link in Seniority Section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (97, 'Indicator specifying whether to display the TradeMark Autocomplete Search Link in Seniority Section', 0, 'service.trademark.seniority.details.urlEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (97, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.seniority.details.urlEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (114, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.seniority.details.urlEnabled'), ${valid.from});

-- Trade Mark Search URI in Seniority Section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (98, 'Trade Mark Search URI in Seniority Section', 0, 'service.trademark.seniority.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (98, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.seniority.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (115, '${url.display.seniority.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.seniority.details.url'), ${valid.from});

-- Indicator specifying whether to display the TradeMark Autocomplete Search Link in Transformation Section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (99, 'Indicator specifying whether to display the TradeMark Autocomplete Search Link in Transformation Section', 0, 'service.trademark.transformation.details.urlEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (99, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.transformation.details.urlEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (116, 'true',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.transformation.details.urlEnabled'), ${valid.from});

-- Trade Mark Search URI in Transformation Section
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (100, 'Trade Mark Search URI in Transformation Section', 0, 'service.trademark.transformation.details.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (100, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.transformation.details.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (117, '${url.display.transformation.details}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.trademark.transformation.details.url'), ${valid.from});

-- Defines the TM-EFILING product code related to the payment
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (101, 'Defines the TM-EFILING product code related to the payment', 0, 'tmefiling.product.code', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (101, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'tmefiling.product.code'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (118, '32',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'tmefiling.product.code'), ${valid.from});

-- Adapters propeties URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (102, 'Adapters setup', 0, 'service.adapters.list', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (102, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.adapters.list'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (119, '${fsp.services.config.dir}/conf/adapter.xml',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'service.adapters.list'), ${valid.from});

-- Representative Association: Num of chars before autocomplete service is called
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (103, 'The number of characters to be entered before autocomplete service for searching a representative is invoked', 0, 'service.representative.association.autocomplete.minchars', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (103, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.association.autocomplete.minchars'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (120, '3',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'service.representative.association.autocomplete.minchars'), ${valid.from});

-- Representative Settings : the maximum number of representatives a user can add
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (104, 'The maximum number of representatives a user can add', 0, 'representative.add.maxNumber', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (104, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'representative.add.maxNumber'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (121, '1000',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'representative.add.maxNumber'), ${valid.from});

-- Applicant Settings : the maximum number of applicants a user can add
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (105, 'The maximum number of applicants a user can add', 0, 'applicant.add.maxNumber', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.core.person'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (105, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'applicant.add.maxNumber'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (122, '1000',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.core.person' and cp.NmParam = 'applicant.add.maxNumber'), ${valid.from});

-- Priority Settings : the maximum number of priorities a user can add
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (106, 'The maximum number of priorities a user can add', 0, 'claim.priority.add.maxNumber', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (106, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.priority.add.maxNumber'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (123, '1000',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.priority.add.maxNumber'), ${valid.from});

-- Seniority Settings : the maximum number of seniorities a user can add
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (107, 'The maximum number of seniorities a user can add', 0, 'claim.seniority.add.maxNumber', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (107, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.seniority.add.maxNumber'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (124, '1000',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.seniority.add.maxNumber'), ${valid.from});

-- Transformation Priority Settings : the maximum number of transformation priorities a user can add
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (108, 'The maximum number of transformation priorities a user can add', 0, 'claim.transformation.add.maxNumber', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (108, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.transformation.add.maxNumber'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (125, '1',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.transformation.add.maxNumber'), ${valid.from});

-- Exhibition Priority Settings : the maximum number of exhibition priorities a user can add
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (109, 'The maximum number of exhibition priorities a user can add', 0, 'claim.exhibition.add.maxNumber', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (109, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.exhibition.add.maxNumber'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (126, '1',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'claim.exhibition.add.maxNumber'), ${valid.from});

-- Submitted page feedback URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (110, 'Submitted page feedback URI', 0, 'submitted.feedback.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (110, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'submitted.feedback.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (127, '${url.submitted.feedback}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'submitted.feedback.url'), ${valid.from});

-- ISO Currency Code
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (111, 'ISO Currency Code', 0, 'currency.code', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (111, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'currency.code'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (128, '${currency.iso.code}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'currency.code'), ${valid.from});

-- FSP External Services get Trademark ApplicationURI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (112, 'FSP External Services get Trademark ApplicationURI', 0, 'sp.getApplication.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (112, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'sp.getApplication.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (129, '${url.sp.external.services.getApplication}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'sp.getApplication.url'), ${valid.from});

-- Configuration of Fee Values
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (113, 'Configuration of Fee Values', 0, 'fees-configuration', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='general'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (113, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'fees-configuration'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (130, '${fsp.services.config.dir}/conf/fees-configuration.xml', (select IdParam from PCONFIGPARAM where NmParam = 'fees-configuration'), ${valid.from});

-- Submitted page communication inbox URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (114, 'Submitted page communication inbox URI', 0, 'submitted.communication.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (114, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'submitted.communication.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (131, '${url.submitted.communication}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'submitted.communication.url'), ${valid.from});

-- Submitted page misleading requests information URI
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (115, 'Submitted page misleading requests information URI', 0, 'submitted.misleading.url', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='general'), 0);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (115, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'submitted.misleading.url'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (132, '${url.submitted.misleading}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='general' and cp.NmParam = 'submitted.misleading.url'), ${valid.from});
