package com.inmed.sync.entity;

public enum SyncStatus {
    PENDING,
    PROCESSING,
    SUCCESS,
    FAILED,
    RECEIVED,
    QUEUED,
    RETRYING,
    CONFLICT
}