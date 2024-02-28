set JAVA_OPTS=-XX:MaxPermSize=312m -Xmx812m
set CATALINA_OPTS=-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dsp.config="@uiConfigDir@" -Dorg.owasp.esapi.SecurityConfiguration=eu.ohim.sp.common.security.esapi.EsapiJndiConfiguration -Dfile.encoding="utf-8"
