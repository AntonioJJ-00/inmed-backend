package com.inmed.company.bootstrap;

import com.inmed.company.entity.Company;
import com.inmed.company.entity.CompanyStatus;
import com.inmed.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyBootstrap implements CommandLineRunner {

    private final CompanyRepository repository;

    @Override
    public void run(String... args) {

        if (repository.count() > 0) {
            return;
        }

        Company company = Company.builder()
                .businessName("INMED SOLUCIONES SA DE CV")
                .tradeName("INMED")
                .taxId("XAXX010101000")
                .email("empresa@inmed.com")
                .phone("")
                .website("")
                .status(CompanyStatus.ACTIVE)
                .build();

        repository.save(company);
    }
}