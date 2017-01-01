# cics-liberty-minibank-example

CICS Minibank is a fully portable JEE7 application which can be developed and tested on laptops with Websphere Development Toolkit(WDT), and
can be ported to CICS Liberty as production environment. The application contains two main parts:

1. A frontend application using JSF, CDI, JAX-RS and JSONP to receive user input from Web pages and to pass request to a bankend application with JSON. 
The Java source components and the artifact of the frontend application can be found in the Minibank-JEE7-Frontend directory in this repository.

1. A backend application using JAX-RS, JSONP, CDI, JPA to update/query database based on the content in JSON requests. 
The Java source components and the artifact of the backend application can be found in the Minibank-JEE7-Backend directory in this repository.


## License
This project is licensed under [Apache License Version 2.0](LICENSE).   

## Contents

###Frontend application
- Minibank-JEE7-Frontend/src
- Minibank-JEE7-Frontend/WebContent
- Minibank-JEE7-Frontend/wlp
- Minibank-JEE7-Frontend/wlp_CICS
- Minibank-JEE7-Frontend/com.ibm.cicsdev.minibank.frontend.war


###Backend application
- Minibank-JEE7-Backend/src
- Minibank-JEE7-Backend/WebContent
- Minibank-JEE7-Backend/wlp
- Minibank-JEE7-Backend/wlp_CICS
- Minibank-JEE7-Backend/com.ibm.cicsdev.minibank.backend.war

###Database table definition
- Minibank_DDL_DB2.jcl
- Minibank_DDL_Derby.sql

## Pre-reqs

* CICS TS V5.3 or later
* Java SE 1.7 or later on the z/OS system
* Java SE 1.7 or later on the workstation
* Eclipse with WebSphere Developer Tools and CICS Explorer SDK installed


## Configuration


### To try the samples in Eclipse:


### To port the samples in CICS Liberty


Welcome contribution!
