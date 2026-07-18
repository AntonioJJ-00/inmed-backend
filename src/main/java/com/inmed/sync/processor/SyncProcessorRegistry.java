package com.inmed.sync.processor;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class SyncProcessorRegistry {

    private final List<SyncProcessor> processors;

    private final Map<String, SyncProcessor> registry =
            new HashMap<>();

    @PostConstruct
    public void initialize() {
        processors.forEach(processor ->
                registry.put(
                        processor.entity().toUpperCase(),
                        processor
                )
        );
    }
    public SyncProcessor getProcessor(String entity) {
        return registry.get(entity.toUpperCase());
    }
}