package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ResponseEntity<Object> createProduct(Product product, MultipartFile file);

    ResponseEntity<Object> listProducts(Pageable pageable);

    ResponseEntity<Object> filterProductsByLocation(String location, Pageable pageable);
    ResponseEntity<Object> filterProductByPrice(BigDecimal startPrice, BigDecimal endPrice, Pageable pageable);
}