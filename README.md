# cics-liberty-minibank-example

CICS Minibank is a fully portable JEE7 application which can be developed and tested on laptops with Websphere Development Toolkit(WDT), and
can be ported to CICS Liberty as production environment. The application contains two main parts:

1. A frontend application using JSF, CDI, JAX-RS and JSONP to receive user input from Web pages and to pass request to a bankend application with JSON. 
The Java source components and the artifact of the frontend application can be found in the Minibank-JEE7-Frontend directory in this repository.

1. A backend application using JAX-RS, JSONP, CDI, JPA to update/query database based on the content in JSON requests. 
The Java source components and the artifact of the backend application can be found in the Minibank-JEE7-Backend directory in this repository.

Besides these 2 parts, we need you to create a derby database for storage.We have provided the DDL for creating tables required by this application. 


## License
This project is licensed under [Apache License Version 2.0](LICENSE).   

## Contents

###Frontend application
- Minibank-JEE7-Frontend/src

	Source code of frontend.In **entity** folder, 
- Minibank-JEE7-Frontend/WebContent

	Web pages' source code and configurations for web layer, e.g. web.xml
- Minibank-JEE7-Frontend/wlp

	server.xml configuration for frontend Liberty Server.
- Minibank-JEE7-Frontend/com.ibm.cicsdev.minibank.frontend.war

	war bundle for frontend. It's independent from the files above, without any coding and import steps, just put it in dropins file of frontend server, then the frontend part can easily run.


###Backend application
- Minibank-JEE7-Backend/src
	
	Source code of backend.
- Minibank-JEE7-Backend/WebContent
	
	Web configuration files in this folder, e.g. web.xml
- Minibank-JEE7-Backend/wlp 
	
	server.xml configuration for backend Liberty Server. etc.
- Minibank-JEE7-Backend/com.ibm.cicsdev.minibank.backend.war
	
	war bundle for backend. Like frontend, you can put it in dropins file of backend server, then the backend part can run on server automatically.
	
###Database table definition
- Minibank_DDL_DB2.jcl

	DDL file for DB2, you can use this JCL to create the DB2 database required by Minibank on z/OS.
- Minibank_DDL_Derby.sql
	
	DDL file for Derby database creation, it's more easy in your local environment.

## Pre-reqs

* CICS TS V5.3 or later
* Java SE 1.7 or later on the z/OS system
* Java SE 1.7 or later on the workstation
* Eclipse with WebSphere Developer Tools and CICS Explorer SDK installed


## Configuration


### To try the samples in Eclipse:
1. First, create a derby database in your laptop. We have already provided the DDL for the database needed, when you successfully create the database, set it to Server mode and start.It will automatically listen on port 1527. 

1. In your eclipse Servers view, create a Liberty server for backend. And replace the server.xml with the one that we provide you in Minibank-JEE7-Backend-->wlp-->server.xml.
	Something need to be changed here in server.xml,for label <dataSource>,please change the 'databaseName' to your own derby database path,and the same for label <library>,change the 'fileset dir' to your derby's installation path for libraries.
	After that,put the Minibank-JEE7-Backend-->com.ibm.cicsdev.minibank.backend.war in backend liberty server's 'dropins' file,then it will deploy and run automatically.
1. The last step is for frontend part. Also you need to create another Liberty server for frontend.And replace the server.xml with the one we provide in Minibank-JEE7-Frontend-->wlp-->server.xml.you don't need to change anything here.
	Then put Minibank-JEE7-Frontend-->com.ibm.cicsdev.minibank.frontend.war in this backend liberty server's 'dropins' file.
	
After these 3 steps above, visit 'localhost:9080/com.ibm.cicsdev.minibank.frontend/' in your browser.And now you can enjoy your Minibank Application!
	

### To port the samples in CICS Liberty


Welcome contribution!
