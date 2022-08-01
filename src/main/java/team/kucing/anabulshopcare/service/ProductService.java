package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {
    ResponseEntity<Object> createProduct(Product product, MultipartFile file);

    ResponseEntity<Object> listProducts(Pageable pageable);

    List<Product> getAllProducts();

    ResponseEntity<Object> updateProduct(Product product, MultipartFile file, UUID id);

    Optional<Product> findById(UUID id);
}