package com.inmed.PosCredential.repository;

import com.inmed.pos.entity.PosDevice;
import com.inmed.PosCredential.entity.PosCredential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PosCredentialRepository
        extends JpaRepository<PosCredential, Long> {

    Optional<PosCredential> findByUsername(
            String username
    );

    Optional<PosCredential> findByPosDevice(
            PosDevice posDevice
    );

    boolean existsByUsername(
            String username
    );

    boolean existsByPosDevice(
            PosDevice posDevice
    );
}