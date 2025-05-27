package com.compass.ecommercechallenge.service;

import com.compass.ecommercechallenge.dto.product.CreateProductDTO;
import com.compass.ecommercechallenge.dto.product.ReadProductDTO;
import com.compass.ecommercechallenge.entity.Product;
import com.compass.ecommercechallenge.repository.ProductRepository;
import com.compass.ecommercechallenge.utils.FormatPrice;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public void deleteProduct(UUID id){
        var productExists = productRepository.findById(id);
        if(productExists.isPresent()){
            productRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public void updateProduct(UUID id, CreateProductDTO dto){
        var product = productRepository.findById(id);

        if(product.isPresent()){
            product.get().setName(dto.name());
            product.get().setDescription(dto.description());
            product.get().setPrice(dto.price());
            product.get().setQuantity(dto.quantity());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void inactivateProduct(UUID id, boolean status){
        var product = productRepository.findById(id);
        if(product.isPresent()){
            product.get().setIsActive(status);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
