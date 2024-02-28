SET search_path TO efiling, public;
drop table TSEQUENCE cascade;
drop table HSTATUSUPDATE cascade;
drop table DSETTINGVALUE cascade;
drop table DCONFIGSETTING cascade;
drop table DFORM cascade;
drop table PCONFIGPARAM cascade;
drop table CCOMPONENT cascade;
drop table CFORMTYPE cascade;
drop table CFORMSTATUS cascade;
drop table hsignstatusupdate cascade;
drop SEQUENCE CCOMPONENT_SEQ;
drop SEQUENCE PCONFIGPARAM_SEQ;
drop SEQUENCE DCONFIGSETTING_SEQ;
drop SEQUENCE DSETTINGVALUE_SEQ;

drop schema efiling;