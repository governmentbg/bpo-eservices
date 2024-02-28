# Harmonization release maker HOWTO
## Download Tomcat and JBoss installation 

It's important its vanilla installation without any custom changes

* [Apache Tomcat 7.0.50](http://ftp.cixug.es/apache/tomcat/tomcat-7/v7.0.50/bin/apache-tomcat-7.0.50.zip)
* [JBossAS 7.1.1](http://download.jboss.org/jbossas/7.1/jboss-as-7.1.1.Final/jboss-as-7.1.1.Final.zip)

## Install Gradle

[Gradle installation](http://www.gradle.org/installation) - basically download archive and add Gradle bin directory to your PATH environmental variable

## Checkout 'conf' SP module

    https://svn.oami.europa.eu/svn/emrepo/softwarepackage/conf/trunk
    
So that structure of your sources directory is:

    .
	├── common-ui
	├── common-utils
	├── conf
	├── core
	├── ds-efiling
	├── external
	├── integration
	├── pom.xml
	└── tm-efiling
	
## Create your environment specific configuration

Copy `conf/installation/scripts/gradle.properties.default` to `conf/installation/scripts/gradle.properties` and update properties according to your needs.
	
## Build release

When all modules (except _conf_) are built, go to _conf_ module and run:

    mvn clean install -P <application profiles>
    
Where application profiles are:

* tm-efiling
* ds-efiling
* eservices

For example, in order to build release package containing *tm-efiling* and *ds-efiling* run:

    mvn clean install -P tm-efiling,ds-efiling

## Install release in your local environment

Go to `conf/target/sp-release/scripts` and run gradle:

* `gradle -q installAll` - to setup Tomcat and JBoss, install SP configuration, install SP binaries, filter SQLs

For list of all available tasks run `gradle tasks --all`:

* **installAll** - Installs everything to clean environment. Configures Tomcat and JBoss, filters SQLs
    * **filterSqls** - Filters SQLs placeholder against gradle.properties file
    * **installCoreApp** - Installs Core EAR and 'modules' to JBoss
    * **installCoreConfiguration** - Installs Core modules configuration files into spCoreConfigDir
    * **installJBossConfiguration** - Installs JBoss configuration like standalone-*.xml, application-users.properties, Jackrabbit
    * **installTomcatConfiguration** - Installs Tomcat setenv.sh/setenv.bat into Tomcat bin directory
    * **installUIApp** - Installs UI WAR files into Tomcat webapps directory
    * **installUIConfiguration** - Installs UI modules configuration like common config, project specific config and jboss-ejb-client into uiConfigDir
* **installEsbApp** - Installs integration modules and their configuration to Mule ESB
* **installEsbConfiguration** - Installs JBoss client JAR files Mule ESB lib endorsed directory


## Execute SQL files

Filtered SQL files are stored in `conf/target/sp-release/conf/sp-fo/db-server-updated`. Unfortunately for now, you need to execute them manually.

## Run JBoss

Run JBoss with command 
    
    standalone.bat -c standalone-mysql.xml -P <path to sp core configuration>/sp-core.properties
    
or with shorthand bat script:

    run-sp-jboss.bat








