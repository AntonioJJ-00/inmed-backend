package com.inmed.sync.entity;

import jakarta.persistence.*;
import lombok.*;
import com.inmed.pos.entity.PosDevice;
import java.time.LocalDateTime;

@Entity
@Table(name = "sync_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private SyncBatch batch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pos_id", nullable = false)
    private PosDevice posDevice;

    @Column(nullable = false)
    private String entityName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncOperation operation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncStatus status;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;


    @Column(columnDefinition = "TEXT")
    private String errorMessage;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime processedAt;

    @Column(nullable = false)
    private Integer retryCount;

    @Column(nullable = false)
    private Integer maxRetries;

    private LocalDateTime nextRetryAt;

    @Column(nullable =false)
    private Boolean retryable;

    private String deviceVersion;

    private String serverVersion;

    @PrePersist
    public void onCreate(){
        createdAt = LocalDateTime.now();
        if(status == null){
            status = SyncStatus.RECEIVED;
        }
        if(retryCount == null){
            retryCount = 0;
        }

        if(maxRetries == null){
            maxRetries = 5;
        }

        if(retryable == null){
            retryable = true;
        }
    }


}