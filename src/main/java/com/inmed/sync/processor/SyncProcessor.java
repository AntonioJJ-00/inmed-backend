package com.inmed.sync.processor;

import com.inmed.sync.dto.SyncItem;

public interface SyncProcessor {

    String entity();

    void process(SyncItem item);

}