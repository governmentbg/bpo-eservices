create schema efiling;
SET search_path TO efiling, public;
/*==============================================================*/
/* Table: CCOMPONENT                                            */
/*==============================================================*/
create table CCOMPONENT
(
   IDCOMPONENT          int not null,
   NMCOMPONENT          varchar(64) not null ,
   primary key (IDCOMPONENT)
);
comment on column CCOMPONENT.IDCOMPONENT is 'ID of component, used as foreign key. This ID will be assigned to each component at deployment as they are known at design time and will not change at runtime.';
comment on column CCOMPONENT.NMCOMPONENT is 'The name of this component.';

/*==============================================================*/
/* Table: CFORMSTATUS                                           */
/*==============================================================*/
create table CFORMSTATUS
(
   IDFORMSTATUS         smallint not null,
   STCODE               smallint,
   DSCFORMSTATUS        varchar(255) not null,
   primary key (IDFORMSTATUS)
);
/*==============================================================*/
/* Table: HSIGNSTATUSUPDATE                                         */
/*==============================================================*/
create table HSIGNSTATUSUPDATE
(
   IDSTATUS             int not null,
   IDFORM               int not null ,
   IDFORMSIGNSTATUS         smallint not null ,
   TXTMESSAGE           varchar(255),
   DTCREATED            timestamp not null,
   primary key (IDSTATUS)
);
comment on column HSIGNSTATUSUPDATE.IDSTATUS is 'ID of the sign status record. This ID will be generated using the TSequence table with IdSequence = "DSignStatus".';
comment on column HSIGNSTATUSUPDATE.IDFORM IS 'The draft application this status update belongs to.';
comment on column HSIGNSTATUSUPDATE.IDFORMSIGNSTATUS IS 'The sign status code.';
comment on column HSIGNSTATUSUPDATE.TXTMESSAGE IS 'Possible message belonging to the sign status change, e.g. in case of an error.';
comment on column HSIGNSTATUSUPDATE.DTCREATED IS 'The date when this status was set, i.e. when the draft application had its sign status changed.';

/*==============================================================*/
/* Table: CFORMTYPE                                             */
/*==============================================================*/
create table CFORMTYPE
(
   IDFORMTYPE           smallint not null,
   DSCFORMTYPE          varchar(64),
   primary key (IDFORMTYPE)
);

/*==============================================================*/
/* Table: PCONFIGPARAM                                          */
/*==============================================================*/
create table PCONFIGPARAM
(
   IDPARAM              int not null,
   IDCOMPONENT          int not null,
   NMPARAM              varchar(64) not null,
   DSCPARAM             varchar(255),
   TYPARAM              smallint not null,
   PRMSOURCE            smallint not null,
   ISHIDDEN             smallint not null default 0,
   primary key (IDPARAM),
   constraint FK_COMPONENTPARAM_IDCOMPONENT foreign key (IDCOMPONENT)
      references CCOMPONENT (IDCOMPONENT) on delete restrict on update restrict
);
comment on column pconfigparam.idparam is 'Unique ID of setting. This ID will be generated using the TSequence table with IdSequence = "idconfigparam".';
comment on column pconfigparam.idcomponent is 'Id of the component this parameter belongs to.';
comment on column pconfigparam.nmparam is 'The name of this parameter.';
comment on column pconfigparam.dscparam is 'A description of this parameter.';
comment on column pconfigparam.typaram is 'This defines the type of parameter:
            0 = simple
            1 = list
            2 = XML
            3 = object';
comment on column pconfigparam.prmsource is 'This contains "0" in case the value contains a file path, and "1" or NULL in case the value is the actual data. Used for XML parameters.';
comment on column pconfigparam.ishidden is 'Whether this parameter is hidden from the administration tool web page.
            0 = false
            1 = true';




/*==============================================================*/
/* Table: DCONFIGSETTING                                        */
/*==============================================================*/
create table DCONFIGSETTING
(
   IDCONFIGSETTING      int not null,
   IDPARAM              int not null,
   DTVALIDFROM          timestamp not null ,
   DTVALIDTO            timestamp,
   LOGMODIFIEDBY        varchar(75),
   DTCREATED            timestamp not null,
   primary key (IDCONFIGSETTING),
   constraint FK_PARAMSETTING_IDCONFIGPARAM foreign key (IDPARAM)
      references PCONFIGPARAM (IDPARAM) on delete restrict on update restrict
);
comment on column DCONFIGSETTING.idparam is 'Unique ID of setting. This ID will be generated using the TSequence table with IdSequence = "idconfigparam".';
comment on column DCONFIGSETTING.DTVALIDFROM is 'What date/time this parameter becomes valid.';
comment on column DCONFIGSETTING.DTVALIDTO is 'What date/time this parameter becomes invalid.';
comment on column DCONFIGSETTING.LOGMODIFIEDBY is 'This contains the username of the administrator that modified this parameter last, if any.';

/*==============================================================*/
/* Table: DFORM                                                 */
/*==============================================================*/
create table DFORM
(
   IDFORM               int not null,
   IDOFFICE             varchar(2) not null,
   IDFILING             varchar(19),
   TYAPPLICATION        smallint not null,
   IDAPPLICATION        varchar(50),
   IDPAYMENT            varchar(32),
   IDSTATUS             smallint not null,
   LOGUSERNAME          varchar(75),
   DTCREATED            timestamp not null,
   IDSIGNSTATUS         smallint DEFAULT NULL,
   primary key (IDFORM),
   constraint FK_FORMTYPE_IDFORMTYPE foreign key (TYAPPLICATION)
      references CFORMTYPE (IDFORMTYPE) on delete restrict on update restrict,
   constraint FK_FORMSTATUS_IDFORMSTATUS foreign key (IDSTATUS)
      references CFORMSTATUS (IDFORMSTATUS) on delete restrict on update restrict,
   constraint FK_DFORM_IDSIGNSTATUS foreign key (IDSIGNSTATUS)
      references hsignstatusupdate (IDSTATUS) on delete restrict on update restrict
);
ALTER TABLE hsignstatusupdate ADD constraint FK_FORMSIGNSTATUS_IDFORM foreign key (IDFORM) references DFORM (IDFORM) on delete restrict on update restrict;
comment on column dform.idform is 'ID of this draft application. This ID will be generated using the TSequence table with IdSequence = "idform".';
comment on column dform.idoffice is 'WIPO ST.3 office code for this application. Always constant for a particular office.';
comment on column dform.idfiling is 'Filing Number assigned to the Application by the SP Front Office. It is constructed as follows: "EF" + IdOffice (2 digits) + Year (4 digits) + IdForm (left-padded to 11 digits)';
comment on column dform.idapplication is 'Application Number built according to ST 13 format  and assigned to the Application by the PO Back Office, after it receives the filing. This value might be received by SP after the BO process the application, but it is not mandatory.';
comment on column dform.idpayment is 'Identifier used as a reference for payment via external platforms. Necessary size depends on payment system used and will be set at deployment time.';
comment on column dform.idstatus is 'Current status of the application. It corresponds to the latest status available for this application in the DStatus table (column StForm). It is stored also in the DForm table for performance reasons.';
comment on column dform.logusername is 'Username of the registered user owning this application, if any.';
comment on column dform.dtcreated is 'Date this draft application was started. Default value is the current date.';


ALTER TABLE dform ADD COLUMN ECORRESPONDENCE boolean DEFAULT NULL;
comment on column dform.ecorrespondence is 'Flag for marking if e-correspondence is requested';
ALTER TABLE dform ADD COLUMN REFAPPLICATION varchar(50) DEFAULT NULL;
comment on column dform.refapplication is 'Reference number that is related to this application';
ALTER TABLE dform ADD COLUMN TITLEAPPLICATION varchar(4000);
comment on column dform.titleapplication is 'Title to refer to for this application';
ALTER TABLE dform ADD COLUMN SUBTYAPPLICATION varchar(100) DEFAULT NULL;
comment on column dform.subtyapplication is 'Application subtype - for example: the subtype selected in generic e-services';
ALTER TABLE dform ALTER COLUMN idsignstatus TYPE integer;
/*==============================================================*/
/* Table: DSETTINGVALUE                                         */
/*==============================================================*/
create table DSETTINGVALUE
(
   IDVALUE              int not null,
   IDCONFIGSETTING      int not null,
   TXTVALUE             text,
   DTCREATED            timestamp,
   primary key (IDVALUE),
   constraint FK_SETTINGVALUE_IDSETTING foreign key (IDCONFIGSETTING)
      references DCONFIGSETTING (IDCONFIGSETTING) on delete restrict on update restrict
);
comment on column dsettingvalue.IDVALUE is 'Unique ID of value. This ID will be generated using the TSequence table with IdSequence = "idvalue".';
comment on column dsettingvalue.txtvalue is 'The parameter value.';

/*==============================================================*/
/* Table: HSTATUSUPDATE                                         */
/*==============================================================*/
create table HSTATUSUPDATE
(
   IDSTATUS             int not null,
   IDFORM               int not null ,
   IDFORMSTATUS         smallint not null,
   TXTMESSAGE           varchar(255),
   DTCREATED            timestamp not null,
   primary key (IDSTATUS),
   constraint FK_FORMSTATUS_IDFORM foreign key (IDFORM)
      references DFORM (IDFORM) on delete restrict on update restrict,
   constraint FK_STATUSUPDATE_IDFORMSTATUS foreign key (IDFORMSTATUS)
      references CFORMSTATUS (IDFORMSTATUS) on delete restrict on update restrict
);
comment on column HSTATUSUPDATE.idstatus is 'ID of the status record. This ID will be generated using the TSequence table with IdSequence = "idstatus".';
comment on column HSTATUSUPDATE.idform is 'The draft application this status update belongs to.';
comment on column HSTATUSUPDATE.idformstatus is 'The status code.';
comment on column HSTATUSUPDATE.txtmessage is 'Possible message belonging to the status change, e.g. in case of an error.';
comment on column HSTATUSUPDATE.dtcreated is 'The date when this status was set, i.e. when the draft application had its status changed.';


/*==============================================================*/
/* Table: TSEQUENCE                                             */
/*==============================================================*/
create table TSEQUENCE
(
   IDSEQUENCE           varchar(64) not null,
   NRLASTVALUE          bigint not null default 0,
   primary key (IDSEQUENCE)
);
comment on column TSEQUENCE.IDSEQUENCE is 'Name of the sequence, the value assigned will be the name of the PK field in lowercase. E.g. "idform", "idconfigparam".';
comment on column TSEQUENCE.NRLASTVALUE is 'The last value used for the specific sequence entry. Default value at creation will be 0. The generation of the values will be managed by the Persistence Layer.';







--ALTER TABLE efiling.cformtype OWNER TO efiling;
--ALTER TABLE efiling.dconfigsetting OWNER TO efiling;
--ALTER TABLE efiling.dform OWNER TO efiling;
--ALTER TABLE efiling.dsettingvalue OWNER TO efiling;
--ALTER TABLE efiling.hsignstatusupdate OWNER TO efiling;
--ALTER TABLE efiling.hstatusupdate OWNER TO efiling;
--ALTER TABLE efiling.pconfigparam OWNER TO efiling;
--ALTER TABLE efiling.tsequence OWNER TO efiling;
