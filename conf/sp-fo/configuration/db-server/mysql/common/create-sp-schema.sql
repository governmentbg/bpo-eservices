/*==============================================================*/
/* Table: CCOMPONENT                                            */
/*==============================================================*/
create table CCOMPONENT
(
   IDCOMPONENT          int not null comment 'ID of component, used as foreign key. This ID will be assigned to each component at deployment as they are known at design time and will not change at runtime.',
   NMCOMPONENT          varchar(64) not null comment 'The name of this component.',
   primary key (IDCOMPONENT)
);

/*==============================================================*/
/* Table: CFORMSTATUS                                           */
/*==============================================================*/
create table CFORMSTATUS
(
   IDFORMSTATUS         smallint not null,
   STCODE               smallint,
   DSCFORMSTATUS        char(255) not null,
   primary key (IDFORMSTATUS)
);
/*==============================================================*/
/* Table: HSIGNSTATUSUPDATE                                         */
/*==============================================================*/
create table HSIGNSTATUSUPDATE
(
   IDSTATUS             int not null comment 'ID of the sign status record. This ID will be generated using the TSequence table with IdSequence = "DSignStatus".',
   IDFORM               int not null comment 'The draft application this status update belongs to.',
   IDFORMSIGNSTATUS         smallint not null comment 'The sign status code.',
   TXTMESSAGE           varchar(255) comment 'Possible message belonging to the sign status change, e.g. in case of an error.',
   DTCREATED            datetime not null comment 'The date when this status was set, i.e. when the draft application had its sign status changed.',
   primary key (IDSTATUS)
);
/*==============================================================*/
/* Table: CFORMTYPE                                             */
/*==============================================================*/
create table CFORMTYPE
(
   IDFORMTYPE           smallint not null,
   DSCFORMTYPE          char(64),
   primary key (IDFORMTYPE)
);

/*==============================================================*/
/* Table: PCONFIGPARAM                                          */
/*==============================================================*/
create table PCONFIGPARAM
(
   IDPARAM              int not null comment 'Unique ID of setting. This ID will be generated using the TSequence table with IdSequence = "idconfigparam".',
   IDCOMPONENT          int not null comment 'Id of the component this parameter belongs to.',
   NMPARAM              varchar(64) not null comment 'The name of this parameter.',
   DSCPARAM             varchar(255) comment 'A description of this parameter.',
   TYPARAM              smallint not null comment 'This defines the type of parameter:
            0 = simple
            1 = list
            2 = XML
            3 = object

            ',
   PRMSOURCE            smallint not null comment 'This contains "0" in case the value contains a file path, and "1" or NULL in case the value is the actual data. Used for XML parameters.',
   ISHIDDEN             smallint not null default 0 comment 'Whether this parameter is hidden from the administration tool web page.
            0 = false
            1 = true',
   primary key (IDPARAM),
   constraint FK_COMPONENTPARAM_IDCOMPONENT foreign key (IDCOMPONENT)
      references CCOMPONENT (IDCOMPONENT) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: DCONFIGSETTING                                        */
/*==============================================================*/
create table DCONFIGSETTING
(
   IDCONFIGSETTING      int not null,
   IDPARAM              int not null comment 'Unique ID of setting. This ID will be generated using the TSequence table with IdSequence = "idconfigparam".',
   DTVALIDFROM          datetime not null comment 'What date/time this parameter becomes valid.',
   DTVALIDTO            datetime comment 'What date/time this parameter becomes invalid.',
   LOGMODIFIEDBY        char(75) comment 'This contains the username of the administrator that modified this parameter last, if any.',
   DTCREATED            datetime not null,
   primary key (IDCONFIGSETTING),
   constraint FK_PARAMSETTING_IDCONFIGPARAM foreign key (IDPARAM)
      references PCONFIGPARAM (IDPARAM) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: DFORM                                                 */
/*==============================================================*/
create table DFORM
(
   IDFORM               int not null comment 'ID of this draft application. This ID will be generated using the TSequence table with IdSequence = "idform".',
   IDOFFICE             char(2) not null comment 'WIPO ST.3 office code for this application. Always constant for a particular office.',
   IDFILING             char(19) comment 'Filing Number assigned to the Application by the SP Front Office. It is constructed as follows:
            
            "EF" + IdOffice (2 digits) + Year (4 digits) + IdForm (left-padded to 11 digits)',
   TYAPPLICATION        smallint not null,
   IDAPPLICATION        char(17) comment 'Application Number built according to ST 13 format  and assigned to the Application by the PO Back Office, after it receives the filing. This value might be received by SP after the BO process the application, but it is not mandatory.',
   IDPAYMENT            char(32) comment 'Identifier used as a reference for payment via external platforms.
            Necessary size depends on payment system used and will be set at deployment time.',
   IDSTATUS             smallint not null comment 'Current status of the application. It corresponds to the latest status available for this application in the DStatus table (column StForm). It is stored also in the DForm table for performance reasons.',
   LOGUSERNAME          char(75) comment 'Username of the registered user owning this application, if any.',
   DTCREATED            datetime not null comment 'Date this draft application was started. Default value is the current date.',
   IDSIGNSTATUS         int(11) DEFAULT NULL,
   primary key (IDFORM),
   constraint FK_FORMTYPE_IDFORMTYPE foreign key (TYAPPLICATION)
      references CFORMTYPE (IDFORMTYPE) on delete restrict on update restrict,
   constraint FK_FORMSTATUS_IDFORMSTATUS foreign key (IDSTATUS)
      references CFORMSTATUS (IDFORMSTATUS) on delete restrict on update restrict,
   constraint FK_DFORM_IDSIGNSTATUS foreign key (IDSIGNSTATUS)
      references hsignstatusupdate (IDSTATUS) on delete restrict on update restrict
);
ALTER TABLE hsignstatusupdate ADD constraint FK_FORMSIGNSTATUS_IDFORM foreign key (IDFORM) references DFORM (IDFORM) on delete restrict on update restrict;
ALTER TABLE dform ADD COLUMN ECORRESPONDENCE boolean DEFAULT NULL;
comment on column dform.ECORRESPONDENCE is 'Flag for marking if e-correspondence is requested';
ALTER TABLE dform ADD COLUMN REFAPPLICATION char(17) DEFAULT NULL;
comment on column dform.REFAPPLICATION is 'Reference number that is related to this application';
ALTER TABLE dform ADD COLUMN TITLEAPPLICATION VARCHAR(4000);
comment on column dform.TITLEAPPLICATION is 'Title to refer to for this application';
ALTER TABLE dform ADD COLUMN SUBTYAPPLICATION varchar(100) DEFAULT NULL;
comment on column dform.subtyapplication is 'Application subtype - for example: the subtype selected in generic e-services';
/*==============================================================*/
/* Table: DSETTINGVALUE                                         */
/*==============================================================*/
create table DSETTINGVALUE
(
   IDVALUE              int not null comment 'Unique ID of value. This ID will be generated using the TSequence table with IdSequence = "idvalue".',
   IDCONFIGSETTING      int not null,
   TXTVALUE             longtext comment 'The parameter value.',
   DTCREATED            datetime,
   primary key (IDVALUE),
   constraint FK_SETTINGVALUE_IDSETTING foreign key (IDCONFIGSETTING)
      references DCONFIGSETTING (IDCONFIGSETTING) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: HSTATUSUPDATE                                         */
/*==============================================================*/
create table HSTATUSUPDATE
(
   IDSTATUS             int not null comment 'ID of the status record. This ID will be generated using the TSequence table with IdSequence = "idstatus".',
   IDFORM               int not null comment 'The draft application this status update belongs to.',
   IDFORMSTATUS         smallint not null comment 'The status code.',
   TXTMESSAGE           varchar(255) comment 'Possible message belonging to the status change, e.g. in case of an error.',
   DTCREATED            datetime not null comment 'The date when this status was set, i.e. when the draft application had its status changed.',
   primary key (IDSTATUS),
   constraint FK_FORMSTATUS_IDFORM foreign key (IDFORM)
      references DFORM (IDFORM) on delete restrict on update restrict,
   constraint FK_STATUSUPDATE_IDFORMSTATUS foreign key (IDFORMSTATUS)
      references CFORMSTATUS (IDFORMSTATUS) on delete restrict on update restrict
);

/*==============================================================*/
/* Table: TSEQUENCE                                             */
/*==============================================================*/
create table TSEQUENCE
(
   IDSEQUENCE           varchar(64) not null comment 'Name of the sequence, the value assigned will be the name of the PK field in lowercase. E.g. "idform", "idconfigparam".',
   NRLASTVALUE          bigint not null default 0 comment 'The last value used for the specific sequence entry. Default value at creation will be 0. The generation of the values will be managed by the Persistence Layer.',
   primary key (IDSEQUENCE)
);
