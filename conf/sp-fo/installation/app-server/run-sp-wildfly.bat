set "JAVA_OPTS=-Xms64M -Xmx512M -XX:MaxPermSize=256M"
set "JAVA_OPTS=%JAVA_OPTS% -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -Djava.net.preferIPv4Stack=true"
set "JAVA_OPTS=%JAVA_OPTS% -Dorg.jboss.resolver.warning=true"
set "JAVA_OPTS=%JAVA_OPTS% -Djboss.modules.system.pkgs=org.jboss.byteman"
set "JAVA_OPTS=%JAVA_OPTS% -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=n"
set "JAVA_OPTS=%JAVA_OPTS% -Dfile.encoding=utf-8"
set "JAVA_OPTS=%JAVA_OPTS% -Dsp.config=@uiConfigDir@/"
set "JAVA_OPTS=%JAVA_OPTS% -Dorg.owasp.esapi.SecurityConfiguration=eu.ohim.sp.common.security.esapi.EsapiJndiConfiguration"
set "JAVA_OPTS=%JAVA_OPTS% -Dorg.jboss.weld.serialization.beanIdentifierIndexOptimization=false
set "JAVA_OPTS=%JAVA_OPTS% -Ddrools.dialect.mvel.strict=false"

standalone.bat -c standalone-sp.xml -P @spCoreConfigDir.local@/sp-core.properties -b=0.0.0.0