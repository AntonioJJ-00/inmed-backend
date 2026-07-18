package com.inmed.sync.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SyncItem {

    private String entity;

    private String operation;

    private String payload;

}