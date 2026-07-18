package com.inmed.configuration.bootstrap;

import com.inmed.company.entity.Company;
import com.inmed.company.repository.CompanyRepository;
import com.inmed.configuration.entity.Configuration;
import com.inmed.configuration.entity.ConfigurationScope;
import com.inmed.configuration.repository.ConfigurationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ConfigurationBootstrap
        implements CommandLineRunner {

    private final ConfigurationRepository configurationRepository;

    private final CompanyRepository companyRepository;

    @Override
    public void run(String... args) {

        if (configurationRepository.count() > 0) {
            return;
        }

        Company company = companyRepository
                .findTopByOrderByIdAsc()
                .orElse(null);

        if (company == null) {
            return;
        }

        List<Configuration> configurations = List.of(

                Configuration.builder()
                        .company(company)
                        .scope(ConfigurationScope.GLOBAL)
                        .configKey("DEFAULT_TAX")
                        .configValue("16")
                        .description("Default VAT percentage")
                        .enabled(true)
                        .build(),

                Configuration.builder()
                        .company(company)
                        .scope(ConfigurationScope.GLOBAL)
                        .configKey("DEFAULT_CURRENCY")
                        .configValue("MXN")
                        .description("System currency")
                        .enabled(true)
                        .build(),

                Configuration.builder()
                        .company(company)
                        .scope(ConfigurationScope.GLOBAL)
                        .configKey("TIME_ZONE")
                        .configValue("America/Mexico_City")
                        .description("Default timezone")
                        .enabled(true)
                        .build(),

                Configuration.builder()
                        .company(company)
                        .scope(ConfigurationScope.GLOBAL)
                        .configKey("ALLOW_NEGATIVE_STOCK")
                        .configValue("false")
                        .description("Allow sales without stock")
                        .enabled(true)
                        .build(),

                Configuration.builder()
                        .company(company)
                        .scope(ConfigurationScope.GLOBAL)
                        .configKey("FORCE_PRINT_TICKET")
                        .configValue("true")
                        .description("Ticket printing required")
                        .enabled(true)
                        .build(),

                Configuration.builder()
                        .company(company)
                        .scope(ConfigurationScope.GLOBAL)
                        .configKey("SYNC_INTERVAL_MINUTES")
                        .configValue("30")
                        .description("Synchronization interval")
                        .enabled(true)
                        .build()

        );

        configurationRepository.saveAll(configurations);

        System.out.println(
                "DEFAULT CONFIGURATIONS CREATED"
        );

    }

}