package com.ibearsmile.orders_service.models.dtos;

public record OrderItemsResponse(
        Long id,
        String sku,
        Double price,
        Long quantity
) {
}
