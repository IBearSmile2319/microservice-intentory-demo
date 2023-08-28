package com.ibearsmile.inventory_service.models.dtos;

public record OrderItemsRequest(
        Long id,
        String sku,
        Double price,
        Long quantity
) {
}
