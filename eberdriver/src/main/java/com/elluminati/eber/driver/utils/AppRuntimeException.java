package com.elluminati.eber.driver.utils;

/**
 * Created by elluminati on 16-Jan-17.
 */

public class AppRuntimeException extends RuntimeException {
    public AppRuntimeException(String message) {
        super(message);
    }

    public AppRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppRuntimeException(Throwable cause) {
        super(cause);
    }

}
