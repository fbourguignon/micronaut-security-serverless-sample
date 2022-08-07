package br.com.security.exception;

public class UserAlreadyRegisteredException extends BusinessException {
    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
