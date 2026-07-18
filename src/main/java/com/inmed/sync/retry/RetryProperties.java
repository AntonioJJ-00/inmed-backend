package com.inmed.sync.retry;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(
        prefix = "sync.retry"
)
public class RetryProperties {


    private int maxAttempts = 5;


    private long initialDelaySeconds = 30;


}