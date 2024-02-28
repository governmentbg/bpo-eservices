package eu.ohim.sp.integration.adapter.cas.exception;

import org.springframework.security.core.AuthenticationException;

public class CASAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 7603508739985219194L;

    public CASAuthenticationException(String msg) {
        super(msg);
    }

}
