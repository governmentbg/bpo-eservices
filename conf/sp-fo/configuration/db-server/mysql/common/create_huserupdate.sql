CREATE TABLE IF NOT EXISTS `huserupdate` (
  `IDUSERUPDATE` int(11) NOT NULL COMMENT 'ID of the user update record. This ID will be generated using the TSequence table with IdSequence = "UserUpdate".',
  `LOGUSERNAMEOLD` varchar(100) COMMENT 'THE OLD USER NAME',
  `LOGUSERNAMENEW` varchar(100) COMMENT 'THE NEW USER NAME',
  `IDFORM` int(11) NOT NULL COMMENT 'The draft application whose user is changed.',
  `DTCREATED` datetime NOT NULL COMMENT 'The date when this user was set, i.e. when the draft application had its user changed.',
  `UPDATEDBY` varchar(100) COMMENT 'THE USER WHO DID THE CHANGE',
  PRIMARY KEY (`IDUSERUPDATE`),
  KEY `FK_FORMSTATUS_IDFORM` (`IDFORM`)
);

INSERT INTO `tsequence` (
`IDSEQUENCE` ,
`NRLASTVALUE`
)
VALUES (
'UserUpdate', '0'
);


