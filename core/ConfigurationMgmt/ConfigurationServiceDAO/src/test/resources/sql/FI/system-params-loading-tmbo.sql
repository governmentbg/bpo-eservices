insert into CCOMPONENT (IdComponent, NmComponent) values (5, 'eu.ohim.sp.tmbo');

-- Values of Common Type "Address Kind"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (116, 'Values for common type "Address Kind"', 0, 'cotypes.address.kind', 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (116, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.address.kind'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (133, '${sp.services.config.dir}/conf/ct-address-kind.xml',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.address.kind'), ${valid.from});

-- Values of Common Type "Color Format"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (117, 'Values for common type "Color Format"', 0, 'cotypes.color.format', 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (117, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.color.format'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (134, '${sp.services.config.dir}/conf/ct-color-format.xml',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.color.format'), ${valid.from});

-- Values of Common Type "Email Kind"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (118, 'Values for common type "Email Kind"', 0, 'cotypes.email.kind', 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (118, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.email.kind'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (135, '${sp.services.config.dir}/conf/ct-email-kind.xml',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.email.kind'), ${valid.from});

-- Values of Common Type "Phone Kind"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (119, 'Values for common type "Phone Kind"', 0, 'cotypes.phone.kind', 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (119, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.phone.kind'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (136, '${sp.services.config.dir}/conf/ct-phone-kind.xml',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.phone.kind'), ${valid.from});

-- Values of Common Type "loginUrl"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (120, 'Values for common type "login URL"', 0, 'cotypes.security.loginUrl', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (120, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.loginUrl'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (137, 'http://${sp.tmbo.config.security.server}/security/login.jsp',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.loginUrl'), ${valid.from});

-- Values of Common Type "artifactName"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (121, 'Values for common type "artifact name"', 0, 'cotypes.security.artifactName', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (121, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.artifactName'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (138, 'ticket',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.artifactName'), ${valid.from});

-- Values of Common Type "ssoEnabled"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (122, 'Values for common type "sso enabled"', 0, 'cotypes.security.ssoEnabled', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (122, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.ssoEnabled'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (139, '${sp.tmbo.config.security.sso}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.ssoEnabled'), ${valid.from});

-- Values of Common Type "cookiePath"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (123, 'Values for common type "sso enabled"', 0, 'cotypes.security.cookiePath', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (123, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.cookiePath'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (140, 'fsp_tm_bo',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.cookiePath'), ${valid.from});

-- Values of Common Type "validateTicketEndpoint"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (124, 'Values for common type "sso enabled"', 0, 'cotypes.security.validateTicketEndpoint', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (124, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.validateTicketEndpoint'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (141, 'http://${sp.tmbo.config.security.server}/security/security_check/validateticket',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.validateTicketEndpoint'), ${valid.from});

-- Values of Common Type "userDetailsEndpoint"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (125, 'Values for common type "sso enabled"', 0, 'cotypes.security.userDetailsEndpoint', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (125, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.userDetailsEndpoint'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (142, 'http://${sp.tmbo.config.security.server}/security/security_check/details',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.userDetailsEndpoint'), ${valid.from});

-- Values of Common Type "logoutTicketEndpoint"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (126, 'Values for common type "sso enabled"', 0, 'cotypes.security.logoutTicketEndpoint', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (126, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.logoutTicketEndpoint'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (143, 'http://${sp.tmbo.config.security.server}/security/security_check/logout',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.logoutTicketEndpoint'), ${valid.from});

-- Configuration of the Roles Permissions mapping
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (127, 'Configuration of the Roles Permissions mapping', 0, 'mapping-roles-tmbo', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (127, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'mapping-roles-tmbo'), null,  ${valid.from}, null,  ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (144, '${sp.services.config.dir}/conf/permissions-tmbo.xml', (select IdParam from PCONFIGPARAM where NmParam = 'mapping-roles-tmbo'),  ${valid.from});


-- Configuration of the Pages (Sections, Forms and Fields)
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (128, 'UI Configuration of pages of the application', 0, 'pages-config-tmbo', 1, 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (128, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'pages-config-tmbo'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (145, 'tmbo-home-conf', (select IdParam from PCONFIGPARAM where NmParam = 'pages-config-tmbo'), ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (146, 'tmbo-trademark-conf', (select IdParam from PCONFIGPARAM where NmParam = 'pages-config-tmbo'), ${valid.from});

-- Configuration of the Pages. Home Page
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (129, 'UI Configuration of the TM-BO home page', 0, 'tmbo-home-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (129, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'tmbo-home-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (147, '${sp.services.config.dir}/conf/tmbo-home-conf.xml', (select IdParam from PCONFIGPARAM where NmParam = 'tmbo-home-conf'), ${valid.from});

-- Configuration of the Pages. Trademark Page
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, PrmSource, IdComponent) values (130, 'UI Configuration of the TM-BO trademark page', 0, 'tmbo-trademark-conf', 3, 1, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'));
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (130, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and cp.NmParam = 'tmbo-trademark-conf'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (148, '${sp.services.config.dir}/conf/tmbo-trademark-conf.xml', (select IdParam from PCONFIGPARAM where NmParam = 'tmbo-trademark-conf'), ${valid.from});


-- Values of Common Type "frontOfficeAddress"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (131, 'Values for common type "front office address"', 0, 'cotypes.security.frontOfficeAddress', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (131, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.frontOfficeAddress'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (149, 'http://${sp.tmbo.config.security.server}/backoffice-fe/',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.security.frontOfficeAddress'), ${valid.from});

-- Values of Common Type "Person Identifier Kind"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (132, 'Values for common type "Person Identifier Kind"', 0, 'cotypes.identifier.kind', 2, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (132, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.identifier.kind'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (150, '${sp.services.config.dir}/conf/ct-identifier-kind.xml',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.identifier.kind'), ${valid.from});

-- Values of Common Type "backOfficeAddress"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (133, 'Values for common type "back offfce address"', 0, 'cotypes.backOfficeAddress', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (133, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.backOfficeAddress'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (151, '${sp.tmbo.config.server.backOfficeAddress}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.backOfficeAddress'), ${valid.from});

-- Values of Common Type "appVersion"
insert into PCONFIGPARAM (IdParam, DscParam, IsHidden, NmParam, TyParam, IdComponent, PrmSource) values (134, 'Values for common type "tmbo version of the application"', 0, 'cotypes.app.version', 0, (select IdComponent from CCOMPONENT c where c.NmComponent='eu.ohim.sp.tmbo'), 1);
insert into DCONFIGSETTING (IdConfigSetting, IdParam, LogModifiedBy, DtValidFrom, DtValidTo, DtCreated) values (134, (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.app.version'), null, ${valid.from}, null, ${valid.from});
insert into DSETTINGVALUE (IdValue, TxtValue, IdConfigSetting, DtCreated) values (152, '${sp.tmbo.config.app.version}',  (select cp.IdParam from PCONFIGPARAM cp, CCOMPONENT c where cp.IdComponent = c.IdComponent and c.NmComponent='eu.ohim.sp.tmbo' and cp.NmParam = 'cotypes.app.version'), ${valid.from});
