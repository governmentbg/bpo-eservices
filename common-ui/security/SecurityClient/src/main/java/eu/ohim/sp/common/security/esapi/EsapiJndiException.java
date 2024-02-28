package eu.ohim.sp.common.security.esapi;

/**
 * Thrown when any problems with loading esapi properties files using JNDI property occurs
 */
class EsapiJndiException extends Exception {
    public EsapiJndiException(String message, Throwable cause) {
        super(message, cause);
    }
}
