package com.ibearsmile.orders_service.models.dtos;

import java.util.List;

public record OrderRequest(
        String orderNumber,
        List<OrderItemsRequest> orderItems
) {
}
