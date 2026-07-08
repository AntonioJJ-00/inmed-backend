package com.inmed.company.repository;


import com.inmed.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface CompanyRepository
        extends JpaRepository<Company, Long> {

    Optional<Company> findTopByOrderByIdAsc();
    //Optional<Company> findFirstBy();
}