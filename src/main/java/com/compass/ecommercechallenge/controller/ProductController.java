package com.compass.ecommercechallenge.controller;

import com.compass.ecommercechallenge.dto.product.CreateProductDTO;
import com.compass.ecommercechallenge.dto.product.ReadProductDTO;
import com.compass.ecommercechallenge.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Transactional
    @PostMapping("/createProduct")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> createProduct(@Valid @RequestBody CreateProductDTO dto){
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

    @Transactional
    @PostMapping("/deleteProduct/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @Transactional
    @PostMapping("/updateProduct/{id}")
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> updateProduct(@PathVariable UUID id, @RequestBody CreateProductDTO dto){
        productService.updateProduct(id, dto);
        return ResponseEntity.ok().build();
    }
}
