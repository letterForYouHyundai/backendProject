package site.letterforyou.spring.exception.service;

public class NotAuthorizedUserException extends DefaultException {
    public NotAuthorizedUserException(String message) {
        super(message);
    }
}