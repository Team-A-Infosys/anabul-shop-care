package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.service.FileStorageService;
import team.kucing.anabulshopcare.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final FileStorageService fileStorageService;

    @Override
    public ResponseEntity<Object> createProduct(Product product, MultipartFile file) {
            String fileName = fileStorageService.storeFile(file);

            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(fileName)
                    .toUriString();

            product.setImageUrl(fileDownloadUri);
            return ResponseEntity.status(HttpStatus.CREATED).body(this.productRepository.save(product));

    }

    @Override
    public ResponseEntity<Object> listProducts(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productRepository.findAll(pageable).toList());
    }

    @Override
    public ResponseEntity<Object> filterProductsByLocation(String location, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByLocation(location, pageable);

        if (getProduct.getTotalPages() == 0){
            throw new ResourceNotFoundException("Sorry, There are no product in " + location + " area...");
        }

        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }

    @Override
    public ResponseEntity<Object> filterProductByPrice(BigDecimal startPrice, BigDecimal endPrice, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByPriceBetween(startPrice, endPrice, pageable);

        if (getProduct.getTotalPages() == 0){
            throw new ResourceNotFoundException("Sorry, There are no product in that price range");
        }

        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }
}