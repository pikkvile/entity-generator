package com.pikkvile.tools.eg;

public class EgException extends RuntimeException {
    public EgException(String message, Throwable cause) {
        super(message, cause);
    }

    public EgException(String s) {
        super(s);
    }
}
