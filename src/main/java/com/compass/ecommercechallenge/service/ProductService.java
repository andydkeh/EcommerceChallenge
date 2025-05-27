package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.dto.product.CreateProductDTO;
import com.compass.ecommercechallenge.dto.product.ReadProductDTO;
import com.compass.ecommercechallenge.entity.Product;
import com.compass.ecommercechallenge.repository.ProductRepository;
import com.compass.ecommercechallenge.utils.FormatPrice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(CreateProductDTO dto){
        var product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setQuantity(dto.quantity());
        productRepository.save(product);
    }

    public List<ReadProductDTO> readAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(product -> new ReadProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        FormatPrice.formatPrice(product.getPrice())
                ))
                .collect(Collectors.toList());
    }

    public Optional<ReadProductDTO> readProduct(UUID id){
        return productRepository.findById(id)
                .map(product -> new ReadProductDTO(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        FormatPrice.formatPrice(product.getPrice())
                ));
    }
}
