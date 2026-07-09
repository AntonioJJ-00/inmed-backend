package com.inmed.pos.repository;

import com.inmed.branch.entity.Branch;
import com.inmed.pos.entity.PosDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PosRepository
        extends JpaRepository<PosDevice, Long> {

    Optional<PosDevice> findBySerialNumber(String serialNumber);

    Optional<PosDevice> findByCode(String code);

    boolean existsBySerialNumber(String serialNumber);

    boolean existsByCode(String code);

    List<PosDevice> findByBranch(Branch branch);

}