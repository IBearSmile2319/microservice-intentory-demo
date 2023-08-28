package com.ibearsmile.inventory_service.controllers;

import com.ibearsmile.inventory_service.models.dtos.BaseResponse;
import com.ibearsmile.inventory_service.models.dtos.OrderItemsRequest;
import com.ibearsmile.inventory_service.services.InventoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryServices inventoryService;

    @GetMapping("/{sku}")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkInventory(@PathVariable("sku") String sku) {
        return inventoryService.isInStock(sku);
    }

    @PostMapping("/in-stock")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse areInStock(@RequestBody List<OrderItemsRequest> orderItems) {
        return inventoryService.areInStock(orderItems);
    }

}
