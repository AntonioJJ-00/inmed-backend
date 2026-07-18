package com.inmed.sync.entity;

import com.inmed.pos.entity.PosDevice;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sync_batches")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String batchUuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private PosDevice posDevice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SyncStatus status;

    @Column(nullable = false)
    private Integer totalOperations;

    @Column(nullable = false)
    private Integer processedOperations;

    @Column(nullable = false)
    private LocalDateTime receivedAt;

    private LocalDateTime finishedAt;

    private Integer successOperations;

    private Integer failedOperations;

    private Integer conflictOperations;

    @PrePersist
    public void onCreate() {

        receivedAt = LocalDateTime.now();

        if(status == null){
            status = SyncStatus.PENDING;
        }

        if(processedOperations == null){
            processedOperations = 0;
        }
    }
}