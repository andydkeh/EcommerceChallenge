package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.dto.product.CreateProductDTO;
import com.compass.ecommercechallenge.dto.product.ReadProductDTO;
import com.compass.ecommercechallenge.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    @PostMapping("/createProduct")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> createProduct(@RequestBody CreateProductDTO dto){
        productService.createProduct(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/products")
    public ResponseEntity<List<ReadProductDTO>> readAllProducts() {
        List<ReadProductDTO> products = productService.readAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Optional<ReadProductDTO>> readProductById(@PathVariable UUID id) {
        var product = productService.readProduct(id);
        return ResponseEntity.ok(product);
    }
}
