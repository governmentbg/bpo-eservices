package eu.ohim.sp.integration.adapter.mockticket.authentication.exception;

import org.springframework.security.core.AuthenticationException;

public class MockAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 7603508739985219194L;

    public MockAuthenticationException(String msg) {
        super(msg);
    }

}
