package com.inmed.auth.audit.enums;

public enum SecurityEventType {
    LOGIN_SUCCESS,
    LOGIN_FAILED,
    REFRESH_TOKEN_USED,
    LOGOUT,
    FORCE_LOGOUT,
    SESSION_REVOKED,
    PASSWORD_CHANGED
}