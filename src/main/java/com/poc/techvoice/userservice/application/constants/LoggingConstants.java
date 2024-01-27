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


    // user login
    public static final String USER_LOGIN_REQUEST_INITIATED = "USER LOGIN REQUEST INITIATED";
    public static final String USER_LOGIN_RESPONSE_SENT = "USER LOGIN RESPONSE SENT";
    public static final String USER_LOGIN_LOG = "USER LOGIN LOG | MESSAGE: {} | OPERATION: {}";
    public static final String USER_LOGIN_ERROR = "USER LOGIN ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";

    public static final String TOKEN_GENERATION_LOG = "TOKEN GENERATION LOG | MESSAGE: {} | OPERATION: {}";
    public static final String TOKEN_GENERATION_ERROR = "TOKEN GENERATION ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";



    // refresh token
    public static final String REFRESH_TOKEN_REQUEST_INITIATED = "REFRESH TOKEN REQUEST INITIATED";
    public static final String REFRESH_TOKEN_RESPONSE_SENT = "REFRESH TOKEN RESPONSE SENT";
    public static final String REFRESH_TOKEN_LOG = "REFRESH TOKEN LOG | MESSAGE: {} | OPERATION: {}";
    public static final String REFRESH_TOKEN_ERROR = "REFRESH TOKEN ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";

    public static final String TOKEN_LOG = "TOKEN LOG | MESSAGE: {} | OPERATION: {}";
    public static final String TOKEN_ERROR = "TOKEN ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";


    // user logout
    public static final String USER_LOGOUT_REQUEST_INITIATED = "USER LOGOUT REQUEST INITIATED";
    public static final String USER_LOGOUT_RESPONSE_SENT = "USER LOGOUT RESPONSE SENT";
    public static final String USER_LOGOUT_LOG = "USER LOGOUT LOG | MESSAGE: {} | OPERATION: {}";
    public static final String USER_LOGOUT_ERROR = "USER LOGOUT ERROR | MESSAGE: {} | ERROR REASON: {}, ERROR STACKTRACE: {}";

}
