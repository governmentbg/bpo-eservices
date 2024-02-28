#!/bin/sh

export JAVA_OPTS="-Xms256M\
	-Xmx2048M\
	-XX:MaxPermSize=256M\
	-Dsun.rmi.dgc.client.gcInterval=3600000\
	-Dsun.rmi.dgc.server.gcInterval=3600000\
	-Djava.net.preferIPv4Stack=true\
	-Dorg.jboss.resolver.warning=true\
	-Djboss.modules.system.pkgs=org.jboss.byteman\
    -Dsp.config=@uiConfigDir@/\
    -Dorg.owasp.esapi.SecurityConfiguration=eu.ohim.sp.common.security.esapi.EsapiJndiConfiguration\
    -Dorg.jboss.weld.serialization.beanIdentifierIndexOptimization=false\
    -Ddrools.dialect.mvel.strict=false\
	-Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"

@jbossHome@/bin/standalone.sh -c standalone-sp.xml -P @spCoreConfigDir.local@/sp-core.properties -b=0.0.0.0

