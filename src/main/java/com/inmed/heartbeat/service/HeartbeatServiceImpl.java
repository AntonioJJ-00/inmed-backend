package com.inmed.heartbeat.service;

import com.inmed.PosCredential.repository.PosCredentialRepository;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.heartbeat.dto.HeartbeatRequest;
import com.inmed.heartbeat.dto.HeartbeatResponse;
import com.inmed.pos.entity.PosDevice;
import com.inmed.pos.entity.PosStatus;
import com.inmed.pos.repository.PosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.inmed.PosCredential.repository.PosCredentialRepository;
import com.inmed.PosCredential.entity.PosCredential;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HeartbeatServiceImpl
        implements HeartbeatService {

    private final PosRepository posRepository;
    private final PosCredentialRepository credentialRepository;

    @Override
    @Transactional
    public HeartbeatResponse receiveHeartbeat(
            Authentication authentication,
            HeartbeatRequest request
    ) {
        String username = authentication.getName();

        PosCredential credential =
                credentialRepository.findByUsername(username)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "POS credential not found"
                                )
                        );

        PosDevice pos = credential.getPosDevice();

        pos.setLastHeartbeat(LocalDateTime.now());

        pos.setAppVersion(request.getAppVersion());

        pos.setStatus(PosStatus.ACTIVE);

        pos.setLastIp(request.getIpAddress());

        pos.setHostname(request.getHostname());

        pos.setOperatingSystem(request.getOperatingSystem());

        posRepository.save(pos);

        return HeartbeatResponse.builder()
                .serverTime(LocalDateTime.now().toString())
                .syncRequired(false)
                .updateAvailable(false)
                .latestVersion(request.getAppVersion())
                .maintenanceMode(false)
                .message("POS operating normally")
                .build();
    }
}


