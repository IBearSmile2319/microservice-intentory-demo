package com.ibearsmile.orders_service.models.dtos;

public record OrderItemsRequest(
        Long id,
        String sku,
        Double price,
        Long quantity
) {
}
