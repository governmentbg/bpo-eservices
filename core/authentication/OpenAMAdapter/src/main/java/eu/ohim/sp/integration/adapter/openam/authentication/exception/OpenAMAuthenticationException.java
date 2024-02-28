package eu.ohim.sp.integration.adapter.openam.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class OpenAMAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 7603508739985219194L;

    public OpenAMAuthenticationException(String msg) {
        super(msg);
    }

}
