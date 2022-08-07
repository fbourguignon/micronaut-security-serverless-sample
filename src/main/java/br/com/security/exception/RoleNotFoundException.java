package br.com.security.exception;

public class RoleNotFoundException extends BusinessException {
    public RoleNotFoundException(String message) {
        super(message);
    }
}
