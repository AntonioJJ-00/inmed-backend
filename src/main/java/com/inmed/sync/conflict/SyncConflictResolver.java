package com.inmed.sync.conflict;

import com.inmed.sync.dto.SyncItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SyncConflictResolver {
    /**
     * Determina si una operación presenta conflicto.
     *
     * Primera versión:
     * Siempre acepta.
     */
    public boolean hasConflict(
            SyncItem item
    ) {
        log.debug(
                "Checking conflict for entity {}",
                item.getEntity()
        );
        return false;
    }
}