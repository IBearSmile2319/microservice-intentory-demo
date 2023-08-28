package com.ibearsmile.inventory_service.utils;

import com.ibearsmile.inventory_service.models.entities.Inventory;
import com.ibearsmile.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info("Loading data...");
        if (inventoryRepository.findAll().isEmpty()){
            inventoryRepository.saveAll(
                    List.of(
                            Inventory.builder().sku("sku1").quantity(10L).build(),
                            Inventory.builder().sku("sku2").quantity(20L).build(),
                            Inventory.builder().sku("sku3").quantity(30L).build(),
                            Inventory.builder().sku("sku4").quantity(40L).build(),
                            Inventory.builder().sku("sku5").quantity(50L).build()
                    )
            );
        }
        log.info("Data loaded. Inventory count: {}", inventoryRepository.count());
    }

}
