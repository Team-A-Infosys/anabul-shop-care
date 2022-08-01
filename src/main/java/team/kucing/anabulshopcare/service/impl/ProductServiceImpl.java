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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct == null){
            throw new ResourceNotFoundException("Product with ID "+id+" Is Not Found");
        }
        return productRepository.findById(id);
    }

    @Override
    public ResponseEntity<Object> updateProduct(Product product, MultipartFile file, UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional == null){
            throw new ResourceNotFoundException("Product not exist with id"+id);
        }

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        product.setImageUrl(fileDownloadUri);
        return ResponseEntity.status(HttpStatus.OK).body(this.productRepository.save(product));
    }

}