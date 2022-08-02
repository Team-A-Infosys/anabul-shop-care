package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    ResponseEntity<Object> createProduct(Product product, MultipartFile file);

    ResponseEntity<Object> listProducts(Pageable pageable);

    ResponseEntity<Object> updateProduct(Product product, MultipartFile file, UUID id);

    Optional<Product> findById(UUID id);
    
    ResponseEntity<Object> filterProductByName(String name, Pageable pageable);
    
    ResponseEntity<Object> filterProductsByLocation(String location, Pageable pageable);
    
    ResponseEntity<Object> filterProductByPrice(BigDecimal startPrice, BigDecimal endPrice, Pageable pageable);

    ResponseEntity<Object> deleteProduct(UUID id);

    ResponseEntity<Object> updatePublishedStatus(UUID id, Product product);

    ResponseEntity<Object> filterUnpublishedProduct(Pageable pageable);
}