package com.poc.techvoice.userservice.application.constants;

public class LoggingConstants {

    private LoggingConstants() {
    }

    public static final String FULL_REQUEST = "FULL REQUEST | CONTROLLER REQUEST: {}";
    public static final String FULL_RESPONSE = "FULL RESPONSE | CONTROLLER RESPONSE: {}";
    public static final String STARTED = "Started";
    public static final String ENDED = "Ended";
    public static final String VALIDATION_ERROR = "REQUEST VALIDATION ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";


    // create new user
    public static final String USER_CREATE_REQUEST_INITIATED = "USER CREATE REQUEST INITIATED";
    public static final String USER_CREATE_RESPONSE_SENT = "USER CREATE RESPONSE SENT";
    public static final String USER_CREATE_LOG = "USER CREATE LOG | MESSAGE: {} | OPERATION: {}";
    public static final String USER_CREATE_ERROR = "USER CREATE ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";

}
