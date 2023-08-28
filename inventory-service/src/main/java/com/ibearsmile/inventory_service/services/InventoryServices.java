package com.ibearsmile.inventory_service.services;

import com.ibearsmile.inventory_service.models.dtos.BaseResponse;
import com.ibearsmile.inventory_service.models.dtos.OrderItemsRequest;
import com.ibearsmile.inventory_service.models.entities.Inventory;
import com.ibearsmile.inventory_service.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServices {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);

        return inventory.filter(i -> i.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemsRequest> orderItems) {
        var errorList = new ArrayList<String>();

        List<String> skus = orderItems.stream().map(OrderItemsRequest::sku).toList();

        List<Inventory> inventories = inventoryRepository.findBySkuIn(skus);

        orderItems.forEach(orderItem -> {
            var inventory = inventories.stream().filter(i -> i.getSku().equals(orderItem.sku())).findFirst();

            if (inventory.isEmpty()) {
                errorList.add(String.format("Product whit sku %s not found", orderItem.sku()));
            } else if (inventory.get().getQuantity() < orderItem.quantity()) {
                errorList.add(String.format("Product whit sku %s has only %d items in stock", orderItem.sku(), inventory.get().getQuantity()));
            }
        });

        return errorList.isEmpty() ? new BaseResponse(null) : new BaseResponse(errorList.toArray(String[]::new));

    }
}
