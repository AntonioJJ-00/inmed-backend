package com.inmed.inventory.bootstrap;


import com.inmed.branch.entity.Branch;
import com.inmed.branch.repository.BranchRepository;

import com.inmed.inventory.entity.Inventory;
import com.inmed.inventory.repository.InventoryRepository;

import com.inmed.product.entity.Product;
import com.inmed.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;



@Component
@RequiredArgsConstructor
public class InventoryBootstrap
        implements CommandLineRunner {



    private final InventoryRepository inventoryRepository;


    private final BranchRepository branchRepository;


    private final ProductRepository productRepository;




    @Override
    public void run(String... args) {


        if(inventoryRepository.count() > 0){

            return;

        }



        Branch branch =
                branchRepository
                        .findAll()
                        .stream()
                        .findFirst()
                        .orElse(null);

        Product product =
                productRepository
                        .findAll()
                        .stream()
                        .findFirst()
                        .orElse(null);


        if(branch == null || product == null){

            System.out.println(
                    "Inventory bootstrap skipped. Branch or Product missing."
            );

            return;

        }



        Inventory inventory =
                Inventory.builder()

                        .branch(branch)

                        .product(product)

                        .stock(100)

                        .reservedStock(0)

                        .minimumStock(10)

                        .maximumStock(500)

                        .lastMovement(
                                LocalDateTime.now()
                        )

                        .lastSync(null)

                        .version(1L)

                        .enabled(true)

                        .build();



        inventoryRepository.save(
                inventory
        );



        System.out.println(
                "DEFAULT INVENTORY CREATED"
        );


    }

}