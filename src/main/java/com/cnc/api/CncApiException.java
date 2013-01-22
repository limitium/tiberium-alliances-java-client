package com.cnc.api;

import org.json.simple.parser.ParseException;

public class CncApiException extends java.lang.Exception {
    public CncApiException(String message) {
        super(message);

    }

    public CncApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
