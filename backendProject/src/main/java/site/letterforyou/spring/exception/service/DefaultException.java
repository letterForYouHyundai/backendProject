package site.letterforyou.spring.exception.service;

public class DefaultException extends RuntimeException {
	
	private static final String message = "알 수 없는 Exception";
	
	public DefaultException() {
		super(message);
	}
	
	public DefaultException(String message) {
        super(message);
    }

    public DefaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public DefaultException(Throwable cause) {
        super(cause);
    }

}
