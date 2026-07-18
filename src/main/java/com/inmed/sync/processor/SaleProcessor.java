package com.inmed.sync.processor;

import com.inmed.sync.dto.SyncItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SaleProcessor
        implements SyncProcessor {

    @Override
    public String entity() {
        return "SALE";
    }

    @Override
    public void process(SyncItem item) {
        log.info("Processing SALE {}", item.getPayload());
    }
}