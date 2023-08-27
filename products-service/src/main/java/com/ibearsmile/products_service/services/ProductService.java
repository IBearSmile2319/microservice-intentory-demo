package com.ibearsmile.products_service.services;

import com.ibearsmile.products_service.model.entities.dtos.ProductRequest;
import com.ibearsmile.products_service.model.entities.Product;
import com.ibearsmile.products_service.model.entities.dtos.ProductResponse;
import com.ibearsmile.products_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void addProduct(ProductRequest productRequest) {
        var product = Product.builder()
                .sku(productRequest.sku())
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .status(productRequest.status())
                .build();
        productRepository.save(product);
        log.info("Product added: {}", product);
    }

    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }


    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }



}