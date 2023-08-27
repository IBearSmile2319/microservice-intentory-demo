package com.ibearsmile.products_service.model.entities.dtos;

public record ProductRequest(
        String sku,
        String name,
        String description,
        Double price,
        Boolean status
) {
}
