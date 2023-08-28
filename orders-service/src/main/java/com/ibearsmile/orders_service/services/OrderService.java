package com.ibearsmile.orders_service.services;

import com.ibearsmile.orders_service.models.dtos.*;
import com.ibearsmile.orders_service.models.entities.Order;
import com.ibearsmile.orders_service.models.entities.OrderItems;
import com.ibearsmile.orders_service.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    public void placeOrder(OrderRequest orderRequest) {

//        check for inventory
        BaseResponse result = this.webClientBuilder.build()
                .post()
                .uri("http://localhost:8083/api/inventory/in-stock")
                .bodyValue(orderRequest.orderItems())
                .retrieve()
                .bodyToMono(BaseResponse.class)
                .block();
        if(result != null && !result.hasErrors()){
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.orderItems().stream()
                    .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                    .toList());
            this.orderRepository.save(order);
        }else {
            throw new IllegalArgumentException("Some of the products are not in stock");
        }
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream()
                .map(this::mapToOrderResponse)
                .toList();

    }

    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderItems().stream()
                        .map(this::mapToOrderItemRequest)
                        .toList()
        );
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemsResponse(
                orderItems.getId(),
                orderItems.getSku(),
                orderItems.getPrice(),
                orderItems.getQuantity()
        );
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemsRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.id())
                .sku(orderItemRequest.sku())
                .price(orderItemRequest.price())
                .quantity(orderItemRequest.quantity())
                .order(order)
                .build();

    }

}
