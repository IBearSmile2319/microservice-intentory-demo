package com.ibearsmile.orders_service.controllers;

import com.ibearsmile.orders_service.models.dtos.OrderRequest;
import com.ibearsmile.orders_service.models.dtos.OrderResponse;
import com.ibearsmile.orders_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderResquest){
        this.orderService.placeOrder(orderResquest);
        return "Order placed successfully";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderResponse> getAllOrders(){
        return this.orderService.getAllOrders();
    }

}
