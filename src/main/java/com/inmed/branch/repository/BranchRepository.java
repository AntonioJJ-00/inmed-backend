package com.inmed.branch.repository;


import com.inmed.branch.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BranchRepository
        extends JpaRepository<Branch,Long> {


    boolean existsByCode(String code);


    Optional<Branch> findByCode(String code);


}