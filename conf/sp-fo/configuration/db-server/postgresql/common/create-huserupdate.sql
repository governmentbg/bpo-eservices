SET search_path TO efiling, public;

/*==============================================================*/
/* Table: HUSERUPDATE                                             */
/*==============================================================*/

CREATE TABLE HUSERUPDATE
(
  IDUSERUPDATE bigint NOT NULL,
  LOGUSERNAMEOLD character varying(100),
  LOGUSERNAMENEW character varying(100),
  IDFORM bigint,
  DTCREATED timestamp with time zone,
  UPDATEDBY character varying(100),
  PRIMARY KEY (IDUSERUPDATE)
);


COMMENT ON COLUMN HUSERUPDATE.IDUSERUPDATE IS 'ID of the user update record. This ID will be generated using the TSequence table with IdSequence = "UserUpdate"';
COMMENT ON COLUMN HUSERUPDATE.LOGUSERNAMEOLD IS 'THE OLD USER NAME';
COMMENT ON COLUMN HUSERUPDATE.LOGUSERNAMENEW IS 'THE NEW USER NAME';
COMMENT ON COLUMN HUSERUPDATE.IDFORM IS 'The draft application whose user is changed';
COMMENT ON COLUMN HUSERUPDATE.DTCREATED IS 'The date when this user was set, i.e. when the draft application had its user changed';
COMMENT ON COLUMN HUSERUPDATE.UPDATEDBY IS 'THE USER WHO DID THE CHANGE';

INSERT INTO TSEQUENCE (
IDSEQUENCE ,
NRLASTVALUE
)
VALUES (
'UserUpdate', '0'
);