//CREATEDB    JOB MSGCLASS=H,MSGLEVEL=(1,1),                    
//         REGION=0M,CLASS=A,NOTIFY=&SYSUID                     
//JOBLIB   DD  DSN=SYS2.DB2.V10.SDSNLOAD,DISP=SHR               
//DDLTABLE EXEC PGM=IKJEFT01,DYNAMNBR=20,TIME=1440,REGION=32M   
//SYSTSPRT DD SYSOUT=*                                          
//SYSPRINT DD SYSOUT=*                                          
//SYSUDUMP DD SYSOUT=*                                          
//SYSTSIN DD *                                                  
 DSN SYSTEM(DSNB)                                               
 RUN  PROGRAM(DSNTEP2) PLAN(DSNTEP10) +                         
      LIB('SYS2.DB2.V10.DSNT.RUNLIB.LOAD') +                    
      PARMS('/ALIGN(MID)')                                      
//SYSIN    DD  *                                                
 SET CURRENT SCHEMA = 'MINIBANK';                                   
 COMMIT;                                                        
 DROP   DATABASE DBMINI;                                        
 CREATE DATABASE DBMINI;                                        
 CREATE TABLESPACE TSMINI IN DBMINI  LOCKSIZE ROW;              
 CREATE TABLE  CUSTOMER   (                                     
        CUSTOMERID        CHAR ( 10 )    NOT NULL               
      , NAME              CHAR ( 30 )    NOT NULL               
      , GENDER            CHAR ( 01 )    NOT NULL               
      , AGE               CHAR ( 03 )    NOT NULL               
      , ADDRESS           CHAR ( 80 )    NOT NULL               
        ) IN DBMINI.TSMINI;                                     
 CREATE UNIQUE INDEX  INDX_CUST ON CUSTOMER  (                  
                     CUSTOMERID  ASC);                          
 ALTER TABLE  CUSTOMER   ADD CONSTRAINT PK_CUST                 
        PRIMARY KEY (CUSTOMERID);                               
                                                                
 CREATE TABLE  ACCOUNT    (                                     
        ACCOUNTNUMBER     CHAR ( 10 )    NOT NULL               
      , CUSTOMERID        CHAR ( 10 )    NOT NULL               
      , BALANCE           DECIMAL ( 15, 3 ) NOT NULL            
      , LASTCHANGETIME    TIMESTAMP                             
        ) IN DBMINI.TSMINI;                                     
 CREATE UNIQUE INDEX  INDX_ACCT ON ACCOUNT   (                  
                  ACCOUNTNUMBER  ASC);                          
 ALTER TABLE  ACCOUNT    ADD CONSTRAINT PK_ACCT                 
        PRIMARY KEY (ACCOUNTNUMBER);                            
                                                                
 CREATE TABLE  TRANSHISTORY    (                                
        TRANSNAME         CHAR ( 10 )    NOT NULL               
      , ACCOUNTNUMBER     CHAR ( 10 )    NOT NULL               
      , TRANSAMOUNT       DECIMAL ( 15, 3 ) NOT NULL            
      , TRANSTIME         TIMESTAMP                             
        ) IN DBMINI.TSMINI;                                     
                                                                
  ALTER TABLE  ACCOUNT     ADD                                  
         FOREIGN KEY FK_ACCT (CUSTOMERID)                       
         REFERENCES  CUSTOMER (CUSTOMERID)                      
         ON DELETE NO ACTION;                                   
                                                 
  ALTER TABLE  TRANSHISTORY ADD                  
        FOREIGN KEY FK_TRAN (ACCOUNTNUMBER)      
        REFERENCES  ACCOUNT      (ACCOUNTNUMBER) 
        ON DELETE NO ACTION;         
                            
COMMIT;                                          
